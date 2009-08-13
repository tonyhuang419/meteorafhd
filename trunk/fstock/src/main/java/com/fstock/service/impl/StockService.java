package com.fstock.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fstock.entity.Stock;
import com.fstock.service.ICommonService;
import com.fstock.service.IStockService;
import com.fstock.util.ConstantValue;
import com.fstock.util.UtilTools;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


@Service("stockService")
@Transactional
public class StockService implements IStockService {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;


	public void getAllStockAndPersist() {
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			for(int i=1;i<=1;i++){
				page = webClient.getPage(ConstantValue.allStockUrlPreFix+i+ConstantValue.allStockUrlSuffix);
				String stockInfo = page.getBody().asText();
				List<Stock> stocklist = UtilTools.getStockList(stockInfo);
				for(Stock stock:stocklist){
					//System.out.println(stock.getCode() + ":" + stock.getName());
					if(this.existRepeat(stock)== false){
						commonService.save(stock);
					}
				}
			}
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public int getStockLevel(String stockCode){
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

	public boolean existRepeat(Stock stock){
		if( StringUtils.isNotBlank(stock.getCode()) &&  StringUtils.isNotBlank(stock.getName()) ){
			logger.info("stock has not enough info , code and name is required");
			return false;
		}
		else{
			Stock stockOrg = (Stock)commonService.listHql(" from Stock s where s.code = ?", "", stock.getCode());
			if(stockOrg==null){
				return true;
			}
			else{
				logger.info(" stock code " + stock.getCode() +":" + stock.getName() + " exist repeat : " + stockOrg.getCode()+":"+stockOrg.getName() );
				return false;
			}
		}
	}

}
