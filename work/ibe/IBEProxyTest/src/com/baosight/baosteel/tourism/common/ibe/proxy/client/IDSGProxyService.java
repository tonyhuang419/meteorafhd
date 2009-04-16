package com.baosight.baosteel.tourism.common.ibe.proxy.client;

import com.travelsky.ibe.client.DsgResult;
import com.travelsky.ibe.exceptions.IBEException;

public interface IDSGProxyService {


	public DsgResult displaySegment(String flight, char cabin, 
			String date, String segment) throws IBEException;

	public DsgResult displaySegment(String pnrno, 
			int[] segIndexInPnr) throws IBEException;

	public DsgResult displaySegmentPnr(String pnrno,
			int[] segIndexInPnr) throws IBEException;

}
