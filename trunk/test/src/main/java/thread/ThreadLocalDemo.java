package thread;


/**
 * http://www.javaeye.com/topic/103804
 */
public class ThreadLocalDemo {
	
	public static void main(String[] args){
		ThreadLocal<String> t = new ThreadLocal<String>();
		t.set("demo");
		String s = t.get();
		System.out.println(s);
	}
	
}
