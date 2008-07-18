package com.baoz.yx.struts2.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConverter extends StrutsTypeConverter {
	//使用千分号格式
	private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.##");
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values !=null && values.length >0){
			if(values[0].indexOf(',') != -1){
				return new BigDecimal(StringUtils.replace(values[0], ",", ""));
			}else{
				return new BigDecimal(values[0]);
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
