package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IIBEClientService;
import com.travelsky.ibe.client.IBEClient;

public class IBEClientService extends IBEClient implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public String query(String[] args) throws Exception{
		logger.info("query has be called");
		for(int i=0;i<args.length;i++){
			logger.info(args[i]);
		}
		return super.query(args);
	}

}
