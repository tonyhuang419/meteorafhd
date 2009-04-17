package com.ibetransfer.todev.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibetransfer.todev.IIBEClientService;

public class IBEClientService  implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	private IIBEClientService iis;
	
	public String query(String[] args) throws Exception{
		logger.info("i'm transfer,query has be called,the args is:");
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<args.length;i++){
			sb.append(args[i]+",");
		}
		logger.info("i'm transfer,i call dest query");
		return iis.query(args);
	}

	
	public IIBEClientService getIis() {
		return iis;
	}

	public void setIis(IIBEClientService iis) {
		this.iis = iis;
	}
	
}
