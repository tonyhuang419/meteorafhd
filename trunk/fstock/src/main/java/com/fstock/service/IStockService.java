package com.fstock.service;

import com.fstock.entity.Stock;

public interface IStockService {

	/**
	 * query all stocks and persost their
	 */
	public void getAllStockAndPersist();
	
	
	/**
	 * save stock average level
	 */
	public void saveAverageLevel(Stock stock);
	
	
	/**
	 * parse page and return its level
	 * @param stockCode
	 * @return
	 */
	public int getStockAverageLevel(String stockCode);
	
	/**
	 * is or not exist repeat
	 * @param stock
	 * @return
	 */
	public boolean existRepeatStock(Stock stock);
	
	
	public void updateAllAverageLevel();
	
}
