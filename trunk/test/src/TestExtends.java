
public class TestExtends extends InheritX{
	public void sayHello(){
		super.sayHello();
		System.out.println("hello");
	}
	
	public static void main(String[] args){
		new TestExtends().sayHello();
	}
}


class InheritX{
	public void sayHello(){
		System.out.println("helloX");
	}
}
 