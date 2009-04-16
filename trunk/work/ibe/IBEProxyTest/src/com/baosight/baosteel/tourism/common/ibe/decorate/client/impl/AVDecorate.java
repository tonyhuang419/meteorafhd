package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.util.Date;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IAVDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IAVProxyService;
import com.travelsky.ibe.client.AvResult;


public class AVDecorate implements IAVDecorate {

	private IAVProxyService avProxyService;

	//implements method
	public AvResult getAvailability(String airline, Date date) throws Exception{
		System.out.println("proxy getAvailability has be called");
		return avProxyService.getAvailability(airline, date);
	}

	//implements method
	public AvResult getAvailability(String org, String dst, Date date, 
			String airline, boolean direct, boolean e_ticket) throws Exception{
		System.out.println("proxy getAvailability has be called");
		return avProxyService.getAvailability(org, dst, date, airline, direct, e_ticket);
	}

	public IAVProxyService getAvProxyService() {
		return avProxyService;
	}

	public void setAvProxyService(IAVProxyService avProxyService) {
		this.avProxyService = avProxyService;
	}



}
