package com.waterking.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.net.URL;

public class DownloadUtil extends Thread {

	String urlStr;
	String baseFilePath = "c:/pic/";

	public DownloadUtil(String urlStr){
		this.urlStr = urlStr;
		this.start();
	}

	public void run() {
		try {
			URL url = null;
			try {
				url = new URL(urlStr);
			} catch(Exception e) {
				System.out.println("url error");
			}
			String s[] = urlStr.split("/");
			FilterInputStream in = (FilterInputStream) url.openStream();
			File fileOut = new File(s[s.length-1]);
			FileOutputStream out = new FileOutputStream(fileOut);
			byte[] bytes = new byte[1024];
			int c;
			while((c = in.read(bytes))!=-1) {
				out.write(bytes,0,c);
			}
			in.close();
			out.close();
		} catch(Exception e) {
			System.out.println("dowload error!");
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		for(int i=0;i<10;i++){
			new DownloadUtil("http://www.163.com/images/neteaselogo.gif");
		}
	}
}
