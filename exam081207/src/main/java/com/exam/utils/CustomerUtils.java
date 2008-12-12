package com.exam.utils;

import com.exam.entity.Customer;
import com.opensymphony.xwork2.ActionContext;

public class CustomerUtils {

	
	public final static String CUSTOMER_SESSION = "CUSTOMER_SESSION";
	
	@SuppressWarnings("unchecked")
	static public void saveCustomerToSession( Customer  c ){
		ActionContext.getContext().getSession().put( CUSTOMER_SESSION , c );
	}
	
	static public Customer getCustomerToSession(){
		return  (Customer)ActionContext.getContext().getSession().get(CUSTOMER_SESSION);
	}
}
