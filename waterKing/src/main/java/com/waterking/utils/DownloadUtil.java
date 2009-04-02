package com.waterking.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class DownloadUtil extends Thread{

	private String baseFilePath = "c:/pic/";
	private String urlStr;
	private String postId;
	private String topic;	

	public DownloadUtil(String urlStr , String postId ,String topic){
		this.urlStr = urlStr;
		this.postId = postId;
		this.topic = topic;
		this.start();
	}

	public void run(){
		HttpClient client = new HttpClient();
		client.setTimeout(15000);
		GetMethod getMethod = new GetMethod(urlStr);

		topic = topic.replaceAll("\\|/|:|\\*|\\?|\"|<|>|\\|", "");
		if(CreateDir.mkdir(baseFilePath+postId+"-"+topic).equals("error")){
			baseFilePath = baseFilePath+"error/";
		}else{
			baseFilePath = baseFilePath+postId+"-"+topic+"/";
		}
		try {
			getMethod = new GetMethod(urlStr);
			client.executeMethod(getMethod);
			byte[] responseBody = getMethod.getResponseBody();

			String s[] = urlStr.split("/");
			File fileOut = new File(baseFilePath+System.currentTimeMillis());
			FileOutputStream out = new FileOutputStream(fileOut+"&&"+s[s.length-1]);
			out.write(responseBody);
			out.flush();  
			out.close();
		} catch(Exception e) {
			System.out.println("dowload error!");
			e.printStackTrace();
		}
	}
}
