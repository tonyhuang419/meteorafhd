package com.baoz.yx.intercept.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class PerformanceMonitorInterceptor implements MethodInterceptor {
	private static Logger logger = Logger
			.getLogger(PerformanceMonitorInterceptor.class);
	private boolean isEnabled = true;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (isEnabled) {
			long startTime = System.currentTimeMillis();
			Object ret = invocation.proceed();
			long endTime = System.currentTimeMillis();
			//
			long spanTime = endTime - startTime;

			if (logger.isDebugEnabled() && spanTime <= 20) {
				logger.debug(getLoggerInfo(spanTime, invocation ,false));
			} else if (logger.isInfoEnabled() && spanTime > 20
					&& spanTime <= 50) {
				logger.info(getLoggerInfo(spanTime, invocation ,false));
			} else if (spanTime >= 50 && spanTime <= 100) {
				logger.warn(getLoggerInfo(spanTime, invocation ,false));
			} else if (spanTime > 100) {
				logger.error(getLoggerInfo(spanTime, invocation ,true));
			}
			return ret;
		} else {
			return invocation.proceed();
		}

	}

	private String getLoggerInfo(long spanTime, MethodInvocation invocation,boolean isError) {
		StringBuffer sb = new StringBuffer();
		sb.append(invocation.getMethod().getDeclaringClass().getName());
		sb.append('.').append(invocation.getMethod().getName()).append("() ");
		sb.append(" ").append(", span time = ").append(spanTime).append("ms  ");
		if(isError){
			if(invocation.getArguments() != null){
				for (int i = 0; i < invocation.getArguments().length; i++) {
					sb.append("arg"+i+":").append(invocation.getArguments()[i]).append("    ");
				}
			}
		}
		return sb.toString();
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
