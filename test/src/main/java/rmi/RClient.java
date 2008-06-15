package rmi;
import java.rmi.Naming;
public class RClient {
    public static void main(String[] args) {
    	  new  RClient().go();
    }  
    public void go(){
    	try{
    		RInterface rr = (RInterface)Naming.lookup("rmi://127.0.0.1/myOJ");
    		System.out.println(rr.test(1,2));
    		rr.setInfo(new EmpInfo("1","2"));
		//	rr.setInfo(new EmpInfo("3","4"));
		//	rr.setInfo(new EmpInfo("5","6"));
			//rr.getInfo();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}