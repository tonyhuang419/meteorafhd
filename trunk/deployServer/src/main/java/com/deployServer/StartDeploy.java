package com.deployServer;

import java.io.IOException;
import java.net.SocketException;

import com.deployServer.commons.ConstantUtil;
import com.deployServer.commons.TelnetCommons;
import com.deployServer.commons.ZipCommons;
import com.deployServer.util.FtpUpload;

public class StartDeploy {

	
	public static void main(String[] args) throws SocketException, IOException{
		ConstantUtil.setServerSign("formal");
		
		//备份项目
//		TelnetCommons.backUpProject();
		
		//将上传文件打包
//		ZipCommons.zipUploadFiles();
		
		//打包文件上传（struts配置文件、spring配置文件不做上传）
//		FtpUpload.uploadFiles();
		
		//解压打包文件
//		TelnetCommons.unzipUploadFiles();
		
	}
	
}
