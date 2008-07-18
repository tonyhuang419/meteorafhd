package com.baoz.yx.action.purchase;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.ISystemService;
/**
 * @author T-08-D-119
 *
 */
@Result(name = "succ", type = ServletRedirectResult.class, value = "/purchase/purManageQuery.action")
public class PurAction extends DispatchAction implements
ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("purService")
	private IPurService purService;
	@Qualifier("systemService")
	private ISystemService systemService;
	PageInfo info;
	private ApplyMessage am;
	private String stateId;
	private ServletRequest request;
	
	private String idss;
	public String getIdss() {
		return idss;
	}
	public void setIds(String idss) {
		this.idss = idss;
	}
	public String doDefault() throws Exception {
		logger.info("申购采购新增");
		String hql = "from ApplyMessage am where am.affirmState = '4'";
		return "success";
	}
	public String verifyState() {
		String aaa[] = stateId.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < aaa.length; i++) {
			sb.append(aaa[i]).append(",");
		}
		sb.append('0');
		int a = purService.updateState(sb);
		if (a > 0)
			return "succ";
		else {
			logger.info("更新状态操作不成功！");
			return "succ";
		}
	}
	public String del() {
		String idss = request.getParameter("idss");
		String hql=" update ApplyMessage obj set obj.is_active=0 ";
		int a = systemService.deleteChose(idss, hql);
		if (a > 0) {
			return "succ";
		} else {
			logger.info("删除操作不成功！");
			return "succ";
			}
		}
		
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public IPurService getPurService() {
		return purService;
	}
	public void setPurService(IPurService purService) {
		this.purService = purService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public ApplyMessage getAm() {
		return am;
	}
	public void setAm(ApplyMessage am) {
		this.am = am;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public ServletRequest getRequest() {
		return request;
	}
	public void setRequest(ServletRequest request) {
		this.request = request;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setIdss(String idss) {
		this.idss = idss;
	}
}
