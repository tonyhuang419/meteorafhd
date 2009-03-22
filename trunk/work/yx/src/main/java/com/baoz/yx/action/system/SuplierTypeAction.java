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
import com.baoz.yx.entity.SuplierType;
import com.baoz.yx.service.ISystemService;
/**
 * 供应商类别查询操作
 * @author Alvin (mixb@baoz.com.cn)
 * 
 */
@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/selfProductionQuery.action" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/suplierType/suplierTypeForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/suplierType/suplierTypeForm.jsp")})
public class SuplierTypeAction extends DispatchAction implements
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
	private SuplierType st;

	@Override
	public String doDefault() throws Exception {
		logger.info("新增供应商类别");
		return "enterSave";	
		} 
	
	public String saveSuplierType() throws Exception{
		this.logger.info("新增供应商类型");
		service.save(st);
		return "success";
	}
	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		SuplierType o = (SuplierType) service
				.uniqueResult("from SuplierType obj where obj.id='" + id + "'");
		this.st = o;

		return "enterUpdate";

	}
	
	public String updateSuplierType() throws Exception {
		if(st.getId()!=null && !"".equals(st.getId())){
			Long id = 1l;
			st.setId(id);
			Date d = new Date();
			st.setUpdateBy(d);
			service.update(st);
			return "success";
		}else service.save(st);
		return "success";

	}
	
	public String del() throws Exception {
		String idss = request.getParameter("ids");
		String hql = "update SuplierType obj set obj.is_active=0";
		int a = systemService.deleteChose(idss, hql);
		if(a>0){
			return "success";
		}else logger.info("删除失败!");
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

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public SuplierType getSt() {
		return st;
	}

	public void setSt(SuplierType st) {
		this.st = st;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
