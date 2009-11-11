package com.ftpUpdate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;


public class UtilTools {

	public final static int GL = 1;
	public final static int CL = 2;
	public final static int ALL = 3;
	public static String ftpIp;
	public static String ftpUsername;
	public static String ftpPassword;
	public static String baseCLUrl;
	public static String baseGLUrl;
	public static String deployCLBaseUrl;
	public static String deployGLBaseUrl;
	
	//allFilePath为全局变量，单线程使用
	volatile private static List<String> allFilePath = new ArrayList<String>();

	public static FTPClient login(FTPClient ftpClient) throws SocketException, IOException{
		ftpClient.connect(UtilTools.ftpIp);   
		ftpClient.login( UtilTools.ftpUsername , UtilTools.ftpPassword);
		return ftpClient;
	}

	public static void init() throws Exception{
		Properties props = UtilTools.readProp("src/main/resources/ftp.properties");
		ftpIp = props.getProperty("ftpIp");
		ftpUsername = props.getProperty("ftpUsername");
		ftpPassword = props.getProperty("ftpPassword");
		baseCLUrl = props.getProperty("baseCLUrl");
		baseGLUrl = props.getProperty("baseGLUrl");
		deployCLBaseUrl = props.getProperty("deployCLBaseUrl");
		deployGLBaseUrl = props.getProperty("deployGLBaseUrl");
	}

	private static void processPath(String path){
		char wildcard = path.charAt(path.length()-1);
		
		//如果有星号，截取
		if(wildcard=='*'){
			path = path.substring(0,path.length()-1);
		}
		
		//跳过svn
		if(path.indexOf("svn")!=-1){
			return;
		}
		
		File parentF = new File(path);
		
		//对路径检查，如果是文件，加入列表
		if(parentF.isFile()){
			allFilePath.add(path);
			return;
		}
		
		//如果是文件夹，获取所有
		String[] subFiles = parentF.list();
		for (int i = 0; i < subFiles.length; i++){
			//如果通配符为星号，就在列表中放入所有文件
			if(wildcard=='*'){
				String tempPath = parentF.getAbsolutePath() + "\\" + subFiles[i];
				File f = new File(tempPath);
				if(f.isFile()){
					allFilePath.add(tempPath);
				}
			}
			//否则进入递归
			else{
				processPath(parentF.getAbsolutePath() + "\\" + subFiles[i]);
			}
		}
	}

	public static List<String> getAllFilePath(String path){
		UtilTools.processPath(path);
		List<String> allFilePathX = new ArrayList<String>();
		allFilePathX.addAll(allFilePath);
		allFilePath = new ArrayList<String>();
		return allFilePathX;
	}


	private static Properties readProp( String filePath ) throws Exception {
		Properties props = new Properties();
		InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		props.load(in);
		in.close();
		return props;
	}

	public static List<String> getUpateFile() throws Exception {
		List<String> list = new ArrayList<String>();
		File  file  =  new  File("src/main/resources/updateFile.txt");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		while( scanner.hasNext() ){
			String line = scanner.next();
			list.add(line);
		}
		return list;
	}

//	public static boolean isDirExist(FTPClient ftpClient ,String dir){  
//		try {  
//			ftpClient.cwd(dir);
//		}catch(Exception e){  
//			return false;  
//		}  
//		return true;   
//	} 
	
	public static void changeWorkingDirectory( FTPClient  ftpClient , String uploadPath ) throws IOException{
		String[] sArr = uploadPath.split("/");
		String tempPath="/";
		for(int i=1;i<sArr.length;i++){
			tempPath = tempPath+"/"+sArr[i];
			if(!ftpClient.changeWorkingDirectory(tempPath)){
				ftpClient.makeDirectory(tempPath);
			}
		}
		ftpClient.changeWorkingDirectory(tempPath);
	}


}
