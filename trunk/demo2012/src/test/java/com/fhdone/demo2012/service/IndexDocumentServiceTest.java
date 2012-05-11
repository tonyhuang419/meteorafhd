package com.fhdone.demo2012.service;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;
import com.fhdone.demo2012.service.luncene.IndexDocumentService;

public class IndexDocumentServiceTest extends BaseTest {  

    @Autowired  
    @Qualifier("indexDocumentService")
    public IndexDocumentService indexDocumentService;  
	
    @Test
//    @Ignore
    public void testIndexDocument() throws Exception{  
    	logger.info("start testIndexDocument");
    	int num = indexDocumentService.indexDocument();
    	logger.info("{}" , num);
    	Assert.assertFalse(0==num);
    	logger.info("end testIndexDocument");
    } 
}
