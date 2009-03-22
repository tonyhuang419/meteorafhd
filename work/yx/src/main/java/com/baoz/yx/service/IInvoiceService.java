package com.baoz.yx.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.vo.ProcessResult;

public interface IInvoiceService {
	
		
	public void text();
		
	/**
	 * 插入一条申请
	 * @param applyBill
	 */	
	public void saveApplications(ApplyBill applyBill,List<ApplyMessage> list,String rcList,Double applyAmount); 
	
	
	
	/**
	 * 保存一条申请
	 * @param applyBill
	 */	
	public void saveApplicationsX(ApplyBill applybill, List<ApplyMessage> list ,Map<Long, Double> m);
	
	
	/**
	 * 保存申请、计划关联 。。。  计划系统号，开票申请系统号，开票金额
	 */	
	public void saveBillandProRelaion(Map<Long, Double> m, ApplyBill applyBill);

	
	/**
	 * 修改申请、计划关联 。。。  计划系统号，开票申请系统号，开票金额
	 */	
	public void updateBillandProRelaion(Map<Long, Double> m, ApplyBill applyBill);
	
	
	
	/**
	 * 修改一条申请
	 * @param applyBill
	 */	
	public void updateInvoiceHasContract(ApplyBill applybill, List<ApplyMessage> list , Map<Long, Double> rrcPlanList );
	
	/**
	 * 根据申请单修改计划申请单状态
	 * @param applyBillId
	 */
	public void updatePlanApplyBillState(Long state,Long applyBillId);
	
	/**
	 *  返回所有开票申请
	 */
	public List<ApplyBill> findInvoiceApplications();
	
	/**
	 * 修改申请状态
	 * @param ids
	 */
	public void updateApplyBillState(String[] ids);
	
	
	/**
	 * 通过id关联
	 * @param id
	 * @return
	 */
	public List applicationsAssociation(String [] id);
	
	
	/**
	 * 通过id查找一条申请
	 * @param id
	 * @return
	 */
	public ApplyBill findOneInvocie(String id);
	
	/**
	 * 查找与开票单相关联的申请单
	 * @param id
	 * @return
	 */
	public List<ApplyMessage> findMessage(String id);
	
	/**
	 * 修改一个开票单
	 * @param ab
	 * @param list
	 * @param String id
	 */
	public void updateInvoice(ApplyBill ab,List<ApplyMessage> list,String id);
	
	/**
	 * 抛入开票申请系统号，断开申购关联
	 * @param id
	 */
	public void disconnectIncoice(String id);
	
	
	/**
	 * 删除一个申请单
	 * @param id
	 */
	public void  deleteApplyBill(String [] id);
	
	
	/**
	 * 按开票申请编号，获取是否统一开票(前提条件：已签开票)
	 * 2008年7月6日14:50:07
	 */
	public boolean getIsUniteBill(Long applysid);
	
	/**
	 * 按开票申请系统号，通过计划-申请关联表，返回实际开票和收款计划(项目号主体系统号、金额)
	 * 2008年7月8日18:26:36
	 */
	public Map<Long,Double> getRCplanFromApply(Long applysid);
	

	
	/**
	 * 按实际开票收款计划系统号，获取合同主体
	 * 2008年7月10日17:32:32
	 */
	public ContractMainInfo getCmiFromRCPlan(Long rcSysId);
	
	/**
	 * 按合同主体信息，获取合同客户信息
	 * 2008年7月10日17:32:32
	 */
	public YXClientCode getClientInfoFromCmi(ContractMainInfo cmi);
	
	
	/**
	 * 按合同主体信息，获取合同开票客户信息
	 * 2008年7月10日17:32:32
	 */
	public YXClientCode getBillClientInfoFromCmi(ContractMainInfo cmi);
	
	
	/**
	 * 按实际开票收款计划系统号，获取实际开票收款计划实例
	 * 2008年7月10日17:32:32
	 */
	public RealContractBillandRecePlan getRCPlanFromRCPlan(Long rcSysId);
	
	
	/**
	 * 按实际开票收款计划系统号，返回项目主体系统号
	 * 2008年7月10日17:32:32
	 */
	public Long getItemNo(Long rcSysId);
	
	
	/**
	 * 按实际开票收款计划系统号，获取项目名称
	 * 2008年7月10日17:32:32
	 */
	public String getCimiName(Long rcSysId);
	
	
	/**
	 * 按开票申请系统号，·获取关联实体
	 * 2008年7月10日17:32:32
	 */
	public BillandProRelaion getBPR(Long applyBillSid);
	
	
	
	/**
	 * 根据开票申请系统号,返回申请List
	 * 2008年7月14日14:34:57
	 */
	public List<ApplyMessage> loadAM(Long applyBillSid);
	
	/**
	 * 根据开票系统申请号设出库out，并录入合同号
	 * 2008年7月14日14:34:53
	 */
	public void makePurOut(Long applyBillSid);
	
	/**
	 * 根据开票系统申请号获取计划日期
	 * 2008年7月15日14:35:04
	 */
	public Date getPlanDate(Long applyBillSid);
	

	
	/**
	 * 根据开票申请系统号，获取其名下的所有关联发票
	 * 2008年7月17日10:59:33
	 */
	public List<InvoiceInfo> getInvoiceInfoFromApplySid (Long applyBillSid);
	
	
	/**
	 * 根据开票申请系统号，将其名下的所有发票关联合同
	 * 2008年7月17日10:59:33
	 */
	public void relateContract(Long applyBillSid , Long conSid);

	
	/**
	 * 根据开票申请系统号，卸掉其名下的所有发票关联合同
	 * 2008年7月17日10:59:33
	 */
	public void unRelateContract(Long applyBillSid);
	
	
	
	/**
	 * 按实际开票收款计划系统号，获取开票申请实例
	 * 2008年7月10日17:32:32
	 */
//	public ApplyBill getApplyBillFromRCPlan(Long rcSysId);
	
	/**
	 * 通过传入的itemSid查询itemMainInfo对象。得到项目主体信息，然后获取项目号
	 */
	public String getItemMainInfoByApplyBillSid(Long itemSid);
	
	
	/**
	 * 按票据类型，返回税率
	 */
	public String getTaxRate(String billType);
	
	/**
	 * 按开票申请获取计划时间
	 */
	public Map<Long,Date> getProDateFromApplyBill(List<Object[]> oArray);
	
	
	/**
	 * 按开票申请获取项目号
	 */
	public Map<Long,List<String>> getItemNumFromApplyBilll(List<Object[]> oArray);
	/**
	 * 红冲开票申请获取项目号
	 * @param oArray
	 * @return
	 */
	
	public Map<Long,List<String>> getItemNumFromHongChongApplyBilll(List<Object[]> oArray);
	
	/**
	 * 增加 销售员 客户 关联
	 */
	public void saveSaleAndCusRealtion(Long saleId , Long cusId);
	
	/**
	 * 按实际计划，获取开票性质
	 */
	public String getBillTypeFromRCPlan(long rcplansid);
	
	/**
	 * 按实际计划，获取阶段信息
	 */
	public String getStageByRCPlan(long rcplansid);
	
	/**
	 *  查找退回申请
	 */
	public PageInfo queryApplyBillRejectInfo(PageInfo info,String groupId ,Long expId);
	
	/**
	 * 修改发票
	 */
	public void modifyInvoice( InvoiceInfo  invoiceInfo);
	
	/**
	 * 按票据系统号返回实际计划
	 */
	public List<RealContractBillandRecePlan> doGetRcbrpByInvoiceId( InvoiceInfo  invoiceInfo);
	
	/**
	 *  获取一个计划下所有开票的最大开票日期
	 */
	public Date doGetMaxInvoiceDate( InvoiceInfo  invoiceInfo);
	
	/**
	 * 检测该申请单是否能取消确认
	 * @param applyBillId
	 * @return
	 */
	public ProcessResult checkApplyBillCancelConfirm(Long applyBillId);
	
}
