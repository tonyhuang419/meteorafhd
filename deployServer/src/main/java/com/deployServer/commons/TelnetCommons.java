package com.deployServer.commons;

import com.deployServer.util.AutomatedTelnetClient;

public class TelnetCommons {

	public static void unzipUploadFiles (){
		try {
			AutomatedTelnetClient telnet = new AutomatedTelnetClient("10.60.2.92", "jboss", "jboss");
//			System.out.println(telnet.sendCommand("bash"));
//			System.out.println(telnet.sendCommand("cd jboss-3.2/server/default/log/"));
//			System.out.println(telnet.sendCommand("tail -f server.log"));
//			System.out.println(telnet.sendCommand("ps -ef"));
			
			System.out.println(telnet.sendCommand("bash"));
			System.out.println(telnet.sendCommand("cd jboss-3.2"));
			
			System.out.println(telnet.sendCommand("cd server/default/deploy/baosteel_travel.ear/baosteel_travel.war"));
			System.out.println(telnet.sendCommand("unzip jsp.zip"));
			System.out.println(telnet.sendCommand("cd WEB-INF/classes"));
			System.out.println(telnet.sendCommand("unzip classes.zip"));

			System.out.println(telnet.sendCommand("cd .."));
			System.out.println(telnet.sendCommand("cd .."));
			System.out.println(telnet.sendCommand("cd .."));
			System.out.println(telnet.sendCommand("cd .."));
			
			System.out.println(telnet.sendCommand("cd bgcl.ear/bgcl.war"));			
			System.out.println(telnet.sendCommand("unzip jsp.zip"));
			System.out.println(telnet.sendCommand("cd WEB-INF/classes"));
			System.out.println(telnet.sendCommand("unzip classes.zip"));
			
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
