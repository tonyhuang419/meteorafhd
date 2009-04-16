package com.ibeproxy.pnr.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.pnr.IRTProxyService;
import com.travelsky.ibe.client.pnr.PNREnvelop;
import com.travelsky.ibe.client.pnr.RTResult;
import com.travelsky.ibe.exceptions.IBEException;

public class RTProxyService implements IRTProxyService{
	protected Log logger = LogFactory.getLog(this.getClass());
	
	public RTResult retrieve(String pnrno) throws Exception{
		logger.info("flightTime has be called");
		return new RTProxyService().retrieve(pnrno);
	}

	
	public RTResult retrieveCompletely(String pnrno) throws Exception{
		logger.info("retrieveCompletely has be called");
		return new RTProxyService().retrieveCompletely(pnrno);
	}

	
	public PNREnvelop retrieveFirstEnvelop(String pnrno) throws IBEException{
		logger.info("retrieveFirstEnvelop has be called");
		return new RTProxyService().retrieveFirstEnvelop(pnrno);
	}
	
}
