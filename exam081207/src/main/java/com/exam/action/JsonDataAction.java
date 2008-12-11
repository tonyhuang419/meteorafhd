package com.exam.action;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.service.client.IRegisterService;
import com.googlecode.jsonplugin.JSONResult;
import com.opensymphony.xwork2.ActionContext;


@ParentPackage(value = "json-default")
@Results( {
	@Result(name = "success", type=JSONResult.class , value = "")
})
public class JsonDataAction extends BaseAction{
	private static final long serialVersionUID = -318177022360230079L;

	private Object jsonData;

	@Autowired
	@Qualifier("registerService")
	private IRegisterService registerService;


	public String uniqueUser(){
		String username = ((String[])ActionContext.getContext().getParameters().get("username"))[0];
		if ( registerService.uniqueClientName(username) ){
			jsonData =  "1";
		}
		else{
			jsonData =  "0";
		}
		return "success";
	}


	public Object getJsonData() {
		return jsonData;
	}


	public void setJsonData(Object jsonData) {
		this.jsonData = jsonData;
	}

}
