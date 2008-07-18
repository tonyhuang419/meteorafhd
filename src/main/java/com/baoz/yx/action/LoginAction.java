package com.baoz.yx.action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

@Results( { @Result(name = "success", value = "/WEB-INF/jsp/Login.jsp"),
		@Result(name = "main", value = "/WEB-INF/jsp/Main.jsp"),
		@Result(name = "error", value = "/WEB-INF/jsp/Login.jsp") })
public class LoginAction extends DispatchAction {
	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	private String username;
	private String password;

	public String login() throws Exception {
		
		logger.info("登陆");
		
		Employee user = userService.getUser(username, password);
		
		logger.info(user);
		
		//登陆失败
		if (user == null) {
			ActionContext.getContext().put("nameOrPassError", true);
			return SUCCESS;
		} else {
			//登陆成功，将user和userDetail放到UserUtil中
			UserDetail userDetail = new UserDetail(user);
			userService.initUserDetail(userDetail);
			UserUtils.setUser(user, userDetail);
			logger.info("登录成功......");
			return "main";
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
