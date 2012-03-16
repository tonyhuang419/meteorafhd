package com.fhdone.demo2012.service;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;

public class UserLogDaoServiceTest extends BaseTest{

	private Logger logger = LoggerFactory.getLogger(UserLogDaoServiceTest.class);  

	@Autowired  
	@Qualifier("userLogDaoService")
	public UserLogDaoService userLogDaoService;  

	@Test  
	public void testSearchLoginfo(){  
		logger.info("start testSearchLoginfo");
		Map<String,Long> m = new HashMap<String,Long>();
    	m.put("ccSelect", new Long("2"));
    	m.put("ccUpdate", new Long("1"));
//		int n = userLogDaoService.updateCompanyCD(m);
//		userLogDaoService.readT(m);
//		logger.info("end testSearchLoginfo {}" , n);

		try{ 
			int n = userLogDaoService.updateCompanyCD(m);
			logger.info("has be updated {}",n);
		}  
		catch(Exception e){  
			Assert.assertEquals("test transactional rollback", e.getMessage());  

		} 
	} 
	
}
