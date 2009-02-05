package xfire;

import java.util.ArrayList;
import java.util.List;

public class HelloServiceImpl implements IHelloService {

	public String sayHello(String ttt) {
		return "Hello, "+ttt;
	}

	public Course choose(User u){
		System.out.println(u.getName());
		Course c=new Course();
		c.setName("Eee");
		return c; 

	}

	public List  test(List t){
		for (int i = 0; i < t.size(); i++) {
			System.out.println((String) t.get(i));
		}
		List  al=new ArrayList();
		Course c=new Course();
		c.setName("EeeDDDDDD");
		al.add(c);
		return al;

	}
}
