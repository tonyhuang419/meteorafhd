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
	
	public void test(){
		System.out.println(testDivide(new BigDecimal("1188888888888888888888888888111.23"), new BigDecimal("17678678687112")));
		System.out.println(testMultipy(new BigDecimal("72342332434.9"), new BigDecimal("1234234321.000023")));
	}
	
	public static void main(String args[]){
		
		new TestBigDeciaml().test();
		
	}
	
}
