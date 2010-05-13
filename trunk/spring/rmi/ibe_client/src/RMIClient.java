import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.IDemoService;

public class RMIClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	public static void main(String[] args) {
		ApplicationContext content = new ClassPathXmlApplicationContext("ApplicationContext.xml");

		IDemoService helloService = (IDemoService)content.getBean("serviceClient");
//		IDemoTwoService helloTwo = (IDemoTwoService)content.getBean("serviceClientTwo");
		
		int i=0;
		ExecutorService exec = Executors.newFixedThreadPool(1);
		while(++i<10000){
			exec.execute(new UserThread( "a" , helloService ));
		}
		exec.shutdown();
		
//		for(int i=0;i<10000;i++){
//			System.out.println(helloService.hello("callan"));
//			System.out.println(hello.getStu("tom").getName());
//			System.out.println("===============================================");
//			System.out.println(helloTwo.helloTwo("callan"));
//			System.out.println(helloTwo.getStuTwo("tom").getName());
//			System.out.println("/==============================================");
//		}

	}
}

class UserThread implements Runnable{

	private String str;
	private IDemoService helloService;
	
	public UserThread(String str , IDemoService helloService ){
		this.str = str;
		this.helloService = helloService;
	}

	public void run() {
		try {
			String s = helloService.hello(str);
			if( s.equals("b") || s.equals("null") ){
				System.out.println( s );
			}
//			System.out.print(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
