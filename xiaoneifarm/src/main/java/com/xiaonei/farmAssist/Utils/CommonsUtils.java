package com.xiaonei.farmAssist.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class CommonsUtils {

	protected static Log logger = LogFactory.getLog(CommonsUtils.class);
	
	
	public  WebClient login(String loginName , String password){
		logger.info("login:"+ loginName);
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		HtmlPage page;
		try{
			page = webClient.getPage(Utils.loginUrl);
//			System.out.println(page.asText());
			//get form
			HtmlForm form = (HtmlForm)page.getElementById("loginForm");
			//get login input
			HtmlTextInput loginNameInput = form.getInputByName("email");
			//get password input
			HtmlPasswordInput passwordInput = form.getInputByName("password");

			loginNameInput.setValueAttribute(loginName);
			passwordInput.setValueAttribute(password);

			//get login button 
			HtmlSubmitInput  button = form.getElementById("login");

			page = button.click();
			System.out.println(page.getTitleText());
			if( page.getTitleText().indexOf("登录")== -1) {
				logger.info(loginName + " login success");
//				page = webClient.getPage(Units.LOGIN_URL);
				return webClient;
			}
			else{
				logger.info(loginName + " login fail");
			}
		}catch( Exception e ){
			logger.info(loginName + "login happen exception");
			e.printStackTrace();
		}
		return webClient;
	}
        
	public String getFramKey(){
		FramKeyUtil fu = new FramKeyUtil();
		return fu.getFarmKey();
	}

	public static void main(String[] args){
		CommonsUtils cu = new CommonsUtils();
//		System.out.println(cu.getFramKey());
		cu.login(Utils.username, Utils.password);
	}
}
