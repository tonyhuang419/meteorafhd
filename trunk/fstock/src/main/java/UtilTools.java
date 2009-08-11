import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilTools {
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
		UtilTools.getStockList("dataArr = [ ['600810','780',9.90,-4.35,10.35,10.26,10.27,9.80,92828.24,92851903,4.46,4.54,0.33], ['600596','新安股份',47.32,-4.38,49.49,48.99,48.99,47.01,41738.57,198895053,2.12,4.00,1.27], ['600596','新安股份',47.32,-4.38,49.49,48.99,48.99,47.01,41738.57,198895053,2.12,4.00,1.27]];StockListPage.GetData(dataArr");
	}
	
}
