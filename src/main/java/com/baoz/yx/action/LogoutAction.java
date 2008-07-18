package com.baoz.yx.action;

import org.apache.struts2.config.Result;

import org.apache.struts2.ServletActionContext;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "success", value = "/WEB-INF/jsp/Login.jsp")
public class LogoutAction extends DispatchAction {
	

	public String login() throws Exception {
		
		logger.info("退出");
		
		//删除session中的用户信息
		ServletActionContext.getRequest().getSession().removeAttribute("USER_SESSION_ID");
		ServletActionContext.getRequest().getSession().removeAttribute("USER_DETAIL_SESSION_ID");
		
		return SUCCESS;
		
	}

	
}
