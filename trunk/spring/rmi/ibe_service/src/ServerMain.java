import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServerMain {

	public static void main(String[] args) {
//		System.out.println(SpringVersion.getVersion());
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("ApplicationContext.xml");

	}

}
