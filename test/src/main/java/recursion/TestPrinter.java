package recursion;

public class TestPrinter {
	private static TestClass tc = null;

	static
	{
		printStaticInfo("initialize test class in static");
		tc = new TestClass();
	}

	public TestPrinter()
	{
		System.out.println("to construct a TestPrinter object");
	}

	public void printOne()
	{
		System.out.println("One");
		System.out.println("compile again!");
	}

	public static void printStaticInfo(String s)
	{
		System.out.println(s);
	}
	
}
