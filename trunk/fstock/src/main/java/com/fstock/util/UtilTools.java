package com.fstock.util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fstock.entity.Stock;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



public class UtilTools {
	protected static Log logger = LogFactory.getLog(UtilTools.class);

	private static Pattern stockPattern = Pattern.compile("\\[ (.*)];Stock");

	public static List<Stock> getStockList(String str){
		List<Stock> stockList = new ArrayList<Stock>();
		Matcher m1 = stockPattern.matcher(str);  
		String pageStocks;
		while( m1.find() ){  
			pageStocks =  m1.group(1);  
			stockList = UtilTools.splitStock(pageStocks);
		}
		return stockList;
	}

	public static int getLevel(String levelInfo){
		int count = StringUtils.countMatches(levelInfo,"/>");
		if(count==0){
			count = StringUtils.countMatches(levelInfo,"<td>");
		}
		if(count!=0){
			return count + 1;
		}
		return -1;
	}


	public static String parseStockLevel(String stockLevel , String newLevel){
		if ( StringUtils.isNotBlank(stockLevel)){
			String temp = stockLevel+newLevel;
			if( stockLevel.length() >= ConstantValue.averageLevelLen ){
				return temp.substring(temp.length()-ConstantValue.averageLevelLen,temp.length());
			}
			else{
				return temp;
			}
		}
		else{
			return newLevel;
		}
	}

	public static String parseStockLevelDate( String stockLevelDate  ){
		String now = UtilTools.getDateFormat(new Date() ,"yyyyMMdd");
		if ( StringUtils.isNotBlank(stockLevelDate)){
			String temp = stockLevelDate+"/"+now;
			while(StringUtils.countMatches(temp, "/") >= ConstantValue.averageLevelLen ){
				temp =  temp.substring( temp.indexOf("/")+1 , temp.length());
			}
			return temp;
		}
		else{
			return now;
		}
	}

	public static Stock  addStockLevelInfo(Stock stock){

		int level = UtilTools.getStockAverageLevelJgpj(stock.getCode());
		logger.info(stock.getId() + " " + stock.getName() + " level:" + level);
		stock.setAverageLevel(UtilTools.parseStockLevel(stock.getAverageLevel() , level+""));

		return stock;
	}

	static public int getStockAverageLevel(String stockCode){
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page=null;
		try{
			page = webClient.getPage(ConstantValue.stockBaseUrl+stockCode+".shtml");
		}catch( Exception e ){
			e.printStackTrace();
		}
		if( page!=null){
			String pageStr = page.asXml();
			String levelStr = pageStr.substring(pageStr.indexOf("height=\"30\""),pageStr.indexOf("/2008/img/img60.gif"));
			return UtilTools.getLevel(levelStr);
		}
		return -1;
	}

	static public int getStockAverageLevelJgpj(String stockCode){
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page=null;
		try{
			page = webClient.getPage(ConstantValue.stockJgpjBaseUrl+stockCode+".shtml");
		}catch( Exception e ){
			e.printStackTrace();
		}
		if( page!=null){
			String pageStr = page.asXml();
			String levelStr = pageStr.substring(pageStr.indexOf("height=\"30\""),pageStr.indexOf("/2008/img/img60.gif"));
			return UtilTools.getLevel(levelStr);
		}
		return -1;
	}


	static public  int getScanPage(){
		String filePath = "src/main/resources/stock.properties";
		String propName = "maxpage";
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty (propName);
			Integer page = new Integer(value);
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(propName, Integer.valueOf(page-1).toString());
			props.store(fos, "Update "+propName+" value");
			fos.close();
			in.close();
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String getDateFormat(Date adate, String format) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		return formatDate.format(adate);
	}

	private static List<Stock> splitStock(String pageStock){
		List<Stock> sArr = new ArrayList<Stock>();
		String[] stockPage = pageStock.split(", ");
		for(int i=0;i< stockPage.length; i++){
			String[] stockStrArr = stockPage[i].split(",");
			String stockCode = stockStrArr[0];
			String stockName = stockStrArr[1];
			sArr.add( new Stock(stockCode.substring(2 , stockCode.length()-1) , stockName.substring(1,stockName.length()-1) ));
		}
		return sArr;
	}

	public static void main(String[] args){
		System.out.println(UtilTools.parseStockLevel("0123456789abcd", "ef"));
		System.out.println(UtilTools.parseStockLevelDate("0/1/2/3/4/5/6/7/8/9/0"));
	}


}
