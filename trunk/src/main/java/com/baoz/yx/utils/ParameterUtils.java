package com.baoz.yx.utils;

import org.apache.struts2.ServletActionContext;

import com.baoz.yx.tools.ObjectPropertySessionHoldTool;

/**
 * 类ParameterUtils.java的实现描述：参数工具类
 * 
 * @author xurong Jun 26, 2008 9:38:10 PM
 */
public class ParameterUtils {
	private static final String CONDITION_PARAM_NAME = "resetCondition";

	public static void prepareParameters(ObjectPropertySessionHoldTool holdTool) {
		if ("true".equals(ServletActionContext.getRequest().getParameter(
				CONDITION_PARAM_NAME))) {
			holdTool.storeToSession();
		} else {
			holdTool.setBackToObject();
		}
	}
}
