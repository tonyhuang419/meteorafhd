package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.util.Date;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IAVDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IAVProxyService;
import com.travelsky.ibe.client.AvResult;


public class AVDecorate implements IAVDecorate{

	private IAVProxyService avProxyService;

	public AvResult getAvailability(String org, String dst, Date date) throws Exception {
		try{
			return getAvailability(org.toUpperCase(), dst.toUpperCase(), date, "ALL", false, false);
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public AvResult getAvailability(String airline, Date date) throws Exception{
		return avProxyService.getAvailability(airline, date);
	}

	public AvResult getAvailability(String org, String dst, Date date, 
			String airline, boolean direct, boolean e_ticket) throws Exception{
		return avProxyService.getAvailability(org, dst, date, airline, direct, e_ticket);
	}



	public IAVProxyService getAvProxyService() {
		return avProxyService;
	}

	public void setAvProxyService(IAVProxyService avProxyService) {
		this.avProxyService = avProxyService;
	}



}
