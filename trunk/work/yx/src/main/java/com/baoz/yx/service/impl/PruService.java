package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.utils.UserUtils;

@Service("purService")
@Transactional
public class PruService implements IPurService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;
	@Autowired
	@Qualifier("queryService")
	private IQueryService service;
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService codeGenerateService;

	protected Log logger = LogFactory.getLog(this.getClass());

	public List queryVerityPurchase(List list) {
		// TODO Auto-generated method stub
		return null;
	}

	public int updateState(StringBuffer sb) {
		int a = 0;
		a = commonDao
		.executeUpdate("update ApplyMessage obj set obj.affirmState=0 where obj.id in("
				+ sb + "0)");
		return a;
	}

	/**
	 * 查询正式合同的所有信息
	 * @return 
	 */
	public List<Object> contractList(List list) {
		List<ContractMainInfo> conList = list;
		List contractMainInfoList = new ArrayList();
		if (conList != null) {
			for (ContractMainInfo cmi : conList) {
				Object[] temp = new Object[3];
				temp[0] = cmi;
				String oql = "from YXClientCode yxcc where id="
					+ cmi.getConCustomer();
				YXClientCode yxcc = (YXClientCode) commonDao.uniqueResult(oql
						.toString());
				String oql1 = "from YXTypeManage yxtm where yxtm.typeSmall='"
					+ cmi.getMainItemDept() + "'";
				YXTypeManage yxtm = (YXTypeManage) commonDao.uniqueResult(oql1
						.toString());
				if (yxcc != null)
					temp[1] = yxcc.getName();
				if (yxtm != null)
					temp[2] = yxtm.getTypeName();
				contractMainInfoList.add(temp);
				temp = null;
			}
		}
		return contractMainInfoList;
	}

	/**
	 * 查询条件拼接合同查询的SQL语句
	 * @return 
	 */
	public String oql(String conName, String conId, String mainItemDept, String partyAProId ,String applyType) {
		StringBuffer str = new StringBuffer();
		if ("1".equals(applyType)){
			str.append("select p , c ," +
					"(select sum(i.conItemAmountWithTax) from ContractItemInfo i  where i.contractItemMaininfo = p.conItemMinfoSid)," +
					" (select yc.name from YXClientCode yc where yc.id = c.conCustomer) , p.conItemMinfoSid ");
		}else{
			str.append("select 0 as p , c ");
		}
		return str.toString();
		
	}
	/**
	 * 查询条件拼接合同查询的SQL语句
	 * @return 
	 */
	public String oqlfromWhere(String conName, String conId, String mainItemDept,
			String partyAProId ,String applyType) {
		StringBuffer str = new StringBuffer();
		if ("1".equals(applyType)){
			str.append(" from ContractItemMaininfo p, ContractMainInfo c where p.contractMainInfo=c.conMainInfoSid and c.saleMan="+UserUtils.getUser().getId());
			str.append(" and p.conItemId is not null");
			str.append(" and c.conState in (4,5,6,7) ");
		}else{
			str.append(" from ContractMainInfo c where c.saleMan="+UserUtils.getUser().getId());
			str.append(" and c.conState in (4,5,6,7) ");
		}
		if (StringUtils.isNotEmpty(conName)){
			str.append(" and c.conName like '%").append(conName).append("%'");
		}
		if (StringUtils.isNotEmpty(conId)){
			str.append(" and c.conId like '%").append(conId).append("%'");
		}
		if ("1".equals(applyType)){
			if (StringUtils.isNotEmpty(mainItemDept) && !"-1".equals(mainItemDept)){
				str.append(" and p.itemResDept = '").append(mainItemDept).append("'");
			}
			if (StringUtils.isNotEmpty(partyAProId)){
				str.append(" and p.conItemId like '%").append(partyAProId).append("%'");
			}
		}
		if(StringUtils.isNotBlank(applyType)){
			str.append(" and c.ContractType = '").append(applyType).append("'");
		}
		
		return str.toString();
		
	}
	/**
	 * 查询条件拼接客户查询的SQL语句
	 * @return 
	 */
	public String oql1(String clientName) {
		StringBuffer str = new StringBuffer();
		str
		.append("from YXClientCode cc where cc.id not in(0) and cc.is_active!=0");
		if (StringUtils.isNotEmpty(clientName))
			str.append(" and name like'%").append(clientName).append("%'");

		return str.toString();
	}

	public ApplyMessage fillContractAM(ApplyMessage am, String idstate1,
			String idstate2, String state) {
		am.setApplyState("1");//设置申购状态1——已签
		am.setOutState("0");// 设置出库状态为0
		am.setApplyDate(new Date());// 设置申购日期
		am.setInformState(this.setIS(idstate1, idstate2));// 设置通知状态
		am.setAffirmState(this.setAS(state));//判断确认状态
		am.setIs_active("1");//初始有效性
		am.setSellmanId(UserUtils.getUser().getId());//销售员号		
		return am;
	}

	/**
	 * 转换通知状态0-不通知 1-到齐通知 2-分批通知 3-全部
	 * @param idstate1
	 * @param idstate2
	 * @return
	 */
	public String setIS(String idstate1,String idstate2){
		if (!StringUtils.isNotEmpty(idstate1))
			idstate1 = "0";
		if (!StringUtils.isNotEmpty(idstate2))
			idstate2 = "0";
		int i = Integer.parseInt(idstate1) + Integer.parseInt(idstate2);
		String temp = String.valueOf(i);
		return temp.toString();
	}
	/**
	 * 判断确认状态
	 * @param state
	 * @return
	 */
	public String setAS(String state){
		String temp="";
		if("draft".equals(state))  //草稿
			temp="0";
		if("wait".equals(state))	//待确认
			temp="1";
		if("through".equals(state)) //确认通过
			temp="2";
		if("back".equals(state)) //确认退回
			temp="3";
		return temp;
	}

	/**
	 * 根据页面的选择和填写的内容充实ApplyMessage类
	 */
	public ApplyMessage fillAM(ApplyMessage am, String idstate1, String idstate2, String projectName1, String projectName2, Long cNameId1, Long cNameId2, String state) {
		am.setOutState("0");// 设置出库状态为0
		am.setApplyDate(new Date());// 设置申购日期
		am.setInformState(this.setIS(idstate1, idstate2));// 设置通知状态
		//根据申购状态设置
		if("0".equals(am.getApplyState())){
			am.setMainId(null);
			am.setEventId(null);
			am.setCustomerId(cNameId1);
		}else{
			am.setEstimateDate(null);
			am.setCustomerId(cNameId2);
		}
		//根据申购类型设置
//		if("1".equals(am.getApplyType())){
//			am.setProjectName(projectName1);
//			//工程类项目名称
//		}else{
//			am.setProjectName(projectName2);
//			//am.setAssignmentId(null);
////			am.setBugget(null);
//		}
		am.setAffirmState(this.setAS(state));//判断确认状态
		am.setIs_active("1");//初始有效性	
		am.setSellmanId(UserUtils.getUser().getId());//销售员号
		return am;
	}
	
	/**
	 * 根据页面的修改和填写的内容充实ApplyMessage类
	 */
	public ApplyMessage updateFillPurchase(ApplyMessage am, ApplyMessage am1, String idstate1, String idstate2, String projectName1, String projectName2, Long cNameId1, Long cNameId2, String state){
		am.setInformState(this.setIS(idstate1, idstate2));// 设置通知状态
		am.setAffirmState(this.setAS(state));
		//根据申购状态设置
		if("0".equals(am.getApplyState())){
			am.setCustomerId(cNameId1);
		}else{
			am.setCustomerId(cNameId2);
		}
		//根据申购类型设置
//		if("0".equals(am.getApplyType())){
//			am.setProjectName(projectName1);
//		}else{
//			am.setProjectName(projectName2);
//		}
		am.setIs_active(am1.getIs_active());
		am.setSellmanId(am1.getSellmanId());
		am.setApplyId(am1.getApplyId());
		am.setApplyDate(am1.getApplyDate());
		am.setOutState(am1.getOutState());
		this.recordUpdate(am);
		return am;
	}

	/**
	 * 查询申购的SQL语句
	 * @return
	 */
	public String applyMessageOql() {
		StringBuffer temp=new StringBuffer();
		temp.append("from ApplyMessage am where am.is_active=1 ");
		temp.append(" order by am.affirmState");
		return temp.toString();
	}

	/**
	 * 查询审核申购的SQL语句
	 * @return
	 */
	public String applyMessageOql1(){
		StringBuffer temp=new StringBuffer();
		temp.append("from ApplyMessage am where am.is_active!=0");
		temp.append("and am.affirmState=1");
		return temp.toString();
	}

	/**
	 * 根据id查询出员工
	 * @param id
	 * @return
	 */
	public Employee getEmployee(Long id) {
		Employee employee=(Employee) commonDao.load(Employee.class, id);
		return employee;
	}

	/**
	 * 根据合同号系统号查询出合同
	 * @param id
	 * @return
	 */
	public ContractMainInfo getContractMainInfo(Long id){
		ContractMainInfo contractMainInfo=(ContractMainInfo) commonDao.load(ContractMainInfo.class, id);
		return contractMainInfo;
	}

	/**
	 * 根据客户系统号查出客户
	 * @param id
	 * @return
	 */
	public YXClientCode getYXClientCode(Long id){
		YXClientCode client=(YXClientCode) commonDao.load(YXClientCode.class, id);
		return client;
	}

	/**
	 * 查询用户，合同
	 */
	public void search(){
		Employee employee=(Employee) commonDao.uniqueResult("from Employee");
		ContractMainInfo contractMainInfo=(ContractMainInfo) commonDao.uniqueResult("from ContractMainInfo c ");
		YXClientCode client=(YXClientCode) commonDao.uniqueResult("from YXClientCode yxc ");
	}

	/**
	 * 删除申购单
	 * @param ids（申购单id）
	 */
	public void delPurchase(String amids){
		String temp[]=amids.split(", ");
		for(int i=0;i<temp.length;i++){
			Long idTemp=Long.valueOf(temp[i]);
			ApplyMessage am=(ApplyMessage) commonDao.load(ApplyMessage.class, idTemp);
			am.setIs_active("0");
			this.recordUpdate(am);
			commonDao.update(am);
		}
	}

	/**
	 * 关联合同
	 * @param amids（申购单id）
	 * @param ids（合同id）
	 */
	public void linkCountract(String amids, String ids) {
		logger.info(amids+"....");
		logger.info("申购单id"+amids);
		ContractMainInfo contractMainInfo=(ContractMainInfo) commonDao.load(ContractMainInfo.class, Long.valueOf(ids));
		Long idTemp=Long.valueOf(amids);
		ApplyMessage am=(ApplyMessage) commonDao.load(ApplyMessage.class, idTemp);
		am.setApplyState("1");
		am.setMainId(contractMainInfo.getConMainInfoSid());
		am.setEventId(contractMainInfo.getPartyAProId());
		am = this.recordUpdate(am);
		commonDao.update(am);
	}

	/**
	 * 确认提交申购单
	 * @param amids（申购单id）
	 */
	public void submitPurchase(String amids) {
		String temp[]=amids.split(", ");
		for(int i=0;i<temp.length;i++){
			Long idTemp=Long.valueOf(temp[i]);
			ApplyMessage am=(ApplyMessage) commonDao.load(ApplyMessage.class, idTemp);
			am.setAffirmState("1");
			this.recordUpdate(am);
			commonDao.update(am);
		}
	}

	/**
	 * 记录修改时间和修改人ID
	 * @param am
	 * @return
	 */
	public ApplyMessage recordUpdate(ApplyMessage am){
		am.setById(UserUtils.getUser().getId());
		am.setUpdateBy(new Date());
		return am;
	}

	/**
	 * 确认通过、确认退回根据传入的i判断（0-确认退回 1-确认通过）
	 * @param i
	 */
	public void checkConfirm(int j, String amids , String returnReason) {
		String temp[]=amids.split(", ");
		for(int i=0;i<temp.length;i++){
			Long idTemp=Long.valueOf(temp[i]);
			ApplyMessage am=(ApplyMessage) commonDao.load(ApplyMessage.class, idTemp);
			if(j==1){
				am.setAffirmState("2");
				am.setApplyId(codeGenerateService.generateApplyCode(am.getApplyType()));//申购单号
			}else{
				am.setReturnReason(returnReason);
				am.setReturnDate(new Date());
				am.setAffirmState("3");
			}
			this.recordUpdate(am);
			commonDao.update(am);
		}
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IQueryService getService() {
		return service;
	}

	public void setService(IQueryService service) {
		this.service = service;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}






}
