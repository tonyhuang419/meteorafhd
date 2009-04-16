package com.baosight.baosteel.tourism.common.ibe.decorate.client;

import java.util.Date;

import com.travelsky.ibe.client.FFResult;

public interface IFFDecorate {

	  public FFResult flightTime(String airNo, Date date) throws Exception;
	    
}
