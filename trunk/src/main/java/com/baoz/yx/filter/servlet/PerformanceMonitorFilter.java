package com.baoz.yx.filter.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 类PerformanceMonitorFilter.java的实现描述：检测请求的花费时间
 * 
 * @author xurong Jun 18, 2008 2:19:37 PM
 */
public class PerformanceMonitorFilter implements Filter {
	private static Logger logger = Logger
			.getLogger(PerformanceMonitorFilter.class);
	private boolean isEnabled = true;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (isEnabled) {
			long startTime = System.currentTimeMillis();
			chain.doFilter(request, response);
			long endTime = System.currentTimeMillis();
			//
			long spanTime = endTime - startTime;
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			if (logger.isDebugEnabled() && spanTime <= 200) {
				logger.debug(getLoggerInfo(spanTime, httpRequest));
			} else if (logger.isInfoEnabled() && spanTime > 200
					&& spanTime <= 1000) {
				logger.info(getLoggerInfo(spanTime, httpRequest));
			} else if (spanTime >= 1000 && spanTime <= 2000) {
				logger.warn(getLoggerInfo(spanTime, httpRequest));
			} else if (spanTime > 2000) {
				logger.error(getLoggerInfo(spanTime, httpRequest));
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private String getLoggerInfo(long spanTime, HttpServletRequest httpRequest) {
		if (httpRequest.getParameter("method") != null
				&& httpRequest.getParameter("method") != "") {
			return httpRequest.getContextPath() + httpRequest.getServletPath()
					+ ", method = " + httpRequest.getParameter("method")
					+ ", span time = " + spanTime + "ms";
		} else {
			return httpRequest.getContextPath() + httpRequest.getServletPath()
					+ ", span time = " + spanTime + "ms";
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String enabledStr = filterConfig.getInitParameter("isEnabled");
		if ("false".equals(enabledStr)) {
			isEnabled = false;
		} else {
			isEnabled = true;
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
