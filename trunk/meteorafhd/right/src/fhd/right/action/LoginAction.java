package fhd.right.action;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{
	private String name;
	private String pwd;
	private MyService ms;
	
	public MyService getMs() {
		return ms;
	}
	public void setMs(MyService ms) {
		this.ms = ms;
	}
	public LoginAction(){		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String execute() {  
		if(ms.valid(name, pwd)){
			return "success";
		}
		return "error";
	}
}
