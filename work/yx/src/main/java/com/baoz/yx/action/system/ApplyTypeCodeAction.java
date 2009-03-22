
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

import com.baoz.yx.entity.ApplyTypeCode;
import com.baoz.yx.service.ISystemService;

/**
 * 工程经济组成表新增，修改操作
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/applyTypeCodeQuery.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/system/applyType/addApplyTypeCode.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/system/applyType/updateApplyTypeCode.jsp")})
public class ApplyTypeCodeAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	private ISystemService 		systemService;
    private ApplyTypeCode  		atc;
	private ServletRequest		request;
	private String[] 			idsss;

	public String[] getIdsss() {
		return idsss;
	}

	public void setIdsss(String[] idsss) {
		this.idsss = idsss;
	}

	public String doDefault() throws Exception {
		logger.info("新增申购类型");
		return ENTER_SAVE;
	}

	/**
	 * 保存工程经济组成费用名称
	 */
	public String saveATC() throws Exception {
		logger.info("保存");
		service.save(atc);
		return "success";
	}

	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("idsss");
		Long id = Long.valueOf(idss);
		ApplyTypeCode o = (ApplyTypeCode) service
				.uniqueResult("from ApplyTypeCode obj where obj.id='" + id + "'");
		this.atc = o;
		return "enterUpdate";
	}

	/**
	 * 执行对申购类型更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateATC() throws Exception {
		Long Id = atc.getId();
		if (Id != null && !"".equals(Id)) {
			Long byid = 1l;
			atc.setById(byid);// 先这样固定设置，等做了用户登入后，在取当前登入用户的id
			Date d = new Date();
			atc.setUpdateBy(d);
			service.update(atc);
			return "success";
		} else {
			service.save(atc);
			return "success";
		}
	}

	// 删除联系人性质
	public String del() {
		String idss = request.getParameter("ids");
		String hql=" update ApplyTypeCode obj set obj.is_active=0 ";
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

	public ApplyTypeCode getAtc() {
		return atc;
	}

	public void setAtc(ApplyTypeCode atc) {
		this.atc = atc;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

}
