package com.fhdone.demo2012.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fhdone.demo2012.dao.UserLogDao;
import com.fhdone.demo2012.service.UserLogDaoService;

@Service("userLogDaoService")
@Transactional
public class UserLogDaoServiceImpl implements UserLogDaoService {

	private static Logger logger = LoggerFactory.getLogger(UserLogDaoServiceImpl.class); 

    @Autowired  
    public UserLogDao userLogDao;  
	
	public int updateCompanyCD(Map<String, Long> m) {
    	int i = userLogDao.updateCompanyCD(m);
    	logger.info("updated {}",i);
    	throw new RuntimeException();
//		return i;
	}

}
