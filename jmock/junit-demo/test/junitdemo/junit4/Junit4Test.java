package junitdemo.junit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//java1.5的 static import
import static org.junit.Assert.*;

public class Junit4Test  {
	@Before public void init() throws Exception {
		System.out.println("init()");
	}
	
	@Before public void setUp() throws Exception {
		//两个Before
		System.out.println("setUp()"); 
	}
	
	@After public void destroy() throws Exception {
		System.out.println("destroy()");
	}
	
	//	增加@Test表示为测试方法
	@Test public void replace() {
		String result = "abc".replace('a', 'b');
		assertEquals("bbc",result);
	}

	@Test(expected=ArithmeticException.class) 
	public void divide() {
		int num = 9/0;	//异常测试
	}
}
