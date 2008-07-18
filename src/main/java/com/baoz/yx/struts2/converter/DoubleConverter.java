package com.baoz.yx.struts2.converter;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 类FloatConverter.java的实现描述：浮点数转换
 * @author xurong Jul 10, 2008 9:46:11 AM
 */
public class DoubleConverter extends StrutsTypeConverter {
	//使用千分号格式
	private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.##");
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values !=null && values.length >0){
			if(values[0].indexOf(',') != -1){
				return Double.parseDouble(StringUtils.replace(values[0], ",", ""));
			}else{
				return Double.parseDouble(values[0]);
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(o != null){
			return decimalFormat.format(o);
		}
		return null;
	}

}
