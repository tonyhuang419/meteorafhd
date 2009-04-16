package com.ibeproxy.client;

import com.travelsky.ibe.client.FDResult;

public interface IFDProxyService {

	public FDResult findPrice(String org, String dst, 
			String date, String airline, 
			String planeModel, String passType, 
			boolean fullFareBasis) throws Exception;
	
}
