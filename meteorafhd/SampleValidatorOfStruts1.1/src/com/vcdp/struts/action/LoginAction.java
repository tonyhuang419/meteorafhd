//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.vcdp.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vcdp.struts.form.LoginForm;

/** 
 * MyEclipse Struts
 * Creation date: 03-18-2007
 * 
 * XDoclet definition:
 * @struts.action path="/login" name="loginForm" input="/form/login.jsp" scope="request" validate="true"
 */
public class LoginAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		LoginForm loginForm = (LoginForm) form;
	
		
		request.getSession().setAttribute("loginIsOk","login is OK");
		
		return mapping.getInputForward();
	}

}

