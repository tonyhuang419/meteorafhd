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
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.service.ISystemService;
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/assistanceQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/assistance/assistanceForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/assistance/assistanceForm.jsp") })

public class AssistanceAction extends DispatchAction implements
		ServletRequestAware {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Qualifier("systemService")
	private ISystemService systemService;
	private PageInfo info;
	private ServletRequest request;
	private Assistance a;
	
	public String doDefault() throws Exception {
		logger.info("新增外协");

		return ENTER_SAVE;
	}

	public String saveAssistance() throws Exception {
		logger.info("保存");

		service.save(a);
		return "success";

	}

	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		Assistance o = (Assistance) service.uniqueResult("from Assistance obj where obj.id='" + id + "'");
		this.a = o;
		return "enterUpdate";
	}

	public String updateAssistance() throws Exception {
		if(a.getId()!=null && !"".equals(a.getId())){
			Long id = 1l;
			Date d = new Date();
			a.setUpdateBy(d);
			a.setId(id);	
			service.update(a);
			return "success";
		}else service.save(a);
		return "success";

	}

	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update Assistance obj set obj.is_active=0 ";
		int a = systemService.deleteChose(idss, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
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
	public Assistance getA() {
		return a;
	}

	public void setA(Assistance a) {
		this.a = a;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
