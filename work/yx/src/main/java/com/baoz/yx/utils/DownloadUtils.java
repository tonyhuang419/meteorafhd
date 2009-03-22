package com.baoz.yx.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * 类DownloadUtils.java的实现描述：下载方法
 * @author xurong Aug 26, 2008 4:24:53 PM
 */
public class DownloadUtils {
	/**
	 * 获得response输出流，用于下载
	 * @param fileName 下载框显示的文件名
	 * @return
	 */
	public static OutputStream getResponseOutput(String fileName){
		 HttpServletResponse oResponse = ServletActionContext.getResponse();
		 // Set the content type
         oResponse.setContentType("application/x-msdownload");
         try {
			//Set the content-disposition
			 oResponse.addHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"iso-8859-1"));
			 //// Get the outputstream
			 return oResponse.getOutputStream();
		}catch (Exception e) {
			new RuntimeException(e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 关闭response输出流
	 */
	public static void closeResponseOutput(){
		HttpServletResponse oResponse = ServletActionContext.getResponse();
		try {
			OutputStream os = oResponse.getOutputStream();
			os.flush();
			os.close();
		} catch (IOException e) {
			new RuntimeException(e.getMessage(),e);
		}
	}
}
