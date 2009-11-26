package fhdone.venusframework.action;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


@Results( 
		{ @Result(name = "success", location = "/test.jsp")
})
public class OrderAction  extends BaseAction{
	private static final long serialVersionUID = 1848292069222307390L;

	public String executeX()  {  
		return "success";  
	} 

	public String xx()  {  
		return "success";  
	} 

} 
