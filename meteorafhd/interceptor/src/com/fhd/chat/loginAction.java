package com.fhd.chat;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class loginAction extends ActionSupport implements SessionAware{
	private Map session;
	private String username;  
	private String password;
	private String notMatch;
	public String getPassword()  
	{  
		return password;  
	}  
	public void setPassword(String password)  
	{  
		this.password = password;  
	}  
	public String getUsername()  
	{  
		return username;
	}  
	public void setUsername(String username) 
	{  
		this.username = username;
	}
	public String getNotMatch() {
		return notMatch;
	}
	public void setNotMatch(String notMatch) {
		this.notMatch = notMatch;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	public String execute()  
	{  
		String strReturn = "error";  
		if ( chatUserValidation.validate(username, password) ==  true){
			session.put( "username" , username);
			strReturn = "success"; 		
		}
		else{
			addFieldError("notMatch",getText("NandPnotMatch"));
		}
		return strReturn;  
	}
}
