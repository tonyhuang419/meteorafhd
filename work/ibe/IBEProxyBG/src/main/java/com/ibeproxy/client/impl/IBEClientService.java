package com.ibeproxy.client.impl;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IIBEClientService;
import com.travelsky.ibe.client.IBEClient;

public class IBEClientService extends IBEClient implements IIBEClientService {

	protected Log logger = LogFactory.getLog(this.getClass());

	private LinkedList queue = new LinkedList();
	private Object[] objArr;
	private String tempArgs[];

	public String query(String[] args) throws Exception{

		if( !this.add(args)){
			return "null";
		}

		StringBuffer sb = new StringBuffer("");
		synchronized(sb){
			logger.info("query has be called,the args is:");
			if(queue.size()==0)
				System.out.println("============0");
			objArr = (Object[])this.remove(queue);
			tempArgs = (String[])objArr;
			for(int i=0;i<tempArgs.length;i++){
				sb.append(tempArgs[i]+",");
			}
			logger.info(sb);
			return super.query(args);
		}
	}

	/**
	 * when do remove operation will note check queue size
	 * because queueu at least has a element 
	 * @param queue
	 * @return
	 */
	private Object remove( LinkedList queue ){
		synchronized(queue) {
			return  queue.remove();	
		}
	}


	private boolean add(String[] args){
		synchronized(queue) {
			if(queue.size()<10){
				queue.add(args);
				return true;
			}
			else{
				System.out.println("同时请求数过大：10");
				return false;
			}
		}
	}


}
