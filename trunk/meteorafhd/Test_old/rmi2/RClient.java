package rmi2;

import java.rmi.Naming;
public class RClient {
    public void go(String _name,String _dept){
    	try{
    		RInterface rr = (RInterface)Naming.lookup("rmi://127.0.0.1/myOJ");
    		rr.setName(_name);
    		rr.setDept(_dept);		
    		rr.getInfo();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
    }
}