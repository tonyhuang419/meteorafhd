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

	public static int GL = 1;
	public static int CL = 2;
	public static String ftpIp;
	public static String ftpUsername;
	public static String ftpPassword;
	public static String baseCLUrl;
	public static String baseGLUrl;
	public static String deployCLBaseUrl;
	public static String deployGLBaseUrl;

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


	public static List<String> processPath(List<String> pathList){
		List<String> allFilePath = new ArrayList<String>();
		for(String path : pathList){
			processPath(path);
			allFilePath.addAll(allFilePath);
		}
		return allFilePath;
	}


	private static List<String> allFilePath = new ArrayList<String>();
	public static void processPath(String path){
		if(path.indexOf("svn")!=-1){
			return;
		}
		File parentF = new File(path);
		if(parentF.isFile()){
			allFilePath.add(path);
			return;
		}
		String[] subFiles = parentF.list();
		for (int i = 0; i < subFiles.length; i++){
			processPath(parentF.getAbsolutePath() + "\\" + subFiles[i]);
		}
	}
	
	public static List<String> getAllFilePath(){
		List<String> allFilePathX = new ArrayList<String>();
		allFilePathX.addAll(allFilePath);
		allFilePath = new ArrayList<String>();
		return allFilePathX;
	}


	public static Properties readProp( String filePath ) throws Exception {
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

}
