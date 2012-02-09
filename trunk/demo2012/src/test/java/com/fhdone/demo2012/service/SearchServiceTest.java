package com.fhdone.demo2012.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fhdone.demo2012.BaseTest;

public class SearchServiceTest extends BaseTest {  

	
    @Autowired  
    @Qualifier("searchService")
    public SearchService searchService;  
	
    @Test  
    public void testSearchLoginfo(){  
    	searchService.searchLoginfo("mainMenu.action");
    } 
	
    
}
