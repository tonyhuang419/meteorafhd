package com.ftpUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUpdate {

	private Log logger = LogFactory.getLog(this.getClass());
	private String perviousUploadPath;

	public static void main(String[] args) throws IOException {  
		FtpUpdate ftpUpdate = new FtpUpdate();
		List<String> uploadFileList = new ArrayList<String>();
		uploadFileList.add("WEB-INF/deploy.wsdd");
		uploadFileList.add("testibeclientSK.jsp");

		ftpUpdate.ftpUpdate(UtilTools.baseCLUrl ,uploadFileList , 2);   
	}

	public void ftpUpdate(String baseUrl , List<String> pathList , int sign) throws IOException {
		String baseUploadPath;
		String baseDeployPath;
		
		if(sign==1){ //国旅
			baseUploadPath = UtilTools.baseGLUrl;
			baseDeployPath = UtilTools.deployGLBaseUrl;
		}
		else if(sign==2){ //差旅
			baseUploadPath = UtilTools.baseCLUrl;
			baseDeployPath = UtilTools.deployCLBaseUrl;
		}
		else{
			return;
		}
		
		FTPClient ftpClient = new FTPClient();   
		ftpClient.setBufferSize(1024);   
		ftpClient.setControlEncoding("GBK");
		ftpClient =  UtilTools.login(ftpClient);
		

		for(String path : pathList ){
			String dir="";
			String fileName = path;
			String uploadPath = baseUploadPath;
			String filePath = baseDeployPath;
			int index = path.lastIndexOf("/");
			if(index!=-1){
				dir = path.substring(0,index+1);
				fileName = path.substring(index+1, path.length());
				uploadPath  +=  dir;
				filePath +=  dir;
			}
			
			
			logger.info(dir);
			logger.info(fileName);
			logger.info(uploadPath);
			logger.info(filePath);
			
//			this.upload(ftpClient, uploadPath, filePath, fileName);
		}

	}   


	public boolean upload( FTPClient  ftpClient  , String uploadPath , String filePath , String fileName) throws IOException{
		if( !perviousUploadPath.equals(uploadPath)){
			ftpClient.changeWorkingDirectory(uploadPath);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
		}
		File srcFile = new File(filePath+fileName);
		FileInputStream fis = new FileInputStream(srcFile);   
		ftpClient.storeFile(fileName, fis); 
		IOUtils.closeQuietly(fis);  
		return true;
	}


}
