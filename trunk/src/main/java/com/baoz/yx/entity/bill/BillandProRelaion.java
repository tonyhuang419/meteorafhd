package com.baoz.yx.entity.bill;

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

//开票申请开票计划关联表

@Entity
@Table(name = "yx_billandpro_relaion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class BillandProRelaion implements java.io.Serializable{
	private static final long serialVersionUID = -2507862306246743822L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "86", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="billandpro_relaion_sid", nullable = false)
	private Long billandproRelaionSid;  //开票申请开票收款计划关联系统号
	

	@Column(name = "fk_real_con_bcplan_sid")
	private Long realContractBillandRecePlan; //实际合同开票收款计划系统号
	

	@Column(name = "fk_apply_bill_sid")
	private Long applyBill;			 //实际合同开票申请系统号
	
	@Column(name = "fk_relate_amount") 
	private Double relateAmount;			 //实际合同开票申请系统号,这个属性好像用不到，因为这个金额就是开票申请中的申请金额


	public Double getRelateAmount() {
		return relateAmount;
	}


	public void setRelateAmount(Double relateAmount) {
		this.relateAmount = relateAmount;
	}


	public Long getBillandproRelaionSid() {
		return billandproRelaionSid;
	}


	public void setBillandproRelaionSid(Long billandproRelaionSid) {
		this.billandproRelaionSid = billandproRelaionSid;
	}


	public Long getRealContractBillandRecePlan() {
		return realContractBillandRecePlan;
	}


	public void setRealContractBillandRecePlan(Long realContractBillandRecePlan) {
		this.realContractBillandRecePlan = realContractBillandRecePlan;
	}


	public Long getApplyBill() {
		return applyBill;
	}


	public void setApplyBill(Long applyBill) {
		this.applyBill = applyBill;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	

}
