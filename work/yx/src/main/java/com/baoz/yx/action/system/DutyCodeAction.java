package com.baoz.yx.action.system;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXDutyCode;
import com.baoz.yx.service.ISystemService;

/**
 * 员工信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/dutyCodeQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/dutyForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/dutyForm.jsp") })
public class DutyCodeAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private YXDutyCode dc;
	private ServletRequest request;
	private String[] idsss;

	/**
	 * 新增职责
	 */
	public String doDefault() throws Exception {
		logger.info("新增职责");
		return ENTER_SAVE;
	}

	/**
	 * 保存职责
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveDutyCode() throws Exception {
		logger.info("保存");
		service.save(dc);
		return "success";

	}

	/**
	 * 进入修改职责代码表信息页
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		YXDutyCode o = (YXDutyCode) service
				.uniqueResult("from YXDutyCode obj where obj.id='" + id + "'");
		this.dc = o;
		return "enterUpdate";

	}

	/**
	 * 执行对职责代码的更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateDuty() throws Exception {
		Long dutyId = dc.getId();

		if (dutyId != null && !"".equals(dutyId)) {
			Long byid = 1l;
			dc.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			dc.setUpdateBy(d);
			service.update(dc);
			return "success";
		} else {
			service.save(dc);
			return "success";
		}

	}

	// 删除所选的员工信息
	public String delExployees() {

		return "success";
	}

	public ICommonService getService() {
		return service;
	}

	public YXDutyCode getDc() {
		return dc;
	}

	public void setDc(YXDutyCode dc) {
		this.dc = dc;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public String[] getIdsss() {
		return idsss;
	}

	public void setIdsss(String[] idsss) {
		this.idsss = idsss;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
