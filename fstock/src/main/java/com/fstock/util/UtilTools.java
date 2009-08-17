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

	private static Pattern stockAvrPattern = Pattern.compile("\\[ (.*)];Stock");
	private static Pattern stockOrgPattern = Pattern.compile("<tr>(.*?)</tr>");

	public static List<Stock> getStockList(String str){
		List<Stock> stockList = new ArrayList<Stock>();
		Matcher m1 = stockAvrPattern.matcher(str);  
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


	public static String buildStockAverageLevel(String stockLevel , String newLevel){
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

	public static String buildStockLevelDate( String stockLevelDate  ){
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

	static public int parseStockAverageLevelJgpj(HtmlPage page){
		if( page!=null){
			String pageStr = page.asXml();
			int start = pageStr.indexOf("height=\"30\"");
			int end = pageStr.indexOf("/2008/img/img60.gif");
			if(start!=-1 && end!=-1){
				String levelStr = pageStr.substring(start,end);
				return UtilTools.getLevel(levelStr);
			}
			else{
				return 0;
			}
		}
		return -1;
	}
	
	static public String parseStockOrganizationLevelJgpj(HtmlPage page){
		if( page!=null){
			String pageStr = page.asXml();
			int start = pageStr.indexOf("1");
			int end = pageStr.indexOf("2");
			pageStr = pageStr.substring(start,end);
			System.out.println(pageStr);
			Matcher m1 = stockOrgPattern.matcher(pageStr);  
			while( m1.find() ){  
				int gc = m1.groupCount();  
				for(int i = 0; i <= gc; i++)  
					System.out.println("group " + i + " :" + m1.group(i));  
			}
		}
		return "";
	}

	public static Stock  addStockLevelInfo(Stock stock){
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page=null;
		try{
			page = webClient.getPage(ConstantValue.stockJgpjBaseUrl+stock.getCode()+".shtml");
		}catch( Exception e ){
			e.printStackTrace();
		}

		int averageLevel = UtilTools.parseStockAverageLevelJgpj(page);
		logger.info(stock.getId() + " " + stock.getName() + " averageLevel:" + averageLevel);
		stock.setAverageLevel(UtilTools.buildStockAverageLevel(stock.getAverageLevel() , averageLevel+""));

		return stock;
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

	@Deprecated
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

	public static void main(String[] args){
//		System.out.println(UtilTools.buildStockAverageLevel("0123456789abcd", "ef"));
//		System.out.println(UtilTools.buildStockLevelDate("0/1/2/3/4/5/6/7/8/9/0"));
		
		
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page=null;
		try{
			page = webClient.getPage(ConstantValue.stockJgpjBaseUrl+"000002"+".shtml");
		}catch( Exception e ){
			e.printStackTrace();
		}
		UtilTools.parseStockOrganizationLevelJgpj(page);
		
		
	}


}
