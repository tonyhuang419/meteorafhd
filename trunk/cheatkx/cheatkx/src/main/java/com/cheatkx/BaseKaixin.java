package com.cheatkx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class BaseKaixin {

	static String urlStr = "www.kaixin001.com";
	static int port = 80;
	static String protocal = "http";
	static String g_verify ="";       				//验证字符串
	static String uid;
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
}
