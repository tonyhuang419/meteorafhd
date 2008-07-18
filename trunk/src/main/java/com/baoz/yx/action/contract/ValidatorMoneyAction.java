package com.baoz.yx.action.contract;


import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.components.json.JSONResult;
import com.baoz.components.json.annotations.JSON;
import com.opensymphony.xwork2.Action;



@ParentPackage(value = "json-default")
@Results( {
	@Result(name = "success", type=JSONResult.class, value = "")
} )
public class ValidatorMoneyAction  {
	
	private String aaa="aaaa";
	
	public String execute() throws Exception {
		
		return Action.SUCCESS;	
	}

	@JSON(name="newName")
	public String getAaa() {
		return this.aaa;
	}


	
}
