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

import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * 类OperationTrackFilter.java的实现描述：记录操作轨迹
 * @author xurong Sep 27, 2008 4:30:54 PM
 */
public class OperationTrackFilter implements Filter {
	private static Logger logger = Logger.getLogger(OperationTrackFilter.class);
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuilder sb = new StringBuilder();
		UserDetail ud = (UserDetail) httpRequest.getSession().getAttribute(UserUtils.USER_DETAIL_SESSION_ID);
		if(ud != null){
			sb.append("user:"+ud.getUser().getUsername()+"["+ud.getUser().getId()+"],name:"+ud.getUser().getName()+",");
		}
		sb.append("clientAddr:"+httpRequest.getRemoteAddr()+"["+httpRequest.getRemoteHost()+"]\n");
		sb.append(httpRequest.getRequestURL()+"[method:"+httpRequest.getParameter("method")+"]");
		logger.info(sb);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
