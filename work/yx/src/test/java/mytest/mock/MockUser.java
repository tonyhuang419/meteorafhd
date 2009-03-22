package mytest.mock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.legacy.ClassImposteriser;

import com.baoz.yx.entity.Employee;
import com.opensymphony.xwork2.ActionContext;
public class MockUser  extends MockObjectTestCase {

	HttpServletRequest 	mockHttpServletRequest;
	HttpSession	 mockSession;
	private Mockery context = new Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	private void  mockASession(){
		mockSession  = context.mock(HttpSession.class); 
		context.checking ( new Expectations(){  {
			allowing (mockSession).getAttribute(with(equal("baox_yx_user")));
			Employee e = new Employee();
			e.setId(-1L);
			will(returnValue(e));  
		}
		});
//		return mockSession;
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
	
	@SuppressWarnings("unchecked")
	public void  mockAdmin(){
		this.mockASession();
		Map m = new HashMap();
		m.put(ServletActionContext.HTTP_REQUEST , this.mockAHttpServletRequest()  );
		ActionContext.setContext(new ActionContext(m));
//		return this.mockAHttpServletRequest();
	}

}
