package com.baoz.yx.tools;

import java.math.BigDecimal;

//mysql下通过测试，oracle下未做测试

/**
 * ORM映射用的是BigDecimal，精度为2，数据结构默认映射为Number(19,2)
 */	
public final class FormatMoney {
	/**
	 * input:6554.11
	 * output:6,554.11
	 */	
	public static String moneyConvert(BigDecimal b){
		StringBuffer ts = new StringBuffer(b.toString()); 
		int pointPostion = ts.indexOf(".");
		int postion = pointPostion;
		while((postion-3)>0){
			ts.insert(postion-3, ",");
			postion = postion-3;
		}
		return ts.toString();
	}
}
