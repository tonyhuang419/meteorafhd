package com.fhdone.demo2012;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = { "/applicationContext.xml"})  
public abstract class BaseTest extends AbstractJUnit4SpringContextTests{

	public Logger logger = LoggerFactory.getLogger(BaseTest.class); 

}
