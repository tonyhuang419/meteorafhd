package fhd.right.action;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.interceptor.SessionAware;

import com.fhd.poll.db.DAO.IDAOPoll;
import com.opensymphony.xwork2.ActionSupport;

//@Component("LoginAction") 
public class LoginAction extends ActionSupport implements SessionAware{

	private String name = null;
	private String pwd = null;
	private Map session = null;
	private ILoginTool loginBean = null;  //IOC
	private IDAOPoll pollDAOBean = null;  //IOC

	public LoginAction(){	
		
	}
	//@Autowired 
	public void setLoginBean(ILoginTool loginBean) {
		this.loginBean = loginBean;
	}
	public void setPollDAOBean(IDAOPoll pollDAOBean) {
		this.pollDAOBean = pollDAOBean;
	}
	@Override
	public void setSession(Map session) {
		this.session = session;	
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String nameLogin() {
		session.put("username",name);
		if(loginBean.validName(this.getName())){	
			return "two";
		}
		else{
			session.put("mjList", pollDAOBean.read().iterator());
			//session.put("username",name);
			return "guest";
		}
	}

	public String pwdLogin() {
		session.put("mjList", pollDAOBean.read().iterator());
		session.put("username",session.get("username"));
		session.put("password", pwd);
		if(loginBean.validPwd(this.getPwd())){		
			return "success";
		}
		return "guest";
	}

	public void validateNameLogin(){
		if(this.getName().equals("") ){
			this.addFieldError("name",this.getText("name.require"));
		}
		else if(!Pattern.matches("(^.{1,20})",this.getName().trim())) {
			this.addFieldError("name",this.getText("name.regex"));		
		}
	}
	
	//因为如果密码校验失败，返回至input界面，故addFieldError（name）
	public void validatePwdLogin(){
		if(this.getPwd().equals("") ){
			this.addFieldError("name",this.getText("pwd.require"));
		}
		else if(!Pattern.matches("(^.{1,20})",this.getPwd().trim())) {
			this.addFieldError("name",this.getText("pwd.regex"));		
		}
	}
}