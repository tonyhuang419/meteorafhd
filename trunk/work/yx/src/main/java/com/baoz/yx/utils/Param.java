package com.baoz.yx.utils;

public class Param {

	private static Long Id=0L;
	
	public static Long getId()
	{
		return Id--;
	}
	
}
