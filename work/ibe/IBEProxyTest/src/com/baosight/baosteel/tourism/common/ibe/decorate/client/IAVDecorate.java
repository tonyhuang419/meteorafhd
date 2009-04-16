package com.baosight.baosteel.tourism.common.ibe.decorate.client;

import java.util.Date;

import com.travelsky.ibe.client.AvResult;


public interface IAVDecorate {

	public AvResult getAvailability(String org, String dst, Date date) throws Exception;

	public AvResult getAvailability(String airline, Date date) throws Exception;

	public AvResult getAvailability(String org, String dst, Date date, 
			String airline, boolean direct, boolean e_ticket) throws Exception;

}
