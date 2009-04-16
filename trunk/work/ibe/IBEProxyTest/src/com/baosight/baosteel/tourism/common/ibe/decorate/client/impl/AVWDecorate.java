package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.util.Date;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IAVWDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IAVWProxyService;
import com.travelsky.ibe.client.AvResult;


public class AVWDecorate implements IAVWDecorate{

	private IAVWProxyService avwProxyService;

	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct) throws Exception{
		return avwProxyService.getAvailability(org, dst, date, airline, direct);
	}

	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket) throws Exception{
		return avwProxyService.getAvailability(org, dst, date, airline, direct, e_ticket);
	}

	
	public IAVWProxyService getAvwProxyService() {
		return avwProxyService;
	}

	public void setAvwProxyService(IAVWProxyService avwProxyService) {
		this.avwProxyService = avwProxyService;
	}

}
