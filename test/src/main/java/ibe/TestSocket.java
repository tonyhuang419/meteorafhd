package ibe;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocket {

	public static void main(String args[]){
		try {
			Socket skt = new Socket("www.sina.com",80);
			OutputStream outStream = skt.getOutputStream();
//			PrintWriter p = new PrintWriter(outStream);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
