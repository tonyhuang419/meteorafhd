package def;

import org.springframework.core.SpringVersion;



public class TestStatic {

	private static int a = 0;
	
	
	public static int getA() {
		return a;
	}


	public static void setA(int a) {
		TestStatic.a = a;
	}

	public static void main(String[] args) {
		System.out.println(new TestStatic().getClass().getPackage().getName());
		TestStatic n = new TestStatic();
		System.out.println(n.getA());;
		n.setA(11);
		System.out.println(n.getA());
		System.out.println(SpringVersion.getVersion());
	}
}
