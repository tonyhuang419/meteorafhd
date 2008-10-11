package junitdemo;
import junit.framework.TestCase;


public class StringTest extends TestCase {

	Object o = new Object();
	
	public static void main(String[] args) {
		new junit.swingui.TestRunner().run(StringTest.class);
	}
	
	protected void setUp() throws Exception {
		System.out.println("setUp() Object="+o);
	}

	protected void tearDown() throws Exception {
		System.out.println("tearDown()");
	}

	public void testReplace() {
		String result = "abc".replace('a', 'b');
		assertEquals("bbc",result);
	}

	public void testSubstring() {
		String result = "abc".substring(1);
		assertEquals("bc",result);
	}
	
}
