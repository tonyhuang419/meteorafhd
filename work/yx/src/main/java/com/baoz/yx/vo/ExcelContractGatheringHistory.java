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
	
	/**实际开票金额**/
	private Double realBillMoney;
	
	/** 收据金额 */
	private Double receiptMoney;
	
	/** 尾款标志 */
	private String lastSymbol;
	
	/** 发票号 */
	private String[] invoiceCode;
	
	/** 实际到款金额 */
	private Double realMoney;
	
	/** 实际到款日期 */
	private Date realDate;
	
	/**合同登记年**/
	private String registerYear;

	/**合同登记月**/
	private String registerMonth;
	
	/**合同登记日**/
	private String registerDate;
	

	/**合同销售员姓名**/
	private String conSaleMan;
	
	/**应收款金额**/
	private Double realReveMoney;
	
	/**合同名称（包含甲方合同号）**/
	private String conName;
	
	/**合同金额**/
	private Double conMoney;
		
	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public Double getRealBillMoney() {
		if(realBillMoney==null){
			return 0.0;
		}
		return realBillMoney;
	}

	public void setRealBillMoney(Double realBillMoney) {
		this.realBillMoney = realBillMoney;
	}

	public String getConSaleMan() {
		return conSaleMan;
	}

	public void setConSaleMan(String conSaleMan) {
		this.conSaleMan = conSaleMan;
	}

	public Double getRealReveMoney() {
		if(realReveMoney==null){
			return 0.0;
		}
		return realReveMoney;
	}

	public void setRealReveMoney(Double realReveMoney) {
		this.realReveMoney = realReveMoney;
	}


	/**实际开票金额**/
	private Double realBillAmount;
	

	public String getRegisterYear() {
		return registerYear;
	}

	public void setRegisterYear(String registerYear) {
		this.registerYear = registerYear;
	}

	public String getRegisterMonth() {
		return registerMonth;
	}

	public void setRegisterMonth(String registerMonth) {
		this.registerMonth = registerMonth;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

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

	public String[] getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String[] invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Double getRealMoney() {
		if(realMoney==null){
			return 0.0;
		}
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


	public Double getConMoney() {
		if(conMoney==null){
			return 0.0;
		}
		return conMoney;
	}

	public void setConMoney(Double conMoney) {
		this.conMoney = conMoney;
	}

	public Double getRealBillAmount() {
		return realBillAmount;
	}

	public void setRealBillAmount(Double realBillAmount) {
		this.realBillAmount = realBillAmount;
	}


	
}

