package def;
import java.text.DecimalFormat;
import java.text.ParseException;


public class TestDecimalFormat {

	public void testx(){
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.##");
		Double d = 112345.67d;
		try{
			System.out.println(decimalFormat.parseObject("123,456.87"));
		}
		catch(ParseException e){
			System.out.println("转换失败");
		}
		System.out.println(decimalFormat.format(d));
	}

	public void test(){
		testx();
	}

	public static void main(String[] args) {
		new TestDecimalFormat().test();

	}

}
