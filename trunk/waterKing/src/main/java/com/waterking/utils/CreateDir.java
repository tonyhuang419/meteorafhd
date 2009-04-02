package com.waterking.utils;

import java.io.File;

public class CreateDir {

	public static void  mkdir(String mkdirName)
	{
		try
		{
			File dirFile = new File(mkdirName);
			boolean bFile   = dirFile.exists();
			if( bFile == true ){
//				System.out.println("The folder exists.");
			}
			else{
//				System.out.println("The folder do not exist,now trying to create a one...");

				bFile = dirFile.mkdir();
				if( bFile == true ){
//					System.out.println("Create successfully!");
//					System.out.println("创建文件夹");
				}
				else{
//					System.out.println("Disable to make the folder,please check the disk is full or not.");
//					System.out.println(" 文件夹创建失败，请确认磁盘没有写保护并且空间足够");
					System.exit(1);
				}
			}
		}
		catch(Exception err){
			System.err.println("ELS - Chart : 文件夹创建发生异常");
			err.printStackTrace();
		}
	}
}
