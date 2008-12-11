package com.exam.service.impl.client;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.service.ICommonService;
import com.exam.service.client.ILoginService;

@Service("loginService")
@Transactional
public class LoginService implements ILoginService{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	public boolean validateUser(String username , String password){
		String hql = " select c.password  from  Customer c where c.username = ? ";
		String p = (String)commonService.uniqueResult( hql , username);
		if( DigestUtils.md5Hex(password).equals(p) ){
			return true;
		}
		else{
			return false;
		}
	}

}
