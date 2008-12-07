package com.exam.action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;


@Results( 
		{ @Result(name = "success", value = "/test.jsp")
})
public class HelloAction  extends BaseAction{


	public String executeX()  {  
		return "success";  
	} 

	public String xx()  {  
		return "success";  
	} 

}  
