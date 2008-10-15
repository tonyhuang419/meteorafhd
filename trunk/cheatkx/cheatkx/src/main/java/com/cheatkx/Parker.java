package com.cheatkx;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Parker {
	
	static String urlStr = "www.kaixin001.com";
	static int port = 80;
	static String protocal = "http";
	String g_verify;       				//验证字符串

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public static void main(String[] args) throws IOException
	{
		Login login = new Login();
		Parker p = new Parker();
		HttpClient client = login.login(urlStr,port,protocal);
		if( client != null ){	
			p.parker(client); 
			p.showUserCar(client);
		}
		else{
//			here can't use logger.info
//			Cannot make a static reference to the non-static field logger
//			System.out.println("login fail");
		}
//		login.showCookie(urlStr, client);
		login.logout(client,urlStr,port,protocal);	
	}
	

	private void parker( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1040";
//		client.getHostConfiguration().setHost(urlStr, port, protocal );		
//		HttpMethod method = getPostMethod(methodUrl);//使用POST方式提交数据
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
			logger.info(" v_userdata: "+Tools.processUserData(context));
			logger.info("..........................");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	

	private void showUserCar( HttpClient client ){
		String verify = g_verify;
		String puid = "3327550";
		String methodUrl = "/parking/usercar.php?verify="+verify+"&puid="+puid;
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
//			String context = Tools.printInfo(method);		
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	

}
