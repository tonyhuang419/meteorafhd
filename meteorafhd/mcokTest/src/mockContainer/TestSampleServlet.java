package mockContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.easymock.MockControl;
public class TestSampleServlet extends TestCase
{
	private SampleServlet servlet;
	private MockControl controlHttpServlet;
	private HttpServletRequest mockHttpServletRequest;
	private MockControl controlHttpSession;
	private HttpSession mockHttpSession;
	protected void setUp()
	{
		servlet = new SampleServlet();
		controlHttpServlet = MockControl.createControl(
				HttpServletRequest.class);
		mockHttpServletRequest =
			(HttpServletRequest) controlHttpServlet.getMock();
		controlHttpSession = MockControl.createControl(
				HttpSession.class);
		mockHttpSession =
			(HttpSession) controlHttpSession.getMock();
	}
	protected void tearDown()
	{
		controlHttpServlet.verify();
		controlHttpSession.verify();
	}
	public void testIsAuthenticatedAuthenticated()
	{
		mockHttpServletRequest.getSession(false);
		controlHttpServlet.setReturnValue(mockHttpSession);
		mockHttpSession.getAttribute("authenticated");
		controlHttpSession.setReturnValue("true");
		controlHttpServlet.replay();
		controlHttpSession.replay();
		assertTrue(servlet.isAuthenticated(mockHttpServletRequest));
	}
	public void testIsAuthenticatedNotAuthenticated()
	{
		mockHttpServletRequest.getSession(false);
		controlHttpServlet.setReturnValue(mockHttpSession);
		mockHttpSession.getAttribute("authenticated");
		controlHttpSession.setReturnValue(null);
		controlHttpServlet.replay();
		controlHttpSession.replay();
		assertFalse(
				servlet.isAuthenticated(mockHttpServletRequest));
	}
	public void testIsAuthenticatedNoSession()
	{
		mockHttpServletRequest.getSession(false);
		controlHttpServlet.setReturnValue(null);
		controlHttpServlet.replay();
		controlHttpSession.replay();
		assertFalse(
				servlet.isAuthenticated(mockHttpServletRequest));
	}
}
