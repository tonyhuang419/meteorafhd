package com.baoz.yx.action.harvestMeansManager;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;


@Results( {
	@Result(name = "enterMain", value = "/WEB-INF/jsp/harvestMeansManager/w_harvestMeansMain.jsp") 
})
public class W_HarvestMeansMainAction extends DispatchAction  {


	public String doDefault() throws Exception {
		return "enterMain";
	}


}