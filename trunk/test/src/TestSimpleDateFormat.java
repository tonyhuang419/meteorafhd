
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestSimpleDateFormat {

	public void test(){
		test1();
		test2();
	}
	
	public void test1(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String str = dateFormat.format(d);
		System.out.println(str);
		
//		str = dateFormat.format(null);  //error, throw exception
//		System.out.println(str);
		
//		str = dateFormat.format(" ");  //error, throw exception
//		System.out.println(str);
		
	}
	
	public void test2(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
		String str = dateFormat.format(d);
		System.out.println(str);
	}
	
	public static void main(String[] args) {
		new TestSimpleDateFormat().test();
	}
}
