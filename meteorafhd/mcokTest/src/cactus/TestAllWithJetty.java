package cactus;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.extension.jetty.JettyTestSetup;

public class TestAllWithJetty
{
	public static Test suite()
	{
		System.setProperty("cactus.contextURL",
		"http://localhost:8080/test");
		TestSuite suite = new TestSuite("All tests with Jetty");
		suite.addTestSuite(TestSampleServletIntegration.class);
		return new JettyTestSetup(suite); 
	}
}