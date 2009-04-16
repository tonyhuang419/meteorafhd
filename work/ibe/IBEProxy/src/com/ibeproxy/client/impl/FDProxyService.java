package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IFDProxyService;
import com.travelsky.ibe.client.FD;
import com.travelsky.ibe.client.FDResult;

public class FDProxyService implements IFDProxyService{
	protected Log logger = LogFactory.getLog(this.getClass());

	public FDResult findPrice(String org, String dst, String date, String airline, 
			String planeModel, String passType, boolean fullFareBasis) throws Exception{
		logger.info("findPrice has be called");
		return new FD().findPrice(org, dst, date, airline, planeModel,  passType, fullFareBasis);
	}
}
