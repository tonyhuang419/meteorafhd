package com.baoz.yx.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author xusheng
 *2008年9月24日10:05:24
 *比较未关闭合同里面的开票收款计划
 */
public class ExcelCompareRealPlanHistory implements Serializable{

	private static final long serialVersionUID = -7944727531625758747L;

	/**计划系统号**/
	private Long realPlanId;
	
	/**合同名称**/
	private String conName;
	
	/**客户名称**/
	private String customerName;
	
	/**合同编号**/
	private String conNo;
	
	/**项目编号**/
	private String itemNo;
	
	/**费用组成**/
	private String partName;
	
	/**阶段名称**/
	private String stageName;
	
	/**工程部门**/
	private String departmentName;
	
	/**开票类型**/
	private String billType;
	
	/**预计开票日期**/
	private Date preBillDate;
	
	/**预计开票金额**/
	private Double preBillAmount;
	
	/**已开票金额**/
	private Double realBillAmount;
	
	/**预计收款日期**/
	private Date preReceiptDate;
	
	/**预计收款金额**/
	private Double preReceiptAmount;
	
	/**已收款金额**/
	private Double realReceiptAmount;
	
	/**销售员姓名**/
	private String saleMan;
	
	private Date realBillDate;
	
	private Date realReceiptDate;

	public Date getRealBillDate() {
		return realBillDate;
	}

	public void setRealBillDate(Date realBillDate) {
		this.realBillDate = realBillDate;
	}

	public Date getRealReceiptDate() {
		return realReceiptDate;
	}

	public void setRealReceiptDate(Date realReceiptDate) {
		this.realReceiptDate = realReceiptDate;
	}

	public Long getRealPlanId() {
		return realPlanId;
	}

	public void setRealPlanId(Long realPlanId) {
		this.realPlanId = realPlanId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Date getPreBillDate() {
		return preBillDate;
	}

	public void setPreBillDate(Date preBillDate) {
		this.preBillDate = preBillDate;
	}

	public Double getPreBillAmount() {
		return preBillAmount;
	}

	public void setPreBillAmount(Double preBillAmount) {
		this.preBillAmount = preBillAmount;
	}

	public Double getRealBillAmount() {
		return realBillAmount;
	}

	public void setRealBillAmount(Double realBillAmount) {
		this.realBillAmount = realBillAmount;
	}

	public Date getPreReceiptDate() {
		return preReceiptDate;
	}

	public void setPreReceiptDate(Date preReceiptDate) {
		this.preReceiptDate = preReceiptDate;
	}

	public Double getPreReceiptAmount() {
		return preReceiptAmount;
	}

	public void setPreReceiptAmount(Double preReceiptAmount) {
		this.preReceiptAmount = preReceiptAmount;
	}

	public Double getRealReceiptAmount() {
		return realReceiptAmount;
	}

	public void setRealReceiptAmount(Double realReceiptAmount) {
		this.realReceiptAmount = realReceiptAmount;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
}
