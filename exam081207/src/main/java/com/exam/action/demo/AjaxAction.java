package com.exam.action.demo;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;
import com.googlecode.jsonplugin.JSONResult;

@ParentPackage(value = "json-default")
@Results( {
	@Result(name = "ajax", value = "/WEB-INF/jsp/demo/ajax.jsp"),
	@Result(name = "success", type=JSONResult.class , value = "")
})
public class AjaxAction extends BaseAction{
	private static final long serialVersionUID = 1445318978316461142L;

	private String info; 
	
	public String ajax(){
		return "ajax";
	}
	
	public String doAjax(){
		return "success";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
