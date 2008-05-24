package mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class TestSenderMail {
	private MailSender mailSender;
	private SimpleMailMessage mailMessage;

	public TestSenderMail() {

	}

	public SimpleMailMessage getMailMessage() {
		return mailMessage;
	}
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	public MailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail() {
		SimpleMailMessage message = new SimpleMailMessage(mailMessage);
		//ÉèÖÃemailÄÚÈİ,
		message.setText("²âÊÔSpring ·¢ËÍEmail.");

		try {
			mailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
} 
