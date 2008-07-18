/**
 * 
 */
package com.baoz.yx.vo;

/**
 * @author T-08-D-121
 *
 */
public class Department {
	private Long   departmentId;
	private String departmentCode;
	private String departmentName;
	
	public Department() {
	}

	public Department(Long departmentId, String departmentCode,
			String departmentName) {
		this.departmentId = departmentId;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentId) {
		this.departmentCode = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
}
