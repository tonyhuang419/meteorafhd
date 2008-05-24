package hello;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport{
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
		return "success";  
	}
	
	public void validate(){
		System.out.println("111");
	}
}
