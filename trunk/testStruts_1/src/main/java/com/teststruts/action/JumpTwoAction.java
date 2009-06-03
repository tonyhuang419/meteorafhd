package com.teststruts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.teststruts.form.LoginForm;


public class JumpTwoAction extends DispatchAction  {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("jumpTwo");
		LoginForm loginForm = (LoginForm) form;
		loginForm.setUsername("username");
		return mapping.findForward("gologin");

	}
}
