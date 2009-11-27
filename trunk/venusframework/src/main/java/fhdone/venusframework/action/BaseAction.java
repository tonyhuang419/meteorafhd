package fhdone.venusframework.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware ,ApplicationAware {
	
	private static final long serialVersionUID = 6222135106052344007L;

	protected Log logger = LogFactory.getLog(this.getClass());

	private String	method;
	protected Map<Object,Object> validateMap;

	final public String execute() throws Exception {
		if (StringUtils.isEmpty(method)) {
			logger.info("defalut method has be called");
			return "success";
		}
		logger.info("method " + method +" has be called ");
		return (String) MethodUtils.invokeMethod(this, method, new Object[] {});
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<Object, Object> getValidateMap() {
		return validateMap;
	}

	public void setValidateMap(Map<Object, Object> validateMap) {
		this.validateMap = validateMap;
	}

	private Map session;  
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map application;

	public void setSession(Map session) {   
		this.session = session;   
	}   

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	 public void setApplication(Map application){
	     this.application = application;
	 }

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Map getSession() {
		return session;
	}

	public Map getApplication() {
		return application;
	}
}
