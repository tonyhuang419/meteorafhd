package com.jmock2.service.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
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
		final int x = 0;
		context.checking(new Expectations() {{
			oneOf (tools).add(1, 2);
			will(returnValue(x)); 
		}});
		int result = tools.add(1, 2);
		junit.framework.Assert.assertEquals(result, x);
	}


	@Test
	public void testReturnString(){
		final String str = "hello";
		context.checking(new Expectations() {{
//			oneOf (tools).sayHello(with(equal("hello")));

			oneOf (tools).sayHello("hello");
			will(returnValue(str)); 
		}});
		String result = tools.sayHello("hello");
		junit.framework.Assert.assertEquals(result, str);
	}

	@After
	public void runAfterTest(){
		context = null;
	}

}
