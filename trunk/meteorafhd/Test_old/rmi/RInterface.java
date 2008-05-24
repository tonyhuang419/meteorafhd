package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RInterface extends Remote{
    public void setInfo(EmpInfo _e1) throws RemoteException;
    public void getInfo() throws RemoteException; 
    public int test(int a,int b) throws RemoteException; 
}