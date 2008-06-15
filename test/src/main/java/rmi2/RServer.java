package rmi2;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


public class RServer extends UnicastRemoteObject implements RInterface{
	
	private static final long serialVersionUID = -6115234837047685799L;
	String temp;
	static RServerUI rs = new RServerUI("Server Demo");
	public RServer() throws RemoteException{}
	public void setName(String _name) throws RemoteException {
		temp = rs.getAreaTest();
		rs.setAreaName(temp + _name);
	}
	
	public void setDept(String _dept) throws RemoteException {
		Date   now = new   Date();
		temp = rs.getAreaTest()+":";	
		rs.setAreaDept(temp + _dept+"¡ª¡ª"+now.toString()+"\n");
	}

	public void getInfo(){
    		//System.out.println("this is getInfo");
    }
	
	public static void main(String[] args) {
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



	

