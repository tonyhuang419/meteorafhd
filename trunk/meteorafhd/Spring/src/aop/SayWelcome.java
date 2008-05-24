package aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

//public class SayWelcome implements AfterReturningAdvice{
//
//	@Override
//	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
//			Object arg3) throws Throwable {
//		System.out.println("Think you.Come again");
//	}
//}
@Aspect
public class SayWelcome{
	@AfterReturning("buy()")
	public void afterReturning()  {
		System.out.println("Think you.Come again");
	}
}