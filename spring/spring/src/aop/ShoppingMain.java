package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ShoppingMain {
	public static void main(String[] args) {	
//		ApplicationContext ctx = new FileSystemXmlApplicationContext("aop.xml");
//		IBuy bean = (IBuy) ctx.getBean("bean");
//
//		Customer customer  = new Customer("customer name");
//		bean.buy(customer);
//		System.out.println("---------");
//		bean.buyX(customer);
		
		ApplicationContext ctx = new FileSystemXmlApplicationContext("aop.xml");
		IBuy bean = (IBuy) ctx.getBean("Buy");

		Customer customer  = new Customer("customer name");
		bean.buy(customer);
		System.out.println("---------");
		bean.buyX(customer);
	}
}
