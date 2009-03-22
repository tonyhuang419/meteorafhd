package com.baoz.yx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.ContractBeforeSellHistory;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.ImpAndCbsRelation;
import com.baoz.yx.entity.ImportantProject;
import com.baoz.yx.entity.ImportantProjectHistroy;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.compare.CompareBeanTools;
import com.baoz.yx.tools.compare.beans.CompareContext;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;

@Service("beforeSellContractService")
@Transactional
public class BeforeSellContractService implements IBeforeSellContractService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;

	/**
	 * 获取项目跟踪结果List
	 */
	public List<YXTypeManage>  loadTrackList( String projectStateSelect ){
		List<YXTypeManage>      itemTrackList;						
		List<YXTypeManage>      tempItemTrackList;
		itemTrackList = typeManageService.getYXTypeManage(1026L);
		tempItemTrackList = typeManageService.getYXTypeManage(1026L);
		for( YXTypeManage type : itemTrackList ){
			if( ! type.getInfo().toLowerCase().equals(projectStateSelect.toLowerCase())){
				tempItemTrackList.remove(type);
			}
		}
		return tempItemTrackList;
	}


	/**
	 *  获取重点工程项目历史
	 */
	@SuppressWarnings("unchecked")
	public   List<Object[]>  doGetImpModHistory( Long impId ){
		String hql = " select imph , e.name  from ImportantProjectHistroy imph , Employee e " +
		"  where  e.id = imph.byId    and  imph.impId = ? order by imph.updateBy desc ";
		return  (List<Object[]>)commonDao.list( hql , impId );	
	}

	/**
	 * 记录重点项目合同修改历史，返回是否存在修改
	 */
	synchronized public boolean recordIMPChange(ImportantProject imp){
		boolean mod = false;
		CompareBeanTools cbt = new CompareBeanTools();
		ImportantProject impOrg;
		impOrg = (ImportantProject)commonDao.load(ImportantProject.class, imp.getId());
		List<CompareContext> ccList = cbt.compare(impOrg, imp, new String[]{"serialVersionUID","id","createTime","createEmployeeID" }); //"createTime","createEmployeeID"
		Long groupId = -1L;
		this.processFieldAndPropertyForIMP(ccList);
		if ( ccList!=null && ccList.size() > 0){
			mod = true;
			for( CompareContext c : ccList  ){
				ImportantProjectHistroy imph = new ImportantProjectHistroy();
				imph.setImpId(imp.getId());
				imph.setIs_active("1");
				imph.setUpdateBy(new Date());
				imph.setById(UserUtils.getUser().getId());		
				imph.setType(c.getFieldName());
				imph.setOriginal(c.getSrcContext());
				imph.setPresent(c.getTargetContext());
				commonDao.save(imph);
				if( groupId.equals(-1L) ){
					groupId = imph.getId();
				}
				imph.setGroupId(groupId);
				commonDao.update(imph);
			}	
		}
		else{
			mod = false;
		}
		return mod;
	}


	private List<CompareContext>  processFieldAndPropertyForIMP(List<CompareContext> ccList  ){
		Map<String,String> cBSNVMap  = this.getIMPNVMap();
		String fieldName;
		String srcContext;
		String tarContext;
		for( CompareContext cc : ccList  ){
			fieldName = cc.getFieldName();
			srcContext = cc.getSrcContext();
			tarContext = cc.getTargetContext();

			if ( null == fieldName ){  fieldName = ""; }
			else if(  "customerId".equals(fieldName)){
				if( StringUtils.isBlank(srcContext)){
					srcContext="";
				}
				else{
					srcContext = (String)  commonDao.uniqueResult("  select c.name from  YXClientCode c where c.id = ? " , Long.valueOf(srcContext) ); 
				}
				if(StringUtils.isBlank(tarContext)){
					tarContext="";
				}
				else{
					tarContext = (String)  commonDao.uniqueResult("  select c.name from  YXClientCode c where c.id = ? " , Long.valueOf(tarContext) );   
				}
			}
			fieldName = cBSNVMap.get(fieldName);
			cc.setFieldName(fieldName);
			cc.setSrcContext(srcContext);
			cc.setTargetContext(tarContext);
		}
		return ccList;
	}


	private Map<String,String> getIMPNVMap(){	
		Map<String,String> cbsNV = new HashMap<String,String>();
		cbsNV.put("projectNum","工程编号");
		cbsNV.put("customerId","客户名称");
		cbsNV.put("projectName","工程名称");
		return cbsNV;
	}


	/**
	 * 记录售前合同修改历史，返回是否存在修改
	 */
	synchronized public boolean recordCBSChange(ContractBeforeSell cbs , Long impId) {
		boolean mod = false;
		CompareBeanTools cbt = new CompareBeanTools();
		ContractBeforeSell cbsOrg;
		cbsOrg = (ContractBeforeSell)commonDao.load(ContractBeforeSell.class, cbs.getID());
		List<CompareContext> ccList = cbt.compare(cbsOrg, cbs, new String[]{"serialVersionUID","id","timeOfVary","conState"});
		Long groupId = -1L;
		this.processFieldAndPropertyForCBS(ccList);
		if ( ccList!=null && ccList.size() > 0){
			mod = true;
			for( CompareContext c : ccList  ){
				ContractBeforeSellHistory cbsh = new ContractBeforeSellHistory();
				cbsh.setCbsId(cbs.getID());
				cbsh.setIs_active("1");
				cbsh.setUpdateBy(new Date());
				cbsh.setById(UserUtils.getUser().getId());		
				cbsh.setType(c.getFieldName());
				cbsh.setOriginal(c.getSrcContext());
				cbsh.setPresent(c.getTargetContext());
				commonDao.save(cbsh);
				if( groupId.equals(-1L) ){
					groupId = cbsh.getId();
				}
				cbsh.setGroupId(groupId);
				commonDao.update(cbsh);
			}	
		}
		else{
			mod = false;
		}
		mod = this.compareCBSImp(cbsOrg, impId , groupId );
		return mod;
	}

	/**
	 *  比较售前合同的重点工程项目
	 */
	private boolean compareCBSImp(ContractBeforeSell cbsOrg , Long importantProjectId , Long groupId){
		boolean sign = false;
		Object[]  icrArray = (Object[])commonDao.uniqueResult("  select  ip.id  , ip.projectName " +
				" from ImpAndCbsRelation icr ,ImportantProject ip  where icr.impID = ip.id   and  icr.cbsId = ?  ", cbsOrg.getID() );
		if( icrArray!=null ){
			if(	icrArray[0].equals(importantProjectId)){
				sign =  false;
			}
			else{
				sign =  true;
			}
		}
		else{
			if(importantProjectId!=null){
				sign =  true;
			}
		}

		if(sign){
			ContractBeforeSellHistory cbsh = new ContractBeforeSellHistory();
			cbsh.setCbsId(cbsOrg.getID());
			cbsh.setIs_active("1");
			cbsh.setUpdateBy(new Date());
			cbsh.setById(UserUtils.getUser().getId());		
			cbsh.setType("重点工程项目");
			String tar;
			if(importantProjectId!=null){
				tar  = (String)commonDao.uniqueResult("  select  ip.projectName   from ImportantProject ip where ip.id = ?", importantProjectId);
			}
			else{
				tar = "";
			}
			if(icrArray==null){
				cbsh.setOriginal("");
			}
			else{
				cbsh.setOriginal(icrArray[1].toString());
			}
			cbsh.setPresent(tar);
			if( groupId.equals(-1L) ){
				groupId = cbsh.getId();
			}
			cbsh.setGroupId(groupId);
			commonDao.save(cbsh);
		}
		return sign;
	}



	/**
	 * 获取员工对应客户List
	 */
	@SuppressWarnings("unchecked")
	public List<YXOEmployeeClient>  getEmployeeClientList( ){
		return commonDao.list(" from YXOEmployeeClient yec where yec.exp.id = " + UserUtils.getUser().getId() );
	}

	/**
	 *  保存重点工程项目
	 */
	public void  saveIMP(  ImportantProject  imp){
		Employee user = UserUtils.getUser();
		imp.setCreateEmployeeID(user.getId());
		imp.setCreateTime(new Date());
		imp.setById(user.getId());
		imp.setUpdateBy(new Date());
		imp.setIs_active("1");
		commonDao.save(imp);
	}

	/**
	 *  修改重点工程项目
	 */
	public void  modIMP(   ImportantProject  imp ){
		Employee user = UserUtils.getUser();
		imp.setById(user.getId());
		imp.setUpdateBy(new Date());
		imp.setIs_active("1");
		commonDao.update(imp);
	}

	/**
	 *  searchIMP
	 */
	public PageInfo  searchIMP(   Long  customerId  , String 	projectNum  ,  String projectName  ,   PageInfo info){

		StringBuffer  hql =   new StringBuffer("  select   imp , yxc.fullName,emp.name  from ImportantProject imp,YXClientCode yxc,Employee emp" +
		"   where yxc.id = imp.customerId  and    emp.id =imp.byId  ");

		if( customerId!=null && !customerId.equals("")){
			hql.append( " and imp.customerId = " + customerId );
		}
		if( StringUtils.isNotBlank(projectNum)){
			projectNum =projectNum.trim();
			hql.append( " and imp.projectNum like '%" + projectNum +"%'" );
		}
		if(  StringUtils.isNotBlank(projectName)){
			projectName =projectName.trim();
			hql.append( " and imp.projectName like  '%" + projectName +"%'");
		}

		hql.append( "   order by   imp.id desc  ");
		String hqls = hql.toString();
		info = queryService.listQueryResult( SqlUtils.getCountSql(hqls),   hqls , info );
		return info;
	}

	/**
	 * 处理员工对应客户List
	 * 如果该重点工程项目客户不在该该销售员客户列表中，增加
	 */
	public List<YXOEmployeeClient>  processEmployeeClientList(Long cid ){
		List<YXOEmployeeClient>  yxClientCodeList = this.getEmployeeClientList();
		int market = 0;
		for (  YXOEmployeeClient yxc :  yxClientCodeList ){
			if ( yxc.getCli().getId().equals(cid)){
				market = 1;
				break;
			}
		}
		if( 0 == market){
			YXClientCode cliExpList = (YXClientCode) commonDao.load(YXClientCode.class, cid);
			YXOEmployeeClient yxoEC = new YXOEmployeeClient();
			yxoEC.setCli(cliExpList);
			yxClientCodeList.add(yxoEC);
		}
		return yxClientCodeList;
	}


	/**
	 * 保存重点工程和售前关联
	 */
	public void saveImpAndCBSRelation( Long cbsSid , Long impSid  ){
		ImpAndCbsRelation icr = (ImpAndCbsRelation)commonDao.uniqueResult( " from  ImpAndCbsRelation icr where icr.cbsId = ?  ", cbsSid);
		if(impSid!=null){
			if(icr!=null ){
				if(  ! icr.getImpID().equals(impSid) ){
					icr.setImpID(impSid);
					commonDao.update(icr);
				}
			}
			else{
				icr = new ImpAndCbsRelation();
				icr.setCbsId( cbsSid);
				icr.setImpID(impSid);
				commonDao.save(icr);
			}
		}
		else{
			if(icr!=null){
				commonDao.delete(icr);
			}
		}
	}

	/**
	 * 删除售前合同 
	 */
	@SuppressWarnings("unchecked")
	public void delCBS(Long ids[]){
		for(Long id : ids){
			ContractBeforeSell cbs = (ContractBeforeSell)commonDao.load(ContractBeforeSell.class, id);
			commonDao.delete(cbs);
			List<ContractBeforeSellHistory> cbshList = commonDao.list("   from ContractBeforeSellHistory c where   c.cbsId = ?  ", id);	
			for( ContractBeforeSellHistory  cbsh :cbshList){
				commonDao.delete(cbsh);
			}
			List<ImpAndCbsRelation> icrList = commonDao.list("  from ImpAndCbsRelation icr   where icr.cbsId = ? " , id);
			for( ImpAndCbsRelation  icr :icrList ){
				commonDao.delete(icr);
			}
		}
	}

	private List<CompareContext>  processFieldAndPropertyForCBS(List<CompareContext> ccList  ){
		Map<String,String> cBSNVMap  = this.getCBSNVMap();
		String fieldName;
		String srcContext;
		String tarContext;
		for( CompareContext cc : ccList  ){
			fieldName = cc.getFieldName();
			srcContext = cc.getSrcContext();
			tarContext = cc.getTargetContext();

			if ( null == fieldName ){  fieldName = ""; }
			else if(  "customerId".equals(fieldName)){
				if( StringUtils.isBlank(srcContext)){
					srcContext="";
				}
				else{
					srcContext = (String)  commonDao.uniqueResult("  select c.name from  YXClientCode c where c.id = ? " , Long.valueOf(srcContext) ); 
				}
				if(StringUtils.isBlank(tarContext)){
					tarContext="";
				}
				else{
					tarContext = (String)  commonDao.uniqueResult("  select c.name from  YXClientCode c where c.id = ? " , Long.valueOf(tarContext) );   
				}
			}

			else if(  "linkManId".equals(fieldName)){
				if( StringUtils.isBlank(srcContext)){
					srcContext="";
				}
				else{
					srcContext = (String)  commonDao.uniqueResult("  select y.name from YXLinkMan y where y.id = ? " , Long.valueOf(srcContext) ); 
				}
				if(StringUtils.isBlank(tarContext)){
					tarContext="";
				}
				else{
					tarContext = (String)  commonDao.uniqueResult("  select y.name from YXLinkMan y where y.id = ? " , Long.valueOf(tarContext) );   
				}
			}

			else if(  "customerProjectTypeId".equals(fieldName)){
				srcContext = this.getTypeName(1007L,srcContext);
				tarContext = this.getTypeName(1007L,tarContext);
			}
			else if(  "projectStateId".equals(fieldName)){
				srcContext = this.getTypeName(1006L,srcContext);
				tarContext = this.getTypeName(1006L,tarContext);
			}
			else if(  "dutyDepartmentId".equals(fieldName)){
				srcContext = this.getTypeName(1018L,srcContext);
				tarContext = this.getTypeName(1018L,tarContext);
			}
			else if(  "projectStateFollowId".equals(fieldName)){
				srcContext = this.getTypeName(1009L,srcContext);
				tarContext = this.getTypeName(1009L,tarContext);
			}
			else if(  "itemTraceResult".equals(fieldName)){
				srcContext = this.getTypeName(1026L,srcContext);
				tarContext = this.getTypeName(1026L,tarContext);
			}
			else if(  "projectStateSelect".equals(fieldName)){
				if(  StringUtils.isBlank(srcContext) ){
					srcContext="";
				}
				else if("1".equals(srcContext)){
					srcContext = "on";
				}
				else{
					srcContext = "off";
				}				
				if( StringUtils.isBlank(tarContext) ){
					tarContext="";
				}
				if("1".equals(tarContext)){
					tarContext = "on";
				}
				else{
					tarContext = "off";
				}
			}

			fieldName = cBSNVMap.get(fieldName);
			cc.setFieldName(fieldName);
			cc.setSrcContext(srcContext);
			cc.setTargetContext(tarContext);
		}
		return ccList;
	}


	private String getTypeName(Long typeBig, String typeSmall){
		if( StringUtils.isBlank( typeSmall )){
			return "";
		}
		else{
			YXTypeManage yxtm =(YXTypeManage) typeManageService.getYXTypeManage(typeBig, typeSmall );
			if( yxtm!=null ){
				return yxtm .getTypeName();
			}
			else{
				return "";
			}
		}
	}


	private Map<String,String> getCBSNVMap(){	
		Map<String,String> cbsNV = new HashMap<String,String>();
		cbsNV.put("sellBeforeNum","编号");
		cbsNV.put("customerId","客户名称");
		cbsNV.put("customerProjectTypeId","客户项目类型");
		cbsNV.put("projectName","项目名称");
		cbsNV.put("projectStateId","项目进度");
		cbsNV.put("projectStateFollowId","项目跟踪状态");
		cbsNV.put("projectSum","预计金额");
		cbsNV.put("bidSum","投标（报价）金额");
		cbsNV.put("ownSum","中标（合同）金额");
		cbsNV.put("dutyDepartmentId","工程责任部门");
		cbsNV.put("linkManId","客户联系人及部门");
		cbsNV.put("projectManId","项目负责人");
		cbsNV.put("mainProjectContent","项目主要内容");
		cbsNV.put("descProjectFollow","项目跟踪情况说明");
		cbsNV.put("projectDate","项目起始跟踪日期");
		cbsNV.put("bidDate","投标（报价）日期");
		cbsNV.put("estimateSignDate","预计合同签订日期");
		cbsNV.put("projectStateSelect","项目跟踪标记");
		cbsNV.put("competeInfo","参与竞争厂家情况概述");
		cbsNV.put("estimateProjectDate","项目（工程）计划投运日期");
		cbsNV.put("ownFactory","中标厂商");
		cbsNV.put("remark","备注");
		cbsNV.put("projectEconomyId","工程经济代码");
		cbsNV.put("projectNameX","工程名称");
		cbsNV.put("projectSummary","工程概况");
		cbsNV.put("customerDepartment","客户联系人部门");
		cbsNV.put("customerLinkManPhone","客户联系电话");
		cbsNV.put("itemTraceResultReason","项目跟踪结果原因");
		cbsNV.put("itemTraceResult","项目跟踪结果");
		cbsNV.put("itemRespeoplePhone","项目负责人电话");	
		cbsNV.put("importantItem","重点项目");	
		cbsNV.put("partyAProId","甲方项目工程编号");	
		return cbsNV;
	}

}
