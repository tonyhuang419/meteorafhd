
public class SubClass extends BaseClass{
	
	public void test(){
		System.out.println(s.i);
	}
	
	public static void main(String args[]){
		
		new SubClass().test();
	}
}
