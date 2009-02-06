package xfire;

import java.util.ArrayList;
import java.util.Collection;
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

	public  Collection<Course> testCollection( Collection<Course> course ){
		for (Course c : course ) {
			System.out.println(c.getName());
		}
		List<Course>  al = new ArrayList<Course>();
		Course c = new Course();
		c.setName("Eng");
		al.add(c);
		return al;

	}

}
