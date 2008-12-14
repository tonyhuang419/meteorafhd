package com.exam.action.client;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.entity.Customer;
import com.exam.service.ICommonService;

@Result(name = "modClientInfo", value = "/WEB-INF/jsp/client/register.jsp")
public class CustomerInfoAction extends BaseAction{
	private static final long serialVersionUID = -5329372234063888503L;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	private Customer customer;
	Long cid;

	public String modClientInfo(){
		customer  = (Customer)commonService.load(Customer.class, cid);
		return "modClientInfo";
	}
	
	
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}


	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
