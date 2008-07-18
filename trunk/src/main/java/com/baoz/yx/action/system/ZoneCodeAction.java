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
import com.baoz.yx.entity.ZoneCode;
import com.baoz.yx.service.ISystemService;

@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/system/zoneCodeQuery.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/system/zoneCode/zoneCodeForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/zoneCode/zoneCodeForm.jsp") })

public class ZoneCodeAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Qualifier("systemService")
	private ISystemService systemService;
	private PageInfo info;
	private ServletRequest request;
	private ZoneCode zc;
	
	public String doDefault() throws Exception {
		logger.info("新增地域");

		return ENTER_SAVE;
	}

	public String saveZoneCode() throws Exception {
		logger.info("保存");

		service.save(zc);
		return "success";

	}

	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		ZoneCode o = (ZoneCode) service
				.uniqueResult("from ZoneCode obj where obj.id='" + id + "'");
		this.zc = o;

		return "enterUpdate";

	}

	public String updateZoneCode() throws Exception {
		if(zc.getId()!=null && !"".equals(zc.getId())){
			Long id = 1l;
			Date d = new Date();
			zc.setUpdateBy(d);
			zc.setId(id);	
			service.update(zc);
			return "success";
		}else service.save(zc);	
		return "success";

	}
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update ZoneCode obj set obj.is_active=0 ";
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
	public ZoneCode getZc() {
		return zc;
	}
	public void setZc(ZoneCode zc) {
		this.zc = zc;
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
