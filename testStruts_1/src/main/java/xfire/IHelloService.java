package xfire;

import java.util.Collection;

public interface IHelloService {
	
	public String sayHello(String str);

	public Course choose( );
	
	public  Collection<Course> testCollection( Collection<Course> course );
	
} 
