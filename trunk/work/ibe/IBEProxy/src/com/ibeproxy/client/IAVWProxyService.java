package com.ibeproxy.client;

import java.util.Date;

import com.travelsky.ibe.client.AvResult;

public interface IAVWProxyService {

	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct)
	throws Exception;

	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket)
	throws Exception;

}
