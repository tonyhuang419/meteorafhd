package com.ibeproxy.client;

import java.util.Date;

import com.travelsky.ibe.client.FFResult;

public interface IFFProxyService {

	  public FFResult flightTime(String airNo, Date date) throws Exception;
	    
}
