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
import com.baoz.yx.entity.YXBusineesType;
import com.baoz.yx.entity.YXClientNature;
import com.baoz.yx.service.ISystemService;

/**
 * 客户性质新增，修改，删除操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/businessTypeQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/businessType/btForm.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/businessType/btForm.jsp") })
public class BusinessTypeAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private YXBusineesType bt;
	private ServletRequest request;
	private String[] idsss;

	/**
	 * 新增行业类型
	 */
	public String doDefault() throws Exception {
		logger.info("新增行业类型");
		return ENTER_SAVE;
	}

	/**
	 * 保存行业类型
	 * 
	 * @return QUERY_LIST
	 * @throws Exception
	 */
	public String saveBT() throws Exception {
		logger.info("保存");
		service.save(bt);
		return "success";

	}

	/**
	 * 进入修改行业类型代码表信息页
	 * 
	 * @return ENTER_UPDATE
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");// 怎么会出现空指针异常
		Long id = Long.valueOf(idss);
		YXBusineesType o = (YXBusineesType) service
				.uniqueResult("from YXBusineesType obj where obj.id='" + id
						+ "'");
		this.bt = o;
		return "enterUpdate";

	}

	/**
	 * 执行对客户性质更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateBT() throws Exception {
		Long did = bt.getId();
		if (did != null && !"".equals(did)) {
			Long byid = 1l;
			bt.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			bt.setUpdateBy(d);
			
			service.saveOrUpdate(bt);
			return "success";
		} else {
			service.saveOrUpdate(bt);
			return "success";
		}

	}

	//
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update YXBusineesType obj set obj.is_active=0 ";
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

	public YXBusineesType getBt() {
		return bt;
	}

	public void setBt(YXBusineesType bt) {
		this.bt = bt;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
