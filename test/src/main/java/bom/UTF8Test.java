package bom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 * http://daimojingdeyu.javaeye.com/blog/397661
 */
public class UTF8Test {
	public static void main(String[] args) throws IOException {
		File f  = new File("./utf.txt");
		FileInputStream in = new FileInputStream(f);
		// 指定读取文件时以UTF-8的格式读取
//		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
		
		String line = br.readLine();
		while(line != null)
		{
			System.out.println(line);
			line = br.readLine();
		}
	}
}
