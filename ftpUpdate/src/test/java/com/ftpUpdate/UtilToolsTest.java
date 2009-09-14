package com.ftpUpdate;

import java.util.List;

import org.junit.Test;

public class UtilToolsTest {
	
	
	//@Test
	public void init(){
		try {
			UtilTools.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(UtilTools.ftpIp);
		System.out.println(UtilTools.ftpUsername);
		System.out.println(UtilTools.ftpPassword);
		System.out.println(UtilTools.baseCLUrl);
		System.out.println(UtilTools.baseGLUrl);
		System.out.println(UtilTools.deployCLBaseUrl);
		System.out.println(UtilTools.deployGLBaseUrl);
	}
	
	
	//@Test
	public void processPath(){
		UtilTools.processPath("D:\\workspace\\fstock\\src\\main");
		for(String s : UtilTools.getAllFilePath()){
			System.out.println(s);
		}
	}
	
	@Test
	public void getUpateFile() throws Exception{
		List<String> list = UtilTools.getUpateFile();
		for(String s : list ){
			System.out.println(s);
		}
	}
	
}
