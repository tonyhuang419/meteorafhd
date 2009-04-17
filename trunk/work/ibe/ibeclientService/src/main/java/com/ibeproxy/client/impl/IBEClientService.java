package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IIBEClientService;
import com.travelsky.ibe.client.IBEClient;

//public class IBEClientService extends IBEClient implements IIBEClientService {
//
//	protected Log logger = LogFactory.getLog(this.getClass());
//	
//	public String query(String[] args) throws Exception{
//		logger.info("query has be called,the args is:");
//		StringBuffer sb = new StringBuffer("");
//		for(int i=0;i<args.length;i++){
//			sb.append(args[i]+",");
//		}
//		logger.info(sb);
//		return super.query(args);
//	}
//
//}

public class IBEClientService  implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public String query() throws Exception{
		System.out.println("...........");
		return "";
	}

}
