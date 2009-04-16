package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import java.util.Date;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IFFDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IFFProxyService;
import com.travelsky.ibe.client.FFResult;


public class FFDecorate implements IFFDecorate{

	private IFFProxyService ffProxyService;

	public FFResult flightTime(String airNo, Date date) throws Exception{
		return ffProxyService.flightTime(airNo, date);
	}

	public IFFProxyService getFfProxyService() {
		return ffProxyService;
	}

	public void setFfProxyService(IFFProxyService ffProxyService) {
		this.ffProxyService = ffProxyService;
	}

}
