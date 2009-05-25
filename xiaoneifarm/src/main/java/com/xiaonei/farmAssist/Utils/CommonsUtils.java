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
	
	public String getFarmlandStatus(WebClient webClient){
		try{
			logger.info("get farmlandStatus"); 
			HtmlPage htmlPage;
			String url = this.getFarmlandStatusUrl();
			System.out.println(url);
			htmlPage = webClient.getPage(url);
			return htmlPage.asText();
		}catch( Exception e ){
			e.printStackTrace();
		}
		return "";
	}
	
	public String getFramFriendList(WebClient webClient){
		try{
			logger.info("get fram friend list");
			HtmlPage htmlPage;
			String url = this.getFramFriendListUrl();
			System.out.println(url);
			htmlPage = webClient.getPage(url);
			System.out.println("asText"+htmlPage.asText());
			return htmlPage.asText();
		}catch( Exception e ){
			e.printStackTrace();
		}
		return "";
	}
        
	
	private WebClient loginFram(WebClient webClient){
		try{
			/**
			 * 只有登陆农场之后才能获得FarmlandStatus
			 * 所以这里就需要setTimeout一下
			 */
			logger.info("login fram");
			webClient.setTimeout(300);
			HtmlPage page = webClient.getPage(Utils.framUrl);
//			System.out.println(page.asText());
		}catch( Exception e ){
			e.printStackTrace();
		}
		return webClient;
	}
	
	private Long getfarmTime(){
		FramKeyUtil fu = new FramKeyUtil();
		return fu.serverTime();
	}
	private String getFramKey(){
		FramKeyUtil fu = new FramKeyUtil();
		return fu.getFarmKey();
	}
	
	private String getFarmlandStatusUrl(){
		StringBuffer sb = new StringBuffer(Utils.farmlandStatusUrl);
		sb.append("&farmKey=").append(this.getFramKey()).
			append("&farmTime=").append(this.getfarmTime()).
			append("&ownerId=240179669").append("&inuId=");
		return sb.toString();
	}
	
	private String getFramFriendListUrl(){
		StringBuffer sb = new StringBuffer(Utils.framFriendListUrl);
		sb.append("&farmKey=").append(this.getFramKey()).
			append("&farmTime=").append(this.getfarmTime()).
			append("&refresh=true").append("&inuId=");
		return sb.toString();
	}
	

	public static void main(String[] args){
		CommonsUtils cu = new CommonsUtils();
//		System.out.println(cu.getFramKey());
//		System.out.println(cu.getFramKey());
//		System.out.println(cu.getFarmlandStatusUrl());
		WebClient webClient = cu.login(Utils.username, Utils.password);
		webClient = cu.loginFram(webClient);
		System.out.println(cu.getFramFriendList(webClient));
//		System.out.println(cu.getFarmlandStatus(webClient));
	}
}
