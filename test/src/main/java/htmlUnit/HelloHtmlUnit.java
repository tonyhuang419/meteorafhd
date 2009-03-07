package htmlUnit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HelloHtmlUnit {

	public void homePage() {
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			page = webClient.getPage("http://bbs.taisha.org/");
			System.out.println(page.getTitleText());
			HtmlForm form = page.getFormByName("login");
			HtmlTextInput username = form.getInputByName("username");
			HtmlPasswordInput password = form.getInputByName("password");
			username.setValueAttribute("非法_用户");
			password.setValueAttribute("happyamiga");
			HtmlButton button = form.getButtonByName("userlogin");
			page = button.click();
			System.out.println(page.getTitleText());
			System.out.println(page.getBody().asText());
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		HelloHtmlUnit helloHtmlUnit = new HelloHtmlUnit();
		helloHtmlUnit.homePage();
	}

}
