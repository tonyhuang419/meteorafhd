package com.fhdone.demo2012.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;

public class IndexServiceTest extends BaseTest {  

    @Autowired  
    @Qualifier("indexService")
    public IndexService indexService;  
	
    @Test  
    @Ignore
    public void testIndexLoginfo(){  
    	logger.info("start testIndexLoginfo");
    	indexService.indexLoginfo();
    	logger.info("end testIndexLoginfo");
    } 
	
}
