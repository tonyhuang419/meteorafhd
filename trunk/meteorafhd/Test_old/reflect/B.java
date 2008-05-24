package reflect;

public class B extends A{
	B(){
		super();
		System.out.println("constrator B");
		Class x = this.getClass();
		System.out.println("Class B:"+x.getName());
	}
}
