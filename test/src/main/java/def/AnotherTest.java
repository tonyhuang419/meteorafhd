package def;

public class AnotherTest {


	public static void main(String[] args) {
		Boolean a = null;
		System.out.println(a);
		//if(  || a == true){
		//	System.out.println(a);
		//}

		int ab = 1;
		ab += ab++  + ab++;
		System.out.println(ab);
		
		StringBuffer sb = new StringBuffer("");
		System.out.println(sb.toString().equals("")); //true
	}

}
