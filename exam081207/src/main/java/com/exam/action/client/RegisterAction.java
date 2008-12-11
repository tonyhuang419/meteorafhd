package com.exam.action.client;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.entity.Customer;
import com.exam.service.client.IRegisterService;


@Results( {
	@Result(name = "welcome", value = "/WEB-INF/jsp/client/welcome.jsp"),
	@Result(name = "register", value = "/WEB-INF/jsp/client/register.jsp")
})
public class RegisterAction extends BaseAction{
	private static final long serialVersionUID = 6635425184920848095L;

	@Autowired
	@Qualifier("registerService")
	private IRegisterService registerService;

	private Customer customer;
	private String againPawword;

	public String registerCustomer(){
		this.vaildateRegisterCustomer();
		if( validateMap!=null && validateMap.size()>0){
			return "register";
		}
		else{
			registerService.saveNewUser(customer);
			return "welcome";
		}
	}

	private void vaildateRegisterCustomer(){
		validateMap = new HashMap<Object, Object>();
		if(StringUtils.isEmpty(customer.getUsername())){
			validateMap.put("customerError", "用户名不能为空");
		}
		if(StringUtils.isEmpty(customer.getPassword())){
			validateMap.put("customerError", "密码不能为空");
		}
		if(  !againPawword.equals(customer.getPassword())){
			validateMap.put("passwordDisagree", "密码不一致");
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAgainPawword() {
		return againPawword;
	}

	public void setAgainPawword(String againPawword) {
		this.againPawword = againPawword;
	}


}
