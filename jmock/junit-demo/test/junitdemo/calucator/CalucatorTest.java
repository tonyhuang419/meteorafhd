package junitdemo.calucator;

import junit.framework.TestCase;


public class CalucatorTest extends TestCase {

	Calucator calucator = new Calucator();
	public static void main(String[] args) {
		new junit.swingui.TestRunner().run(CalucatorTest.class);
	}
	
	protected void setUp() throws Exception {
		System.out.println("setUp() Calucator="+calucator);
	}

	protected void tearDown() throws Exception {
		System.out.println("tearDown()");
	}

	public void testAdd() {
		assertEquals(3,calucator.add(1, 2));
	}
	
	public void testDivide() {
		try {
			int result = calucator.divide(9,0);
			fail("不可能执行到这里");
		}catch(ArithmeticException expected) {
			assertTrue(true);
		}
	}
}
