package annotaion_test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		//ApplicationContext ctx = 
		//new ClassPathXmlApplicationContext("/annotaion_test/annotation.xml");
		
		ClassPathXmlApplicationContext ctx =
			new ClassPathXmlApplicationContext("/annotaion_test/annotation.xml");
		
		Father f = null;
		f = (Father) ctx.getBean("father");
		System.out.println(f.getS2());
		ctx.destroy();
	}
}
