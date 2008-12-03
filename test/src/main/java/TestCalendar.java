import java.util.Calendar;
import java.util.TimeZone;


public class TestCalendar {
	public void tc(){
		Calendar c = Calendar.getInstance();
		System.out.println(c.toString());	
		System.out.println(c.getTime());
		System.out.println(c.getTimeInMillis());
		System.out.println(System.currentTimeMillis());
		
		System.out.println(c.getTimeZone());
		c.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		System.out.println(c.getTimeZone());
		System.out.println(c.getTime());
		
		c.setLenient(true);
		c.set(2001, 11, 7);
		System.out.println(c.getTime());
		
		//严格控制日期书输入....注意：月、日 从0开始计算
		c.setLenient(false);
		c.set(2001, 12, 7);
		System.out.println(c.getTime());
	}
	
	public void test(){
		tc();
	}
	
	public static void main(String[] args) {
		new TestCalendar().test();
	}
}
