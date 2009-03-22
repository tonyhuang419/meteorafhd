package com.baoz.yx.action.statistics;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;


//自由产品签订情况
@Results( {	
	@Result(name = "SUCCESS", value = "/statisticsDemo/own_productM.html")
})
public class OwnProductSignInfoStatAction extends DispatchAction {
	
	@Override
	public String doDefault()  throws Exception  {
		
		return "SUCCESS";
	}
}
