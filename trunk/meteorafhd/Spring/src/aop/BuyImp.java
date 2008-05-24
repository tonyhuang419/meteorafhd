package aop;

import org.aspectj.lang.annotation.Pointcut;

public class BuyImp implements IBuy {
	@Pointcut("execution(* *.(..))")
	public void buy(Customer customer) {		
//		System.out.println(this.getClass().getName()
//		+ "." + new Exception().getStackTrace()[0].getMethodName()
//		+ "()"
//		+ " says HELLO!");
		System.out.println("got sth you need");
	}
	
	@Pointcut("execution(* *.(..))")
	public void buyX(Customer customer) {			
		System.out.println("buyX");
	}
}