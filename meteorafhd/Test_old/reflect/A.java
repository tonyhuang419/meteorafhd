package reflect;

public class A {
	A(){
		System.out.println("constrator A");
		Class x = this.getClass();
		System.out.println("Class A:"+x.getName());
	}
}
