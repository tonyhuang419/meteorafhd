package com.fhdone.demo2012.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.entity.DisableFunction;
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
    
    //@Test  
    public void testDisableFunctionName(){
    	List<DisableFunction> list  =  disableFunctionDao.groupDisableFunctionName();
    	this.scanDisableFunction(list);
    }


    
    @Test
    public void testgGroupBy(){
    	List<DisableFunction> list  =  disableFunctionDao.cascadeQuery();
    	this.scanDisableFunction(list);
    }
    
    
    
	private void scanDisableFunction(List<DisableFunction> list) {
		logger.info("has DisableFunction: {}" , list.size()  );
    	for(  DisableFunction d: list ){
        	logger.info("DisableFunction: {}:{} ",d.getCompanyCd() , d.getFunctionName() );
        	List<UserLog> ull = d.getUserLogList();
        	if( ull!=null && !ull.isEmpty() ){
        		logger.info("has user logs: {} ",d.getUserLogList().size());
//        		for(UserLog u:ull){
//        			logger.info ( u.getId()+","+u.getActionName() );
//            	}
        	}
        	else{
        		logger.info("has user logs: 0");
        	}
    	}
	}
    
    
}    
    

