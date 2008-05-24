package hello;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction  extends ActionSupport
{  
	private String username;  
	private String password;  
	public String getPassword()  {  
		return password;  
	}  
	public void setPassword(String passwordx)  {  
		this.password = passwordx;  
	}  
	public String getUsername()  {  
		return username;  
	}  
	public void setUsername(String username)  	{  
		this.username = username;  
	}  
	public String execute()  {  
		String strReturn = "error";  
		if (username.equals("fhdone"))  
			strReturn = "success";  
		return strReturn;  
	}  
}  
