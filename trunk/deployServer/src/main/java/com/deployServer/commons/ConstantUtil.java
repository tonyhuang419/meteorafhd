package com.deployServer.commons;

public class ConstantUtil {

	/**
	 * sever sign
	 */
	private static String serverSign = "test";

	
	public static String getIp(){
		if( isTestServer()  ){
			return "10.60.2.92";
		}
		return "10.60.2.243";
	}
	
	public static String getPassword(){
		if( isTestServer()  ){
			return "jboss";
		}
		return "jboss0819";
	}
	
	public static String getBaseUrl(){
		if( isTestServer()  ){
			return "cd jboss-3.2";
		}
		return "cd jboss-3.2e";
	}
	
	public static String getFtpBaseGLUrl(){
		if( isTestServer()  ){
			return "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
		}
		return "\\data\\jboss\\jboss-3.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
	}
	
	public static String getFtpBaseCLUrl(){
		if( isTestServer()  ){
			return "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";
		}
		return "\\data\\jboss\\jboss-3.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";	
	}
	
	public static boolean isTestServer(){
		if("test".equals(serverSign)){
			return true;
		}
		return false;
	}

	public static String getServerSign() {
		return serverSign;
	}

	public static void setServerSign(String serverSign) {
		ConstantUtil.serverSign = serverSign;
	}
	
}
