package com.baoz.yx.tools;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTools {

	public static int getLastDayOfMonth(String  strMaxMonth){
		 int lastday;   
		 String[] days = strMaxMonth.split("-");
		 GregorianCalendar   d=   new GregorianCalendar();   
		 d.set(Integer.parseInt(days[0]), Integer.parseInt(days[1])-1, Integer.parseInt(days[2]));
		 int   month=d.get(Calendar.MONTH);    
	    do   
	    {   
			lastday=d.get(Calendar.DAY_OF_MONTH);
			d.add(Calendar.DAY_OF_MONTH,1);   
	    }while(d.get(Calendar.MONTH)==month);   
	     return lastday;
	}
	public static void main(String[] args){
		int lastday = DateTools.getLastDayOfMonth("2008-12-1");
		System.out.println(lastday);
	}
}
