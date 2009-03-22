package com.baoz.yx.action.contract;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;
@Result(name = "success", value = "/WEB-INF/jsp/contract/inputReport.jsp")
public class ContractinputReportAction extends DispatchAction{
	public String doDefault() throws Exception {
		logger.info("====================state");
		return "success";
	}
}
