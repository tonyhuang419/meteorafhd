package com.baoz.yx.action.assistance;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;
@Result(name = "success", value = "/WEB-INF/jsp/assistance/affirmMain.jsp") 
public class AffirmMainAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		this.logger.info("外协合同确认");
		return "success";
	}

}
