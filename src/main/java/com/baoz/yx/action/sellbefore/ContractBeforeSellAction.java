
package com.baoz.yx.action.sellbefore;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.impl.EventInfoService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 售前合同新增操作
 */
@Results( {	
		@Result(name = "success", type = ServletRedirectResult.class, value = "/sellbefore/contractBeforeSellQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/sellbefore/addContractBeforeSell.jsp")})
public class ContractBeforeSellAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 		systemService;
    private ContractBeforeSell  cbs;
	private ServletRequest		request;
	
	private Long				clientId;
	private Long 				businessTypeId;
	
	private Long				projectEconomyId;
	
	private String 				succSave; 
	
	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventService;
	
	public IEventInfoService getEventService() {
		return eventService;
	}

	public void setEventService(IEventInfoService eventService) {
		this.eventService = eventService;
	}

	public String doDefault() throws Exception {
		logger.info("售前合同管理新增");		
		return ENTER_SAVE;
	}

	/**
	 * 保存售前合同管理
	 */
	public String saveCBS() throws Exception {
		logger.info("保存售前合同");
		cbs.setIs_active("1");
		cbs.setById(UserUtils.getUser().getId());
		cbs.setUpdateBy(new Date());
		cbs.setEmployeeId(UserUtils.getUser().getId());
		eventService.saveContractBeforeSell(cbs);
		logger.info("新增售前合同>>成功");
		
		succSave="0";
		ActionContext.getContext().getSession().put("succSave", succSave);
		return "success";
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
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

	public ContractBeforeSell getCbs() {
		return cbs;
	}

	public void setCbs(ContractBeforeSell cbs) {
		this.cbs = cbs;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public Long getProjectEconomyId() {
		return projectEconomyId;
	}

	public void setProjectEconomyId(Long projectEconomyId) {
		this.projectEconomyId = projectEconomyId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getSuccSave() {
		return succSave;
	}

	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
}
