import java.text.DecimalFormat;


public class Test {

	public void testx(){
		Double d = 1111111111.11;
		System.out.println(d);

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		System.out.println(decimalFormat.format(d));
	}


	public void testEnter(){
		testx();
	}

	public static void main(String[] args) {
		new Test().testEnter();
	}
}
