package com.deployServer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;

import com.deployServer.commons.Commons;

public class FtpUpload {

	private static Log logger = LogFactory.getLog("FtpUpload");
	public static String ftpIp = "10.60.2.92";
	public static String ftpUsername = "jboss";
	public static String ftpPassword = "jboss";
	public static String baseGLUrl = "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
	public static String baseCLUrl = "\\usr\\jboss\\jboss-3.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";
	

	public static FTPClient login(FTPClient ftpClient) throws SocketException, IOException{
		ftpClient.connect(ftpIp);   
		ftpClient.login( ftpUsername , ftpPassword);
		return ftpClient;
	}
	
	public static void uploadFiles() throws SocketException, IOException{
		FTPClient ftpClient = new FTPClient();   
		ftpClient.setBufferSize(1024);   
		ftpClient.setControlEncoding("GBK");
		ftpClient = login(ftpClient);
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
		
		String uploadPath = baseGLUrl;
		uploadPath = FtpUpload.changeWorkingDirectory(ftpClient, uploadPath);
		FileInputStream fis = new FileInputStream( Commons.deployPath+"\\gljsp.zip");   
		ftpClient.storeFile("jsp.zip", fis); 
		
		
		uploadPath = baseGLUrl+"/WEB-INF/classes";
		uploadPath = FtpUpload.changeWorkingDirectory(ftpClient, uploadPath);
		fis = new FileInputStream( Commons.deployPath+"\\glclass.zip");   
		ftpClient.storeFile("class.zip", fis); 
		
		uploadPath = baseCLUrl;
		uploadPath = FtpUpload.changeWorkingDirectory(ftpClient, uploadPath);
		fis = new FileInputStream( Commons.deployPath+"\\cljsp.zip");   
		ftpClient.storeFile("jsp.zip", fis); 
		
		
		uploadPath = baseCLUrl+"/WEB-INF/classes";
		uploadPath = FtpUpload.changeWorkingDirectory(ftpClient, uploadPath);
		ftpClient.changeWorkingDirectory(baseCLUrl+"\\WEB-INF\\classes");
		fis = new FileInputStream( Commons.deployPath+"\\clclass.zip");   
		ftpClient.storeFile("class.zip", fis); 
		
		IOUtils.closeQuietly(fis);  
		
	}
	
	
	public static String changeWorkingDirectory( FTPClient  ftpClient , String uploadPath ) throws IOException{
		uploadPath = uploadPath.replaceAll("\\\\", "/");
		ftpClient.changeWorkingDirectory(uploadPath);
		logger.info(uploadPath);
		return uploadPath;
	}
	
	
	public static void main(String[] args) throws SocketException, IOException{
		FtpUpload.uploadFiles();
	}
	
	
}
