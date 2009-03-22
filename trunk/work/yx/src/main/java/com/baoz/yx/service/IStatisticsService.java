package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.Employee;
import com.baoz.yx.vo.Department;

public interface IStatisticsService {

	/**
	 *查询组别,按照组织代码排序
	 ***/
	public List<Department> getDepartment();
	
	/**
	 * 查询所有的销售员
	 * @return
	 */
	public List<Employee> getEmployee();
}
