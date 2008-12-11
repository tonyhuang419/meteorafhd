package com.exam.action.manage;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.exam.action.BaseAction;
import com.exam.vo.ProcessResult;


@Results( {
	@Result(name = "login", value = "/WEB-INF/jsp/manage/login_manage.jsp"),
})
public class LoginAction extends BaseAction{
	private static final long serialVersionUID = -8140562167196929742L;

	
	private String workId;
	private String password;
	private ProcessResult rs;
	
	public String login(){
		return "login";
	}
	
	public String validateEmp(){
//		if ( loginService.validateUser(username, password)){
//			logger.info("login success");
//			return "welcome";
//		}
//		else{
//			rs = new ProcessResult();
//			rs.addErrorMessage("用户名或密码错");
//			logger.info("login fail");
			return "login";
//		}
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

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

}
