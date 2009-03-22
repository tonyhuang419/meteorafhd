package com.baoz.yx.vo;

import java.util.Date;

/**
 * 类ExcelIHistoryContract.java的实现描述：集成类合同导入
 * @author xurong Jul 7, 2008 3:57:07 PM
 */
public class ExcelIntegrationHistoryContract {
	/** 甲方合同号 */
	private String[] jiaContractCodes;
	/** 甲方项目号 */
	private String[] jiaProjectCodes;
	/** 合同号 */
	private String contractCode;
	/** 设备内容 */
	private String contractName;
	/** 客户名称 */
	private String clientName;
	/** 合同金额 */
	private Double taxContractAmount;
	/** 合同金额(不含税） */
	private Double noTaxContractAmount;
	/** 经办人 */
	private String sellerName;
	/** 签订日期 */
	private Date contractSignDate;
	/** 责任部门 */
	private String mainProjectDepartment;
	/** 股份/股份其他/集团/宝钢外 */
	private String clientQuality;
	/** 钢铁/非钢铁(Y/N) */
	private String clientSteelIndustry;
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
	/** 设备内容(合同名称) */
	public String getContractName() {
		return contractName;
	}
	/** 客户名称 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/** 合同金额 */
	public Double getTaxContractAmount() {
		return taxContractAmount;
	}
	public void setTaxContractAmount(Double taxContractAmount) {
		this.taxContractAmount = taxContractAmount;
	}
	/** 合同金额(不含税） */
	public Double getNoTaxContractAmount() {
		return noTaxContractAmount;
	}
	public void setNoTaxContractAmount(Double noTaxContractAmount) {
		this.noTaxContractAmount = noTaxContractAmount;
	}
	/** 经办人 */
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	/** 签订日期 */
	public Date getContractSignDate() {
		return contractSignDate;
	}
	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
	/** 责任部门 */
	public String getMainProjectDepartment() {
		return mainProjectDepartment;
	}
	public void setMainProjectDepartment(String mainProjectDepartment) {
		this.mainProjectDepartment = mainProjectDepartment;
	}
	/** 股份/股份其他/集团/宝钢外 */
	public String getClientQuality() {
		return clientQuality;
	}
	public void setClientQuality(String clientQuality) {
		this.clientQuality = clientQuality;
	}
	/** 钢铁/非钢铁(Y/N) */
	public String getClientSteelIndustry() {
		return clientSteelIndustry;
	}
	public void setClientSteelIndustry(String clientSteelIndustry) {
		this.clientSteelIndustry = clientSteelIndustry;
	}
	/** 备注 */
	public String getContractMemo() {
		return contractMemo;
	}
	public void setContractMemo(String contractMemo) {
		this.contractMemo = contractMemo;
	}
}
