package com.exam.tools.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


public class MailTool {

	private MailSender mailSender;
	
	public MailTool(MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	
	public void sendMail(SimpleMailMessage smm) {
		try {
			this.mailSender.send(smm);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
	
//	public static final void main(String[] args) {
//		ApplicationContext appCtx  = new ClassPathXmlApplicationContext("applicationContext.xml");
//		MailTool tester = (MailTool) appCtx.getBean("mailTool");
//		SimpleMailMessage smm = new SimpleMailMessage();
//		smm.setTo("meteorafhd@gmail.com");
//		smm.setSubject("subject");
//		smm.setText("sendText"); //"This is a test.\nGo Spring!\n"
//		tester.sendMail(smm);
//	}
	
}
