package aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//public class BuyBeforeAdvice implements MethodBeforeAdvice {
//	public void before(Method m, Object[] args, Object target) throws Throwable {
//		Customer c = (Customer)args[0];
//		System.out.println(c.getName()+" welcome");
//		
//		//System.out.println("Hello world! (by "+ this.getClass().getName() + ")");
//	}
//}

@Aspect
public class BuyBeforeAdvice{
	@Before("buy()")
	public void sayhello(){
//		Customer c = (Customer)args[0];
//		System.out.println(c.getName()+" welcome");
		System.out.println("hello");
		//System.out.println("Hello world! (by "+ this.getClass().getName() + ")");
	}
}
