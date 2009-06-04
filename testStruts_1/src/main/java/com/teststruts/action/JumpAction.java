package com.teststruts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.teststruts.form.LoginForm;


public class JumpAction extends DispatchAction  {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("jump");
		LoginForm loginForm = (LoginForm) form;
		loginForm.setUsername("");
		return mapping.findForward("gologin");

	}
}
