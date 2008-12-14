package com.cheatkx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Tools {

	static private Log logger = LogFactory.getLog(Tools.class);

	
	/**
	 * 获取v_userdata
	 */
	static public String processUserData(String context){
		String ud = "";
		Pattern p = Pattern.compile("v_userdata = (\\{.*);\\s"); 
		Matcher matcher = p.matcher(context);
		if(matcher.find()){  
			ud = matcher.group(1);
//			ud = temp.substring(13, temp.length()-2);
			logger.info("v_userdata: " + ud );
		}
		
		JSONObject jb = JSONObject.fromObject(ud);  
//		Object o = jb.get("user"); 
		
//		for(int i=0;i<array.size();i++){
//			System.out.println(array.get(i));
//		}
		return ud;
	}

	/**
	 * 获取g_verify
	 */
	static public String getVerify(String context){
		String verify = "";
//		int pos = context.indexOf("g_verify");
//		if(pos!=-1){
//		logger.info(context);
		Pattern p = Pattern.compile("g_verify = \"(.*)\";\\s"); 
		Matcher matcher = p.matcher(context); 
		if(matcher.find()){  
			verify = matcher.group(1);
			logger.info(" verify: " + verify);
		}  
//		}
		return verify;
	}

	static public String getUid(String context){
		Pattern p = Pattern.compile("我的开心网ID:(\\d*)\">"); 
		Matcher matcher = p.matcher(context); 
		if(matcher.find()){  
			logger.info(" uid: " + matcher.group(1));
			return matcher.group(1);
		} 
		return "";
	}
	
	

	//通过HttpMethod，获得context
	static public String printInfo(HttpMethod method)  throws IOException{
		String str;
		StringBuffer sb = new StringBuffer();
//		Header[] h = method.getRequestHeaders();
//		logger.info(method.getStatusCode());
		InputStream in = method.getResponseBodyAsStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"),1024);
		while( (str = br.readLine()) !=null){
			sb.append(str).append("\n");
		}
		in.close();
		method.releaseConnection();
		return sb.toString();
	}
}
