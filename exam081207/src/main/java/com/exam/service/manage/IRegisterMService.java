package com.exam.service.manage;

import com.exam.entity.Employee;

public interface  IRegisterMService {
	
	public boolean saveNewEmployeer(Employee  emp) ;
	
	public boolean uniqueEmployeeJobNum(String jobNum);
	
}
