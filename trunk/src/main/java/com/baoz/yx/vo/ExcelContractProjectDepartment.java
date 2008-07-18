package com.baoz.yx.vo;

/**
 * 合同项目号导入
 * @author T-08-D-120
 *
 */

public class ExcelContractProjectDepartment{
	
	/** 合同号 */
	private String contractCode;
	
	/** 项目号 */
	private String projectCode;
	
	/** 部门编号 */
	private String projectDepartment;

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
	
	
	
}