package fhd.right.action;


//@Component("LoginTool")
public class LoginTool implements ILoginTool{
	public boolean validName(String name){
		if(name.equals("adminx")){
			return true;
		}
		return false;
	}
	
	public boolean validPwd(String pwd){
		if(pwd.equals("lzxx")){
			return true;
		}
		return false;
	}	
}
