package com.baoz.yx.service;

import java.util.List;
import java.util.Map;

import com.baoz.yx.entity.YXTypeManage;

public interface IYXTypeManageService {
	public List<YXTypeManage> loadAll(List<YXTypeManage> typeManageList);
	
	public void deleteAll(List<YXTypeManage> typeManageList);
	
	/**
	 * 根据大类获得大类下面的值集
	 * @param typeBig
	 * @return
	 */
	public List<YXTypeManage> getYXTypeManage(Long typeBig);
	/**
	 * 根据大类和小类获得typeManage
	 * @param typeBig
	 * @return
	 */
	public YXTypeManage getYXTypeManage(Long typeBig , String typeSmall);
	
	/**
	 * 得到typeManage对应的typeBig的名称
	 * @param typeManageList
	 * @return
	 */
	public List<String> getAllTypeBig(List<YXTypeManage> typeManageList);
	/**
	 * 得到typeBig的名称
	 * @param typeManageList
	 * @return
	 */
	public String getTypeBigName(Long typeBig);
	/**
	 * 得到所有typeBig的数值和名称的map
	 * @return
	 */
	public Map<Long,String> getTypeBigMap();
	/**
	 *  获得收款相对于开票的延后月数
	 * @param typeSmall
	 * @return
	 */
	public int getHarvestMonth(String typeSmall);
}
