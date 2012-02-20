package elanceTest;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class DateCalc {

	public static void main(String[] args) throws ParseException {
		Locale.getDefault();
		String[] weekdays = new DateFormatSymbols().getWeekdays();
		String str1;
		String str2;
		Scanner in = new Scanner(System.in);
		str1 = in.next();
		str2 = in.next();
		calc(weekdays, str1);
		calc(weekdays, str2);
	}

	private static void calc(String[] weekdays, String str)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		Date d = df.parse(str+"-28");
		Calendar calender = Calendar.getInstance();
		calender.setTime(d);
		calender.add(Calendar.MONTH, 1);
		System.out.println( weekdays[calender.get(Calendar.DAY_OF_WEEK)-1] );
	}
}
