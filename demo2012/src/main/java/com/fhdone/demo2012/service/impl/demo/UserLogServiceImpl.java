package com.fhdone.demo2012.service.impl.demo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhdone.demo2012.dao.demo.UserLogDao;
import com.fhdone.demo2012.service.demo.UserLogService;

@Service("userLogDaoService")
//@Transactional
public class UserLogServiceImpl implements UserLogService {

	private static Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class); 

    @Autowired  
    public UserLogDao userLogDao;  
	
	public int updateCompanyCD(Map<String, Long> m) {
    	int i = userLogDao.updateCompanyCD(m);
    	logger.info("updated {}",i);
    	throw new RuntimeException("test transactional rollback");
//		return i;
	}
	
	public void readT(Map<String, Long> m){
		this.updateCompanyCD(m);
	}

}
