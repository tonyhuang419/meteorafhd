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

		String helloWorldURL = "http://localhost:8080/testStruts/xfire/HelloService";
		try {
			IHelloService srvc = (IHelloService) factory.create(srvcModel,helloWorldURL);
			System.out.println(srvc.sayHello("Hello XFire"));

			Course c = srvc.choose();
			System.out.println(c.getName());

			List  al = new ArrayList();
			al.add("1212");
			al.add("2222");
			List t = srvc.test(al);
			for (int i = 0; i < t.size(); i++) {
				Course co=(Course)t.get(i);
				System.out.println(co.getName());
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}

