package com.fhdone.demo2012.utils.lucene;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsX extends StringUtils{
	
	public String getString(String str){
		if( isEmpty(str) ){
			return "";
		}
		return str;
	}
	
}
