package recursion;

public class TestClass {
	private static TestPrinter printer = null;

	static
	{
		printStaticInfo("initialize test printer in static");
		printer = new TestPrinter();
	}

	public TestClass()
	{
		System.out.println("to construct a TestClass object");
	}

	public void printOne()
	{
		printer.printOne();
	}

	public static void printStaticInfo(String s)
	{
		System.out.println(s);
	}

	public static void main(String[] args) {
		new TestClass();

	}
}
