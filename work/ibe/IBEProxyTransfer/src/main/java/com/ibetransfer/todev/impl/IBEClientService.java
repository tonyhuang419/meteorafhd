package com.ibetransfer.todev.impl;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibetransfer.todev.IIBEClientService;

public class IBEClientService  implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	private IIBEClientService iis;
	private LinkedList queue = new LinkedList();
	private byte[] lock = new byte[0];
	
	String pekString;
	String canString;
	
	public String query(String[] args) throws Exception{
		if(!this.add(args)){
			return "null";
		}
		
		Object[] objArr;
		String s[];
		synchronized (lock) {
			logger.info("i'm transfer,query has be called,the args is:");
			StringBuffer sb = new StringBuffer("");
			objArr = (Object[])this.remove(queue);
			s = (String[])objArr;
			for(int i=0;i<s.length;i++){
				sb.append(s[i]+",");
			}
			logger.info(sb.toString());
			return iis.query(s);
		}
	}

	
	/**
	 * when do remove operation will note check queue size
	 * because queue at least has a element 
	 * @param queue
	 * @return
	 */
	private Object remove( LinkedList queue ){
		synchronized(queue) {
			return  queue.removeFirst();	
		}
	}


	private boolean add(Object[] args){
		synchronized(queue) {
			if(queue.size()<3){
				queue.add(args);
				return true;
			}
			else{
				System.out.println("========同时请求数过大：3========");
				return false;
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
