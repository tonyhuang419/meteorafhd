package com.baoz.yx.action.system;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.CustomerProjectType;
@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/selfProductionQuery.action" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/customerProjectType/addCustomerProjectType.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/customerProjectType/editCustomerProjectType.jsp")})
public class CustomerProjectTypeAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private ServletRequest request;
	private CustomerProjectType cpt;

	@Override
	public String doDefault() throws Exception {
		logger.info("新增客户项目类型");
		return "enterSave";	
		} 
	
	public String saveCustomerProjectType() throws Exception{
		this.logger.info("新增客户项目类型");
		service.save(cpt);
		return "success";
	}
	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		CustomerProjectType o = (CustomerProjectType) service
				.uniqueResult("from CustomerProjectType obj where obj.id='" + id + "'");
		this.cpt = o;

		return "enterUpdate";

	}
	
	public String updateCustomerProjectType() throws Exception {
		Long id = 2l;
		cpt.setId(id);
		Date d = new Date();
		cpt.setModifyTime(d);
		return "success";

	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
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

	public CustomerProjectType getCpt() {
		return cpt;
	}

	public void setCpt(CustomerProjectType cpt) {
		this.cpt = cpt;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		
	}
}
