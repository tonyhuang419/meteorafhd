package net_home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

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




public class FormLoginDemo {
	static String urlStr = "www.kaixin001.com";
	static int port = 80;
	static String protocal = "http";


	public static void main(String[] args) throws IOException
	{	
		FormLoginDemo f = new FormLoginDemo();
		HttpClient client = f.loginUrl();
		if( client != null ){
//			f.parker(client); 
		}
		else{
			System.out.println("login fail");
		}
//		new Time().x();
		f.logout(client);
		
		
		if( client != null ){
			f.parker(client); 
		}
		else{
			System.out.println("login fail");
		}

	}


	private void parker( HttpClient client ){
		String methodUrl = "/app/app.php?aid=1040";
		client.getHostConfiguration().setHost(urlStr, port, protocal );
		HttpMethod method = getPostMethod(methodUrl);//使用POST方式提交数据
		try{
//			client.executeMethod(method);
//			String response =   new String(method.getResponseBodyAsString().getBytes("GBK"));
//			System.out.println(response);
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
			System.out.println(sb.toString());	
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}


	private HttpClient loginUrl(  ){
		String methodUrl = "/login/login.php";
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(urlStr, port, protocal );
		HttpMethod method = getPostMethod(methodUrl);//使用POST方式提交数据
		try{
			client.executeMethod(method);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
//		打印服务器返回的状态
//		System.out.println(method.getResponseBodyAsString());
//		System.out.println(method.getStatusLine());
//		打印结果页面
//		String response =   new String(method.getResponseBodyAsString().getBytes("GBK"));
//		打印返回的信息
//		System.out.println(response);
		method.releaseConnection();	
//		new FormLoginDemo().showCookie(urlStr,client);		
		int statuscode = method.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
				(statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
				(statuscode == HttpStatus.SC_SEE_OTHER) ||
				(statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) 
		{//读取新的URL地址
			Header header = method.getResponseHeader("location");
			if (header != null){
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals(""))){
					newuri = "/"; 
				}
				GetMethod redirect = new GetMethod(newuri);
				try{
					client.executeMethod(redirect);
//					System.out.println("Redirect:"+ redirect.getStatusLine().toString()); 
//					String responseX =   new String(redirect.getResponseBodyAsString().getBytes("GBK"));
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
//				打印返回的信息
//				System.out.println(responseX);

			} else {
				System.out.println("Invalid redirect");
				return null;
			}
		}
		return null;		
	}
	
	private void logout(HttpClient client){
		String methodUrl = "/login/logout.php";
		client.getHostConfiguration().setHost(urlStr, port, protocal );
		HttpMethod method = getGetMethod(methodUrl);//使用POST方式提交数据
		try{
			client.executeMethod(method);
			System.out.println("logout success");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}



	public void showCookie(String localSite ,HttpClient client){
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
		Cookie[] cookies = cookiespec.match("www.kaixin001.com", 80, "/", false, client.getState().getCookies());
		if (cookies.length == 0) {
			System.out.println("None");    
		} else {
			for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].toString());    
			}
		}
	}

	/** *//**
	 * 使用GET方式提交数据
	 * @return
	 */
	private static HttpMethod getGetMethod(String methodUrl){
		return new GetMethod(methodUrl);
	}
	/** *//**
	 * 使用POST方式提交数据
	 * @return
	 */
	private static HttpMethod getPostMethod(String methodUrl){
		String email = "meteorafhd@gmail.com";
		String pw = "...";
		PostMethod post = new PostMethod(methodUrl);
		NameValuePair x = new NameValuePair("url","/");
		NameValuePair name = new NameValuePair("email",email);
		NameValuePair password = new NameValuePair("password",pw);
		post.setRequestBody(new NameValuePair[] {x, name,password});
		return post;
	}
}

class Time  {
	public void x(){
		while(true){
			System.out.println(new Date());
			try{
				Thread.sleep(1000);
			}catch (InterruptedException ie){
				ie.printStackTrace();
			}

		}
	}
}
