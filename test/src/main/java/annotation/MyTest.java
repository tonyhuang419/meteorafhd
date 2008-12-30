package annotation;

public class MyTest {
	//一个方法可以有多个注解类   
	@Deprecated  
	@MyAnnotation(hello="china",world="earth")   
	public void say(){   
		System.out.println("say hello");   
	}   
}    
