package com.baoz.yx.action.harvestMeansManager;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.baoz.core.web.struts2.DispatchAction;


@Results( {
		
		@Result(name = "enterMain", value = "/WEB-INF/jsp/harvestMeansManager/harvestMeansMain.jsp") })
public class HarvestMeansMainAction extends DispatchAction implements ServletRequestAware {
	

	public String doDefault() throws Exception {
	
		
		return "enterMain";
	}




	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

}