package mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AttachedFileDemo {
	public static void main(String[] args) throws Exception {
		JavaMailSenderImpl senderImpl =
			new JavaMailSenderImpl();

		//设定 Mail Server
		senderImpl.setHost("smtp.gmail.com");

		//SMTP验证时，需要用户名和密码
		senderImpl.setUsername("meteorafhd");
		senderImpl.setPassword("***");
		//不设这个是不能用用户名密码通过验证发的
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");       
		senderImpl.setJavaMailProperties(prop);
		// 建立邮件讯息
		MimeMessage mailMessage =  senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true,"GBK");
		//设定收件人、寄件人、主题与内文      
		messageHelper.setTo("meteorafhd@gmail.com");
		messageHelper.setFrom("meteorafhd@gmail.com");
		messageHelper.setSubject("Test");
		messageHelper.setText(
				"<html><head></head><body><h1>你好! Spring!"
				+ "</h1></body></html>", true);
		//发附件
		//File file = new File("d:/中文文件.rar");
		//messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
//		传送邮件
		senderImpl.send(mailMessage);
	}
}