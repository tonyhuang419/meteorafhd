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
	public void saveAverageLevel(String stockCode);
	
	
	/**
	 * parse page and return its level
	 * @param stockCode
	 * @return
	 */
	public int getStockLevel(String stockCode);
	
	/**
	 * is or not exist repeat
	 * @param stock
	 * @return
	 */
	public boolean existRepeat(Stock stock);
	

	
}
