package cactus;

import mockContainer.SampleServlet;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
public class TestSampleServletIntegration extends ServletTestCase
{
	private SampleServlet servlet;
	protected void setUp()
	{
		servlet = new SampleServlet();
	}
	public void testIsAuthenticatedAuthenticated()
	{
		session.setAttribute("authenticated", "true");
		assertTrue(servlet.isAuthenticated(request));
	}
	public void testIsAuthenticatedNotAuthenticated()
	{
		assertFalse(servlet.isAuthenticated(request));
	}
	public void beginIsAuthenticatedNoSession(WebRequest request)
	{
		request.setAutomaticSession(false);
	}
	public void testIsAuthenticatedNoSession()
	{
		assertFalse(servlet.isAuthenticated(request));
	}
}
