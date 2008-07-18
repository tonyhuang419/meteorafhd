package com.baoz.yx.action.contract.formalContractManage;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;

@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/formalContractManage/formalContractManageFrame.jsp")
	 })
public class FormalContractManageFrameAction extends DispatchAction{
	public String doDefault() throws Exception {
		return SUCCESS;
	}
}
