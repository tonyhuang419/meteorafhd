package com.baoz.yx.service;

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
}
