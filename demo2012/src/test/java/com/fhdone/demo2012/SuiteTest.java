package com.fhdone.demo2012;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.fhdone.demo2012.service.UserLogDaoServiceTest;


@RunWith(Suite.class)  
@SuiteClasses({  
//	LuceneUtilsTest.class,
//	UserLogDaoTest.class,  
//	IndexServiceTest.class,  
//	SearchServiceTest.class,
	UserLogDaoServiceTest.class
})  
public class SuiteTest extends TestCase{

}
