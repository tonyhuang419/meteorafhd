package com.waterking.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class DownloadUtil {

	String baseFilePath = "c:/pic/";

	public void downFile(String urlStr , String postId ,String topic ){
		HttpClient client = new HttpClient();
		client.setTimeout(30000);
		GetMethod getMethod = new GetMethod(urlStr);

		topic = topic.replaceAll("\\|/|:|\\*|\\?|\"|<|>|\\|", "");
		CreateDir.mkdir(baseFilePath+postId+"-"+topic);
		baseFilePath = baseFilePath+postId+"-"+topic+"/";
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
