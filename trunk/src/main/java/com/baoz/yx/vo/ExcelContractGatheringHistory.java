package com.baoz.yx.vo;

import java.util.Date;

/**
 * 历史合同计划开票收款明细导入
 * @author T-08-D-120
 *
 */

public class ExcelContractGatheringHistory{
	
	/** 合同编号 */
	private String contractCode;
	
	/** 项目编号 */
	private String projectCode;
	
	/** 项目责任部门 */
	private String projectDepartment;
	
	/** 项目负责人 */
	private String projectPerson;
	
	/** 开票确定收入标志 */
	private String incomeSymbol;
	
	/** 收款和开票阶段 */
	private String gatheringPhase;
	
	/** 发票类型 */
	private String invoiceType;
	
	/** 预计收款日期 */
	private Date gatheringDate;
	
	/** 预期开票日期 */
	private Date invoiceDate;
	
	/** 项目阶段性预计完成日期 */
	private Date projectPhaseDate;
	
	/** 预计开票金额 */
	private Double invoiceMoney;
	
	/** 收据金额 */
	private Double receiptMoney;
	
	/** 尾款标志 */
	private String lastSymbol;
	
	/** 发票号 */
	private String invoiceCode;
	
	/** 实际到款金额 */
	private Double realMoney;
	
	/** 实际到款日期 */
	private Date realDate;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectDepartment() {
		return projectDepartment;
	}

	public void setProjectDepartment(String projectDepartment) {
		this.projectDepartment = projectDepartment;
	}

	public String getProjectPerson() {
		return projectPerson;
	}

	public void setProjectPerson(String projectPerson) {
		this.projectPerson = projectPerson;
	}

	public String getIncomeSymbol() {
		return incomeSymbol;
	}

	public void setIncomeSymbol(String incomeSymbol) {
		this.incomeSymbol = incomeSymbol;
	}

	public String getGatheringPhase() {
		return gatheringPhase;
	}

	public void setGatheringPhase(String gatheringPhase) {
		this.gatheringPhase = gatheringPhase;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Date getGatheringDate() {
		return gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getProjectPhaseDate() {
		return projectPhaseDate;
	}

	public void setProjectPhaseDate(Date projectPhaseDate) {
		this.projectPhaseDate = projectPhaseDate;
	}

	public Double getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	public Double getReceiptMoney() {
		return receiptMoney;
	}

	public void setReceiptMoney(Double receiptMoney) {
		this.receiptMoney = receiptMoney;
	}

	public String getLastSymbol() {
		return lastSymbol;
	}

	public void setLastSymbol(String lastSymbol) {
		this.lastSymbol = lastSymbol;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Double getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	
}

