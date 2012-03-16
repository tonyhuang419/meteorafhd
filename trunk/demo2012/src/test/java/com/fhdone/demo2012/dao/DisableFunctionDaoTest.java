package com.fhdone.demo2012.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.entity.UserLog;

public class DisableFunctionDaoTest extends BaseTest {  
  
	private static Logger logger = LoggerFactory.getLogger(DisableFunctionDaoTest.class); 
	
    @Autowired  
    public DisableFunctionDao disableFunctionDao;  
      
    @Test  
    public void testCountUserLog(){  
    	Long count = disableFunctionDao.countUserLog();
        Assert.assertNotNull(count);
        logger.info("count:{}",count);
    } 
    
    
    @Test  
    public void testGetUserLog(){  
      	List<UserLog> userLog = disableFunctionDao.getUserLog();
        Assert.assertNotNull(userLog);
        logger.info("count:{}",userLog.size());
    }
    
 
    @Test
    public void tmp(){  
      	List<Object[]> oneToMany = disableFunctionDao.oneToMany();
        Assert.assertNotNull(oneToMany);
        logger.info("count:{}",oneToMany.size());
    }
}    
    

