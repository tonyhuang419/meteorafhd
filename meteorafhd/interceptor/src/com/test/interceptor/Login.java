package com.test.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport{ //implements SessionAware {
	private String role;    
	//private Map session;
	//Map session = ActionContext.getContext().getSession();
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this .role = role;
	}

//	public void setSession(Map session) {
//		this .session = session;
//	}

	@Override
	public String execute() {
		ActionContext.getContext().getSession().put( " ROLE " , role);
		return SUCCESS;
	}    
} 
