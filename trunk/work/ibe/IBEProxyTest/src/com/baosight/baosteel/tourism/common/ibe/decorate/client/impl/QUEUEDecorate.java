package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IQUEUEDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IQUEUEProxyService;
import com.travelsky.ibe.client.QueueContent;
import com.travelsky.ibe.client.QueueItem;


public class QUEUEDecorate implements IQUEUEDecorate{

	private IQUEUEProxyService queueProxyService;

	public QueueItem getQueue(String boxName, boolean release, boolean decode) throws Exception{
		return queueProxyService.getQueue(boxName, release, decode);
	}

	public IQUEUEProxyService getQueueProxyService() {
		return queueProxyService;
	}

	
	public void setQueueProxyService(IQUEUEProxyService queueProxyService) {
		this.queueProxyService = queueProxyService;
	}

	public QueueContent getQueueContent() throws Exception{
		return queueProxyService.getQueueContent();
	}

}
