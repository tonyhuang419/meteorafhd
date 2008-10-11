package mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ApplicationContext ctx = new FileSystemXmlApplicationContext("mail.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("/mail/mail2.xml");

		TestSenderMail sender = (TestSenderMail)context.getBean("testMailSender");
		sender.sendMail();
	}
} 