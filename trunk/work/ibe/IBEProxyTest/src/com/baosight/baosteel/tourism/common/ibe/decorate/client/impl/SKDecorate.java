package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.util.Date;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.ISKDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.ISKProxyService;
import com.travelsky.ibe.client.SkResult;


public class SKDecorate implements ISKDecorate{

	private ISKProxyService skProxyService;

	public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct, boolean nostop) throws Exception{
		return skProxyService.getSchedule(org, dst, date, airline, direct, nostop);
	}

	
	public SkResult getSchedule(String org, String dst, Date date)  throws Exception{
		try{
			return skProxyService.getSchedule(org, dst, date, "ALL", false, false);
		}
		catch(Exception e){
			throw e;
		}
	}
	
	
	
	public ISKProxyService getSkProxyService() {
		return skProxyService;
	}

	public void setSkProxyService(ISKProxyService skProxyService) {
		this.skProxyService = skProxyService;
	}

}
