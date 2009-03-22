package com.baoz.yx.service;

import java.util.List;

import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.HongChongRelationPlan;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.vo.ProcessResult;

public interface IApplyBillService {
	/**
	 * 根据主合同查询开票申请
	 * 
	 * @param contractMainInfo
	 *            合同主体信息号
	 */
	public List<ApplyBill> findApplyBillsByContractNo(Long contractMainInfo);

	/**
	 * 根据 合同主体号，获取未出库申购清单
	 * 
	 */
	public PageInfo loadNotOutPurchase(Long uid, Long conSid, String aid,
			PageInfo info);

	/**
	 * 查询出没又确认录入的开票列表
	 * 
	 * @return
	 */
	public String showNoInputState();

	/**
	 * 查询显示发票收据管理
	 * 
	 * @param confirm
	 * @param selectState
	 * @return
	 */
	public String showBillReceiptQuery(Long confirm, String groupId,
			Long saleMan, String contractNo, String itemNo, Double billAmountTax,String invoiceNo,String hasSigned);

	/**
	 * 根据开票id计算已开票后的余额
	 * 
	 * @param billId
	 * @return
	 */
	public Double balanceInvoiceAmount(Long billId);

	/**
	 * 查询登陆用户开票信息
	 * 
	 * @param uid
	 * @return
	 */
	public List<InvoiceInfo> findInvoiceInputState(Long uid);

	/**
	 * 查询显示实际合同以供关联发票
	 * 
	 * @param conId
	 * @param itemId
	 * @return
	 */
	public String[] showRealRelation(String conName, String conId,
			String itemId, Long billId);

	/**
	 * 显示已签开票申请实际表内容
	 * @param isFromMenu
	 * @param conId
	 * @param itemId
	 * @param customerName
	 * @param customer
	 * @param minRealTaxBillAmount
	 * @param maxRealTaxBillAmount
	 * @param minRealBillAmount
	 * @param maxRealBillAmount
	 * @param monthSel
	 * @param yearSel
	 * @param minConAmount
	 * @param maxConAmount
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String[] showApplyBillQuery(boolean isFromMenu, String conId,
			String itemId, String customerName, Long customer,
			Double minRealTaxBillAmount, Double maxRealTaxBillAmount,
			Double minRealBillAmount, Double maxRealBillAmount,
			String monthSel, String yearSel, Double minConAmount,
			Double maxConAmount,String startDate,String endDate);
	/**
	 * 判断收据是否被关联过
	 * @param planId
	 * @return
	 */
	public boolean isRelationReceipt(Long planId);
	
	/**
	 * 显示已签开票申请中选中弹出的已签开票
	 * 
	 * @param monthlyBillproSids
	 * @return
	 */
	public String showSelectApplyBillQuery(Long[] monthlyBillproSids);

	/**
	 * 只能查询出不符合开票的计划
	 * 
	 * @param monthlyBillproSids
	 * @return
	 */
	public String showSelectRealReceipt(Long[] monthlyBillproSids);

	/**
	 * 显示单条计划
	 * 
	 * @param realId
	 * @return
	 */
	public String showSelectRealReceipt(Long realId);

	/**
	 * 显示计划开票关联收据
	 * 
	 * @param monthlyBillproSids
	 * @param billNature
	 * @return
	 */
	public String showRecitpQuery(String[] billNature, Long[] conId);

	/**
	 * 显示相同合同号的实际开票
	 * 
	 * @param conId
	 * @return
	 */
	public String showTheSameConId(String conId);

	/**
	 * 开票收据管理
	 * 
	 * @param confirm
	 * @param selectState
	 * @return
	 */
	public String showBillReceiptQuery(Long confirm, Long selectState);

	/**
	 * 收据转发票
	 * 
	 * @param relationState
	 * @param contractId
	 * @param minBillAmount
	 * @param maxBillAmount
	 * @param stratReceiptDate
	 * @param endReceiptDate
	 * @return
	 */
	public String[] showReceiptToBillQuery(Integer relationState,
			Long customerId, String contractId, Double minBillAmount,
			Double maxBillAmount, String stratReceiptDate, String endReceiptDate);

	/**
	 * 开票时候判断是否需要添加关联收据
	 * 
	 * @param monthlyBillproSids
	 * @return
	 */
	public boolean judgementBill(Long[] monthlyBillproSids);

	/**
	 * 关联合同时候判断是否需要关联收据
	 * 
	 * @param realId
	 * @return true不需要关联，false需要关联
	 */
	public boolean judgementBillSingle(Long realId);

	/**
	 * 根据bill的ID或者receipt的id去计算总收据金额
	 * 
	 * @param billRealId
	 * @return
	 */
	public Double sumRelationBill(Long billRealId);

	/**
	 * 根据bill的ID或者receipt的id去计算总收据金额
	 * 
	 * @param receiptId
	 * @return
	 */
	public Double sumRelationReceipt(Long receiptId);

	/**
	 * 计算计划内笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalPlaninside(Long groupId, Long saleMan,
			String yearSel, String monthSel);

	/**
	 * 计划实际笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalReal(Long groupId, Long saleMan, String yearSel,
			String monthSel);

	/**
	 * 计算实际计划内笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalRealInside(Long groupId, Long saleMan,
			String yearSel, String monthSel);

	/**
	 * 计划内收款笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalRecePlaninside(Long groupId, Long saleMan,
			String yearSel, String monthSel);

	/**
	 * 计算实际收款笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalReceReal(Long groupId, Long saleMan,
			String yearSel, String monthSel);

	/**
	 * 计算实际计划内笔数和额度
	 * 
	 * @param groupId
	 * @param saleMan
	 * @param yearSel
	 * @param monthSel
	 * @return
	 */
	public List<Object> totalReceRealInside(Long groupId, Long saleMan,
			String yearSel, String monthSel);

	/**
	 * 查询显示开票精度统计明细
	 * 
	 * @param info
	 * @param empId
	 * @return
	 */
	public PageInfo billDetialQuery(PageInfo info, Long empId, String yearSel,
			String monthSel);

	/**
	 * 查询显示收款精度统计明细
	 * 
	 * @param info
	 * @param empId
	 * @return
	 */
	public PageInfo receDetialQuery(PageInfo info, Long empId, String yearSel,
			String monthSel);

	/**
	 * 判断开票申请是否开过票
	 * 
	 * @param applyBillId
	 * @return
	 */
	public Boolean isBill(Long applyBillId);

	/**
	 * 验证是否可以合并计划
	 * 
	 * @param monthlyBillproSids
	 * @return
	 */
	public Boolean isSameReal(Long monthlyBillproSids[]);
	/**
	 * 判断是否能被一个未签申请单关联,只判断是否在一个合同和一样的费用类型
	 * @param monthlyBillproSids
	 * @return
	 */
	public Boolean isSameRealById(Long monthlyBillproSids[]);
	
	/**
	 * 取消拆分,合并计划 留id小的,删除大的! 有开票申请的不可以合并
	 * 
	 * @param realId
	 */
	public void mergeRealPlan(Long monthlyBillproSids[]);
	/**
	 * 提交红冲申请
	 * @param invoiceId
	 * @param hongChongRelation TODO
	 */
	public ProcessResult applyHongChong(Long invoiceId, List<HongChongRelationPlan> hongChongRelation);
	
	/**
	 * 显示发票和申请单信息
	 * @param invoiceId
	 * @return
	 */
	public List<Object> shouInvoiceInfo(Long invoiceId);
	/**
	 * 显示红冲申请时候显示计划
	 * @param invoiceId
	 * @return
	 */
	public List<Object[]> showPlanHongChong(Long invoiceId);
	/**
	 * 红冲确认时候显示发票申请单明细
	 * @param invoiceId
	 * @return
	 */
	public List<Object> showConfirmInvoiceInfo(Long invoiceId);
	/**
	 * 显示确认红冲时候计划信息
	 * @param invoiceId
	 * @return
	 */
	public List<Object[]> showConfirmPlanInfo(Long invoiceId);
	/**
	 * 取消一张发票,生成一个新的计划重新申请开票
	 * 
	 * @param invoiceId
	 */
	public ProcessResult returnInvoicePlan(Long invoiceId);
	/**
	 * 取消处理
	 * @param billId
	 * @return
	 */
	public ProcessResult returnProcessed(Long billId);
	/**
	 * 取消确认
	 * @param billId
	 * @return
	 */
	public ProcessResult returnConfirm(Long billId);
	/**
	 * 计算精度百分比
	 * @param divisor
	 * @param divdend
	 * @return
	 */
	public Double calPercentage(Double divisor,Double divdend);
	/**
	 * 计算实际帐龄
	 * @param minMonth
	 * @param maxMonth
	 * @return
	 */
	public Double calRealTotal(Long minMonth,Long maxMonth);
	/**
	 * 计算逻辑帐龄
	 * @param minMonth
	 * @param maxMonth
	 * @return
	 */
	public Double calLogicTotal(Long minMonth,Long maxMonth);
	/**
	 * 拆分计划开票金额
	 * @param realPlanId
	 * @param splitAmount
	 * @param splitReceAmount
	 */
	public void splitBillAmount(Long realPlanId,Double splitAmount,Double splitReceAmount);
	/**
	 * 显示发票信息
	 * @param billId TODO
	 * @return
	 */
	public List<InvoiceInfo> showInvoiceInfo(Long billId);
	
	/**
	 * 删除已被确认的发票
	 */
	public ProcessResult delHasSuredInvoice( Long invoiceId );
	
}
