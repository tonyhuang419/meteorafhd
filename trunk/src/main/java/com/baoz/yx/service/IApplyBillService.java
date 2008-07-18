package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.bill.ApplyBill;

public interface IApplyBillService {
	/**
	 * 根据主合同查询开票申请
	 * @param contractMainInfo 合同主体信息号
	 */
	public List<ApplyBill> findApplyBillsByContractNo(Long contractMainInfo);
	
	
	
	/**
	 * 根据 合同主体号，获取未出库申购清单
	 * 
	 */
	public PageInfo loadNotOutPurchase(Long conSid,String aid,PageInfo info);


}
