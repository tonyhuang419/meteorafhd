import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.IDemoService;
import service.IDemoTwoService;

public class RMIClient {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	public static void main(String[] args) {
		ApplicationContext content = new ClassPathXmlApplicationContext("ApplicationContext.xml");

		IDemoService hello = (IDemoService)content.getBean("serviceClient");

		System.out.println(hello.hello("callan"));


		System.out.println(hello.getStu("tom").getName());

		System.out.println("===============================================");

		IDemoTwoService helloTwo = (IDemoTwoService)content.getBean("serviceClientTwo");
		System.out.println(helloTwo.helloTwo("callan"));
		System.out.println(helloTwo.getStuTwo("tom").getName());

	}
}
