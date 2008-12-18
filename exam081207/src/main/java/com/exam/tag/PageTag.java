package com.exam.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PageTag extends ComponentTagSupport {
	private static final long serialVersionUID = 8889766206086327774L;
	
	private String pageNo;
	private String total;
	private String styleClass;
	private String baseAction;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new Pages(arg0);
	}

	protected void populateParams() {
		super.populateParams();
		Pages pages = (Pages)component;
		pages.setPageNo(pageNo);
		pages.setTotal(total);
		pages.setStyleClass(styleClass);
		pages.setBaseAction(baseAction);
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
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
}


