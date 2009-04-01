package com.waterking.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownloadUtil extends Thread {

	String urlStr;
	String baseFilePath = "c:/pic/";

	public DownloadUtil(String urlStr){
		this.urlStr = urlStr;
		this.start();
	}

	public void run() {
		TaiShaTools tool = new TaiShaTools();
		HttpClient client = tool.login("e.taisha.org",80,"http");
		GetMethod getMethod = new GetMethod(urlStr);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		try
		{
			//执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK){
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			//读取内容
			byte[] responseBody = getMethod.getResponseBody();
			System.out.println(urlStr);
			String s[] = urlStr.split("/");
			String serverfile = "c:/pic/"+s[s.length-1];
			OutputStream serverout = new FileOutputStream(serverfile);

			serverout.write(responseBody);   
			serverout.flush();   
			serverout.close();   

			//处理内容
			//System.out.println(new String(responseBody));
			System.out.println("OK!");
		}
		catch (HttpException e)
		{
			//发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			//发生网络异常
			e.printStackTrace();
		}
		finally
		{
			//释放连接
			getMethod.releaseConnection();
		}
	}

	public static void main(String args[]){
		for(int i=0;i<10;i++){
			new DownloadUtil("http://www.163.com/images/neteaselogo.gif");
		}
	}
}
