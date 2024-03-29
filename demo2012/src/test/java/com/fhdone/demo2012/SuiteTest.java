package com.fhdone.demo2012;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.fhdone.demo2012.dao.DisableFunctionDaoTest;
import com.fhdone.demo2012.dao.UserLogDaoTest;
import com.fhdone.demo2012.service.IndexDocumentServiceTest;
import com.fhdone.demo2012.service.IndexServiceTest;
import com.fhdone.demo2012.service.SearchServiceTest;
import com.fhdone.demo2012.service.UserLogDaoServiceTest;
import com.fhdone.demo2012.utils.lucene.LuceneUtilsTest;


@RunWith(Suite.class)  
@SuiteClasses({  
	LuceneUtilsTest.class,
	IndexServiceTest.class, 
	IndexDocumentServiceTest.class,
	UserLogDaoTest.class,  
	UserLogDaoServiceTest.class,
	SearchServiceTest.class,
	DisableFunctionDaoTest.class
})  
public class SuiteTest extends TestCase{

}
