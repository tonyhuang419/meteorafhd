package com.waterking.action.demo;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.googlecode.jsonplugin.JSONResult;
import com.opensymphony.xwork2.ActionContext;
import com.waterking.action.BaseAction;

@ParentPackage(value = "json-default")
@Results( {
	@Result(name = "ajax", value = "/WEB-INF/jsp/demo/ajax.jsp"),
	@Result(name = "success", type=JSONResult.class , value = "")
})
public class AjaxAction extends BaseAction{
	private static final long serialVersionUID = 1445318978316461142L;

	private Object  jsonData; 
	
	public String ajax(){
		return "ajax";
	}
	
	public String doAjax(){
		String info = ((String[])ActionContext.getContext().getParameters().get("info"))[0];
		jsonData = info;
		return "success";
	}
	
	public String doAjaxArray(){
		String info = ((String[])ActionContext.getContext().getParameters().get("info"))[0];
		jsonData = new String[]{info,info};
		return "success";
	}

	public Object getJsonData() {
		return jsonData;
	}

	public void setJsonData(Object jsonData) {
		this.jsonData = jsonData;
	}



	
}
