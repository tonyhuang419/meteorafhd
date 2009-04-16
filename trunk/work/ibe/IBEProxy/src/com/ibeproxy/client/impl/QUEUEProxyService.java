package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IQUEUEProxyService;
import com.travelsky.ibe.client.QUEUE;
import com.travelsky.ibe.client.QueueContent;
import com.travelsky.ibe.client.QueueItem;

public class QUEUEProxyService implements IQUEUEProxyService{

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public QueueItem getQueue(String boxName, boolean release, boolean decode) throws Exception{
		logger.info("getQueue has be called");
		return new QUEUE().getQueue(boxName, release);
	}

	public QueueContent getQueueContent() throws Exception{
		logger.info("getQueueContent has be called");
		return new QUEUE().getQueueContent();
	}

}
