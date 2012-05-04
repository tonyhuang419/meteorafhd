package com.fhdone.demo2012.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.service.luncene.IndexLogInfoService;

public class IndexServiceTest extends BaseTest {  

    @Autowired  
    @Qualifier("indexLogInfoService")
    public IndexLogInfoService indexLogInfoService;  
	
    @Test 
    @Ignore
    public void testIndexLoginfo() throws Exception{  
    	logger.info("start testIndexLoginfo");
    	logger.info("{}" , indexLogInfoService.indexLoginfo());
    	logger.info("end testIndexLoginfo");
    } 
	
}
