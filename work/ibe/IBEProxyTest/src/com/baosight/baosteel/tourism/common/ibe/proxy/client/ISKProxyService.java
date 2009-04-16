package com.baosight.baosteel.tourism.common.ibe.proxy.client;

import java.util.Date;

import com.travelsky.ibe.client.SkResult;

public interface ISKProxyService {

	 public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct, boolean nostop)
	    throws Exception;
	
}
