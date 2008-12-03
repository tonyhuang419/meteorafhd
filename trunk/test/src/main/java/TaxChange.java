import java.math.BigDecimal;
import java.text.DecimalFormat;

/*
 * 票据类型说明
 * 1：服务业务普通发票 5.5%
 * 2：增值税发票	17%
 * 3：商业普通发票 17%
 * 4：收据 0%.....这个就不转了
 */
public class TaxChange {

	//抛入含税价，返回不含税价...double
	public static  Double TaxToNoTaxDouble(Double tax, String type){
		DecimalFormat df = new DecimalFormat("0.00");
		int typex = Integer.parseInt(type);
		Double noTax = new Double(0.00);   //不含税价
		switch(typex){
		case 1:
			noTax = Double.parseDouble(df.format(tax/1.055));
			return noTax;
		case 2:
			noTax = Double.parseDouble(df.format(tax/1.17));
			return noTax;
		case 3:
			noTax = Double.parseDouble(df.format(tax/1.17));
			return noTax;

		default:
			return 0.00;
		}
	}

	//抛入不含税价，返回含税价...double
	public static  Double NoTaxToTaxDouble(Double noTax, Long type){
		DecimalFormat df = new DecimalFormat("0.00");
		int typex = Integer.parseInt(type.toString());
		Double tax = new Double(0.00);   //含税价
		switch(typex){
		case 1:
			tax = Double.parseDouble(df.format(noTax*1.055));
			return tax;
		case 2:
			tax = Double.parseDouble(df.format(noTax*1.17));
			return tax;
		case 3:
			tax = Double.parseDouble(df.format(noTax*1.17));
			return tax;

		default:
			return 0.00;
		}
	}

	//抛入含税价，返回不含税价...BigDecimal
	public static  BigDecimal TaxToNoTaxBigDecimal(BigDecimal tax, Long type){
		DecimalFormat df = new DecimalFormat("0.00");
		int typex = Integer.parseInt(type.toString());
		BigDecimal noTax = null;   //不含税价

		switch(typex){
		case 1:
			noTax = new BigDecimal(df.format(tax.divide(new BigDecimal(1.055),2,BigDecimal.ROUND_HALF_UP)));
			return noTax;
		case 2:
			noTax = new BigDecimal(df.format(tax.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP)));
			return noTax;
		case 3:
			noTax = new BigDecimal(df.format(tax.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP)));
			return noTax;
		default:
			return new BigDecimal(0);
		}
	}

	//抛入不含税价，返回含税价...BigDecimal
	public static  BigDecimal NoTaxToTaxDoubleBigDecimal(BigDecimal noTax, Long type){
		DecimalFormat df = new DecimalFormat("0.00");
		int typex = Integer.parseInt(type.toString());
		BigDecimal tax ;   //含税价
		switch(typex){
		case 1:
			tax = new BigDecimal(df.format(noTax.multiply(new BigDecimal(1.055))));
			return tax;
		case 2:
			tax = new BigDecimal(df.format(noTax.multiply(new BigDecimal(1.17))));
			return tax;
		case 3:
			tax = new BigDecimal(df.format(noTax.multiply(new BigDecimal(1.17))));
			return tax;

		default:
			return new BigDecimal(0);
		}
	}



//	@Test
//	public static void main(String[] args) {
//		TaxChange t  = new TaxChange();
//		Double a = new Double(t.TaxToNoTaxDouble(3500.00, 1L));
//		Double b = new Double(t.TaxToNoTaxDouble(3600.00, 2L));
//		Double c = new Double(t.TaxToNoTaxDouble(3700.00, 3L));
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);

//		System.out.println(t.NoTaxToTaxDouble(a, 1L));
//		System.out.println(t.NoTaxToTaxDouble(b, 2L));
//		System.out.println(t.NoTaxToTaxDouble(c, 3L));

//		BigDecimal a = t.TaxToNoTaxBigDecimal(new BigDecimal(3500.00), 1L);
//		BigDecimal b = t.TaxToNoTaxBigDecimal(new BigDecimal(3600.00), 2L);
//		BigDecimal c = t.TaxToNoTaxBigDecimal(new BigDecimal(3700.00), 3L);
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//
//		System.out.println(t.NoTaxToTaxDoubleBigDecimal(a, 1L));
//		System.out.println(t.NoTaxToTaxDoubleBigDecimal(b, 2L));
//		System.out.println(t.NoTaxToTaxDoubleBigDecimal(c, 3L));
//	}
}
