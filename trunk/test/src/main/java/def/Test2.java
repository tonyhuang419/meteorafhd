package def;

public class Test2 {

	
	public void trimDemo(){
		String s = "123\n";
		System.out.println(s);
		System.out.println("========");
		System.out.println(s.trim());
		System.out.println("========");
	}
	
	public static void main(String[] args) {
		Test2 t = new Test2();
		t.trimDemo();
	}
}
