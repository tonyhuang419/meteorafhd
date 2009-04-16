package com.baosight.baosteel.tourism.common.ibe.decorate.client;

import java.util.Date;

import com.travelsky.ibe.client.SkResult;


public interface ISKDecorate {


	public SkResult getSchedule(String org, String dst, Date date)  throws Exception;

	public SkResult getSchedule(String org, String dst, Date date, String airline, 
			boolean direct, boolean nostop) throws Exception;

}
