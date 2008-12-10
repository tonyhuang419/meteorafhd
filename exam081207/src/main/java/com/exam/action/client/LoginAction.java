package com.exam.action.client;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;


@Results( 
		{ @Result(name = "login", value = "/WEB-INF/jsp/client/login.jsp")
})
public class LoginAction extends BaseAction{
	
	private String username;
	private String password;
	
	public String login(){
		return "login";
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
