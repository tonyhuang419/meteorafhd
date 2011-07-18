package mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AttachedFileDemo {
	public static void main(String[] args) throws Exception {
		JavaMailSenderImpl senderImpl =
			new JavaMailSenderImpl();

		//�趨 Mail Server
		senderImpl.setHost("smtp.gmail.com");

		//SMTP��֤ʱ����Ҫ�û���������
		senderImpl.setUsername("meteorafhd");
		senderImpl.setPassword("***");
		//��������ǲ������û�������ͨ����֤����
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");       
		senderImpl.setJavaMailProperties(prop);
		// �����ʼ�ѶϢ
		MimeMessage mailMessage =  senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true,"GBK");
		//�趨�ռ��ˡ��ļ��ˡ�����������      
		messageHelper.setTo("meteorafhd@gmail.com");
		messageHelper.setFrom("meteorafhd@gmail.com");
		messageHelper.setSubject("Test");
		messageHelper.setText(
				"<html><head></head><body><h1>���! Spring!"
				+ "</h1></body></html>", true);
		//������
		//File file = new File("d:/�����ļ�.rar");
		//messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
//		�����ʼ�
		senderImpl.send(mailMessage);
	}
}