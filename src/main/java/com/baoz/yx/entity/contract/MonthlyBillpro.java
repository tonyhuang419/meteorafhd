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
	
	@Column(name="pro_create_date",length=20, nullable = false)
	private Date proCreateDate;//计划创建时间

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

	
}