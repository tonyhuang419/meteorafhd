package com.exam.action.manage;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.entity.Employee;
import com.exam.service.manage.IRegisterMService;


@Results( {
	@Result(name = "welcome", value = "/WEB-INF/jsp/manage/manage_main.jsp")
})
public class RegisterMAction extends BaseAction{
	private static final long serialVersionUID = 6635425184920848095L;

	@Autowired
	@Qualifier("registerMService")
	private IRegisterMService registerMService;

	private Employee emp;
	private String againPawword;

	public String registerEmployee(){
		registerMService.saveNewEmployeer(emp);
		return "welcome";
	}



	public String getAgainPawword() {
		return againPawword;
	}

	public void setAgainPawword(String againPawword) {
		this.againPawword = againPawword;
	}



	public Employee getEmp() {
		return emp;
	}



	public void setEmp(Employee emp) {
		this.emp = emp;
	}


}
