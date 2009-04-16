package com.ibeproxy.pnr.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.pnr.ISellSeatProxyService;
import com.travelsky.ibe.client.pnr.SSResult;

public class SellSeatProxyService implements  ISellSeatProxyService{
	protected Log logger = LogFactory.getLog(this.getClass());

	public String commit() throws Exception{
		logger.info("commit has be called");
		return new SellSeatProxyService().commit();
	}

	public SSResult commit1() throws Exception{
		logger.info("commit1 has be called");
		return new SellSeatProxyService().commit1();
	}
}
