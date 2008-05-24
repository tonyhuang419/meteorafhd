package rmi2;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RInterface extends Remote{
    public void setName(String _name) throws RemoteException;
    public void setDept(String _dept) throws RemoteException;
    public void getInfo() throws RemoteException; 
}