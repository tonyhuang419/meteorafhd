package com.ibeproxy.client.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IAVProxyService;
import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvResult;

public class AVProxyService implements IAVProxyService{

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public AvResult getAvailability(String airline, Date date) throws Exception{
		logger.info("getAvailability has be called");
		return new AV().getAvailability(airline, date);
	}
	
	public AvResult getAvailability(String org, String dst, Date date, String airline, 
			boolean direct, boolean e_ticket) throws Exception{
		logger.info("getAvailability2 has be called");
		return new AV().getAvailability(org, dst, date, airline, direct, e_ticket);
		
	}

	
}
