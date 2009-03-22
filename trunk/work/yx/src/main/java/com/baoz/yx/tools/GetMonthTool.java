package com.baoz.yx.tools;

import java.util.Calendar;
import java.util.Date;

public class GetMonthTool {

	public static Date  getFirstDayOfMonth(){
		Calendar c  = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,1);// 1st day of month.
		return  c.getTime();
	}

	public static Date getLastDayOfMonth(){
		Calendar c  = Calendar.getInstance();
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DATE,-1);   // last day of month.
		return c.getTime();
	}
	
}
