package com.exam.tools.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailTest {
	private MailSender mailSender;
	private SimpleMailMessage templateMessage;
	public MailTest(MailSender mailSender, SimpleMailMessage templateMessage) {
		super();
		this.mailSender = mailSender;
		this.templateMessage = templateMessage;
	}

	public void sendMeMail() {
		SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
		msg.setTo("meteorafhd@gmail.com");
		msg.setText("This is a test.\nGo Spring!\n");
		try {
			this.mailSender.send(msg);
		} catch (MailException e) {
			System.err.println("Didn't work.");
			e.printStackTrace();
		}
	}

	public static final void main(String[] args) {
		ApplicationContext appCtx  = new ClassPathXmlApplicationContext("applicationContext.xml");

		MailTest tester = (MailTest) appCtx.getBean("mailTest");
		tester.sendMeMail();
	}
}
