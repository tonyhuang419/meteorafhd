package com.baoz.yx.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.NoContractRecevieAmount;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;

public interface IHarvestService {
	/**
	 * 删除收款明细
	 * 
	 * @param idss
	 * @return
	 */
	public void delReveInfoByID(Long id);
	
	
	/**
	 * 获取项目开票总额By项目系统号
	 */
	public Double getItemBillAmoutByItemID(Long itemID);
	
	
	/**
	 * 获取合同开票总额By合同系统号
	 */
	public Double getItemBillAmoutByConID(Long conID);
	
	
	
	/**
	 * 获取项目开票收款计划By项目系统号
	 */
	public List<RealContractBillandRecePlan> getRealContractBillandRecePlanByItemID(Long itemID);
	
	
	/**
	 * 获取合同开票收款计划By合同系统号
	 */
	public List<RealContractBillandRecePlan> getRealContractBillandRecePlanByConID(Long conID);
		
		
	/**
	 * 获取合同开票总额By项目List
	 */
	public Map<Long,Double> getItemBillAmoutByItemList(List<ContractItemMaininfo> iteminfolist);
	
	
	/**
	 * 获取项目可收款余额
	 */
	public Double getBalanceByItemID(Long itemID);
	
	/**
	 * 获取合同可收款余额
	 */
	public Double getBalanceByConID(Long conID);
	
	
	/**
	 *  查询收款信息
	 */
	public PageInfo getReveinfo(PageInfo info,String groupId, Long expId ,String projectNo, 
			String contractId , String noRemain, String  reveStartDate, String  reveEndDate);
	
	
	/**
	 *  查询收款信息,首页用
	 */
	public PageInfo getReveinfoForSaler(PageInfo info,Long uid);
	
	/**
	 * 查询收款信息by项目系统号 	
	 */
	public List<ReveInfo> getReveInfoByItemId(Long itemID);
	
	/**
	 * 查询收款信息by合同系统号 	
	 */
	public List<ReveInfo> getReveInfoByConId(Long conID);
	
	
	
	/**
	 * 获取未确认的收款
	 */
	public List<ReveInfo> getReveInfoNotSure();
	
	/**
	 * 查询ReveInfoByID	
	 */
	public ReveInfo loadReveInfoById(Long reveID);
	
	/**
	 * 获得项目已收总额 by项目系统号
	 */
	public Double getSumByItemId(Long itemID);
	
	
	/**
	 * 获得合同已收总额 by合同系统号
	 */
	public Double getSumByConId(Long conID);
	
	/**
	 * 增加、修改 收款信息
	 */
	public void saveOrUpdateReveInfo(ReveInfo r);
	
	
	/**
	 * 获取分配金额通过合同号
	 */
	public Double getDistributeMoneyXByConId(Long conId);
	
	
	/**
	 * 按输入分配金额
	 */
	public List<RealContractBillandRecePlan> processRP(List<RealContractBillandRecePlan> rp);
	
	/**
	 * 获取分配金额通过项目号
	 */
	public Double getDistributeMoneyXByItemId(Long itemId);
	
	
	/**
	 * 删除收款信息
	 */
//	public void delReveInfo(ReveInfo r);
	
	//测试用
//	public void testGetReveinfo();
	/**
	 * 传入的是list。然后修改状态
	 */
	public boolean confirmReveInfo(List<ReveInfo> list);
	
	public void modifyRealArriveAmount(Long conId,Long itemId, Date amountTime, Long reveInfoId);
	
	/**
	 * 更新计划的
	 * @param plan
	 * @param amountTime
	 */
	public void updateMonthlyRecePlan(RealContractBillandRecePlan plan,Date amountTime);
	
	public void modifyInvoiceAmount(Long conId);
	
	/**
	 * 通过传入的reveinfo。修改realContractBillandRecePlan里面的本次到款金额的值
	 * @param conId合同号，itemId项目号
	 */
	public void modifyCurrentArriveAmount(Long conId,Long itemId);

	
    //查询出当前登陆人员所录入的未签收款
	public PageInfo getNoContractReceiveAmount(String groupId,Long saleMan,String startRecevieDate,String endRecevieDate,String state,Long id,PageInfo info);
	/**
	 * sum出总收款
	 * @param groupId
	 * @param saleMan
	 * @param startRecevieDate
	 * @param endRecevieDate
	 * @param state
	 * @param id
	 * @return
	 */
	public Double getSumReceiveAmount(String groupId,Long saleMan,String startRecevieDate,String endRecevieDate,String state,Long id);

	//根据系统ID号查询未签合同收款对象
	public NoContractRecevieAmount loadNoContractRecevieAmount(
			Long recevieAmountId);


	//根据客户ID获取客户名称
	public String getCustomerNameById(Long customer);

    //保存合同未签收款对象
	public void saveNoContractReceiveAmount(NoContractRecevieAmount receAmount);

    //删除未签合同收款对象
	public void delReceAmount(Long deleteid);

    //改变收款状态
	public void changeState(Long long1,String str,String remark);
	
	/**
	 * 传入收款信息id进行单个收款确认
	 * @param reveId
	 */
	public void confirmOneReveInfo(Long reveId);
	
	public List<Object[]> test();

	/**
	 * 传入一个到款日期，如果该日期没有在月度收款计划内，
	 * 返回false，表示不能确认到款，
	 * 返回true表示在月度收款计划内，表示可以到款
	 * @param arriveDate
	 * @return
	 */
	public Boolean checkDateInMonthlyRece(Date arriveDate);
	
	public void modifyInfo(Long conId,Long itemId);
}
