package com.baoz.yx.action.system;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXClientHistory;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.compare.BeanCompareUtils;
import com.baoz.yx.tools.compare.beans.ClassInfo;
import com.baoz.yx.tools.compare.beans.CompareObject;
import com.baoz.yx.tools.compare.beans.CompareResult;
import com.baoz.yx.tools.compare.beans.PropertyCompareResult;
import com.baoz.yx.utils.UserUtils;

/**
 * 客户信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/clientQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/client/clientAddForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/client/clientModifyForm.jsp") })
public class ClientAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private ServletRequest request;
	private String[] ids;
	private YXClientCode cc;
//	private YXClientHistory ch;

	private List<YXTypeManage> clientNature; // 客户性质
	private List<YXTypeManage> businessType; // 行业类型
	private List<YXTypeManage> clientArea; // 客户地域
	private List<YXTypeManage> businessArea; // 行业市场
	
	private String nameHidden;
	private String fullnameHidden;
	private String usercodeHidden;
	
	private List<YXClientHistory> listCh;
//    private Boolean showListCh;


	/**
	 * 新增客户
	 */
	public String doDefault() throws Exception {
		logger.info("新增客户");
		// 查询出客户性质//行业类型//客户地域
		
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		return ENTER_SAVE;
	}

	/**
	 * 保存客户信息
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveExployee() throws Exception {
		logger.info("保存");
		Employee user=UserUtils.getUser();
		cc.setIs_active("1");
		cc.setById(user.getId());
		cc.setUpdateBy(new Date());
		service.save(cc);
		logger.info("保存完成");
		return "success";

	}

	/**
	 * 修改客户信息
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String enterUpdate() throws Exception {
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		businessType = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		clientArea = typeManageService.getYXTypeManage(1005L);// 显示所有客户地域
		businessArea = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
//		System.out.print( "--------------idsss"+idss);
		YXClientCode o = (YXClientCode) service
				.uniqueResult("from YXClientCode obj where obj.id='" + id + "'");
		this.cc = o;
		nameHidden=o.getName();
		fullnameHidden=o.getFullName();
		usercodeHidden=o.getUserCode();
//		logger.info(usercodeHidden);
		listCh = service.list("from YXClientHistory obj where obj.clientId= ? order by obj.updateby desc", id);
//		showListCh = listCh.isEmpty();
		return ENTER_UPDATE;

	}

	/**
	 * 执行对客户信息的更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateExp() throws Exception {

		if (cc.getId() != null && !"".equals(cc.getId())) {
			YXClientCode temp = (YXClientCode) service
			.uniqueResult("from YXClientCode obj where obj.id='" + cc.getId() + "'");
			//////////
			ClassInfo classInfo = new ClassInfo(YXClientCode.class);
			classInfo.addPropertyInfo("name", String.class, "简称");
			classInfo.addPropertyInfo("fullName", String.class, "全称");
			classInfo.addPropertyInfo("businessID", String.class, "行业类别");
			classInfo.addPropertyInfo("clientNID", String.class, "客户性质");
			classInfo.addPropertyInfo("billBank", String.class, "开户银行");
			classInfo.addPropertyInfo("taxNumber", String.class, "税号");
			classInfo.addPropertyInfo("billPhone", String.class, "开票电话");
			classInfo.addPropertyInfo("billName", String.class, "开票名称");
			classInfo.addPropertyInfo("isEventUnit", String.class, "是否为项目单位");
			classInfo.addPropertyInfo("userCode", String.class, "ERP编号");
			classInfo.addPropertyInfo("account", String.class, "开户帐号");
			classInfo.addPropertyInfo("billAdd", String.class, "开票地址");
			classInfo.addPropertyInfo("areaID", String.class, "地域");
			classInfo.addPropertyInfo("businessAreaID", String.class, "行业市场");
			CompareResult result = BeanCompareUtils.compareObject(new CompareObject(classInfo, temp),new CompareObject(classInfo, cc));
			Long groupId = null;
			for (PropertyCompareResult presult : result.getChangedPropertyCompareResults()) {
				if(presult.isChanged()){
//					System.out.println(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc()+"Old:"+presult.getSourcePropertyObject().getPropertyValue());
//					System.out.println(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc()+"New:"+presult.getTargetPropertyObject().getPropertyValue());
					if(StringUtils.isBlank((String)presult.getSourcePropertyObject().getPropertyValue())&&StringUtils.isBlank((String)presult.getTargetPropertyObject().getPropertyValue())){
						continue;
					}else{
						Employee userTemp=UserUtils.getUser();
						YXClientHistory ch = new YXClientHistory();
						ch.setById(userTemp.getId());
						ch.setByName(userTemp.getName());
						ch.setUpdateby(new Date());
						ch.setClientId(cc.getId());
						ch.setType(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc());
						if(StringUtils.equals(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc(), "行业类别")){
							YXTypeManage tm1 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1002 and obj.is_active!=0 and obj.typeSmall = ?", presult.getSourcePropertyObject().getPropertyValue());
							if(tm1!=null){
								ch.setOriginal(tm1.getTypeName());
							}						
							YXTypeManage tm2 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1002 and obj.is_active!=0 and obj.typeSmall = ?", presult.getTargetPropertyObject().getPropertyValue());
							if(tm2!=null){
								ch.setPresent(tm2.getTypeName());
							}
							
						}else if(StringUtils.equals(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc(), "客户性质")){
							YXTypeManage tm1 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1001 and obj.is_active!=0 and obj.typeSmall = ?", presult.getSourcePropertyObject().getPropertyValue());
							if(tm1!=null){
								ch.setOriginal(tm1.getTypeName());
							}
							YXTypeManage tm2 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1001 and obj.is_active!=0 and obj.typeSmall = ?", presult.getTargetPropertyObject().getPropertyValue());
							if(tm2!=null){
								ch.setPresent(tm2.getTypeName());
							}
							
						}else if(StringUtils.equals(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc(), "地域")){
							YXTypeManage tm1 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1005 and obj.is_active!=0 and obj.typeSmall = ?", presult.getSourcePropertyObject().getPropertyValue());
							if(tm1!=null){
								ch.setOriginal(tm1.getTypeName());
							}
							
							YXTypeManage tm2 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1005 and obj.is_active!=0 and obj.typeSmall = ?", presult.getTargetPropertyObject().getPropertyValue());
							if(tm2!=null){
								ch.setPresent(tm2.getTypeName());
							}
							
						}else if(StringUtils.equals(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc(), "行业市场")){
							YXTypeManage tm1 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1016 and obj.is_active!=0 and obj.typeSmall = ?", presult.getSourcePropertyObject().getPropertyValue());
							if(tm1!=null){
								ch.setOriginal(tm1.getTypeName());
							}
							
							YXTypeManage tm2 = (YXTypeManage)service.uniqueResult("from YXTypeManage obj where obj.typeBig = 1016 and obj.is_active!=0 and obj.typeSmall = ?", presult.getTargetPropertyObject().getPropertyValue());
							if(tm2!=null){
								ch.setPresent(tm2.getTypeName());
							}
							
						}else if(StringUtils.equals(presult.getSourcePropertyObject().getPropertyInfo().getShortDesc(), "是否为项目单位")){
							
							if(StringUtils.equals((String)presult.getSourcePropertyObject().getPropertyValue(), "1")){
								ch.setOriginal("是");
							} else{ch.setOriginal("否");}
							if(StringUtils.equals((String)presult.getTargetPropertyObject().getPropertyValue(), "1")){
								ch.setPresent("是");
							} else{ch.setPresent("否");}	
							
						}else{
							
							ch.setOriginal((String)presult.getSourcePropertyObject().getPropertyValue());
							ch.setPresent((String)presult.getTargetPropertyObject().getPropertyValue());
							
						}
						service.save(ch);
						if(groupId == null){
							groupId = ch.getId();
						}
						ch.setGroupId(groupId);
						service.update(ch);
					}
					
				}
			}
			//////////
			Employee user=UserUtils.getUser();
			Long byid = user.getId();
			cc.setById(byid);
			cc.setIs_active("1");	
			Date d = new Date();		
			cc.setUpdateBy(d);
			service.update(cc);
			
			return "success";
		} else {
			service.saveOrUpdate(cc);
			return "success";
		}

	}

	// 删除客户信息
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update YXClientCode obj set obj.is_active=0 ";
		int a = systemService.deleteChose(idss, hql);
		if (a > 0) {
			String clientIds[] = idss.split(",");
			// 删除和员工的关联
			for (String clientId : clientIds) {
				service.executeUpdate("delete from YXOEmployeeClient ec where ec.cli.id = ?", Long.parseLong(clientId));
			}
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}
	
	public String isExist(){
		logger.info("isExist");
		YXClientCode Temp=(YXClientCode) service.uniqueResult("from YXClientCode sp where sp.is_active=1 and sp.userCode = ?", cc.getUserCode());

		logger.info(Temp);
		if(Temp!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}
	
	public String isExistName(){
		logger.info("isExistName");
		YXClientCode Temp=(YXClientCode) service.uniqueResult("from YXClientCode sp where sp.is_active=1 and sp.name = ?", cc.getName());

		logger.info(Temp);
		if(Temp!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}
	
	public String isExistFullName(){
		logger.info("isExistFullName");
		YXClientCode Temp=(YXClientCode) service.uniqueResult("from YXClientCode sp where sp.is_active=1 and sp.fullName = ?", cc.getFullName());

		logger.info(Temp);
		if(Temp!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	

	public List<YXTypeManage> getClientNature() {
		return clientNature;
	}

	public void setClientNature(List<YXTypeManage> clientNature) {
		this.clientNature = clientNature;
	}

	public List<YXTypeManage> getBusinessType() {
		return businessType;
	}

	public void setBusinessType(List<YXTypeManage> businessType) {
		this.businessType = businessType;
	}

	public List<YXTypeManage> getClientArea() {
		return clientArea;
	}

	public void setClientArea(List<YXTypeManage> clientArea) {
		this.clientArea = clientArea;
	}

	public YXClientCode getCc() {
		return cc;
	}

	public void setCc(YXClientCode cc) {
		this.cc = cc;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(List<YXTypeManage> businessArea) {
		this.businessArea = businessArea;
	}

	public List<YXClientHistory> getListCh() {
		return listCh;
	}

	public void setListCh(List<YXClientHistory> listCh) {
		this.listCh = listCh;
	}

	public String getNameHidden() {
		return nameHidden;
	}

	public void setNameHidden(String nameHidden) {
		this.nameHidden = nameHidden;
	}

	public String getFullnameHidden() {
		return fullnameHidden;
	}

	public void setFullnameHidden(String fullnameHidden) {
		this.fullnameHidden = fullnameHidden;
	}

	public String getUsercodeHidden() {
		return usercodeHidden;
	}

	public void setUsercodeHidden(String usercodeHidden) {
		this.usercodeHidden = usercodeHidden;
	}

	
	
}
