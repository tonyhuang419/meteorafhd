package fhd.right.action;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class _TestAction extends  TestCase {
	Resource resource = new ClassPathResource("_JUapplicationContext-LoginAction.xml");
	BeanFactory factory = new XmlBeanFactory(resource);

	public void test() throws Exception {	
		ILoginTool h = (ILoginTool)factory.getBean("loginTool");
		System.out.println(h.validName("adminx"));
		System.out.println(h.validPwd("adminx"));
	}
	public void test2() throws Exception {
		LoginAction h = (LoginAction)factory.getBean("login");
		h.setName("a");
		h.setPwd("a");
		System.out.println(h.nameLogin());
		System.out.println(h.pwdLogin());
	}
}
