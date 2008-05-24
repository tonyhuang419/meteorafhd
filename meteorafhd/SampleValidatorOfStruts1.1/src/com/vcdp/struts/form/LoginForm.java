//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.vcdp.struts.form;

import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 03-18-2007
 * 
 * XDoclet definition:
 * @struts.form name="loginForm"
 */
public class LoginForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables


	private String password;

	private String username;

	// --------------------------------------------------------- Methods


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

}

