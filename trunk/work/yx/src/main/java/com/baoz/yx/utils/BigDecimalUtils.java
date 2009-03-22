/**
 * 
 */
package com.baoz.yx.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Administrator
 *
 */
public class BigDecimalUtils {
	public static BigDecimal toBigDecial(Number number){
		if(number == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("0.0#");
		return new BigDecimal(df.format(number));
	}
	public static BigDecimal toBigDecial(String number){
		if(number == null){
			return null;
		}
		return new BigDecimal(number);
	}
	
	public static double defaultIfNull(Number number){
		if(number == null){
			return 0.0;
		}else{
			return number.doubleValue();
		}
	}
	public static String doubleToString(Double number){
		if(number == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
		return df.format(number);
	}
}
