package com.deployServer.commons;

public class ConstantUtil {


	public  static String ip = "10.60.2.92";
	public  static 	String	password = "jboss"; 
	public  static String baseUrlCommand = "cd jboss-3.2";
	public static String ftpBaseGLUrl = "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
	public static String ftpBaseCLUrl = "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";


	public static void setServerSign(String serverSign) {
		if( "formal".equals(serverSign) ){
			ip = "10.60.2.243";
			password = "jboss0819"; 
			baseUrlCommand = "cd jboss-3.2e";
			ftpBaseGLUrl = "\\data\\jboss\\jboss-3.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
			ftpBaseCLUrl = "\\data\\jboss\\jboss-3.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";
		}
	}

}
