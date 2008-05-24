package DMI;

public class LoginAction {
	private String username;  
	private String password;  
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
	public String anotherAction(){   
		return "input";  
	}
	public String loginAction(){ 
		String strReturn = "error";  
		if (username.equals("fhdone"))  
			strReturn = "success";  
		return strReturn;  
	}
}
