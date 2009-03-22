package com.baoz.yx.action.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.baoz.core.web.struts2.DispatchAction;


@Results( {
		
		@Result(name = "enterMain", value = "/WEB-INF/jsp/system/client/clientMain.jsp") })
public class ClientMainAction extends DispatchAction implements ServletRequestAware {
	

	public String doDefault() throws Exception {
	
		
		return "enterMain";
	}




	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

}