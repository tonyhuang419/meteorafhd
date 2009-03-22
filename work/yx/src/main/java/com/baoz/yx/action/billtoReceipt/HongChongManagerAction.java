package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;

/**
 * 进入红冲管理页面
 * 
 */
@Result(name="showManagerMain",value = "/WEB-INF/jsp/billtoReceipt/hongChongManagerMain.jsp")
public class HongChongManagerAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		return "showManagerMain";
	}

}
