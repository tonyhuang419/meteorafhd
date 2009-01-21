package com.exam.action.demo;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;

import com.exam.action.BaseAction;
import com.exam.tools.mail.MailTool;


@SuppressWarnings("serial")
@Results( {
	@Result(name = "writeMail", value = "/WEB-INF/jsp/demo/sendMail.jsp")
})
public class SendMailAction extends BaseAction{

	@Autowired
	@Qualifier("mailTool")
	private MailTool 		mailTool;
	
	private SimpleMailMessage smm;
	
	public String writeMail(){
		smm  = new SimpleMailMessage();
		return "writeMail";
	}
	
	public String sendMail(){
		mailTool.sendMail(smm);
		this.cleanSmm();
		return "writeMail";
	}
	
	private void cleanSmm(){
		if(smm!=null){
			smm.setTo("");
			smm.setSubject("");
			smm.setText(""); //"This is a test.\nGo Spring!\n"
		}
	}

	public SimpleMailMessage getSmm() {
		return smm;
	}

	public void setSmm(SimpleMailMessage smm) {
		this.smm = smm;
	}
	
}
