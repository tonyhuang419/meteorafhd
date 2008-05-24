package rmi;
import java.io.Serializable;
public class EmpInfo implements Serializable{
	private static final long serialVersionUID = 737714721675960192L;
		String name;
		String dept;
		EmpInfo(){ }
		EmpInfo(String _name,String _dept){
			name = _name;
			dept = _dept;
		}	
}