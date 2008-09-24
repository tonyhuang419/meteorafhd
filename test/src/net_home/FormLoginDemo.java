package net_home;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class FormLoginDemo {

	public static void main(String[] args) throws IOException
	{
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("www.kaixin001.com", 80, "http");
		HttpMethod method = getPostMethod();//使用POST方式提交数据
		client.executeMethod(method);
		//打印服务器返回的状态
		System.out.println(method.getStatusLine());
		//打印结果页面
		String response =   new String(method.getResponseBodyAsString().getBytes("GBK"));
		//打印返回的信息
		System.out.println(response);
		method.releaseConnection();

		int statuscode = method.getStatusCode();
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
				(statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
				(statuscode == HttpStatus.SC_SEE_OTHER) ||
				(statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) 
		{//读取新的URL地址
			Header header = method.getResponseHeader("location");
			if (header != null){
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals("")))
					newuri = "/"; 
				GetMethod redirect = new GetMethod(newuri);
				client.executeMethod(redirect);
				System.out.println("Redirect:"+ redirect.getStatusLine().toString()); 
				
				response =   new String(redirect.getResponseBodyAsString().getBytes("GBK"));
				//打印返回的信息
				System.out.println(response);
				
				
				redirect.releaseConnection();
			} else {
				System.out.println("Invalid redirect");
			}
		}
	}
	/** *//**
	 * 使用GET方式提交数据
	 * @return
	 */
	private static HttpMethod getGetMethod(){
		return new GetMethod("/login/login.php");
	}
	/** *//**
	 * 使用POST方式提交数据
	 * @return
	 */
	private static HttpMethod getPostMethod(){
		PostMethod post = new PostMethod("/login/login.php");
		NameValuePair x = new NameValuePair("url","/");
		NameValuePair simcard = new NameValuePair("email","meteorafhd@gmail.com");
		NameValuePair simcard2 = new NameValuePair("password","happyamiga");
		post.setRequestBody(new NameValuePair[] {x, simcard,simcard2});
		return post;
	}
}
