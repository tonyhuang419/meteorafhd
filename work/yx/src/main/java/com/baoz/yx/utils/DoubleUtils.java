package com.baoz.yx.utils;

import java.text.NumberFormat;

import org.apache.commons.lang.math.NumberUtils;

/**
 *判断两个double类型数据是否相等，允许误差0.0000001
 * @author xusheng
 *
 */
public class DoubleUtils {

	public static boolean compareDouble(double number1,double number2){
		if(Math.abs(number1-number2)<0.000001){
			return true;
		}else{
			return false;
		}
	}
}
