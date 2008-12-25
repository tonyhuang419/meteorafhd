package tools.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;

import com.exam.ExamBaseTest;
import com.exam.tools.mail.MailTool;

public class MailTest extends ExamBaseTest {
	

	@Autowired
	@Qualifier("mailTool")
	private MailTool 		mailTool;
	
	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(false);
	}
	

	public void  testSendMail( ) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo("meteorafhd@yahoo.com.cn");
		smm.setSubject("subject");
		smm.setText("sendText"); //"This is a test.\nGo Spring!\n"
		mailTool.sendMail(smm);
	}
}
