
package com.baoz.yx.action.programEconomy;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;


/**
 * 简单的查询返回一个List供<s:select/>用，
 * 或其他的简单用途
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/programEconomy/programEconomyVerifyQuery.action"),
		@Result(name = "success", type = ServletRedirectResult.class, value = "/programEconomy/programEconomyManageQuery.action"),
		@Result(name = "pinfo", value = "/WEB-INF/jsp/programEconomy/projectInfo.jsp"),
		@Result(name = "showpurs", value = "/WEB-INF/jsp/purchase/showpurs.jsp"),
		@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
		@Result(name = "economyInfo", value = "/WEB-INF/jsp/programEconomy/projectEconomyInfo.jsp"),
		@Result(name = "economyView", value = "/WEB-INF/jsp/programEconomy/economyView.jsp"),
		@Result(name = "showClientsList", value = "/WEB-INF/jsp/programEconomy/showClients.jsp"),
		@Result(name = "edit", value = "/WEB-INF/jsp/programEconomy/economyEdit.jsp")})
public class TypeOptionList extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
    @Autowired
    @Qualifier("yXTypeManageService")
	private IYXTypeManageService typeManageService;

	public String doDefault() throws Exception {
		logger.info("");

		return "";
	}
   
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
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

	
}

