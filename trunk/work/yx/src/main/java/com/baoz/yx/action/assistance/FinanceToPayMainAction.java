package com.baoz.yx.action.assistance;

import org.apache.struts2.config.Result;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "success", value = "/WEB-INF/jsp/assistance/financeToPayMain.jsp") 
public class FinanceToPayMainAction extends DispatchAction{

	@Override
	public String doDefault() throws Exception {
		return SUCCESS;
	}
	
}
