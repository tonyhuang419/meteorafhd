package com.baoz.yx.action.contract;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;
@Results( {
	@Result(name = "home", value = "/WEB-INF/jsp/contract/reservertionRetrunHome.jsp") })
public class ReservationReturnAction extends DispatchAction{

	@Override
	public String doDefault() throws Exception {
		
		return "home";
	}

}
