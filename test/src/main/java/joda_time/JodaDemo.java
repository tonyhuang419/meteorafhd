package joda_time;

import org.joda.time.DateTime;

public class JodaDemo {

	
	public static void main(String[] args) {
		DateTime d = new DateTime(2014,3,8,0,0,0);
		int sum = d.getYear() + d.getMonthOfYear() + d.getDayOfMonth();
		System.out.println(sum);
		
		int nextYear = 2015;
		DateTime d2 = new DateTime(nextYear,1,1,0,0,0);
		while (d2.getYear()<2018){
			if ( d2.getYear() + d2.getMonthOfYear() + d2.getDayOfMonth() == sum ){
				System.out.println( d2.toString("yyyy/MM/dd")   );
			}
			d2 = d2.plusDays(1);
		}
	}

}
