package extend;

public class Test {

	public People fun1(){
		return new Man();		
	}

	public void test(){
		People p = fun1();
		p.say();
	}
	

	public static void main(String[] args) {
		new Test().test();
		
	}
}
