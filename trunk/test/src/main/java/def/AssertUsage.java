package def;

public class AssertUsage {


	public static void main(String[] args){

		boolean isOpen = false;  
		assert isOpen=true; //如果开启了断言，会将isOpen的值改为true
		System.out.println(isOpen);//打印是否开启了断言  

		//assert 1==2 ;   //assert失败，程序终止
		System.out.println("==========");
		assert 1==2 : "1!=2";
	}
}
