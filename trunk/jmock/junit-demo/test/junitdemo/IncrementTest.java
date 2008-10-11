package junitdemo;

import junit.framework.TestCase;



public class IncrementTest extends TestCase {

	Object o = new Object();
	public void setUp() {
		System.out.println("setUp() object="+o);
	}
	
	public void tearDown() {
		System.out.println("tearDown()");
	}

	
}
