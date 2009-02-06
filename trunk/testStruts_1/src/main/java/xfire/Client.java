package xfire;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;


public class Client {

	public static void main(String[] args) {

		Service srvcModel = new ObjectServiceFactory().create(IHelloService.class);
		XFireProxyFactory factory =
			new XFireProxyFactory(XFireFactory.newInstance().getXFire());

		String helloWorldURL = "http://localhost:8080/testStruts/servlet/XFireServlet/HelloService";
		try {
			IHelloService srvc = (IHelloService) factory.create(srvcModel,helloWorldURL);
			System.out.println(srvc.sayHello("Robin"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}

