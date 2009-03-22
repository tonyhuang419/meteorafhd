package com.baoz.yx.vo;

import java.io.Serializable;
import java.util.*;
/**
 * 类ExcelHistoryReveInfo.java实现的描述：导入收款明细
 * @author xusheng
 *
 */
public class ExcelHistoryReveInfo implements Serializable{

	private static final long serialVersionUID = -1001111120215L;

	/**到款日期**/
	private Date amountTime;
	
	/**到款金额**/
	private Double amount;
	
	/**合同/项目号**/
	private String conId;
	
	/**应收销售员**/
	private String saleMan;
	
	/**到款方式**/
	private String receType;
	
	/**客户名称**/
	private String customerName;
	
	/**是否预收**/
	private String isPerArrive;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Date amountTime) {
		this.amountTime = amountTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getReceType() {
		return receType;
	}

	public void setReceType(String receType) {
		this.receType = receType;
	}

	public String getIsPerArrive() {
		return isPerArrive;
	}

	public void setIsPerArrive(String isPerArrive) {
		this.isPerArrive = isPerArrive;
	}
	
}
