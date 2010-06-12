package demo;

public class Test {

	public Test(char[] chars){
		System.out.println("New line at "+findNewLine(chars));
	}

	private int findNewLine(char[] chars)
	{
		int i = 0;
		char aChar;
		do{
			aChar = chars[i];
		}while(aChar!='\n');
		return i;
	}

	public static void main(String argv[])
	throws Exception
	{
		new Test("Hello World!\nHowz goin?!".toCharArray());
	}
}
