package com.ibeproxy.client;

import com.travelsky.ibe.client.QueueContent;
import com.travelsky.ibe.client.QueueItem;

public interface IQUEUEProxyService {

	public QueueItem getQueue(String boxName, boolean release, boolean decode) throws Exception;

	public QueueContent getQueueContent() throws Exception;

}
