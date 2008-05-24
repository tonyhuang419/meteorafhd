package hello;

import junit.framework.TestCase;

public class test extends TestCase {

	public void testExecute() {
		HelloAction hello = new HelloAction();
		hello.setUsername("fhdone");
		hello.setPassword("pwd");
		String result = hello.execute();

		assertTrue("Expected a success result!", result.equals("success"));

//		final String msg = "Hello, World!";
//		assertTrue("Expected the default message!", msg.equals(hello.getUsername()));
//		assertTrue("Expected the default message!", msg.equals(hello.getPassword()));
	}

}
