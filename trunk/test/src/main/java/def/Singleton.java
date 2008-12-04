package def;

public class Singleton {
	private static Singleton obj = new Singleton();
	public static int counter1;
	public static int counter2 = 100;

	private Singleton() {
		System.out.println("frist construct,then run static variables evaluate");
		counter1++;
		counter2++;
	}

	public static Singleton getInstance() {
		return obj;
	}

	public static void main(String[] args) {
		Singleton.getInstance();
		System.out.println("obj.counter1==" + counter1);
		System.out.println("obj.counter2==" + counter2);
	}
}
