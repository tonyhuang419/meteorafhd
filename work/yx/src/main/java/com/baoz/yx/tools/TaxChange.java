package com.baoz.yx.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.utils.BigDecimalUtils;

public class TaxChange {

	private static IInvoiceService invoiceService;
	
	//抛入含税价，返回不含税价...double
	public static  Double TaxToNoTaxDouble(Double tax, String type){
		DecimalFormat df = new DecimalFormat("0.00");
		Double noTax = new Double(0.00);   //不含税价
		noTax = Double.parseDouble(df.format(tax / Double.valueOf(getInvoiceService().getTaxRate(type))));
		return noTax;

	}

	//抛入不含税价，返回含税价...double
	public static  Double NoTaxToTaxDouble(Double noTax, String type){
		DecimalFormat df = new DecimalFormat("0.00");
		Double tax = new Double(0.00);   //含税价
		tax = Double.parseDouble(df.format(noTax * Double.valueOf(getInvoiceService().getTaxRate(type))));
		return tax;

	}

	//抛入含税价，返回不含税价...BigDecimal
	public static  BigDecimal TaxToNoTaxBigDecimal(BigDecimal tax, String type){
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal noTax = null;   //不含税价
		noTax = BigDecimalUtils.toBigDecial(df.format(tax.divide(BigDecimalUtils.toBigDecial(getInvoiceService().getTaxRate(type)),2,BigDecimal.ROUND_HALF_UP)));
		return noTax;
	}

	//抛入不含税价，返回含税价...BigDecimal
	public static  BigDecimal NoTaxToTaxBigDecimal(BigDecimal noTax, String type){
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal tax = null;   //含税价
		tax = BigDecimalUtils.toBigDecial(df.format(noTax.multiply(BigDecimalUtils.toBigDecial(getInvoiceService().getTaxRate(type)))));
		return tax;
	}

	public static IInvoiceService getInvoiceService() {
		if(invoiceService == null){
			WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
			return (IInvoiceService) webAppContext.getBean("invoiceService");
		}else{
			return invoiceService;
		}
	}

	public static void setInvoiceService(IInvoiceService invoiceService) {
		TaxChange.invoiceService = invoiceService;
	}
}

