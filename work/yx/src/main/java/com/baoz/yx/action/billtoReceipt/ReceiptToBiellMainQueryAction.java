package com.baoz.yx.action.billtoReceipt;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	收据转发票管理
 *
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/receiptToBillMain.jsp")})

public class ReceiptToBiellMainQueryAction extends DispatchAction {
		@Override
		public String doDefault() throws Exception {
			this.logger.info("收据转发票");
			return SUCCESS;
		}
	}

