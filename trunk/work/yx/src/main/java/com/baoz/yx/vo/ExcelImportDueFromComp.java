package com.baoz.yx.vo;

import java.util.Date;

public class ExcelImportDueFromComp {
	private Date baseDate;////财务日期
	
	private String customerName;///客户名称
	
	private String orderOrItemNo;//订单/项目号
	
	private String itemName;///项目名称
	
	private String saleManName;//销售人员
	
	private Double billFee;//发票余额
	
	private Double logicDayAccountAge;//帐龄(天数)
	
	private Integer logicMonthAccountAge;//账龄(月)
	
	private Double firstThreeMonth;//《=1-3个月
	
	private Double secondThreeMonth;//4-6个月
	
	private Double thridSixMonth;//7-12个月
	
	private Double blowOneYear;//1年以上


	public Date getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderOrItemNo() {
		return orderOrItemNo;
	}

	public void setOrderOrItemNo(String orderOrItemNo) {
		this.orderOrItemNo = orderOrItemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSaleManName() {
		return saleManName;
	}

	public void setSaleManName(String saleManName) {
		this.saleManName = saleManName;
	}

	public Double getBillFee() {
		return billFee;
	}

	public void setBillFee(Double billFee) {
		this.billFee = billFee;
	}

	public Double getLogicDayAccountAge() {
		return logicDayAccountAge;
	}

	public void setLogicDayAccountAge(Double logicDayAccountAge) {
		this.logicDayAccountAge = logicDayAccountAge;
	}

	public Integer getLogicMonthAccountAge() {
		return logicMonthAccountAge;
	}

	public void setLogicMonthAccountAge(Integer logicMonthAccountAge) {
		this.logicMonthAccountAge = logicMonthAccountAge;
	}

	public Double getFirstThreeMonth() {
		return firstThreeMonth;
	}

	public void setFirstThreeMonth(Double firstThreeMonth) {
		this.firstThreeMonth = firstThreeMonth;
	}

	public Double getSecondThreeMonth() {
		return secondThreeMonth;
	}

	public void setSecondThreeMonth(Double secondThreeMonth) {
		this.secondThreeMonth = secondThreeMonth;
	}

	public Double getThridSixMonth() {
		return thridSixMonth;
	}

	public void setThridSixMonth(Double thridSixMonth) {
		this.thridSixMonth = thridSixMonth;
	}

	public Double getBlowOneYear() {
		return blowOneYear;
	}

	public void setBlowOneYear(Double blowOneYear) {
		this.blowOneYear = blowOneYear;
	}
}
