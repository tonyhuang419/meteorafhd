package com.ibeproxy.client.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibeproxy.client.IDSGProxyService;
import com.travelsky.ibe.client.DSG;
import com.travelsky.ibe.client.DsgResult;
import com.travelsky.ibe.exceptions.IBEException;

public class DSGProxyService implements IDSGProxyService{

	protected Log logger = LogFactory.getLog(this.getClass());

	public DsgResult displaySegment(String flight, char cabin, 
			String date, String segment) throws IBEException{
		logger.info("displaySegment has be called");
		return new DSG().displaySegment(flight, cabin, date, segment);
	}

	public DsgResult displaySegment(String pnrno, 
			int[] segIndexInPnr) throws IBEException{
		logger.info("displaySegment has be called");
		return new DSG().displaySegment(pnrno, segIndexInPnr);
	}

	public DsgResult displaySegmentPnr(String pnrno,int[] segIndexInPnr) throws IBEException{
		logger.info("displaySegmentPnr has be called");
		return new DSG().displaySegmentPnr(pnrno, segIndexInPnr);
	}
}
