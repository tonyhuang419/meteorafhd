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
import com.baoz.yx.entity.ProjectState;
import com.baoz.yx.entity.ZoneCode;
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/projectStateQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/projectState/addProjectState.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/projectState/editProjectState.jsp") })
public class ProjectStateAction extends DispatchAction implements
		ServletRequestAware {

		@Autowired
		@Qualifier("commonService")
		private ICommonService service;
		@Qualifier("queryService")
		private IQueryService queryService;
		private PageInfo info;
		private ServletRequest request;
		private ProjectState ps;
		
		public String doDefault() throws Exception {
			logger.info("新增项目状态");

			return ENTER_SAVE;
		}

		public String saveProjectState() throws Exception {
			logger.info("保存");

			service.save(ps);
			return "success";

		}

		public String enterUpdate() throws Exception {
			String idss = request.getParameter("idsss");
			Long id = Long.valueOf(idss);
			ProjectState o = (ProjectState) service
					.uniqueResult("from ProjectState obj where obj.id='" + id + "'");
			this.ps = o;

			return "enterUpdate";

		}

		public String updateProjectState() throws Exception {
			Long id = 1l;
			Date d = new Date();
			ps.setModifyTime(d);
			ps.setId(id);	
			service.update(ps);
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
		public ProjectState getPs() {
			return ps;
		}

		public void setPs(ProjectState ps) {
			this.ps = ps;
		}

		public void setServletRequest(HttpServletRequest arg0) {
			// TODO Auto-generated method stub
			this.request = arg0;
		}
}
