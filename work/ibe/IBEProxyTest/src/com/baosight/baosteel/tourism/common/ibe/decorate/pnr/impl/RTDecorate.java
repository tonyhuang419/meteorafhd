package com.baosight.baosteel.tourism.common.ibe.decorate.pnr.impl;

import com.baosight.baosteel.tourism.common.ibe.decorate.pnr.IRTDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.pnr.IRTProxyService;
import com.travelsky.ibe.client.pnr.PNREnvelop;
import com.travelsky.ibe.client.pnr.RTResult;
import com.travelsky.ibe.exceptions.IBEException;


public class RTDecorate implements IRTDecorate{

	private IRTProxyService rtProxyService;

	public RTResult retrieve(String pnrno) throws Exception{
		return rtProxyService.retrieve(pnrno);
	}

	public RTResult retrieveCompletely(String pnrno) throws Exception{
		return rtProxyService.retrieveCompletely(pnrno);
	}

	public PNREnvelop retrieveFirstEnvelop(String pnrno) throws IBEException{
		return rtProxyService.retrieveFirstEnvelop(pnrno);
	}

	public IRTProxyService getRtProxyService() {
		return rtProxyService;
	}

	public void setRtProxyService(IRTProxyService rtProxyService) {
		this.rtProxyService = rtProxyService;
	}

}
