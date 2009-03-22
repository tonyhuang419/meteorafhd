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

import com.baoz.yx.entity.YXLinkManN;
import com.baoz.yx.service.ISystemService;

/**
 * 联系人性质新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/linkManNQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/linkManN/linkManNForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/linkManN/linkManNForm.jsp") })
public class LinkManNAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private YXLinkManN lmn;
	private ServletRequest request;
	private String[] idsss;

	/**
	 * 新增行业类型
	 */
	public String doDefault() throws Exception {
		logger.info("新增联系人性质");
		return ENTER_SAVE;
	}

	/**
	 * 保存联系人性质
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveLM() throws Exception {
		logger.info("保存");
		service.save(lmn);
		return "success";

	}

	/**
	 * 进入修改联系人性质表信息页
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");// 怎么会出现空指针异常
		Long id = Long.valueOf(idss);
		YXLinkManN o = (YXLinkManN) service
				.uniqueResult("from YXLinkManN obj where obj.id='" + id + "'");
		this.lmn = o;
		return "enterUpdate";

	}

	/**
	 * 执行对客户性质更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateLM() throws Exception {
		Long lmId = lmn.getId();
		if (lmId != null && !"".equals(lmId)) {
			Long byid = 1l;
			lmn.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			lmn.setUpdateBy(d);
			service.update(lmn);
			return "success";
		} else {
			service.save(lmn);
			return "success";
		}

	}

	// 删除联系人性质
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update YXLinkManN obj set obj.is_active=0 ";
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

	public YXLinkManN getLmn() {
		return lmn;
	}

	public void setLmn(YXLinkManN lmn) {
		this.lmn = lmn;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
