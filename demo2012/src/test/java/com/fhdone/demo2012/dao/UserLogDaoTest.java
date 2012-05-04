package com.fhdone.demo2012.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.dao.demo.UserLogDao;
import com.fhdone.demo2012.entity.UserLog;

public class UserLogDaoTest extends BaseTest {  
  
	private static Logger logger = LoggerFactory.getLogger(UserLogDaoTest.class); 
	
    @Autowired  
    public UserLogDao userLogDao;  
      
    @Test  
    public void testCountUserLog(){  
    	Long count = userLogDao.countUserLog();
        Assert.assertNotNull(count);
        logger.info("count:{}",count);
    } 
    
    
    @Test  
    public void testGetUserLogById(){  
    	UserLog u = userLogDao.getUserLogById(new Long(4692234));
    	userLogDao.getUserLogById(new Long(4692234));
        Assert.assertNotNull(u);
        logger.info("{}",u.getCompanyCd());
    } 
    
    @Test  
    public void testGetUserLogltId(){  
    	List<UserLog> list = userLogDao.getUserLogltId(new Long(4693232));
    	logger.info("{}",list.size());
    } 
    
    @Test
    public void testGetUserLogsByTwoId(){
    	Map<String,Long> m = new HashMap<String,Long>();
    	m.put("id1", new Long(4690000));
    	m.put("id2", new Long(4693232));
    	List<UserLog> list = userLogDao.getUserLogsByTwoId(m);
    	logger.info("{}",list.size());
    }
    
    @Test  
    public void testGetUserLogByMaxId(){  
    	long maxid = userLogDao.getUserLogByMaxId();
    	logger.info("{}",maxid);
    }
    
    @Test
    public void selectUserLogByIdFromCache(){
    	Map<String , Object> paras = new HashMap<String , Object>() ;
    	paras.put("ID", 4692233L);
    	paras.put("COMPANY_CD", "0270");
    	List<UserLog> list = userLogDao.selectUserLogByIdFromCache(paras);
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    	list = userLogDao.selectUserLogByIdFromCache(paras);
    	logger.info("{}",list.size());
    }
    
    

}    
    

