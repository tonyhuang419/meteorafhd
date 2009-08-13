package com.fstock.util;

public class ConstantValue {

	//save average level max amount
	public static int averageLevelLen = 10;
	
	public static String allStockUrlPreFix = "http://quote.tool.hexun.com/hqzx/quote.aspx?type=2&market=0&sorttype=0&updown=down&page=";
	
	public static String allStockUrlSuffix = "&count=50"; //&time=172020

	public static String stockBaseUrl = "http://stockdata.stock.hexun.com/";
	
	/**
	 * first run use max page num like 31 
	 * and them u should use like 1、2 add new stock
	 */
	public static int maxPage = 31;
	
}
