package com.ftpUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUpdate {

	private static Log logger = LogFactory.getLog("FtpUpdate");
	private String perviousUploadPath;

	public static void main(String[] args) throws Exception {  
		UtilTools.init();
		FtpUpdate ftpUpdate = new FtpUpdate();
		List<String> uploadFileList = UtilTools.getUpateFile();
		ftpUpdate.ftpUpdate(UtilTools.baseCLUrl ,uploadFileList , UtilTools.CL );

		//		for(String s:uploadFileList){
		//			UtilTools.processPath(s);
		//			List<String> fileList = UtilTools.getAllFilePath();
		//		}
	}

	public void ftpUpdate(String baseUrl , List<String> pathList , int sign) throws IOException {
		String baseUploadPath;
		String baseDeployPath;

		if(sign==UtilTools.GL){ //国旅
			baseUploadPath = UtilTools.baseGLUrl;
			baseDeployPath = UtilTools.deployGLBaseUrl;
		}
		else if(sign==UtilTools.CL){ //差旅
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
			UtilTools.processPath(baseDeployPath+path);
			List<String> fileList = UtilTools.getAllFilePath();
			for(String s:fileList){
				int index = s.lastIndexOf("\\");
				String filePath =  s.substring( 0 , index+1);
				String fileName = s.substring( index+1, s.length());
				String uploadPath = baseUploadPath+s.substring(baseDeployPath.length() , index+1);

				//				logger.info(uploadPath);
				//				logger.info(filePath);
				//				logger.info(fileName);
				//				logger.info("------------------------");

				this.upload(ftpClient, uploadPath, filePath, fileName);
			}
		}
	}   


	public boolean upload( FTPClient  ftpClient  , String uploadPath , String filePath , String fileName) throws IOException{
		uploadPath = uploadPath.replaceAll("\\\\", "/");
		filePath = filePath.replaceAll("\\\\", "/");

//		if( !uploadPath.equals(perviousUploadPath)){
//			if(!ftpClient.changeWorkingDirectory(uploadPath)){
//				ftpClient.makeDirectory(uploadPath);
//				ftpClient.changeWorkingDirectory(uploadPath);
//			} 
//			perviousUploadPath = uploadPath;
//		}
		if( !uploadPath.equals(perviousUploadPath)){
			UtilTools.changeWorkingDirectory(ftpClient, uploadPath);
			perviousUploadPath = uploadPath;
		}
		
		logger.info("file: "+ filePath + fileName + " update to "+ perviousUploadPath  );
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
		File srcFile = new File(filePath+fileName);
		FileInputStream fis = new FileInputStream(srcFile);   
		ftpClient.storeFile(fileName, fis); 
		IOUtils.closeQuietly(fis);  
		return true;
	}


}
