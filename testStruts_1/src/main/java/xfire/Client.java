package xfire;

import java.util.ArrayList;
import java.util.List;

import com.caucho.services.server.Service;

public class Client {

	public static void main(String[] args) {

		Service srvcModel = new ObjectServiceFactory()
		.create(IHelloService.class);
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory
				.newInstance().getXFire());

		String helloWorldURL = "http://localhost:8080/xfiretest/services/HelloService";
		try {
			IHelloService srvc = (IHelloService) factory.create(srvcModel,
					helloWorldURL);
			System.out.println(srvc.sayHello("Robin"));

			User u=new User();
			u.setName("RRRRR");
			Course c=srvc.choose(u);
			System.out.println(c.getName());

			List  al=new ArrayList();
			al.add("1212");
			al.add("2222");
			List t=srvc.test(al);
			for (int i = 0; i < t.size(); i++) {
				Course co=(Course)t.get(i);
				System.out.println(co.getName());
			}


		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

}

