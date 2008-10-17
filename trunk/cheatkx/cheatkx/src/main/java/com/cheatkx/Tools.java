package com.cheatkx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
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
		JSONArray array = jb.getJSONArray("error"); 
		for(int i=0;i<array.size();i++){
			System.out.println(array.get(i));
		}
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


	//通过HttpMethod，获得context
	static public String printInfo(HttpMethod method)  throws IOException{
		String str;
		StringBuffer sb = new StringBuffer();
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
