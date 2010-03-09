package com.deployServer.commons;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deployServer.util.AntZip;


public class ZipCommons {

	private static Log logger = LogFactory.getLog("ZipCommons");
	final static public String deployGLPath = "D:\\ide\\jboss\\3.2.2\\server\\default\\deploy\\baosteel_travel.ear\\baosteel_travel.war";
	final static public String deployCLPath = "D:\\ide\\jboss\\3.2.2\\server\\default\\deploy\\bgcl.ear\\bgcl.war";
	final static public String deployPathSuffixJspGL = "\\gl";
	final static public String deployPathSuffixJspCL = "\\cl";
	final static public String deployPathSuffixClass = "\\WEB-INF\\classes\\com";
	final static public String deployPathSuffixHibernate = "\\WEB-INF\\classes\\hibernate3";
	final static public String deployPath = "C:\\glclDeploy";
	
	public static void zipUploadFiles(){
		AntZip zip = new AntZip();
		
		logger.info("make gljsp.zip");
		String[] zipFiles = new String[]{deployGLPath+deployPathSuffixJspGL,deployGLPath+deployPathSuffixJspCL};	
		zip.doZip(getFiles(zipFiles) , deployPath+"\\gljsp.zip" );
		
		logger.info("make glclass.zip");
		zipFiles = new String[]{deployGLPath+deployPathSuffixClass , deployGLPath+deployPathSuffixHibernate };	
		zip.doZip(getFiles(zipFiles) , deployPath+"\\glclass.zip" );
		
		logger.info("make cljsp.zip");
		zipFiles = new String[]{deployCLPath+deployPathSuffixJspGL,deployCLPath+deployPathSuffixJspCL};	
		zip.doZip(getFiles(zipFiles) , deployPath+"\\cljsp.zip" );
		
		logger.info("make clclass.zip");
		zipFiles = new String[]{deployCLPath+deployPathSuffixClass , deployCLPath+deployPathSuffixHibernate};	
		zip.doZip(getFiles(zipFiles) , deployPath+"\\clclass.zip" );
		
	}
	
	private static File[] getFiles(String[] strArr){
		File[] files = new File[ strArr.length];
		for(int i = 0;i < strArr.length; i++){
			files[i] = new File(strArr[i]);
		}
		return files;
	}
	
	
}
