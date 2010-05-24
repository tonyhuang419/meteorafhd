package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IIBEClientService;
import com.travelsky.ibe.client.IBEClient;

public class IBEClientService extends IBEClient implements IIBEClientService {

	private Log logger = LogFactory.getLog(this.getClass());
	private byte[] lock = new byte[0];

	public String query(String[] args) throws Exception{
		synchronized(lock){
			StringBuffer sb = new StringBuffer();
			logger.info("query has be called,the args is:");
			for(int i=0;i<args.length;i++){
				sb.append(args[i]+",");
			}
			logger.info(sb);
			return super.query(args);
		}
	}
}
