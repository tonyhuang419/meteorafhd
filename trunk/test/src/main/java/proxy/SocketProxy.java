package proxy;

//http://blog.csdn.net/java2000_net/article/details/7826660
import java.net.ServerSocket;
import java.net.Socket;

public class SocketProxy {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				new SocketThread(socket).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
