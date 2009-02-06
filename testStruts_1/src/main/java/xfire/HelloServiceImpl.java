package xfire;

import java.util.ArrayList;
import java.util.List;


public class HelloServiceImpl implements IHelloService {

	public String sayHello(String str) {
		return str ;
	}

	public Course choose( ){
		Course c = new Course();
		c.setName("English");
		return c; 
	}

	public List  test(List t){
		for (int i = 0; i < t.size(); i++) {
			System.out.println((String) t.get(i));
		}
		List  al = new ArrayList();
		Course c = new Course();
		c.setName("Eng");
		al.add(c);
		return al;

	}

}
