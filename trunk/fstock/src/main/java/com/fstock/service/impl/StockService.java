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

	public void saveStockLevel(Stock stock){
		if(stock==null){
			return;
		}
		if(stock!=null){
			stock = UtilTools.addStockLevelInfo(stock);
			stock.setAverageLevelDate(UtilTools.buildStockLevelDate(stock.getAverageLevelDate()));
		}
		commonService.update(stock);
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
					this.saveStockLevel(stock);
				}
				else{
					logger.info( stock.getCode() + " today has got level");	
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> scanNewestAveragerLevel(String level){
		List<Object[]> stoclList = commonService.listSQL("select right(s.AVERAGE_LEVEL_DATE,8) , s.CODE ,s.NAME  from stock s where right(s.AVERAGE_LEVEL , 1 ) = '" + level + "'"
				 , " order by s.id asc ");
		return stoclList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Stock> findDateOrganizationLevel( IStockService stockService , String date , String level){
		List<Stock> stockList = commonService.listHql(" from Stock s where s.organizationLevelDate like ? ",  " order by s.id asc " , "%"+date+"%" );
		if(StringUtils.isBlank(level)){
			return stockList;
		}
		for(Stock stock:stockList){
			int i = 0;
			String[] dateArr = stock.getOrganizationLevelDate().split("/");
			for(String str :dateArr ){
				if(str.equals(date)){
					if( level.equals(stock.getOrganizationLevel().charAt(i)+"")){
						break;
					}
				}
				else{
					i++;
					if(i==dateArr.length){
						stockList.remove(stock);
					}
					continue;
				}
			}
		}
		return stockList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Stock> findDateOrganizationLevel( IStockService stockService , String startDate , String endDate ,  String level){
		if( startDate.compareTo(endDate)>0 ){
			String temp = endDate;
			endDate = startDate;
			startDate = temp;
		}
		StringBuffer sb = new StringBuffer("s.organizationLevelDate like '%"+startDate+"%' ");
		String tempDate = UtilTools.addDate(startDate, 1, "yyyyMMdd");
		while(tempDate.compareTo(endDate)<1){
			sb.append("or s.organizationLevelDate like '%"+tempDate+"%'");
			tempDate = UtilTools.addDate(tempDate, 1, "yyyyMMdd");
		}
		List<Stock> stockList = commonService.listHql(" from Stock s where "+    sb.toString() ,  " order by s.id asc "  );
		return stockList;
	}
	
	
}



