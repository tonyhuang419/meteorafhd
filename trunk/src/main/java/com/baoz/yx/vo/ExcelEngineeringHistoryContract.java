package com.baoz.yx.vo;

import java.util.Date;

/**
 * 类ExcelHistoryContract.java的实现描述：excel中对应的历史合同记录
 * 
 * @author xurong Jul 1, 2008 1:30:10 PM
 */
public class ExcelEngineeringHistoryContract {
	/** 甲方合同号 */
	private String[] jiaContractCodes;
	/** 甲方项目号 */
	private String[] jiaProjectCodes;
	/** 合同号 */
	private String contractCode;
	/** 项目代码 */
	private String[] projectCode;
	/** 项目名称,这里是合同名称 */
	private String contractName;
	/** 客户名称 */
	private String clientName;
	/** 合同金额（含税） */
	private Double taxContractAmount;
	/** 合同金额（不含税） */
	private Double noTaxContractAmount;
	/** 预决算(Y/N) */
	private String preDecide;
	/** 设备金额 */
	private Double deviceAmount;
	/** 非设备金额 */
	private Double noDeviceAmount;
	/** 软件开发金额 */
	private Double softDevelopAmount;
	/** 服务外包费（含税） */
	private Double taxOutsourcingAmount;
	/** 服务外包费（不含税） */
	private Double noTaxOutsourcingAmount;
	/** 合同经办人 */
	private String sellerName;
	/** 责任部门 */
	private String mainProjectDepartment;
	/** 合同签定日期 */
	private Date contractSignDate;
	/** 合同有效期 */
	private Date contractEndDate;
	/** 技术开发(Y/N) */
	private String technicalContract;
	/** 股份/股份其他/集团/宝钢外 */
	private String clientQuality;
	/** 钢铁/非钢铁(Y/N) */
	private String clientSteelIndustry;
	/** 工程/科研 */
	private String contractClientProjCategory;
	/** 中标/非中标(Y/N) */
	private String bidContract;
	/** 合同及概况说明提交日期 */
	private Date approvedDate;
	/** 备注 */
	private String contractMemo;
	/** 甲方合同号 */
	public String[] getJiaContractCodes() {
		return jiaContractCodes;
	}
	public void setJiaContractCodes(String[] jiaContractCodes) {
		this.jiaContractCodes = jiaContractCodes;
	}
	/** 甲方项目号 */
	public String[] getJiaProjectCodes() {
		return jiaProjectCodes;
	}
	public void setJiaProjectCodes(String[] jiaProjectCodes) {
		this.jiaProjectCodes = jiaProjectCodes;
	}
	/** 合同号 */
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	/** 项目代码 */
	public String[] getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String[] projectCode) {
		this.projectCode = projectCode;
	}
	/** 合同名称 */
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	/** 客户名称 */
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/** 合同金额（含税） */
	public Double getTaxContractAmount() {
		return taxContractAmount;
	}
	public void setTaxContractAmount(Double taxContractAmount) {
		this.taxContractAmount = taxContractAmount;
	}
	/** 合同金额（不含税） */
	public Double getNoTaxContractAmount() {
		return noTaxContractAmount;
	}
	public void setNoTaxContractAmount(Double noTaxContractAmount) {
		this.noTaxContractAmount = noTaxContractAmount;
	}
	/** 预决算(Y/N) */
	public String getPreDecide() {
		return preDecide;
	}
	public void setPreDecide(String preDecide) {
		this.preDecide = preDecide;
	}
	/** 设备金额 */
	public Double getDeviceAmount() {
		return deviceAmount;
	}
	public void setDeviceAmount(Double deviceAmount) {
		this.deviceAmount = deviceAmount;
	}
	/** 非设备金额 */
	public Double getNoDeviceAmount() {
		return noDeviceAmount;
	}
	public void setNoDeviceAmount(Double noDeviceAmount) {
		this.noDeviceAmount = noDeviceAmount;
	}
	/** 软件开发金额 */
	public Double getSoftDevelopAmount() {
		return softDevelopAmount;
	}
	public void setSoftDevelopAmount(Double softDevelopAmount) {
		this.softDevelopAmount = softDevelopAmount;
	}
	/** 服务外包费（含税） */
	public Double getTaxOutsourcingAmount() {
		return taxOutsourcingAmount;
	}
	public void setTaxOutsourcingAmount(Double taxOutsourcingAmount) {
		this.taxOutsourcingAmount = taxOutsourcingAmount;
	}
	/** 服务外包费（不含税） */
	public Double getNoTaxOutsourcingAmount() {
		return noTaxOutsourcingAmount;
	}
	public void setNoTaxOutsourcingAmount(Double noTaxOutsourcingAmount) {
		this.noTaxOutsourcingAmount = noTaxOutsourcingAmount;
	}
	/** 合同经办人 */
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	/** 责任部门 */
	public String getMainProjectDepartment() {
		return mainProjectDepartment;
	}
	public void setMainProjectDepartment(String mainProjectDepartment) {
		this.mainProjectDepartment = mainProjectDepartment;
	}
	/** 合同签定日期 */
	public Date getContractSignDate() {
		return contractSignDate;
	}
	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
	/** 合同有效期 */
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	/** 技术开发(Y/N),合同性质 */
	public String getTechnicalContract() {
		return technicalContract;
	}
	public void setTechnicalContract(String technicalContract) {
		this.technicalContract = technicalContract;
	}
	/** 股份/股份其他/集团/宝钢外,客户性质 */
	public String getClientQuality() {
		return clientQuality;
	}
	public void setClientQuality(String clientQuality) {
		this.clientQuality = clientQuality;
	}
	/** 钢铁/非钢铁(Y/N) ，客户行业类别*/
	public String getClientSteelIndustry() {
		return clientSteelIndustry;
	}
	public void setClientSteelIndustry(String clientSteelIndustry) {
		this.clientSteelIndustry = clientSteelIndustry;
	}
	/** 工程/科研，客户项目类型 */
	public String getContractClientProjCategory() {
		return contractClientProjCategory;
	}
	public void setContractClientProjCategory(String contractClientProjCategory) {
		this.contractClientProjCategory = contractClientProjCategory;
	}
	/** 中标/非中标(Y/N) */
	public String getBidContract() {
		return bidContract;
	}
	public void setBidContract(String bidContract) {
		this.bidContract = bidContract;
	}
	/** 合同及概况说明提交日期 */
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	/** 备注 */
	public String getContractMemo() {
		return contractMemo;
	}
	public void setContractMemo(String contractMemo) {
		this.contractMemo = contractMemo;
	}
	
}
