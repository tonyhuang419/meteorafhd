package com.fhdone.demo2012.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;

public class SearchServiceTest extends BaseTest {  

	Logger logger = LoggerFactory.getLogger(SearchServiceTest.class);  

	@Autowired  
	@Qualifier("searchService")
	public SearchService searchService;  

	@Test  
	public void testSearchLoginfo(){  
		logger.info("start testSearchLoginfo");
		searchService.searchLoginfo(".");
	} 


}
