package quote;

class Change{
	static void cpy(String x,String y){
		char buf[] = new char[x.length()];
		for(int i=0; i<x.length(); i++){
			buf[i] = y.charAt(i);
		}
		x = new String(buf);
		System.out.println(x);
	}
}

public class QuoteDemo {
	public static void main(String[] args) {
		String a="aaa";
		String b="bbb";
		Change.cpy(a,b);
		System.out.println(a);
		System.out.println(b);
	}
}
