package htmlUnit.waterKing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
	public static Date stringToDate(String str){
		try {
			return dateFormat.parse(str);
		} catch (ParseException pe) {
			System.out.println("日期格式转换出错");
			pe.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args){
		System.out.println(Tools.stringToDate("2009-3-3"));
	}
}
