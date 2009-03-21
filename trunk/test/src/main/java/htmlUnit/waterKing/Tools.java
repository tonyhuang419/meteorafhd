package htmlUnit.waterKing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {


	synchronized  public static Date stringToDate(String str , SimpleDateFormat  simpleDateFormat ){
		try {
			return simpleDateFormat.parse(str);
		} catch (ParseException pe) {
			System.out.println("date convert error");
			pe.printStackTrace();
		}
		return null;
	}

	private static Pattern p = Pattern.compile("^thread-\\d*-");  
	synchronized public static String getBoardDetailUrl( Board board , int pageNum){
		String baseUrl = board.getTopicUrl().trim();
		Matcher m = p.matcher(baseUrl);  
		while( m.find() ){ 
			return Units.URL_PREFIX + m.group(0) + pageNum + "-1.html";
		}
		return null;
	}

	private static String filePath = "src/main/java/htmlUnit/waterKing/page.properties";
	private static String propName = "page";
	synchronized public static int getScanPage(){
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty (propName);
			Integer page = new Integer(value);
			OutputStream fos = new FileOutputStream(filePath);
			props.setProperty(propName, new Integer(page-1).toString());
			props.store(fos, "Update "+propName+" value");
			fos.close();
			in.close();
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}


	public static void main(String[] args){
		//		System.out.println(Tools.stringToDate("2009-3-3" , Units.dateFormatDate ));
		//		System.out.println(Tools.stringToDate("2009-3-3 21:59" , Units.dateFormatTime ));
		//
		//		Board board = new Board();
		//		board.setTopicUrl("thread-512263-1-768.html");
		//		System.out.println(Tools.getBoardDetailUrl(board, 2));
		int i=100;
		while(i>0){
			System.out.println(Tools.getScanPage());
			i--;
		}

	}
}
