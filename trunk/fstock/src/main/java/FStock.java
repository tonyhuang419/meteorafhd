
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class FStock {

	public void homePage() {
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			String basseUrlPreFix = "http://quote.tool.hexun.com/hqzx/quote.aspx?type=2&market=0&sorttype=3&updown=up&page=";
			String baseUrlSuffix = "&count=50";
			for(int i=1;i<=2;i++){
				page = webClient.getPage(basseUrlPreFix+i+baseUrlSuffix);
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

	public static void main(String[] args){
		FStock fstock = new FStock();
		fstock.homePage();
	}
}
