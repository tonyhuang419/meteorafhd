package com.deployServer.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deployServer.util.AutomatedTelnetClient;

public class TelnetCommons {

	private static Log logger = LogFactory.getLog("TelnetCommons");
	
	public static void unzipUploadFiles (){
		try {
			AutomatedTelnetClient telnet = new AutomatedTelnetClient( ConstantUtil.ip , "jboss", ConstantUtil.password );
//			System.out.println(telnet.sendCommand("bash"));
//			System.out.println(telnet.sendCommand("cd jboss-3.2/server/default/log/"));
//			System.out.println(telnet.sendCommand("tail -f server.log"));
//			System.out.println(telnet.sendCommand("ps -ef"));
			
			logger.info(telnet.sendCommand("bash"));
			logger.info(telnet.sendCommand(ConstantUtil.baseUrlCommand ));
			
			logger.info(telnet.sendCommand("cd server/default/deploy/baosteel_travel.ear/baosteel_travel.war"));
			logger.info(telnet.sendCommand("unzip jsp.zip"));
			logger.info(telnet.sendCommand("cd WEB-INF/classes"));
			logger.info(telnet.sendCommand("unzip class.zip"));

			logger.info(telnet.sendCommand("cd .."));
			logger.info(telnet.sendCommand("cd .."));
			logger.info(telnet.sendCommand("cd .."));
			logger.info(telnet.sendCommand("cd .."));
			
			logger.info(telnet.sendCommand("cd bgcl.ear/bgcl.war"));			
			logger.info(telnet.sendCommand("unzip jsp.zip"));
			logger.info(telnet.sendCommand("cd WEB-INF/classes"));
			logger.info(telnet.sendCommand("unzip class.zip"));
			
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
