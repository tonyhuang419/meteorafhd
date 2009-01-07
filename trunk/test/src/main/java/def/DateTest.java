package def;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;


public class DateTest {
	public static void main(String[] args) {
		Date d = new Date(System.currentTimeMillis());
		
//		System.out.println(d.toGMTString());
//		
//		System.out.println(d.toLocaleString());
//		
//		System.out.println(d.toString());
//		
//		System.out.println(d.getYear());
		
		System.out.println(d.getYear());
	}
}
