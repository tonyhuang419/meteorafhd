/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.fhdone.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fhdone.struts.form.LoginForm;

public class LoginAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
		if(loginForm.getUserName().equals("fhd")){
			return mapping.findForward("s");
		}
		else{
			loginForm.setUserName("");
			loginForm.setPassWord("");
			return mapping.getInputForward();
		}
	}
}