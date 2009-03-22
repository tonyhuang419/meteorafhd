package com.baoz.yx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * 比较应收（）主要是从excel中导入到数据库中，并对数据库中的数据进行操作！
 * @author xusheng@baoz.com.cn
 *
 */
@Entity
@Table(name = "YX_IMPORT_DUE_FROM_COMPARE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ImportDueFromCompare implements Serializable{


	private static final long serialVersionUID = 1252825128L;
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "YX_IMPORT_DUE_FROM_COMPARE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long 	id; 
	
	@Column(name = "BASEDATE")
	private Date baseDate;////财务日期
	
	
	@Column(name = "CUSTOMERNAME")
	private String customerName;///客户名称
	
	@Column(name = "ORDERORITEMNO")
	private String orderOrItemNo;//订单/项目号
	
	@Column(name = "ITEMNAME")
	private String itemName;///项目名称
	
	@Column(name = "SALEMANNAME")
	private String saleManName;//销售人员
	
	@Column(name = "BILLFEE")
	private Double billFee;//发票余额
	
	@Column(name = "LOGICDAYACCOUNTAGE")
	private Double logicDayAccountAge;//帐龄(天数)
	
	@Column(name = "LOGICMONTHACCOUNTAGE")
	private Integer logicMonthAccountAge;//账龄(月)
	
	@Column(name = "FIRSTTHREEMONTH")
	private Double firstThreeMonth;//《=1-3个月
	
	@Column(name = "SECONDTHREEMONTH")
	private Double secondThreeMonth;//4-6个月
	
	@Column(name = "THIRDSIXMONTH")
	private Double thridSixMonth;//7-12个月
	
	@Column(name = "BLOWONEYEAR")
	private Double blowOneYear;//1年以上
	
	@Column( name = "OPPERSON")
	private Long opPerson;///操作人id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getOpPerson() {
		return opPerson;
	}

	public void setOpPerson(Long opPerson) {
		this.opPerson = opPerson;
	}
}
