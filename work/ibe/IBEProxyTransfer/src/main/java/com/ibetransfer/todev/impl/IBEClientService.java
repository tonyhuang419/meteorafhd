package com.ibetransfer.todev.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibetransfer.todev.IIBEClientService;

public class IBEClientService  implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());

	private IIBEClientService iis;

	private Map resultMap = new HashMap();
	private byte[] lock = new byte[0];

	public String query(String[] args) throws Exception{
		synchronized(lock){
//			logger.info("i'm transfer,query has be called,the args is:");
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<args.length;i++){
				sb.append(args[i]+",");
			}
			logger.info(sb.toString());

			if(resultMap.get(sb.toString())!=null){
				System.out.println("get info from cache");
				return (String)resultMap.get(sb.toString());
			}
			else{
				System.out.println("get info from remote interface");
				String result = iis.query(args);
				resultMap.put(sb.toString(), result);
				return result;
			}
		}
	}

	public IIBEClientService getIis() {
		return iis;
	}

	public void setIis(IIBEClientService iis) {
		this.iis = iis;
	}

}
