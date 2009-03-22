package com.baoz.yx.action.assistance;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "main", value = "/WEB-INF/jsp/assistance/applyMain.jsp") 
public class ApplyMainAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		return "main";
	}

}
