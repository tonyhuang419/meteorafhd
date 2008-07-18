package com.baoz.yx.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//mysql下通过测试，oracle下未做测试


/**	
 * 转换通过System.currentTimeMillis()来存取的时间，同时也方便格式统一
 * String转Long:  Long.parseLong(s);
 */
public final class NumberToTime {

	/**	
	 * 转换后格式：2008-05-27
	 */
	public static String timeConvert1(Long n){  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", 
				Locale.SIMPLIFIED_CHINESE);
		return sdf.format(n);
	}

	/**
	 *	转换后格式：2008-05-27 23:42:50
	 */
	public static String timeConvert2(Long n){ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", 
				Locale.SIMPLIFIED_CHINESE);
		return sdf.format(n);
	}

	/**
	 *	获取月份
	 */
	@SuppressWarnings("static-access")
	public static String getMonth(Long n){ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", 
				Locale.SIMPLIFIED_CHINESE);
		sdf.format(n);
		int m = sdf.getCalendar().MONTH;
		return String.valueOf(m);
	}

	/**
	 *	通过毫秒数计算两个时间间隔（天数），b>a
	 */
	public static String timeDistance(Long a,Long b)	{
		long distance = (b-a) /(3600*24*1000);	
		return String.valueOf(distance);
	}
	/**
	  *将字符型的日期转为日期类型author by xiaoping
	*/
    public static Date getStringConvertDate(String dString){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Date date=null;
		try {
			date = sdf.parse(dString);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
    	return date;
    }
    /**
     * 传入自己定义的时间格式显示出时间
     * @param adate
     * @param format
     * @return
     */
    public static String getDateFormat(Date adate,String format) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(format);
            return formatDate.format(adate);
        } catch (Exception e) {
            return "";
        }
    }
	public static void main(String args[]){
		/*String n = MoneyConvert(new BigDecimal("6554.12"));
		System.out.println(n);

		long a = System.currentTimeMillis()- new Long("9990000000");
		long bc = System.currentTimeMillis();
		System.out.println(a);
		System.out.println(bc);
		System.out.println(timeConvert2(a));
		System.out.println(timeConvert2(bc));
		System.out.println(timeDistance(a,bc));

		long a = System.currentTimeMillis()- new Long("9990000000");
		System.out.println(timeConvert2(a));
		System.out.println(getMonth(a));*/
//		System.out.println(NumberToTime.getStringConvertDate("2008-3-5"));
		System.out.println(NumberToTime.getDateFormat(new Date(), "yyyyMMdd"));
	}
}



