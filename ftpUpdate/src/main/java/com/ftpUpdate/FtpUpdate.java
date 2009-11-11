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

	//更新国旅、差旅标志位
	private static final int UPDATESERVER  = UtilTools.ALL;
	private static Log logger = LogFactory.getLog("FtpUpdate");
	private String perviousUploadPath;
	
	public static void main(String[] args) throws Exception {  
		UtilTools.init();
		FtpUpdate ftpUpdate = new FtpUpdate();
		List<String> uploadFileList = UtilTools.getUpateFile();
		
		if( UPDATESERVER == UtilTools.ALL){
			ftpUpdate.ftpUpdate( uploadFileList , UtilTools.GL );
			ftpUpdate.ftpUpdate( uploadFileList , UtilTools.CL );
		}
		else{
			ftpUpdate.ftpUpdate( uploadFileList , UPDATESERVER );
		}
	}

	public void ftpUpdate( List<String> pathList , int sign) throws IOException {
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
			//获取文件列表
			List<String> fileList = UtilTools.getAllFilePath(baseDeployPath+path);
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
		//测试服务期为Linux，需要路径通配符兼容
		uploadPath = uploadPath.replaceAll("\\\\", "/");
		filePath = filePath.replaceAll("\\\\", "/");

		//检查上传路径是否和上次相同，相同的话就不做改动，不同改之
		if( !uploadPath.equals(perviousUploadPath)){
			UtilTools.changeWorkingDirectory(ftpClient, uploadPath);
			perviousUploadPath = uploadPath;
		}
		
		logger.info("file: "+ filePath + fileName + " \r\n update to "+ perviousUploadPath  );
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
		File srcFile = new File(filePath+fileName);
		FileInputStream fis = new FileInputStream(srcFile);   
		ftpClient.storeFile(fileName, fis); 
		IOUtils.closeQuietly(fis);  
		return true;
	}


}
