package com.waterking.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PageTag extends ComponentTagSupport {
	private static final long serialVersionUID = 8889766206086327774L;
	
	private String beanName;
	private String styleClass;
	private String baseAction;
	

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new Pages(arg0);
	}

	protected void populateParams() {
		super.populateParams();
		Pages pages = (Pages)component;
		pages.setStyleClass(styleClass);
		pages.setBaseAction(baseAction);
		pages.setBeanName(beanName);
	}


	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getBaseAction() {
		return baseAction;
	}

	public void setBaseAction(String baseAction) {
		this.baseAction = baseAction;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}


