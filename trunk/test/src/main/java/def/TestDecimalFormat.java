package def;
import java.text.DecimalFormat;
import java.text.ParseException;


public class TestDecimalFormat {

	public void testx(){
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.00");
		DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###,###,###.00");
		Double d = 112345.67d;
		Double d2 = 112345.60d;
		try{
			System.out.println(decimalFormat.parseObject("123,456.87"));
		}
		catch(ParseException e){
			System.out.println("转换失败");
		}
		System.out.println(decimalFormat.format(d));
		System.out.println(decimalFormat2.format(d2));
	}

	public void test(){
		testx();
	}

	public static void main(String[] args) {
		new TestDecimalFormat().test();

	}

}
