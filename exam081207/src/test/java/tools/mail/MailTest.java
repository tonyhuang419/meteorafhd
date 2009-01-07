package tools.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;

import com.exam.ExamBaseTest;
import com.exam.tools.mail.MailTool;

public class MailTest extends ExamBaseTest {
	

	@Autowired
	@Qualifier("mailTool")
	private MailTool 		mailTool;
	

	@Test
	public void  sendMail( ) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo("meteorafhd@gmail.com");
		smm.setSubject("subject");
		smm.setText("sendText"); //"This is a test.\nGo Spring!\n"
		mailTool.sendMail(smm);
	}
}
