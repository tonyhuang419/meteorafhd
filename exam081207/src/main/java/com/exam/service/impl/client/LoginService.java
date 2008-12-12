package com.exam.service.impl.client;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Customer;
import com.exam.service.ICommonService;
import com.exam.service.client.ILoginService;
import com.opensymphony.xwork2.ActionContext;

@Service("loginService")
@Transactional
public class LoginService implements ILoginService{

	public final static String CUSTOMER_SESSION = "CUSTOMER_SESSION";

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@SuppressWarnings("unchecked")
	public boolean validateUser(String username , String password){
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)  ){
			return false;
		}
		else{
			String hql = "   from  Customer c where c.username = ? ";
			Customer  c  = (Customer)commonService.uniqueResult( hql , username);
			if( DigestUtils.md5Hex(password).equals(c.getPassword()) ){
				ActionContext.getContext().getSession().put( CUSTOMER_SESSION , c );
				return true;
			}
			else{
				return false;
			}
		}
	}

}
