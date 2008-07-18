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
import com.baoz.yx.entity.TicketType;
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/ticketTypeQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/ticketType/addTicketType.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/ticketType/editTicketType.jsp") })
public class TicketTypeAction extends DispatchAction implements
		ServletRequestAware {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private ServletRequest request;
	private TicketType tt;
	
	public String doDefault() throws Exception {
		logger.info("新增开票类型");
		
		return ENTER_SAVE;
	}

	public String saveTicketType() throws Exception {
		logger.info("保存");
		service.save(tt);
		return "success";

	}

	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		TicketType o = (TicketType) service
				.uniqueResult("from TicketType obj where obj.id='" + id + "'");
		this.tt = o;
		return "enterUpdate";

	}

	public String updateTicketType() throws Exception {
		Long id = 1l;
		tt.setId(id);
		Date d = new Date();
		service.update(tt);
		return "success";

	}

	public String delGroup() {

		return "success";
	}
	
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
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
	public ServletRequest getRequest() {
		return request;
	}
	public void setRequest(ServletRequest request) {
		this.request = request;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}

	public TicketType getTt() {
		return tt;
	}

	public void setTt(TicketType tt) {
		this.tt = tt;
	}

}
