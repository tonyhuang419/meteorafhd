package com.teststruts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class GoLoginDyAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm df = (DynaActionForm)form; 
		System.out.println(df.get("usernamedy"));
		System.out.println(df.get("passworddy"));
		df.set("usernamedy", "username");
		df.set("passworddy", null );
//		form = df;
		System.out.println(form.hashCode());
		System.out.println(df.hashCode());
		return mapping.findForward("gologin");

	}
}
