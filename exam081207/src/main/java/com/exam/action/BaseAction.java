package com.exam.action;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;

public class BaseAction implements Action{
	protected Log logger = LogFactory.getLog(this.getClass());

	private String	method;

	@Override
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

}
