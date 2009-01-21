package com.exam.tools.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailTool")
public class MailTool {

	private MailSender mailSender;
	
	@Autowired
	public MailTool(@Qualifier("mailSender")MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	
	public void sendMail(SimpleMailMessage smm) {
		try {
			mailSender.send(smm);
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
