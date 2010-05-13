package com.ibetransfer.todev.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibetransfer.todev.IIBEClientService;

public class IBEClientService  implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());

	private IIBEClientService iis;

	String pekStr;
	String canStr;

	synchronized public String query(String[] args) throws Exception{
		logger.info("i'm transfer,query has be called,the args is:");
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<args.length;i++){
			sb.append(args[i]+",");
		}
		logger.info(sb.toString());
//		if( sb.indexOf("PEK")!=-1  ){
//			if(pekStr==null){
//				pekStr = iis.query(args);
//			}
//			return pekStr;
//		}
//		else {
//			if(canStr==null){
//				canStr = iis.query(args);
//			}
//			return canStr;
//		}
		return iis.query(args);
	}

	public IIBEClientService getIis() {
		return iis;
	}

	public void setIis(IIBEClientService iis) {
		this.iis = iis;
	}

}
