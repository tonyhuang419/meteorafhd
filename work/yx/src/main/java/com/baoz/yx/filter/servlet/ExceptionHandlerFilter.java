package com.baoz.yx.filter.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.baoz.core.web.struts2.AbstractBaseAction;
import com.opensymphony.xwork2.util.OgnlValueStack;

/**
 * 类ExceptionHandlerFilter.java的实现描述：处理程序的异常
 * @author xurong Aug 8, 2008 9:54:17 AM
 */
public class ExceptionHandlerFilter implements Filter {
	private static Logger logger = Logger.getLogger(ExceptionHandlerFilter.class);
	private String errorPage;
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
			Throwable e = (Throwable) request.getAttribute("javax.servlet.error.exception");
			if(e != null){
				handleException(request, response, e);
			}
		} catch (Exception e) {
			handleException(request, response, e);
		}
	}

	private void handleException(ServletRequest request,
			ServletResponse response, Throwable e) throws ServletException,
			IOException {
		logger.error(e.getMessage(),e);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		//处理struts2的异常，由于com.baoz.core.web.struts2.AbstractBaseAction.execute(),没抛异常，在这打出message
		//struts.valueStack=com.opensymphony.xwork2.util.OgnlValueStack@b01669}
		OgnlValueStack onglValueStack = (OgnlValueStack) httpRequest.getAttribute("struts.valueStack");
		if(onglValueStack.getRoot().size()>0){
			Object action = onglValueStack.getRoot().get(0);
			if(action != null && action instanceof AbstractBaseAction){
				List<String> errMsgs = ((AbstractBaseAction)action).getErrMsgs();
				if(errMsgs != null){
					logger.error("AbstractBaseAction has errors,error messages is :"+StringUtils.join(errMsgs,","));
				}
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		request.setAttribute("rootCause", ExceptionUtils.getRootCause(e));
		request.setAttribute("originalCause", e);
		if(StringUtils.isNotBlank(errorPage)){
			if(!response.isCommitted()){
				httpRequest.getRequestDispatcher(errorPage).forward(request, response);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		errorPage = filterConfig.getInitParameter("errorPage");
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
}
