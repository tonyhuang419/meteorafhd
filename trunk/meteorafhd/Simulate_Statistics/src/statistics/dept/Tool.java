package statistics.dept;

import java.text.NumberFormat;

class Tool{
	static public String c_precentage(double target,double accomplish){
		String str;
		double  p3  =  accomplish  /  target;
		NumberFormat nf  =  NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits( 2 );
		str  =  nf.format(p3);
		return str;
	}

	static public double balance(double target,double accomplish){
		return target-accomplish;
	}
}
