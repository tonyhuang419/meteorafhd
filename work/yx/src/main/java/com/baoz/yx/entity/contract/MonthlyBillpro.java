package com.baoz.yx.entity.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

//月度开票计划表
@Entity
@Table(name = "yx_monthly_billpro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class MonthlyBillpro implements java.io.Serializable {

	private static final long serialVersionUID = -7230747775965548603L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "84", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="monthly_billpro_sid", length = 20)
	private Long monthlyBillproSid; //合同月度开票计划系统号
	
	@ManyToOne
	@JoinColumn(name = "fk_real_con_billpro_sid", nullable = false)
	private RealContractBillandRecePlan realContractBillandRecePlan;  //实际合同开票计划系统号

	@Temporal(TemporalType.DATE)
	@Column(name="billpro_month", nullable = false)
	private Date billproMonth;   //开票计划月份
	
	@Column(name="bill_plan_date",nullable = false)
	private Date billPlanDate; //生成计划时，对应的预计开票日期
	
	@Column(name="bill_amount",nullable = false)
	private Double billAmount; //生成计划时，对应的开票金额（基准）
	
	@Column(name="bill_tax_amount",nullable = false)
	private Double billTaxAmount; //生成计划时，对应的开票金额（含税）
	
	@Column(name="pro_create_date" ,nullable = false)
	private Date proCreateDate;//计划创建时间

	@Column(name="actual_bill_date")
	private Date actualBillDate;// 实际开票日期
	
	@Column(name="is_inside_plan", nullable = false)
	private Integer isInsidePlan;				//是否则计划内外   0 内  1 外
	
	@Column(name="plan_bill_amount")
	private Double planBillAmount;				//已开票金额
	
	public Long getMonthlyBillproSid() {
		return monthlyBillproSid;
	}

	public void setMonthlyBillproSid(Long monthlyBillproSid) {
		this.monthlyBillproSid = monthlyBillproSid;
	}

	public RealContractBillandRecePlan getRealContractBillandRecePlan() {
		return realContractBillandRecePlan;
	}

	public void setRealContractBillandRecePlan(
			RealContractBillandRecePlan realContractBillandRecePlan) {
		this.realContractBillandRecePlan = realContractBillandRecePlan;
	}

	public Date getBillproMonth() {
		return billproMonth;
	}

	public void setBillproMonth(Date billproMonth) {
		this.billproMonth = billproMonth;
	}





	public Date getProCreateDate() {
		return proCreateDate;
	}

	public void setProCreateDate(Date proCreateDate) {
		this.proCreateDate = proCreateDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getBillPlanDate() {
		return billPlanDate;
	}

	public void setBillPlanDate(Date billPlanDate) {
		this.billPlanDate = billPlanDate;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Double getBillTaxAmount() {
		return billTaxAmount;
	}

	public void setBillTaxAmount(Double billTaxAmount) {
		this.billTaxAmount = billTaxAmount;
	}

	public Date getActualBillDate() {
		return actualBillDate;
	}

	public void setActualBillDate(Date actualBillDate) {
		this.actualBillDate = actualBillDate;
	}

	public Integer getIsInsidePlan() {
		return isInsidePlan;
	}

	public void setIsInsidePlan(Integer isInsidePlan) {
		this.isInsidePlan = isInsidePlan;
	}

	public Double getPlanBillAmount() {
		return planBillAmount;
	}

	public void setPlanBillAmount(Double planBillAmount) {
		this.planBillAmount = planBillAmount;
	}


	
}