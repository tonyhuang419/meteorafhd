package com.baoz.yx.action.system;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

/**
 *	根据查询条件显示自有产品管理
 *  
 */

@Result(name = "queryList", value = "/WEB-INF/jsp/manage/system/selfproductManage/selfproductManageList.jsp")

public class SelectSelfProductAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 				systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo 					info;
    private String						projectCode;
    private String						certificateNumber;
    private String                      registerNames;
    private String				registerDate1;
    private String				registerDate2;
    private String              isValidProduction;
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"SelectSelfProductParameter",this,new String[]{"projectCode","certificateNumber","registerNames"
    				,"registerDate1","registerDate2","isValidProduction"});
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append(" from SelfProduction sp where sp.is_active='1'");
//		if (StringUtils.isNotBlank(projectCode)) {
//			str.append(" and lower(sp.pid) like '%").append(StringUtils.lowerCase(projectCode)).append("%'");
//		}
		if (StringUtils.isNotBlank(certificateNumber)){
			str.append(" and lower(sp.certificateNum) like '%").append(StringUtils.lowerCase(certificateNumber)).append("%'");
		}
		if (StringUtils.isNotBlank(registerNames)) {
			str.append(" and lower(sp.registerName) like '%").append(StringUtils.lowerCase(registerNames)).append("%'");
		}
		if (StringUtils.isNotBlank(registerDate1)){
			str.append(" and sp.registerDate >= to_date('"+registerDate1+"','yyyy-MM-dd')");
		}
		if (StringUtils.isNotBlank(registerDate2)){
			str.append(" and sp.registerDate <= to_date('"+registerDate2+"','yyyy-MM-dd')");
		}
		//0/全部；2/是:失效；1/否：有效
		if (StringUtils.equals(isValidProduction, "0")){

		}
		if (StringUtils.equals(isValidProduction, "1") || StringUtils.isBlank(isValidProduction)){
			str.append(" and sp.validateTime >= to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd')");
		}
		if (StringUtils.equals(isValidProduction, "2")){
			str.append(" and sp.validateTime < to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd')");
		}
//		str.append(" order by sp.id");	
		
/*		str.append("select cbs,yc from ContractBeforeSell cbs,YXClientCode yc where cbs.is_active='1' and cbs.customerId=yc.id ");
		if (clientName!=null&&!"".equals(clientName)) {
			str.append(" and cbs.customerId=").append(clientName);
		}
		if (projectName!=null&&!"".equals(projectName)) {
			str.append(" and cbs.projectName= ").append(projectName);
		}
		if (dutyDepartmentId!=null&&!"".equals(dutyDepartmentId)) {
			str.append(" and cbs.dutyDepartmentId= ").append(dutyDepartmentId);
		}
		str.append(" order by cbs.ID");				*/		
		info = queryService.listQueryResult(str.toString(), info);
		return "queryList";
	}


	public IQueryService getQueryService() {
		return queryService;
	}


	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}


	public ISystemService getSystemService() {
		return systemService;
	}


	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}


	public PageInfo getInfo() {
		return info;
	}


	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getRegisterNames() {
		return registerNames;
	}
	
	public void setRegisterNames(String registerNames) {
		this.registerNames = registerNames;
	}
	
	public String getCertificateNumber() {
		return certificateNumber;
	}
	
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getRegisterDate1() {
		return registerDate1;
	}
	public void setRegisterDate1(String registerDate1) {
		this.registerDate1 = registerDate1;
	}
	
	public String getRegisterDate2() {
		return registerDate2;
	}
	public void setRegisterDate2(String registerDate2) {
		this.registerDate2 = registerDate2;
	}
	
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public String getIsValidProduction() {
		return isValidProduction;
	}


	public void setIsValidProduction(String isValidProduction) {
		this.isValidProduction = isValidProduction;
	}


	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

}

