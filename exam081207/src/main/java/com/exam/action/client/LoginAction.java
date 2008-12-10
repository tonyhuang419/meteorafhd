package com.exam.action.client;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.service.ILoginService;


@Results( {
	@Result(name = "login", value = "/WEB-INF/jsp/client/login.jsp"),
	@Result(name = "welcome", value = "/WEB-INF/jsp/client/welcome.jsp")
})
public class LoginAction extends BaseAction{
	
	@Autowired
	@Qualifier("loginService")
	private ILoginService loginService;
	
	private String username;
	private String password;
	
	public String login(){
		return "login";
	}
	
	public String validateUser(){
		if ( loginService.validateUser(username, password)){
			logger.info("login success");
			return "welcome";
		}
		else{
			logger.info("login fail");
			return "login";
		}
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
