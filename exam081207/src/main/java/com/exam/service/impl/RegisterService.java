package com.exam.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Customer;
import com.exam.service.ICommonService;
import com.exam.service.IRegisterService;

@Service("registerService")
@Transactional
public class  RegisterService implements IRegisterService{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	public boolean saveNewUser(Customer customer) {
		customer.setPassword( DigestUtils.md5Hex(customer.getPassword()));
		commonService.save(customer);
		return true;
	}
	
}
