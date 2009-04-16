package com.baosight.baosteel.tourism.common.ibe.decorate.client.impl;

import com.baosight.baosteel.tourism.common.ibe.decorate.client.IDSGDecorate;
import com.baosight.baosteel.tourism.common.ibe.proxy.client.IDSGProxyService;
import com.travelsky.ibe.client.DsgResult;
import com.travelsky.ibe.exceptions.IBEException;


public class DSGDecorate implements IDSGDecorate{

	private IDSGProxyService dsgProxyService;

	public DsgResult displaySegment(String flight, char cabin, 
			String date, String segment) throws IBEException{
		return dsgProxyService.displaySegment(flight, cabin, date, segment);
	}

	public DsgResult displaySegment(String pnrno, 
			int[] segIndexInPnr) throws IBEException{
		return dsgProxyService.displaySegment(pnrno, segIndexInPnr);
	}

	public DsgResult displaySegmentPnr(String pnrno,
			int[] segIndexInPnr) throws IBEException{
		return dsgProxyService.displaySegmentPnr(pnrno, segIndexInPnr);
	}

	public IDSGProxyService getDsgProxyService() {
		return dsgProxyService;
	}

	public void setDsgProxyService(IDSGProxyService dsgProxyService) {
		this.dsgProxyService = dsgProxyService;
	}


	
	



}
