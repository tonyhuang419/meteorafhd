package com.waterking.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class DownloadUtil extends Thread{

	private String baseFilePath = "c:/pic/";

	public void downFile(String urlStr , String postId ,String topic){
		try {
			urlStr = URLEncoder.encode(urlStr,"GBK");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		System.out.println(urlStr);
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
			client.executeMethod(getMethod);
			InputStream in = getMethod.getResponseBodyAsStream();
			String s[] = urlStr.split("/");
			File fileOut = new File(baseFilePath+System.currentTimeMillis());
			FileOutputStream out = new FileOutputStream(fileOut+"&&"+s[s.length-1]);
			byte[] bytes = new byte[1024];
			int c;
			while((c = in.read(bytes))!=-1) {
				out.write(bytes,0,c);
			}
			out.flush();  
			out.close();
			in.close();
		} catch(Exception e) {
			System.out.println("dowload error!");
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new DownloadUtil().downFile("http://e.taisha.org/attachments/Img222981634[1]_k1V9GQsUA05L.jpg", "xx", "xx");
	}

}

//class GBPostMethod extends GetMethod{
//	public GBPostMethod(String url){
//		super(url);
//	}
//	@Override
//	public String getRequestCharSet() {
//		//return super.getRequestCharSet();
//		return "GB2312";
//	}
//}
