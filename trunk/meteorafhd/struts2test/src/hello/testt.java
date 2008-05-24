package hello;

import com.opensymphony.xwork2.Action;

public class testt implements Action{
	public static final String List = "list"; 
	
	String para;

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}
	public String execute()  {  
		return "list";  
	}
}
