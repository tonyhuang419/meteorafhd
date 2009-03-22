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
import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.entity.YXGroupCode;
import com.baoz.yx.service.ISystemService;

/**
 * 组别信息新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/groupCodeQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/group/groupForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/group/groupForm.jsp") })
public class GroupCodeAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private YXGroupCode gc;
	private ServletRequest request;
	private String[] idsss;

	/**
	 * 新增组
	 */
	public String doDefault() throws Exception {
		logger.info("新增组");

		return ENTER_SAVE;
	}

	/**
	 * 保存组
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveGroupCode() throws Exception {
		logger.info("保存");

		service.save(gc);
		return "success";

	}

	/**
	 * 进入修改组别代码表信息页
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");// 怎么会出现空指针异常

		Long id = Long.valueOf(idss);
		YXGroupCode o = (YXGroupCode) service
				.uniqueResult("from YXGroupCode obj where obj.id='" + id + "'");
		this.gc = o;

		return "enterUpdate";

	}

	/**
	 * 执行对组别代码的更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateGroup() throws Exception {
		if (gc.getId() != null && !"".equals(gc.getId())) {
			Long byid = 1l;
			gc.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			gc.setUpdateBy(d);
			service.saveOrUpdate(gc);
			return "success";
		} else {
			service.saveOrUpdate(gc);
			return "success";
		}

	}

	// 删除组别
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update YXGroupCode obj set obj.is_active=0 ";
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

	public YXGroupCode getGc() {
		return gc;
	}

	public void setGc(YXGroupCode gc) {
		this.gc = gc;
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