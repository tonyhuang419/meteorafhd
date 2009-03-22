package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;

/**
 * 进入开票签收主操作页
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/billtoReceipt/signMain.jsp")
public class SignMainAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		return "queryList";
	}

}
