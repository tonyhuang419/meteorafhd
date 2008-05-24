package fhd.right.action;

public class MyServiceImpl implements MyService{
	public boolean valid(String name,String pwd){
		if(name.equals("xxxx")){
			return true;
		}
		return false;
	}
}
