import java.math.BigDecimal;
import java.text.DecimalFormat;


public class TestBigDeciaml {


	public BigDecimal testDivide(BigDecimal x, BigDecimal y){
		DecimalFormat df = new DecimalFormat("0.00");
		return new BigDecimal(df.format(x.divide(y,2,BigDecimal.ROUND_HALF_UP)));
	}

	public  BigDecimal testMultipy(BigDecimal x, BigDecimal y){
		DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
		System.out.println(df.format(x.multiply(y)));
		
		df = new DecimalFormat("0.00");
		return  new BigDecimal(df.format(x.multiply(y)));
		
	}
	
	public void testX(){
		DecimalFormat   format = new   DecimalFormat("###0.00");   
		
		BigDecimal b = new BigDecimal("123456789123456789123456789123456789");
		Double d = new Double(b.toString());
		
		Double dd = new Double("123456789123456781234567899");
		
		System.out.println(format.format(d));
		System.out.println(format.format(dd));
		
		//整数部分超过7位就自动使用科学计数法，解决办法在上面
		System.out.println(new Double("1234567.123456789"));
	}
	
	
	public void testXX(){
//		DecimalFormat   format = new   DecimalFormat("###0.00");   
		
//		Double dd = new Double("1234567890123456789");
		
		BigDecimal b = new BigDecimal("1234567890");
		Double d = new Double(b.toString());
		System.out.println(d);
		
//		System.out.println(dd);
//		System.out.println(format.format(dd));

	}
	
	public void test(){
//		testX();
		testXX();
//		System.out.println(testDivide(new BigDecimal("1188888888888888888888888888111.23"), new BigDecimal("17678678687112")));
//		System.out.println(testMultipy(new BigDecimal("72342332434.9"), new BigDecimal("1234234321.000023")));
	}
	
	public static void main(String args[]){
		
		new TestBigDeciaml().test();
		
	}
	
}
