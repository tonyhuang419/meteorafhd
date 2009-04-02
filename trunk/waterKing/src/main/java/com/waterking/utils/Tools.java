package com.waterking.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

public class Tools {

	synchronized static public  int getScanDetailNum(){
		String filePath = "src/main/resources/boarddetail.properties";
		String propName = "min";
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty (propName);
			Integer page = new Integer(value);
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(propName, new Integer(page+1000).toString());
			props.store(fos, "Update "+propName+" value");
			fos.close();
			in.close();
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	synchronized static String encodeUrl(String url){
		String s[] = url.split("/");
		String temp = "";
		try {
			temp = URLEncoder.encode(s[s.length-1],"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<s.length-1;i++){
			sb.append(s[i]).append("/");
		}
		sb.append(temp);
		return sb.toString();
	}

	
	public static void main(String args[]) throws Exception{
		System.out.println(Tools.encodeUrl("http://e.taisha.org/attachments/Img222981634[1]_k1V9GQsUA05L.jpg"));
	}
}
