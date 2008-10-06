package com.cheatkx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Parker {
	
	static String urlStr = "www.kaixin001.com";
	static int port = 80;
	static String protocal = "http";

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public static void main(String[] args) throws IOException
	{
		Login login = new Login();
		Parker p = new Parker();
		HttpClient client = login.login(urlStr,port,protocal);
		if( client != null ){
			p.parker(client); 
		}
		else{
			System.out.println("login fail");
		}
		login.showCookie(urlStr, client);
		login.logout(client,urlStr,port,protocal);
		
	}
	
	
	private void parker( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1040";
		client.getHostConfiguration().setHost(urlStr, port, protocal );		
//		HttpMethod method = getPostMethod(methodUrl);//使用POST方式提交数据
		PostMethod post = new PostMethod(methodUrl);
		HttpMethod method  = post;
		try{
			client.executeMethod(method);
			String str;
			StringBuffer sb = new StringBuffer();
			InputStream in = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"),1024);
			while( (str = br.readLine()) !=null){
				sb.append(str).append("\n");
			}
			in.close();
			method.releaseConnection();
			logger.info(sb.toString());	
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
