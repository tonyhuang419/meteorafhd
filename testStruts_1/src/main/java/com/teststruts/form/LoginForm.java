package com.teststruts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class LoginForm extends ActionForm {
	private static final long serialVersionUID = -2438269811866986786L;

	private String userName;
	private String passWord;

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		userName = null;
		passWord = null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}