package com.baosight.baosteel.tourism.common.ibe.decorate.client;

import com.travelsky.ibe.client.FDResult;

public interface IFDDecorate {

	public FDResult findPrice(String org, String dst, 
			String date, String airline, 
			String planeModel, String passType, 
			boolean fullFareBasis) throws Exception;
	
}
