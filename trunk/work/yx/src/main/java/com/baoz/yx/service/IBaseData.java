package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.vo.ProcessResult;

public interface  IBaseData {
	
	public PageInfo queryModifyContractBaseInfo(PageInfo info , String conId , String itemId );
	
	public PageInfo cancelReceByReceQuery(PageInfo info , String conId);
	
	public void saveChange(ContractMainInfo cmi);
	
	/**
	 * 取消月度开票查询Query
	 */
	public PageInfo cancelMonthBillQuery(PageInfo info , String conId);
	
	
	/**
	 * 取消月度收款查询Query
	 */
	public PageInfo cancelMonthReceQuery(PageInfo info , String conId);
	
	/**
	 * 取消月度开票查询
	 */
	public ProcessResult cancelMonthBill(Long planId);
	
	
	/**
	 * 取消月度收款查询
	 */
	public ProcessResult cancelMonthRece(Long planId);
	
	public void updatePartName(List<ContractMaininfoPart> mainInfoPartList,Long mainId);
		
}
