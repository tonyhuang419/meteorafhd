package com.exam.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware  {
	private static final long serialVersionUID = 6222135106052344007L;

	protected Log logger = LogFactory.getLog(this.getClass());

	private String	method;
	protected Map<Object,Object> validateMap;

	final public String execute() throws Exception {
		if (StringUtils.isEmpty(method)) {
			logger.info("defalut method has be called");
			return "success";
		}
		logger.info("method:" + method);
		return (String) MethodUtils.invokeMethod(this, method, new Object[] {});
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<Object, Object> getValidateMap() {
		return validateMap;
	}

	public void setValidateMap(Map<Object, Object> validateMap) {
		this.validateMap = validateMap;
	}

	private Map session;  
	private HttpServletRequest request;

	public void setSession(Map session) {   
		this.session = session;   
	}   
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
