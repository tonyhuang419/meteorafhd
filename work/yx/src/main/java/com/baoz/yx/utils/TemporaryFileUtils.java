package com.baoz.yx.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 类TemporaryFileUtils.java的实现描述：生成零时文件工具类
 * @author xurong Oct 16, 2008 9:21:59 AM
 */
public class TemporaryFileUtils {
	private static Logger logger = Logger.getLogger(TemporaryFileUtils.class);
	private static Properties p = new Properties();
	static {
		InputStream stream = TemporaryFileUtils.class.getClassLoader().getResourceAsStream("yx.properties");
		try {
			p.load(stream);
			stream.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		tempFileDir = p.getProperty("temp_dir");
	}
	/**
	 * 零时文件所在目录
	 */
	private static String tempFileDir;
	/**
	 * 生成子目录
	 */
	private static SimpleDateFormat dirSdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 生成文件名
	 */
	private static SimpleDateFormat fileSdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS");
	/**
	 * utils使用后，生成文件名的次数，没生成一个文件名加1，为了防止文件名重复。
	 */
	private static long generateNameCount = 0;
	
	/**
	 * 获得新的临时文件存放路径
	 * @param extension 文件后缀名，不带点。如jpg,text,doc等
	 * @return 文件存放路径
	 */
	public static File newTemporaryFile(String extension){
		File tempFile = new File(getTemporarySubDir(),generateNewFileName(extension));
		while(tempFile.exists()){
			tempFile = new File(getTemporarySubDir(),generateNewFileName(extension));
		}
		return tempFile;
	}

	/**
	 * 按时间目录
	 */
	private static File getTemporarySubDir(){
		File subDir = new File(tempFileDir,dirSdf.format(new Date()));
		if(!subDir.exists()){
			subDir.mkdirs();
		}
		return subDir;
	}
	/**
	 * 生成新文件名
	 * @param extension 文件后缀名，不带点。如jpg,text,doc等
	 * @return
	 */	
	private static String generateNewFileName(String extension){
		generateNameCount++;
		String timePart = fileSdf.format(new Date());
		String randomPart = String.valueOf(generateNameCount);
		return timePart+"-"+randomPart+"."+extension;
	}
	
	/**
	 * 获得可删除的文件
	 * @return
	 */
	public static List<File> getDeletableFile(){
		Calendar c = Calendar.getInstance();
		//保留两天的临时文件
		c.add(Calendar.DAY_OF_MONTH, -1);
		List<File> deletableFileList = new ArrayList<File>();
		File tempDir = new File(tempFileDir);
		File[] subFile = tempDir.listFiles();
		if(subFile != null){
			for (File file : subFile) {
				//只判目录
				if(file.isDirectory()){
					try {
						Date dirDate = dirSdf.parse(file.getName());
						Date compDate = dirSdf.parse(dirSdf.format(c.getTime()));
						//只取date之前的
						if(dirDate.before(compDate)){
							deletableFileList.add(file);
						}
					} catch (ParseException e) {
						logger.error("can't parse temporary sub dir ["+file.getName()+"],"+e.getMessage());
					}
				}
			}
		}
		return deletableFileList;
	}
	/**
	 * 清除零时目录可删除的文件
	 */
	public static void cleanTemporaryDir(){
		List<File> deletableFileList = getDeletableFile();
		for (File file : deletableFileList) {
			try {
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				logger.error("can't clean temporary file ["+file+"],"+e.getMessage());
			}
		}
	}
	/**
	 * 零时文件所在子目录
	 */
	public static String getTempFileDir() {
		return tempFileDir;
	}
	
	public static void setTempFileDir(String tempFileDir) {
		TemporaryFileUtils.tempFileDir = tempFileDir;
	}
}
