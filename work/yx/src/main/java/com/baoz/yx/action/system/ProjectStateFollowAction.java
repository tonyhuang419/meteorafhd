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
import com.baoz.yx.entity.ProjectStateFollow;
import com.baoz.yx.entity.ZoneCode;
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/projectStateFollowQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/projectStateFollow/addProjectStateFollow.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/projectStateFollow/editProjectStateFollow.jsp") })
public class ProjectStateFollowAction extends DispatchAction implements
		ServletRequestAware {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private ServletRequest request;
	private ProjectStateFollow psf;
	
	public String doDefault() throws Exception {
		logger.info("新增地域");

		return ENTER_SAVE;
	}

	public String saveProjectStateFollow() throws Exception {
		logger.info("保存");

		service.save(psf);
		return "success";

	}

	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		ProjectStateFollow o = (ProjectStateFollow) service
				.uniqueResult("from ProjectStateFollow obj where obj.id='" + id + "'");
		this.psf = o;

		return "enterUpdate";
	}
	public String updateProjectStateFollow() throws Exception {
		psf.setType(psf.getType());
		Long id = 1l;
		Date d = new Date();
		psf.setModifyTime(d);
		psf.setId(id);	
		service.update(psf);
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

	public ProjectStateFollow getPsf() {
		return psf;
	}

	public void setPsf(ProjectStateFollow psf) {
		this.psf = psf;
	}
}
