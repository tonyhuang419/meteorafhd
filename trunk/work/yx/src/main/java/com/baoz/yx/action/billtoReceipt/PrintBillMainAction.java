package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;


@Results( {
		@Result(name = "showmain", value = "/WEB-INF/jsp/billtoReceipt/printMain.jsp")
})
public class PrintBillMainAction extends DispatchAction {

	/**
	 * 显示打印申请管理mian
	 */
	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showmain";
	}

}

