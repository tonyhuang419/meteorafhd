package def;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
		if( str instanceof String){
			System.out.println("  str instanceof String ");
		}
	}

	public void testY(){
		//		int i = 2;
		//		Integer ii = 20;
		//		i = ii;
		//		System.out.println(i);
		//		ii = i;
		//		System.out.println(ii);

		//		Double d = new Double("1.2222222222222222222");
		//		System.out.println(d);
		//		System.out.println(d.floatValue());

		//		Object id = null;
		//		System.out.println((Long)id);

		if(   ! (1==1) ){
			System.out.println("1");
		}

	}

	public void testFor(){
		for(int i=0;i<3;i++){
			if(i<1){
				break;
			}
			System.out.println("bbbb");
		}
		System.out.println("aaaaa");
	}


	public void testMemory(){
		String s[] = new String[5];
		s[0]="1";
		s[2]="2";
		s[3]="3";
		s[4]="4";
		System.out.println(s[4]);
		s[4]= null;
		System.out.println(s[4]);

	}

//=======================
	public void testFor2(){
		for(int i=0,n=this.getC();i<10;i++){
			System.out.println("for"+n);
		}
	}

	public int getC(){
		System.out.println("get c");
		return 10;
	}
	//=======================
	
	
	public void sort(){
		List<String> list = new ArrayList<String>();
		list.add("c23");
		list.add("a23");
		list.add("123");
		Collections.sort(list , String.CASE_INSENSITIVE_ORDER);
		System.out.println(list);
		
		String[] s = {"asd","saf","123"};
		System.out.println(Arrays.asList(s));
	}
	

	public static void main(String[] args) {
		Test test = new Test();

		System.out.println(1.03 - 0.42);
		
//		test.sort();
		
		//		new Test().testY();
//		test.testFor2();
//		test.testMemory();

		//		String s=null;
		//		s +="a";
		//		System.out.println(s);
		//		BigDecimal b = new BigDecimal("0");

		//		Double n = new Double("12345678912");
		//		System.out.println(n);
		//		System.out.println(n.toString());

		//		System.out.println(b.add(new BigDecimal(n.toString())));

		//		System.out.println(new Double("1000000"));
		//		System.out.println(new Double("10000000"));

		//		Integer i = 0;
		//		String s = "s";
		//		boolean x = Integer.class.isAssignableFrom(s.getClass());
		//		boolean y = Integer.class.isAssignableFrom(i.getClass());
		//		System.out.println(x+" "+y);

		//		Object i = null;
		//		Double b = (Double)i;
		//		System.out.println(b);

		//		Double d = 0d;
		//		Object o = 0d;
		//		System.out.println(o.equals(0d));
		//		Long x = new Long("1");


		//		Double n = new Double("12345678912");
		//		System.out.println(n);
		//		System.out.println(n.toString());

		//		System.out.println(b.add(new BigDecimal(n.toString())));

		//		System.out.println(new Double("1000000"));
		//		System.out.println(new Double("10000000"));

		//		Integer i = 0;
		//		String s = "s";
		//		boolean x = Integer.class.isAssignableFrom(s.getClass());
		//		boolean y = Integer.class.isAssignableFrom(i.getClass());
		//		System.out.println(x+" "+y);

		//		Object i = null;
		//		Double b = (Double)i;
		//		System.out.println(b);

		//		Double d = 0d;
		//		Object o = 0d;
		//		System.out.println(o.equals(0d));
		//		Long x = new Long("1");

		//		double x = 3/2+0.3;
		//		System.out.println(x);


	}
}
