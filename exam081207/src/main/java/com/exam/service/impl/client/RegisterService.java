package com.exam.service.impl.client;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Customer;
import com.exam.service.ICommonService;
import com.exam.service.client.IRegisterService;

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
	
	public boolean uniqueClientName(String username){
		Long x = (Long)commonService.uniqueResult(" select c.id from Customer c where c.username = ? ", username);
		if(x!=null){
			return false;
		}
		else{
			return true;
		}
	}
}
