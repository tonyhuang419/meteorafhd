package guiceDemo.helloworld;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;




public class InterceptorService  implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("interceptor function");
		return invocation.proceed();
	}
}

