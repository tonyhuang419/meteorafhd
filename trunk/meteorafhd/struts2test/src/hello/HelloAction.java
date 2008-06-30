package hello;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction  extends ActionSupport
{  
	private Testgetout t = new Testgetout();
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
	public Testgetout getT() {
		return t;
	}
	public void setT(Testgetout t) {
		this.t = t;
	} 
}


class Testgetout{
	public String method(String str){
		return "let me out" + str;
	}
}
