package com.baoz.yx.utils;

import java.io.File;

import junit.framework.TestCase;

public class TemporaryFileUtilsTest extends TestCase {

	public void testNewTemporaryFile() throws Exception {
		//TemporaryFileUtils.setTempFileDir("C:/yingxiaoDir/tempFileDir");
		File f = TemporaryFileUtils.newTemporaryFile("txt");
		f.createNewFile();
		System.out.println(f);
		TemporaryFileUtils.cleanTemporaryDir();
	}
}
