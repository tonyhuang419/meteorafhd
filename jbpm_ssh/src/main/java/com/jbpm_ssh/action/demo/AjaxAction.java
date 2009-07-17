package com.jbpm_ssh.action.demo;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.googlecode.jsonplugin.JSONResult;
import com.jbpm_ssh.action.BaseAction;
import com.jbpm_ssh.service.ICommonService;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage(value = "json-default")
@Results( {
	@Result(name = "ajax", value = "/WEB-INF/jsp/demo/ajax.jsp"),
	@Result(name = "success", type=JSONResult.class , value = "")
})
public class AjaxAction extends BaseAction{

	private static final long serialVersionUID = 6221557218613573642L;

	private Object  jsonData; 
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	public String ajax(){
		return "ajax";
	}
	
	public String doAjax(){
		commonService.jbpmTest();
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
