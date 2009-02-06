package com.teststruts.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.AutoArrayList;


public class LoginForm extends ActionForm {
	private static final long serialVersionUID = -2438269811866986786L;

	private String username;
	private String password;
	private String listX[];
	private List listStu = new AutoArrayList(Stu.class);
	private Stu stu = new Stu();

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


	public String[] getListX() {
		return listX;
	}

	public void setListX(String[] listX) {
		this.listX = listX;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List getListStu() {
		return listStu;
	}

	public void setListStu(List listStu) {
		this.listStu = listStu;
	}

	public Stu getStu() {
		return stu;
	}

	public void setStu(Stu stu) {
		this.stu = stu;
	}


}