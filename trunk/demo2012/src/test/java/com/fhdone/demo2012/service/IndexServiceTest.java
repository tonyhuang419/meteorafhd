package com.fhdone.demo2012.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "/applicationContext.xml"})  
public class IndexServiceTest extends AbstractJUnit4SpringContextTests{  

    @Autowired  
    @Qualifier("indexService")
    public IndexService indexService;  
	
    @Test  
    public void testIndexLoginfo(){  
    	indexService.indexLoginfo();
    } 
	
}
