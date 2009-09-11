package com.ftpUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;


public class UtilTools 
{
	
	public static String baseCLUrl = "/usr/jboss/jboss-3.2/server/default/deploy/bgcl.ear/bgcl.war/";
	public static String deployCLBaseUrl = "D:/ide/jboss/3.2.2/server/default/deploy/bgcl.ear/bgcl.war/";
	
	public static String uploadFile = "WEB-INF/deploy.wsdd";
	
	public static void main(String[] args) {   
		testUpload(baseCLUrl , null );   
		//testDownload();   
	}   

	/**  
	 * FTP上传单个文件测试  
	 */   
	public static void testUpload(String baseUrl , List<String> path) {  
		FTPClient ftpClient = new FTPClient();   
		FileInputStream fis = null;   

		try {   
			ftpClient.connect("10.60.2.92");   
			ftpClient.login("jboss", "jboss");   
			File srcFile = new File("C:/gdiplus.dll");   
			fis = new FileInputStream(srcFile);   
			
			//设置上传目录   
			ftpClient.changeWorkingDirectory(baseUrl);   
			ftpClient.setBufferSize(1024);   
			ftpClient.setControlEncoding("GBK");
			
			//设置文件类型（二进制）   
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);   
			ftpClient.storeFile("apache+tomcat.zip", fis);   
			System.out.println("成功！");  
		} catch (IOException e) {   
			e.printStackTrace();   
			throw new RuntimeException("FTP客户端出错！", e);   
		} finally {   
			IOUtils.closeQuietly(fis);   
			try {   
				ftpClient.disconnect();   
			} catch (IOException e) {   
				e.printStackTrace();   
				throw new RuntimeException("关闭FTP连接发生异常！", e);   
			}   
		}   
	}   

	/**  
	 * FTP下载单个文件测试  
	 */   
//	public static void testDownload() {   
//		FTPClient ftpClient = new FTPClient();   
//		FileOutputStream fos = null;   
//
//		try {   
//			ftpClient.connect("192.168.14.117");   
//			ftpClient.login("admin", "123");   
//
//			String remoteFileName = "/admin/pic/3.gif";   
//			fos = new FileOutputStream("c:/down.gif");   
//
//			ftpClient.setBufferSize(1024);   
//			//设置文件类型（二进制）   
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);   
//			ftpClient.retrieveFile(remoteFileName, fos);   
//		} catch (IOException e) {   
//			e.printStackTrace();   
//			throw new RuntimeException("FTP客户端出错！", e);   
//		} finally {   
//			IOUtils.closeQuietly(fos);   
//			try {   
//				ftpClient.disconnect();   
//			} catch (IOException e) {   
//				e.printStackTrace();   
//				throw new RuntimeException("关闭FTP连接发生异常！", e);   
//			}   
//		}   
//	}  
}
