package socket.test2;

import java.net.Socket;
public class Client {  
	public static void main(String[] args) {  
		try {  
			Socket socket = new Socket("127.0.0.1", 30001);  
			socket.setKeepAlive(true);           //java.net.SocketException: Connection reset  
//			while(true && null != socket){       
				Thread.sleep(10 * 1000);         //java.net.SocketTimeoutException: Read timed out  
//			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		
	}  
}  