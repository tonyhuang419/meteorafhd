package com.baoz.yx.action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/Login.jsp"),
		@Result(name = "main",type = ServletRedirectResult.class , value = "/login.action?method=toFirstPage"),
		@Result(name = "firstPage", value = "/WEB-INF/jsp/Main.jsp"),
		@Result(name = "error", value = "/WEB-INF/jsp/Login.jsp") })
public class LoginAction extends DispatchAction {
	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	private String username;
	private String password;

	//显示欢迎信息的用户名称
	private String userChineseName;
	
	public String login() throws Exception {
		
		Employee user = userService.getUser(username);
		if(user == null){
			ActionContext.getContext().put("userNotExist", true);
			logger.info("用户["+username+"]不存在");
			return SUCCESS;
		}
		Employee passUser = userService.getUser(username , password);
		//登陆失败
		if (passUser == null) {
			ActionContext.getContext().put("passwordInvalid", true);
			logger.info("用户["+username+"]密码不正确");
			return SUCCESS;
		} else {
			//登陆成功，将user和userDetail放到UserUtil中
			UserDetail userDetail = new UserDetail(passUser);
			userService.initUserDetail(userDetail);
			UserUtils.setUser(passUser, userDetail);
			logger.info("用户["+username+"登录成功");
			return "main";
		}
	}
	
	public String toFirstPage(){
		// session超时后，user就没有了
		if(UserUtils.getUser() != null){
			userChineseName = UserUtils.getUser().getName();
		}
		return "firstPage";
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getUserChineseName() {
		return userChineseName;
	}

	public void setUserChineseName(String userChineseName) {
		this.userChineseName = userChineseName;
	}
}
