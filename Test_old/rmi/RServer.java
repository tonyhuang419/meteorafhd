package rmi;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class RServer extends UnicastRemoteObject implements RInterface{

	private static final long serialVersionUID = 7886897019122501319L;
	//List<EmpInfo> empList = new ArrayList<EmpInfo>();
	EmpInfo e = new EmpInfo();
	public RServer() throws RemoteException{}
    
    public void setInfo(EmpInfo _e1){
    	//EmpInfo info = new EmpInfo(_e1.name,_e1.dept);
    	//empList.add(info);
    	e.name = _e1.name;
    	e.dept = _e1.dept;
    	System.out.println("has add"+e.name+"/n"+e.dept);
    }
    public void getInfo( ){
    //	for(Iterator i=empList.iterator();i.hasNext();){
    	//	EmpInfo e = (EmpInfo)i.next();
    		System.out.println("this is getInfo"+e.name+"/n"+e.dept);
    //	}
    }
    public int test(int a,int b){
    	return a+b;
    }
    public static void main(String[] args){
    	try{
    		RServer r = new RServer();
    		Naming.bind("myOJ",r);
    		System.out.print("Server Ready");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
}