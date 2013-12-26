package def;

import java.text.ParseException;
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

	// http://www.cnblogs.com/peida/archive/2013/05/31/3070790.html
	public static class TestSimpleDateFormatThreadSafe extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					this.join(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					System.out.println(this.getName()+":"+DateUtil.parse("2013-05-24 06:02:20"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}    
	}


	public static void main(String[] args) {
//		new TestSimpleDateFormat().test();

		for(int i = 0; i < 3; i++){
			new TestSimpleDateFormatThreadSafe().start();
		}
	}
}

class DateUtil {
	private static final  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static  String formatDate(Date date)throws ParseException{
		return sdf.format(date);
	}
	public static Date parse(String strDate) throws ParseException{
		return sdf.parse(strDate);
	}
}
