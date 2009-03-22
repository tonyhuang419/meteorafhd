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

//月度收款计划表
@Entity
@Table(name = "yx_monthly_recepro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class MonthlyRecepro implements java.io.Serializable {

	private static final long serialVersionUID = 2353780720986141730L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "83", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="monthly_recepro_sid", length = 20)
	private Long monthlyReceproSid; //月度收款计划系统号
	
	@ManyToOne
	@JoinColumn(name = "fk_real_con_recepro_sid", nullable = false)
	private RealContractBillandRecePlan realContractBillandRecePlan;  //实际合同收款计划系统号

	@Temporal(TemporalType.DATE)
	@Column(name="billpro_month", nullable = false)
	private Date billproMonth;  //收款计划月份
	
	@Column(name="pro_create_date", nullable = false)
	private Date proCreateDate; //计划创建时间
	
	@Column(name="pro_create_peo", nullable = false)
	private Long proCreatePeo;  //计划创建人
	
	@Column(name="rece_plan_date", nullable = false)
	private Date recePlanDate; //生成计划时，对应的预计收款日期
	
	@Column(name="rece_tax_amount", nullable = false)
	private Double receTaxAmount; //生成计划时，对应的计划内收款金额（含税）
	
	@Column(name="actual_arrived_date")
	private Date actualArrivedDate;// 实际到款日期
	
	@Column(name="is_inside_plan", nullable = false)
	private Integer isInsidePlan; //是否则计划内外 0 内  1 外
	
	@Column(name="already_arrived_amount")
	private Double alreadyArrivedAmount; //本计划内到款金额，可能分两个月到款
	
	public Date getRecePlanDate() {
		return recePlanDate;
	}

	public void setRecePlanDate(Date recePlanDate) {
		this.recePlanDate = recePlanDate;
	}

	public Double getReceTaxAmount() {
		return receTaxAmount;
	}

	public void setReceTaxAmount(Double receTaxAmount) {
		this.receTaxAmount = receTaxAmount;
	}

	public Long getMonthlyReceproSid() {
		return monthlyReceproSid;
	}

	public void setMonthlyReceproSid(Long monthlyReceproSid) {
		this.monthlyReceproSid = monthlyReceproSid;
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



	public Date getProCreateDate() {
		return proCreateDate;
	}

	public void setBillproMonth(Date billproMonth) {
		this.billproMonth = billproMonth;
	}

	public void setProCreateDate(Date proCreateDate) {
		this.proCreateDate = proCreateDate;
	}

	public Long getProCreatePeo() {
		return proCreatePeo;
	}

	public void setProCreatePeo(Long proCreatePeo) {
		this.proCreatePeo = proCreatePeo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getActualArrivedDate() {
		return actualArrivedDate;
	}

	public void setActualArrivedDate(Date actualArrivedDate) {
		this.actualArrivedDate = actualArrivedDate;
	}

	public Integer getIsInsidePlan() {
		return isInsidePlan;
	}

	public void setIsInsidePlan(Integer isInsidePlan) {
		this.isInsidePlan = isInsidePlan;
	}

	public Double getAlreadyArrivedAmount() {
		return alreadyArrivedAmount;
	}

	public void setAlreadyArrivedAmount(Double alreadyArrivedAmount) {
		this.alreadyArrivedAmount = alreadyArrivedAmount;
	}

	

}