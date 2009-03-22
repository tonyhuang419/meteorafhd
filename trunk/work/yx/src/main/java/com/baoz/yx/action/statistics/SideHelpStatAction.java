package com.baoz.yx.action.statistics;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;


//外协合同统计
@Results( {	
	@Result(name = "SUCCESS", value = "/statisticsDemo/sideHelp_statisticsM.html")
})
public class SideHelpStatAction extends DispatchAction {
	
	@Override
	public String doDefault()  throws Exception  {
		
		return "SUCCESS";
	}
}
