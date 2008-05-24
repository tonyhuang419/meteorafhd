package hello;

import com.opensymphony.xwork2.ActionSupport;

public class testtt extends ActionSupport{
	int i;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	public String execute()  {  
		return "success";  
	}  
}
