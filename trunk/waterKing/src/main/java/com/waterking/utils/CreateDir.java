package com.waterking.utils;

import java.io.File;

public class CreateDir {

	synchronized public static String  mkdir(String mkdirName)
	{
		try
		{
			File dirFile = new File(mkdirName);
			boolean bFile   = dirFile.exists();
			if( bFile == true ){
				System.out.println("The folder exists.");
				return null;
			}
			else{
				System.out.println("The folder do not exist,now trying to create a one...");

				bFile = dirFile.mkdir();
				if( bFile == true ){
					System.out.println("Create successfully!");
					System.out.println("创建文件夹");
					return null;
				}
				else{
//					System.out.println(mkdirName);
//					System.out.println("Disable to make the folder,please check the disk is full or not.");
					System.out.println(" 文件夹创建失败，请确认磁盘没有写保护并且空间足够"+mkdirName);
//					System.exit(1);
					CreateDir.mkdir("c:/pic/error");
					return "error";
				}
			}
		}
		catch(Exception err){
			System.err.println("ELS - Chart : 文件夹创建发生异常 "+mkdirName);
			err.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]){
		CreateDir.mkdir("c:/pic/必杀技-凵吓鞑ァ辉佟阂粋�颂琛�/a");
	}
}
