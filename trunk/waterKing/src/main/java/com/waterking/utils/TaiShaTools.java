package com.waterking.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaiShaTools {
	static String username = "非法_用户";
	static String password = "happyamiga";

	protected Log logger = LogFactory.getLog(this.getClass());


	public HttpClient login(String urlStr, int port, String protocal ){

		String methodUrl = "/index.php";
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(urlStr, port, protocal );

		PostMethod post = new PostMethod(methodUrl);
		NameValuePair name = new NameValuePair("username",username);
		NameValuePair passwordX = new NameValuePair("password",password);
		post.setRequestBody(new NameValuePair[] { name,passwordX});

		try{
			int status = client.executeMethod(post);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}

		String str ;
		StringBuffer sb = new StringBuffer();
		InputStream in;
		try {
			in = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"),1024);
			while( (str = br.readLine()) !=null){
				sb.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		post.releaseConnection();
		return client;
	}


	/**
	 *  showCookie
	 */
	public void showCookie(HttpClient client){
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match("e.taisha.org", 80, "/", false, client.getState().getCookies());
		if (cookies.length == 0) {
			logger.info("none");    
		} else {
			for (int i = 0; i < cookies.length; i++) {
				logger.info(cookies[i].toString());    
			}
		}
	}

	public static void main(String args[]){
		TaiShaTools tool = new TaiShaTools();
		HttpClient client = tool.login("e.taisha.org",80,"http");
		//		tool.showCookie( client);
	}

}
