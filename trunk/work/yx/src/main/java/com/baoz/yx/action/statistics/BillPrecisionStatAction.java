package com.baoz.yx.action.statistics;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;

//开票精度统计

@Results( {	
	@Result(name = "SUCCESS", value = "/statisticsDemo/bill_precisionM.html")
})
public class BillPrecisionStatAction  extends DispatchAction {
	@Override
	public String doDefault()  throws Exception  {
		
		return "SUCCESS";
	}
}
