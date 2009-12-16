package com.deployServer.util;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * http://twit88.com/blog/2007/12/22/java-writing-an-automated-telnet-client/
 */
public class AutomatedTelnetClient {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = "$";

	public AutomatedTelnetClient(String server, String user, String password) {
		try {
			// Connect to the specified server
			telnet.connect(server, 23);

			// Get input and output stream references
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			// Log the user on
			readUntil("login: ");
			write(user);
			readUntil("Password: ");
			write(password);

			// Advance to a prompt
			readUntil(prompt + " ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void su(String password) {
//		try {
//			write("su");
//			readUntil("Password: ");
//			write(password);
//			prompt = "#";
//			readUntil(prompt + " ");
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
//			boolean found = false;
			char ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						String rs = sb.toString();
						rs = new String(rs.getBytes("iso8859-1"), "GBK"); 
						return rs;
					}
				}
				ch = (char) in.read();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String value) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			if(command.indexOf("unzip")!=-1){
				write("A");
			}
			return readUntil(prompt + " ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		try {
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			AutomatedTelnetClient telnet = new AutomatedTelnetClient("10.60.2.92", "jboss", "jboss");
//			System.out.println(telnet.sendCommand("bash"));
//			System.out.println(telnet.sendCommand("cd jboss-3.2/server/default/log/"));
//			System.out.println(telnet.sendCommand("tail -f server.log"));
			System.out.println(telnet.sendCommand("ps -ef"));
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
