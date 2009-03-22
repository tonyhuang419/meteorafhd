package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;

/**
 * 进入红冲确认页面
 * 
 */
@Result(name="showConfirmMain",value = "/WEB-INF/jsp/billtoReceipt/hongChongConfirmMain.jsp")
public class HongChongConfirmAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		return "showConfirmMain";
	}

}
