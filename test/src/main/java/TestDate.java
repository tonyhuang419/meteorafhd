import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;


public class TestDate {

	public static void main(String[] args) {

		Date today = new Date();
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		System.out.println(nowMonth);
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		System.out.println(nextMonth);

	}

}
