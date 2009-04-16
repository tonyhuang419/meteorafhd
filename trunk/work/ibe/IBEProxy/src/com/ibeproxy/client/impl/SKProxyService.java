package com.ibeproxy.client.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.ISKProxyService;
import com.travelsky.ibe.client.SK;
import com.travelsky.ibe.client.SkResult;

public class SKProxyService implements ISKProxyService{
	protected Log logger = LogFactory.getLog(this.getClass());
	
	public SkResult getSchedule(String org, String dst, Date date, String airline, boolean direct, boolean nostop) throws Exception{
		logger.info("getSchedule has be called");
		return new SK().getSchedule(org, dst, date, airline, direct, nostop);
	}

}
