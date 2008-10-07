import java.math.BigDecimal;
import java.text.DecimalFormat;


public class Test {

	public void testx(){
		Double d = 1111111111.11;
		System.out.println(d);

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		System.out.println(decimalFormat.format(d));
	}
	
	public void testxx(){
//		int x = 111;
//		System.out.println(x%100);
//		System.out.println(x%10);
//		System.out.println(x%1);
		
//		String xx = "";
//		
//		System.out.println(xx.charAt(0));
//		System.out.println(xx.charAt(1));
//		System.out.println(xx.charAt(2));
		
		char x = 1;
		String xx= "1";
		System.out.println(xx.equals(x));
	}


	public void testxxx(){
		String x = "xx123";

		System.out.println(x.contains("xx")); 
		System.out.println(x.contentEquals("xx123"));
	}
	
	public void testxxxx(){
		long x = 4L;
		System.out.println( "4".equals(x));
		
		Long xx = 4L;
		System.out.println(xx.equals(4L));
		System.out.println(xx.equals("4L"));
	}
	
	public void testUpcase(){
		String x = "a1b2";
		System.out.println(x.toUpperCase());
	}
	
	public void testEnter(){
		testUpcase();
	}
	
	public void subString(){
		String str = "123456789";
		System.out.println(str.substring(2,3));
	}

	public static void main(String[] args) {
//		new Test().subString();
//		
//		String s=null;
//		s +="a";
//		System.out.println(s);
		BigDecimal b = new BigDecimal("0");
		
//		Double n = new Double("12345678912");
//		System.out.println(n);
//		System.out.println(n.toString());
		
//		System.out.println(b.add(new BigDecimal(n.toString())));
		
		System.out.println(new Double("1000000"));
		System.out.println(new Double("10000000"));
	}
}
