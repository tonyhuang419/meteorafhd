package com.baoz.yx.vo;
/**
 * 类ExcelFullBillAndReceHisImport.java的实现描述：全额开票收款excel导入 
 * @author xurong Sep 19, 2008 1:04:15 PM
 */
public class ExcelFullBillAndReceHisImport {
	/** 合同号 */
	private String contractCode;
	/** 项目号 */
	private String projectCode;
	/** 开票余额 */
	private Double billRemain;
	/** 收款余额 */
	private Double receRemain;
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
	public Double getBillRemain() {
		return billRemain;
	}
	public void setBillRemain(Double billRemain) {
		this.billRemain = billRemain;
	}
	public Double getReceRemain() {
		return receRemain;
	}
	public void setReceRemain(Double receRemain) {
		this.receRemain = receRemain;
	}
}
