package socket.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketServer extends Thread {
	private ServerSocket serverSocket;

	public SocketServer() throws IOException {
		serverSocket = new ServerSocket(8008);
		serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket client = serverSocket.accept();
				System.out.println("Just connected to " + client.getRemoteSocketAddress());
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));  
				System.out.println("you input is : " + br.readLine());  
				client.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		try {
			Thread t = new SocketServer();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}