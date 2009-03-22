package com.baoz.yx.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.baoz.yx.entity.Employee;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

public class UserUtils {
	public final static String USER_SESSION_ID = "baox_yx_user";
	public final static String USER_DETAIL_SESSION_ID = "baox_yx_userDetail";
	/**
	 * 将user和userDetail放进session
	 * @param user
	 * @param userDetail
	 */
	public static void setUser(Employee user, UserDetail userDetail) {
		//将user、userDetail放到session中
//		ServletActionContext.getRequest().getSession().setAttribute(USER_SESSION_ID, user);
//		ServletActionContext.getRequest().getSession().setAttribute(USER_DETAIL_SESSION_ID, userDetail);
		getHttpServletRequest().getSession().setAttribute(USER_SESSION_ID, user);
		getHttpServletRequest().getSession().setAttribute(USER_DETAIL_SESSION_ID, userDetail);

	}
	/**
	 * 获得当前用户
	 * @return
	 */
	public static Employee getUser() {
//		return (Employee)ServletActionContext.getRequest().getSession().getAttribute(USER_SESSION_ID);
		return (Employee)getHttpServletRequest().getSession().getAttribute(USER_SESSION_ID);
	}
	/**
	 * 获得当前用户详细信息
	 * @return
	 */
	public static UserDetail getUserDetail() {
//		return (UserDetail)ServletActionContext.getRequest().getSession().getAttribute(USER_DETAIL_SESSION_ID);
		return (UserDetail)getHttpServletRequest().getSession().getAttribute(USER_DETAIL_SESSION_ID);
	}
	
	
	public static HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	}
	
}
