package com.baoz.yx.action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/main_view.jsp"),
		@Result(name = "notice", value = "/WEB-INF/jsp/notice.jsp"),
		@Result(name = "message", value = "/WEB-INF/jsp/message.jsp") })
public class StatAction extends DispatchAction {

	public String doDefault() throws Exception {
		return SUCCESS;
	}

	public String getNotice() throws Exception {
		return "notice";
	}

	public String getMessage() throws Exception {
		return "message";
	}

}
