package com.baoz.yx.filter.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baoz.yx.utils.UserUtils;

/**
 * 类AuthenticationFilter.java的实现描述：TODO 类实现描述
 * 
 * @author xurong Jun 20, 2008 2:31:15 PM
 */
public class AuthenticationFilter implements Filter {

	private String loginUrl = "login.action";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!httpRequest.getServletPath().endsWith(loginUrl)
				&& httpRequest.getSession().getAttribute(UserUtils.USER_DETAIL_SESSION_ID) == null) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			// 需要登陆
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/"+loginUrl);
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
