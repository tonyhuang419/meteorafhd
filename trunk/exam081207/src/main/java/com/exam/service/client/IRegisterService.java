package com.exam.service.client;

import com.exam.entity.Customer;

public interface  IRegisterService {
	
	public boolean saveNewUser(Customer customer) ;
	
	public boolean uniqueClientName(String username);
	
}
