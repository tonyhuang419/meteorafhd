package com.baoz.yx.utils;

import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.opensymphony.xwork2.ActionContext;

/**
 * 类ParameterUtils.java的实现描述：参数工具类
 * 
 * @author xurong Jun 26, 2008 9:38:10 PM
 */
public class ParameterUtils {
	public static final String CONDITION_PARAM_NAME = "resetCondition";
	public static final String CURRENT_PAGE_PARAM_NAME = "resetPage";

	/**
	 * 设置参数值
	 * @param holdTool
	 */
	public static void prepareParameters(ObjectPropertySessionHoldTool holdTool) {
		if (isResetCondition()) {
			holdTool.storeToSession();
		} else {
			holdTool.setBackToObject();
		}
	}
	/**
	 * 设置当前页
	 * @param info
	 * @param infoSessionKey 存放当前页面的session的key值
	 * @return
	 */
	public static com.baoz.core.utils.PageInfo preparePageInfo(com.baoz.core.utils.PageInfo info , String infoSessionKey){
		com.baoz.core.utils.PageInfo varInfo = info;
		// 没有当前页面信息，从session中取
		if(varInfo == null){
			varInfo = new PageInfo();
			if(ActionContext.getContext().getSession().get(infoSessionKey) != null){
				varInfo.setCurPage((Integer)ActionContext.getContext().getSession().get(infoSessionKey));
			}
		}
		// 重置条件和页数都设为第一页
		if(isResetCondition() || isResetCurrentPage()){
			varInfo.setCurPage(1);
		}
		// 保存当前页到session
		ActionContext.getContext().getSession().put(infoSessionKey, varInfo.getCurPage());
		return varInfo;
	}
	/**
	 * 生成request中参数，method除外,
	 * 生成这样的格式&xx=xx&xx=xx，开头有&号
	 */
	public static String generateParameterUrl(){
		Enumeration<String> emu = ServletActionContext.getRequest().getParameterNames();
		StringBuilder pUrl = new StringBuilder();
		while(emu.hasMoreElements()){
			String key = emu.nextElement();
			if(!key.equals("method")){
				pUrl.append("&"+key+"="+StringUtils.defaultString(ServletActionContext.getRequest().getParameter(key)));
			}
		}
		return pUrl.toString();
	}
	private static boolean isResetCondition(){
		return "true".equals(ServletActionContext.getRequest().getParameter(CONDITION_PARAM_NAME));
	}
	
	private static boolean isResetCurrentPage(){
		return "true".equals(ServletActionContext.getRequest().getParameter(CURRENT_PAGE_PARAM_NAME));
	}
}
