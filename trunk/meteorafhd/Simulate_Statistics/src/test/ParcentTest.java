package test;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ParcentTest {
	public  static void main(String args[]){
		DecimalFormat myformat = null;
		myformat= (DecimalFormat)NumberFormat.getPercentInstance();
		myformat.applyPattern("0%"); //0表示加的小数点,00表示两位小数点，你用00试一下你就知道效果
		double cs_count=11.1;
		double cs_sum=111.2;
		double rat = (double)cs_count/(double)cs_sum;
		System.out.println(myformat.format(rat));	
	}
	
	public String c_precentage(double p1,double p2){
		String str;
        double  p3  =  p1  /  p2;
        NumberFormat nf  =  NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits( 2 );
        str  =  nf.format(p3);
        return str;
	}
}
