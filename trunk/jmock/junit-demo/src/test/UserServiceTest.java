package test;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class UserServiceTest extends TestCase {   
	Mockery context = new Mockery();   
	IUserService userService = null;   

	protected void setUp() {   
		userService = context.mock(IUserService.class);   
	}   

	public void testSayHello() {   
		final String message = "HelloSuperLeo";   
		context.checking(new Expectations() {   
			{   
				one(userService).sayHello("HelloSuperLeo");    //if function has call by this way
				will(returnValue(message));   				   //she will return this
			}   
		});   

		// 测试成功   
		String result = userService.sayHello("HelloSuperLeo");   

		// 测试失败   
		// String result = userService.sayHello("fdjsasdfa");   

		assertSame(result, message);   

	}   

} 
