package aboutTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AboutTime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		
		SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println(sdf.format(System.currentTimeMillis()));
    
		Date date = new Date(time);
		System.out.println(date.getDate()+" "+ date.getMinutes());
	}

}
