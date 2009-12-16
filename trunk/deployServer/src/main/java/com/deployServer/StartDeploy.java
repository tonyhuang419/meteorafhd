package com.deployServer;

import java.io.IOException;
import java.net.SocketException;

import com.deployServer.commons.Commons;
import com.deployServer.util.FtpUpload;

public class StartDeploy {

	
	public static void main(String[] args) throws SocketException, IOException{
		
		//将上传文件打包
		Commons.zipUploadFiles();
		
		//打包文件上传（struts配置文件、spring配置文件不做上传）
		FtpUpload.uploadFiles();
		
		
		
	}
	
}
