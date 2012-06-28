package com.fhdone.demo2012.service;

import org.junit.Assert;
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
    	boolean succ = indexLogInfoService.indexLoginfo();
    	logger.info("{}" , indexLogInfoService.indexLoginfo());
    	Assert.assertTrue(succ);
    	logger.info("end testIndexLoginfo");
    } 
    
    @Test
    @Ignore
    public void testIndexEcel() throws Exception{  
    	logger.info("start testIndexEcel");
    	boolean succ = indexLogInfoService.indexEcel("src/main/resources/weibo20120625_2.xls");
    	Assert.assertTrue(succ);
    	logger.info("end testIndexEcel");
    }
	
}
