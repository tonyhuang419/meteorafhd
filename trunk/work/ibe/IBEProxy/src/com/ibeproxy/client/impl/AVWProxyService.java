package com.ibeproxy.client.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IAVWProxyService;
import com.travelsky.ibe.client.AVW;
import com.travelsky.ibe.client.AvResult;

public class AVWProxyService implements IAVWProxyService{

	protected Log logger = LogFactory.getLog(this.getClass());

	public AvResult getAvailability(String org, String dst, Date date,
			String airline, boolean direct, boolean e_ticket) throws Exception {
		logger.info("getAvailability has be called");
		return new AVW().getAvailability(org, dst, date, airline, direct, e_ticket);
	}

	public AvResult getAvailability(String org, String dst, Date date,
			String airline, boolean direct) throws Exception {
		logger.info("getAvailability2 has be called");
		return new AVW().getAvailability(org, dst, date, airline, direct);
	}
	

	
}
