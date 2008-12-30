package annotation.inherited;

@MyInherit("test class")   
public class Parent {   
	@MyInherit("test method")   
	public void doSomething(){   
		System.out.println("hello");   
	}   
}  

