package reflect;

public class C extends B{
	C(){
		super();
		System.out.println("constrator C");
		Class x = this.getClass();
		System.out.println("Class C:"+x.getName());
	}
	public static void main(String[] args) {
		new C();
		System.out.println("C");	
	}
}
