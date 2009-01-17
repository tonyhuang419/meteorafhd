package com.teststruts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class LoginForm extends ActionForm {
	private static final long serialVersionUID = -2438269811866986786L;

	private String username;
	private String password;

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		username = null;
		password = null;
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