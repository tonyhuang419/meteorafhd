package com.cheatkx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Login {

	protected Log logger = LogFactory.getLog(this.getClass());
	

	public HttpClient login(String urlStr, int port, String protocal ){
		String methodUrl = "/login/login.php";
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(urlStr, port, protocal );
		
		String email = "meteorafhd@gmail.com";
		String pw = "happyamiga";
		PostMethod post = new PostMethod(methodUrl);
		NameValuePair x = new NameValuePair("url","/");
		NameValuePair name = new NameValuePair("email",email);
		NameValuePair password = new NameValuePair("password",pw);
		post.setRequestBody(new NameValuePair[] {x, name,password});
		
//		HttpMethod method = getPostMethod(methodUrl);
		
		HttpMethod method = post;
		
		try{
			client.executeMethod(method);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		method.releaseConnection();		
		int statuscode = method.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
				(statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
				(statuscode == HttpStatus.SC_SEE_OTHER) ||
				(statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) 
		{
			Header header = method.getResponseHeader("location");
			if (header != null){
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals(""))){
					newuri = "/"; 
				}
				GetMethod redirect = new GetMethod(newuri);
				try{
					client.executeMethod(redirect);

					String str ;
					StringBuffer sb = new StringBuffer();
					InputStream in = redirect.getResponseBodyAsStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"),1024);
					while( (str = br.readLine()) !=null){
						if ( str.indexOf("我的首页 - 开心网")!=-1 ){
//							System.out.println("1");
							return client;
						}
						else{
							sb.append(str);
						}
					}
					in.close();
					redirect.releaseConnection();
					if( sb.indexOf("我的首页 - 开心网")!=-1 ){
						return client;
					}
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			} else {
				logger.info("Invalid redirect");
				return null;
			}
		}
		return null;		
	}
	
	public void logout(HttpClient client,String urlStr,int port,String protocal){
		String methodUrl = "/login/logout.php";
		client.getHostConfiguration().setHost(urlStr, port, protocal );
//		HttpMethod method = getGetMethod(methodUrl);
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			logger.info("logout success");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}


	public void showCookie(String localSite ,HttpClient client){
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match("www.kaixin001.com", 80, "/", false, client.getState().getCookies());
		if (cookies.length == 0) {
			logger.info("none");    
		} else {
			for (int i = 0; i < cookies.length; i++) {
				logger.info(cookies[i].toString());    
			}
		}
	}

}
