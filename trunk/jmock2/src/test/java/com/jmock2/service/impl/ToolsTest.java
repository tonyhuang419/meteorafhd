package com.jmock2.service.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.jmock2.service.ITools;

public class ToolsTest {

	Mockery context = new Mockery();   
	ITools tools = null;

	
	@Before 
	  public void runBeforeTest(){
		tools = context.mock(ITools.class); 
	  }
	
	
	@Test
	public void testAdd(){
		final int x=0;
		context.checking(new Expectations() {{
		    one (tools).add(1, 2);
		    will(returnValue(x)); 
		}});
		int result = tools.add(1, 2);
		junit.framework.Assert.assertEquals(result, x);
		
	}
	
}
