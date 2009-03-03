package mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class MockUser   {

	HttpServletRequest 	mockHttpServletRequest;
	HttpSession	 mockSession;
//	private Mockery context = new Mockery() {{
//		setImposteriser(ClassImposteriser.INSTANCE);
//	}};
	Mockery context = new JUnit4Mockery();

	private void  mockASession(){
		mockSession  = context.mock(HttpSession.class); 
		context.checking ( new Expectations(){  {
			allowing (mockSession).getAttribute(with(equal("session_employee_id")));
			will(returnValue(1L));  
		}
		});
	}

	private HttpServletRequest  mockAHttpServletRequest(){
		mockHttpServletRequest  = context.mock(HttpServletRequest.class); 
		context.checking ( new Expectations(){  {
			allowing (mockHttpServletRequest).getSession();
			will(returnValue(mockSession));  
		}
		});
		return mockHttpServletRequest;
	}
	
	public void  mockEmployee(){
		this.mockASession();
		ServletActionContext.setRequest( this.mockAHttpServletRequest() );
	}

}
