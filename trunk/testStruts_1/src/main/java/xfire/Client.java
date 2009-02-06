package xfire;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
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

			Collection<Course>  al = new ArrayList<Course>();
			Course course = new Course();
			course.setName("chinese");
			al.add(course);
			course.setName("english");
			al.add(course);
			Collection<Course> t = srvc.testCollection(al);
			for (Course co: t ) {
				System.out.println(co.getName());
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}

