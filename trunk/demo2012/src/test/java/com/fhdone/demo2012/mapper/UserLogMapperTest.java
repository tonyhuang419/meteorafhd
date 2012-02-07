package com.fhdone.demo2012.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.fhdone.demo2012.entity.UserLog;
import com.fhdone.demo2012.service.UserLogService;

@ContextConfiguration(locations = { "/applicationContext.xml"})  
public class UserLogMapperTest extends AbstractJUnit4SpringContextTests{  
  
    @Autowired  
    public UserLogService userLogMapper;  
      
    @Test  
    public void testCountUserLog(){  
        Assert.assertNotNull(userLogMapper.countUserLog());
        System.out.println(userLogMapper.countUserLog());
    } 
    
    
    @Test  
    public void testGetUserLogById(){  
    	UserLog u = userLogMapper.getUserLogById(new Long(4692234));
        Assert.assertNotNull(u);
        System.out.println(u.getCompanyCd());
    } 
    
    @Test  
    public void testGetUser(){  
    	List<UserLog> list = userLogMapper.getUserLogs(new Long(4693232));
    	System.out.println(list.size());
    } 
    
    @Test
    public void getUserLogsByTwoId(){
    	Map<String,Long> m = new HashMap<String,Long>();
    	m.put("id1", new Long(4690000));
    	m.put("id2", new Long(4693232));
    	List<UserLog> list = userLogMapper.getUserLogsByTwoId(m);
    	System.out.println(list.size());
    }
    
}  


