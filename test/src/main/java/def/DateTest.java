package def;
import java.util.Date;


public class DateTest {
	public static void main(String[] args) {
		Date d = new Date(System.currentTimeMillis());
		
		System.out.println(d.toGMTString());
		
		System.out.println(d.toLocaleString());
		
		System.out.println(d.toString());
		
		System.out.println(d.getYear());
	}
}
