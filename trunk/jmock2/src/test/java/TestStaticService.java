import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

import com.jmock2.StaticService;


public class TestStaticService {
	private StaticService staticService;
	private Mockery mockContext = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	
	@Test
	public void testGetStr(){
		staticService = mockContext.mock(StaticService.class);
		final String str = "str";
		
		mockContext.checking(new Expectations(){  
			{  
				oneOf(staticService).getStr();
				will(returnValue(str));  
			}  
		}); 
		
		new StaticService().setStr(str);
		String result = StaticService.getStr();
		junit.framework.Assert.assertEquals(result, str);
	}
}
