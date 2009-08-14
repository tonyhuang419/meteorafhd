package com.fstock.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fstock.entity.Stock;
import com.fstock.service.ICommonService;
import com.fstock.service.IStockService;
import com.fstock.util.ConstantValue;
import com.fstock.util.UtilTools;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


@Service("stockService")
//@Transactional
public class StockService implements IStockService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	protected Log logger = LogFactory.getLog(this.getClass());

	public void getAllStockAndPersist() {
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			int pageNum = UtilTools.getScanPage();
			while(pageNum>0){
				page = webClient.getPage(ConstantValue.allStockUrlPreFix+pageNum+ConstantValue.allStockUrlSuffix);
				String stockInfo = page.getBody().asText();
				List<Stock> stocklist = UtilTools.getStockList(stockInfo);
				for(Stock stock:stocklist){
					//System.out.println(stock.getCode() + ":" + stock.getName());
					if(this.existRepeatStock(stock)== false){
						commonService.save(stock);
					}
				}
				pageNum = UtilTools.getScanPage();
			}
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public void saveAverageLevel(Stock stock){
		int level = this.getStockAverageLevel(stock.getCode());
		logger.info(stock.getId() + " " + stock.getName() + " level:" + level);
		if(stock!=null){
			stock.setAverageLevel(UtilTools.addStockLevel(stock.getAverageLevel() , level+""));
			stock.setAverageLevelDate(UtilTools.addStockLevelDate(stock.getAverageLevelDate()));
		}
		commonService.update(stock);
	}

	public int getStockAverageLevel(String stockCode){
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

	public boolean existRepeatStock(Stock stock){
		if( StringUtils.isBlank(stock.getCode()) ||  StringUtils.isBlank(stock.getName()) ){
			logger.info("stock has not enough info , code and name is required");
			return true;
		}
		else{
			Stock stockOrg = (Stock)commonService.uniqueResult(" from Stock s where s.code = ? ", stock.getCode());
			if(stockOrg==null){
				return false;
			}
			else{
				logger.info(" stock code " + stock.getCode() +":" + stock.getName() + "  exist repeat : " + stockOrg.getCode()+":"+stockOrg.getName() );
				return true;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateAllAverageLevel(){
		int pageSize = 30;
		Long count = (Long)commonService.uniqueResult("select count(*) from Stock ");
		int tmpCount = 0;
		for(int i=0; i<count; i = i+pageSize-1){
			List<Stock> list = commonService.listHql(" from Stock s ", " order by s.id asc " , tmpCount , pageSize );
			tmpCount = tmpCount+pageSize;
			for(int j=0;j<list.size();j++){
				Stock stock = (Stock)list.get(j);
				logger.info("start : " + stock.getCode());
				if( StringUtils.isBlank(stock.getAverageLevelDate())
						|| stock.getAverageLevelDate().indexOf(UtilTools.getDateFormat(new Date() ,"yyyyMMdd")) == -1){
					this.saveAverageLevel(stock);
				}
				else{
					logger.info( stock.getCode() + " today has got level");	
				}
			}
		}
	}

}



