package com.exam.service.manage;

import org.acegisecurity.annotation.Secured;

import com.exam.entity.Employee;

public interface  IRegisterMService {
	
	@Secured ({"ROLE_ADMIN"})
	public boolean saveNewEmployeer(Employee  emp) ;
	
	public boolean uniqueEmployeeJobNum(String jobNum);
	
}
