package com.fstock.run;

import java.util.List;

import com.fstock.entity.Stock;
import com.fstock.util.UtilTools;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



public class FStock {

	static String allStockUrlPreFix = "http://quote.tool.hexun.com/hqzx/quote.aspx?type=2&market=0&sorttype=3&updown=up&page=";
	static String allStockUrlSuffix = "&count=50";

	static String stockBaseUrl = "http://stockdata.stock.hexun.com/";

	public void getAllStock() {
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			for(int i=1;i<=1;i++){
				page = webClient.getPage(FStock.allStockUrlPreFix+i+FStock.allStockUrlSuffix);
				String stockInfo = page.getBody().asText();
				List<Stock> stocklist = UtilTools.getStockList(stockInfo);
				for(Stock stock:stocklist){
					System.out.println(stock.getCode() + ":" + stock.getName());
				}
			}
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public void parseStockPage(String stockCode){
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page=null;
		try{
			page = webClient.getPage(FStock.stockBaseUrl+stockCode+".shtml");
		}catch( Exception e ){
			e.printStackTrace();
		}
		if( page!=null){
			String pageStr = page.asXml();
			String levelStr = pageStr.substring(pageStr.indexOf("height=\"30\""),pageStr.indexOf("/2008/img/img60.gif"));
			System.out.println(UtilTools.getLevel(levelStr));
		}

	}

	public static void main(String[] args){
		FStock fstock = new FStock();
		//		fstock.getAllStock();
		fstock.parseStockPage("600320");

	}
}
