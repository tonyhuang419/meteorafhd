package com.exam.action.client;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.entity.Customer;
import com.exam.service.IRegisterService;


@Results( {
	@Result(name = "welcome", value = "/WEB-INF/jsp/client/welcome.jsp")
})
public class RegisterAction extends BaseAction{
	
	
	@Autowired
	@Qualifier("registerService")
	private IRegisterService registerService;
	
	private Customer customer;
	
	public String registerCustomer(){
		registerService.saveNewUser(customer);
		return "welcome";
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
