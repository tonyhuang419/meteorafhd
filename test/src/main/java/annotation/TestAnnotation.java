package annotation;

public class TestAnnotation {
	//  @DefineAnnotation 有默认值的第一种使用方式   
	//  @DefineAnnotation() 有默认值的第二种使用方式   
	@DefineAnnotation("ttitfly")   
	public void say(){   
		System.out.println("say hello");   
	}   
	public static void main(String[] args){   
		TestAnnotation ta = new TestAnnotation();   
		ta.say();   
	}   
}    
