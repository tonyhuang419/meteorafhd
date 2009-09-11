package com.ftpUpdate;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;


public class UtilTools 
{
	public static String ftpIp = "10.60.2.92";
	public static String ftpUsername = "jboss";
	public static String ftpPassword = "jboss";
	public static String baseCLUrl = "/usr/jboss/jboss-3.2/server/default/deploy/bgcl.ear/bgcl.war/";
	public static String baseGLUrl = "/usr/jboss/jboss-3.2/server/default/deploy/baosteel_travel.ear/baosteel_travel.war/";
	public static String deployCLBaseUrl = "D:/ide/jboss/3.2.2/server/default/deploy/bgcl.ear/bgcl.war/";
	public static String deployGLBaseUrl = "D:/ide/jboss/3.2.2/server/default/deploy/baosteel_travel.ear/baosteel_travel.war";


	public static FTPClient login(FTPClient ftpClient) throws SocketException, IOException{
		ftpClient.connect(UtilTools.ftpIp);   
		ftpClient.login( UtilTools.ftpUsername , UtilTools.ftpPassword);
		return ftpClient;
	}


}
