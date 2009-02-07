package hessian;

public class BasicService implements IBasic {   
	private String hello= "Hello, Vincent";      

	public String hello()      
	{      
		System.out.println("i will say hello to client");
		return hello;      
	}      

	public Person getPerson() {      
		Person person = new Person();      
		person.setColor("Yello");      
		person.setLength("176cm");      
		person.setName("vincent");      
		return person;      
	} 
}  

