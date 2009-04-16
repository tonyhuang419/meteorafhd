package com.ibeproxy.client.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IFFProxyService;
import com.travelsky.ibe.client.FF;
import com.travelsky.ibe.client.FFResult;

public class FFProxyService implements IFFProxyService{
	protected Log logger = LogFactory.getLog(this.getClass());

	public FFResult flightTime(String airNo, Date date) throws Exception{
		logger.info("flightTime has be called");
		return new FF().flightTime(airNo, date);

	}
}
