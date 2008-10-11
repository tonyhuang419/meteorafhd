package junitdemo;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for junitdemo");
		//$JUnit-BEGIN$
		suite.addTestSuite(IncrementTest.class);
		suite.addTestSuite(StringTest.class);
		//$JUnit-END$
		return suite;
	}

}
