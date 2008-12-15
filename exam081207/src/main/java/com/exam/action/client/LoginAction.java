package com.exam.action.client;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.action.BaseAction;
import com.exam.entity.Book;
import com.exam.entity.VBook;
import com.exam.service.ICommonService;
import com.exam.service.client.ILoginService;
import com.exam.vo.ProcessResult;


@Results( {
	@Result(name = "input", value = "/WEB-INF/jsp/client/login.jsp"),
	@Result(name = "login", value = "/WEB-INF/jsp/client/login.jsp"),
	@Result(name = "welcome", value = "/WEB-INF/jsp/client/client_main.jsp"),
	@Result(name = "register", value = "/WEB-INF/jsp/client/register.jsp")
})
public class LoginAction extends BaseAction{
	private static final long serialVersionUID = 7919815656835302318L;

	@Autowired
	@Qualifier("loginService")
	private ILoginService loginService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;
	
	private String username;
	private String password;
	private ProcessResult rs;
	
	public String login(){
		List<VBook> eList = commonService.list(" from VBook ");
		System.out.println(eList.size());
		return "login";
	}
	
	public String register(){
		return "register";
	}
	
	public String validateUser(){
		if ( loginService.validateUser(username, password)){
			logger.info("login success");
			return "welcome";
		}
		else{
			rs = new ProcessResult();
			rs.addErrorMessage("用户名或密码错");
			logger.info("login fail");
			return "login";
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ProcessResult getRs() {
		return rs;
	}

	public void setRs(ProcessResult rs) {
		this.rs = rs;
	}

}
