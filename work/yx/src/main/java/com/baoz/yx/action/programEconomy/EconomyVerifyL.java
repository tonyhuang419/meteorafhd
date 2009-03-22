package com.baoz.yx.action.programEconomy;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import com.baoz.core.web.struts2.DispatchAction;


/**
 * 工程经济相关操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
	
		@Result(name = "main", value = "/WEB-INF/jsp/programEconomy/economyVerifyLMain.jsp") })
public class EconomyVerifyL extends DispatchAction {
	@Override
	public String doDefault() throws Exception {
		logger.info("");
        return "main";
	}

}