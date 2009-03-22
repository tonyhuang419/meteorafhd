package com.baoz.yx.action.billtoReceipt;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.utils.UserUtils;


/**
 *	开票收据管理
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/searchReceipt.jsp"),
	@Result(name = "showselect", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp")})

public class SearchReceiptQueryAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;

	private ServletRequest				request;
	private List<YXClientCode>       	yxClientCodeList;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("开票收据管理");
		Long uid = new UserUtils().getUser().getId();
		
		yxClientCodeList = service.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		return SUCCESS;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public ServletRequest getRequest() {
		return request;
	}
	public void setRequest(ServletRequest request) {
		this.request = request;
	}
	public List<YXClientCode> getYxClientCodeList() {
		return yxClientCodeList;
	}
	public void setYxClientCodeList(List<YXClientCode> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;		
	}

}

