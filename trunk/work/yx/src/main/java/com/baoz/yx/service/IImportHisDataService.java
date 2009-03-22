package com.baoz.yx.service;

import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.entity.importfile.TempImportReveInfo;
import com.baoz.yx.vo.ExcelContractGatheringHistory;
import com.baoz.yx.vo.ProcessResult;

public interface IImportHisDataService {

	/**
	 * 错误信息
	 * **/
	public static StringBuffer loggerError=new StringBuffer();
	
	/**
	 * 成功信息
	 * **/
	public static StringBuffer loggerSuccess=new StringBuffer();
	
	/**
	 * 合同生成正式合同以后的导入开票收款计划，此时根据实际的项目开票收款计划表生成开票申请和发票。同时生成开票申请和发票的多对多的表
	 * @param history
	 * @param plan
	 * @param mainCon
	 */

	
	public void importBillAndRecePlan(ExcelContractGatheringHistory history,List<RealContractBillandRecePlan> planList,ContractMainInfo mainCon);
	
	public void GeneratePlanByStageId(Long mainid, Long stageid,String moneytype, Double money);

	/**
	 * 导入收款确认
	 * @param reveInfoList
	 * @throws Exception
	 */
	public void importReveInfo(List<TempImportReveInfo> reveInfoList) throws Exception;
	
	/**
	 * 传入已经关闭的合同对象。然后生成开票申请，生成发票,生成收款
	 * @param contract
	 * @param fullBill TODO
	 * @param fullRece TODO
	 */
	public void createFullByImoprtClosedCon(ContractMainInfo contract, boolean fullBill, boolean fullRece);
	/**
	 * 全额开票和收款项目
	 * @param project
	 * @param fullBill 是否全额开票
	 * @param fullRece 是否全额收款
	 */
	public void createFullBillAndReceProject(ContractItemMaininfo project,boolean fullBill,boolean fullRece);
	/**
	 * 全额开票和收款计划
	 * @param plan
	 * @param fullBill 是否全额开票
	 * @param fullRece 是否全额收款
	 */
	public void createFullBillAndRecePlan(RealContractBillandRecePlan plan,boolean fullBill,boolean fullRece);

	/**
	 * 全额开票和收款计划
	 * @param plan
	 * @param fullBill 是否全额开票
	 * @param fullRece 是否全额收款
	 * @return TODO
	 */
	public ProcessResult createFullBillAndRecePlan(RealContractBillandRecePlan plan,boolean fullBill,boolean fullRece,Date preBillDate,Date preReceDate);

	public RealContractBillandRecePlan getPlan(Long id);
	
	/**
	 * 取消开票
	 * @param planId
	 * @param contractCode 用来验证是否和planId一致
	 * @param saleName 用来验证是否和planId一致
	 */
	public void cancelPlanBill(long planId,String contractCode ,String saleName);
	/**
	 * 取消开票
	 * @param planId
	 * @return TODO
	 */
	public ProcessResult cancelPlanBill(long planId);
	/**
	 * 取消收款
	 * @param planId
	 * @param contractCode
	 * @param saleName
	 */
	public void cancelPlanRece(long planId,String contractCode ,String saleName);	
	/**
	 * 取消收款
	 * @param planId
	 * @param contractCode 用来验证是否和planId一致
	 * @param saleName 用来验证是否和planId一致
	 * @return TODO
	 */
	public ProcessResult cancelPlanRece(long planId);
	
	/***
	 * 修改预计开票日期和预计收款日期
	 * @param plan
	 * @param realBillDate
	 * @param realReceDate
	 */
	public void updateRealBillAndReceDate(RealContractBillandRecePlan plan,Date realBillDate,Date realReceDate);
	
	/**
	 * 更行统计应收和统计字段
	 * @param planId
	 */
	public void updateAndCal(Long planId);
	
	/**
	 * 导入收款
	 * @param reveSheet
	 * @throws Exception
	 */
	public void importReve(Sheet reveSheet)throws Exception;

	
	/**
	 * 导入应收
	 * @param compareSheet
	 * @throws Exception
	 */
	public void importDueFromCompare(Sheet compareSheet) throws Exception;
	
	/**
	 * 检查应收信息是否合适
	 * @param compareId
	 * @return
	 */
	public ProcessResult checkDueFromCompare(Long compareId);
	
	/**
	 * 取消收款
	 */
	public ProcessResult cancelReceByRece(long receId);
	
}
