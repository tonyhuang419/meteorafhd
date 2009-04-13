import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServerMain {

	protected Log logger = LogFactory.getLog(this.getClass());


	public void startServer(){
//		System.out.println(SpringVersion.getVersion());
		logger.info("sever start");
		ApplicationContext ac = new ClassPathXmlApplicationContext("ApplicationContext.xml");

	}

	public static void main(String[] args) {
		new ServerMain().startServer();
	}

}
