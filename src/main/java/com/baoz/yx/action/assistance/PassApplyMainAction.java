package com.baoz.yx.action.assistance;

import org.apache.struts2.config.Result;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "main", value = "/WEB-INF/jsp/assistance/passApplyMain.jsp") 
public class PassApplyMainAction extends DispatchAction {
	public String doDefault() throws Exception {
		logger.info("");
        return "main";
	}
}
