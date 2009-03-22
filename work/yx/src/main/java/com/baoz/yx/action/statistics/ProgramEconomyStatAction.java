package com.baoz.yx.action.statistics;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;


//工程经济统计
@Results( {	
	@Result(name = "SUCCESS", value = "/statisticsDemo/project_economyM.html")
})
public class ProgramEconomyStatAction  extends DispatchAction {
	
	@Override
	public String doDefault()  throws Exception  {
		
		return "SUCCESS";
	}
}