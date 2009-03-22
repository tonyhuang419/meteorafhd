package com.baoz.yx.action.assistance;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;

	@Result(name = "main", value = "/WEB-INF/jsp/assistance/assistanceMain.jsp") 
public class LoginMainAction extends DispatchAction {
	@Override
	public String doDefault() throws Exception {
		logger.info("");
        return "main";
	}
}
