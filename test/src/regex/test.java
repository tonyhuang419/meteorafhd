package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public void test1 (){
		String[] ss = new String[]{"a98b", "c0912d",  "c10b",  "a12345678d",  "ab"};
		for(String s: ss)
			System.out.println(s.matches("^[ac]\\d*[bd]$"));
	}

	public void test2(){
		String regex = "\\w(\\d\\d)(\\w+)";  
		String candidate = "x99SuperJava";  

		Pattern p = Pattern.compile(regex);  
		Matcher matcher = p.matcher(candidate);  
		if(matcher.find()){  
			int gc = matcher.groupCount();  
			for(int i = 0; i <= gc; i++)  
				System.out.println("group " + i + " :" + matcher.group(i));  
		}  
	}

	public  void test3(){  
		Pattern p = Pattern.compile("\\d");  
		Matcher m1 = p.matcher("55");  
		Matcher m2 = p.matcher("fdshfdgdfh");  

		System.out.println(m1.pattern() == m2.pattern());  
		//return true  
	}  

	public  void test4(){  
		Pattern p = Pattern.compile("nfa|nfa not",Pattern.CASE_INSENSITIVE);  
		Matcher m1 = p.matcher("nfa not");  

		while( m1.find() ){  
			System.out.println(m1.group());
		}

//		System.out.println(m1.pattern());  
	}  

	public void testX(){
//		test1();
//		test2();
//		test3();
		test4();
	}

	public static void main(String[] args) {
		new test().testX();
	}

}
