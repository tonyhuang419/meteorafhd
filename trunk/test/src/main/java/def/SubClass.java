package def;

public class SubClass extends BaseClass{
	
	public void hellWorld(){
		System.out.println(" hello world ");
	}
	
	public void fatherHello(){
		super.hellWorld();
	}
	
	public void test(){
		fatherHello();
		System.out.println(s.i);
	}
	
	public static void main(String args[]){
		new SubClass().test();
	}
	
}
