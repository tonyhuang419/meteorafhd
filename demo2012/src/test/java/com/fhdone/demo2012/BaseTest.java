package com.fhdone.demo2012;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = { "/applicationContext.xml"})  
public abstract class BaseTest extends AbstractJUnit4SpringContextTests{


}
