package com.baoz.yx.service;

import java.util.Date;
import java.util.List;

import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.vo.ProcessResult;

/**
 * 
 * @author xusheng
 *@version 1.0
 *@createDate 2008年7月14日
 */
public interface IContractCommonService {

	/**
	 * 通过输入的部门名称和部门负责人判断是否有关联，如果没有关联的话新建一个用户名关联
	 * @param departmentCode
	 * @param chargeManName
	 */
	public void addChargeManAndDepartment(String departmentCode,String chargeManName);

	/**
	 * 通过合同id判断合同下面的项目总金额是否等于合同总金额
	 * @param mainInfoId
	 * @return 0、不相等;1、相等
	 */
	public int checkItemInfoAmountEqualsMainInfoAmount(Long mainInfoId);
	
	/**
	 * 通过合同id判断合同下面的阶段总金额是否等于合同总金额
	 * @param mainInfoId
	 * @return 0、不相等;1、相等
	 */
	public int checkStagePlanAmountEqualsMainInfoAmount(Long mainInfoId);	
	/**
	 * 通过合同id判断合同下面的计划开票金额是否等于合同总金额
	 * @param mainInfoId
	 * @return 0、不相等;1、相等
	 */
	public int checkBillProAmountEqualsMainInfoAmount(Long mainInfoId);
	
	/**
	 * 通过合同id判断合同下面的费用总金额是否等于合同总金额
	 * @param mainInfoId
	 * @return 0、不相等;1、相等
	 */
	public int checkPartInfoAmountEqualsMainInfoAmount(Long mainInfoId);
	
	/**
	 * <p>在添加发票关联的时候。
	 * 传入类型为收据的发票，
	 * 然后判断里面的金额是否转换完毕，
	 * 如果转换完毕了需要把发票表的changeStatus字段设为1，
	 * 其他的为0;</p>
	 * @param invoiceId
	 */
	public void setChangeStatusByInvoiceId(Long invoiceId);
	
	/**
	 * 根据传入的老项目号查询申购采购和外协合同里面与之相关联的记录，并且修改成新的项目号
	 * @param oldItemNo
	 * @param newItemNo
	 */
	public void updateItemNoOnMessageAndAssistanceContract(String oldItemNo,String newItemNo);
	
	/**
	 * 通过客户id查询出客户名称
	 * @param id
	 * @return
	 */
	public YXClientCode getOneYXclientCode(Long id);
	
	/**
	 * 修改实际收款计划表中的实际收款日期。并往变更表中添加一条数据
	 * @param oldPlan
	 * @param history
	 */
	public void updateBillAndReceplan(RealContractBillandRecePlan oldPlan,BillReceChangeHistory history);
	
	/**
	 * 计算计划的已开票总金额，每次操作invoiceInfo表时调用
	 * @param invoiceInfo
	 */
	public void calBillInvoiceAmount(Long applyBillId);
	
	/**
	 * 更新收据的应收
	 * @param receiptPlan
	 */
	public void updateReciptShouldAmount(RealContractBillandRecePlan receiptPlan);
	
	/**
	 * 更新关联应收，和收据的应收
	 * @param billPlanId 开票计划id
	 */
	public void updateRelationAndReceiptShouldAmount(Long billPlanId);
	
	
	/**
	 * 取消发票收据关联
	 */
	public void releaseRelationAndReceiptShouldAmount(Long billPlanId);
	
	/**
	 * 添加的时候判断发票号是否重复，如果重复把该发票号返回
	 * @param invoiceNo
	 * @return
	 */
	public String checkInvoiceNoIsExists(String invoiceNo);
	/**
	 * 修改的时候判断发票号是否重复
	 * @param invoiceNo
	 * @param invoiceId
	 * @return
	 */
	public String checkInvoiceNoIsExists(String invoiceNo,Long invoiceId);
	
	/**
	 * 实际开票日期和计划id
	 * @param invoiceDate
	 * @param realPlanId
	 * @param isHongChong
	 */
	public void updatePlanStatistic(Date invoiceDate, Long realPlanId, boolean isHongChong);
	
	public void cancelInvoice(Long invoiceInfoId);
	/**
	 * 更新没有开票计划的金额
	 * @param newNoBillApplyPlan
	 */
	public ProcessResult updateNoApplyBillPlan(RealContractBillandRecePlan newNoBillApplyPlan);
	/**
	 * 根据计划中的金额，计算并更新合同，费用，阶段，项目的金额
	 * @param contractId
	 * @return
	 */
	public ProcessResult updateContractTaxAndNoTaxByPlan(Long contractId);
	
	/**
	 * 根据申请单ID查询出和他关联过的收据信息,收据并且是开过票的
	 * @param billId
	 * @return
	 */
	public List<RealContractBillandRecePlan> showReceInfo(Long billId);
	
}
