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

//红冲发票申请单关联计划

@Entity
@Table(name = "yx_hongchong_relaion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class HongChongRelationPlan implements java.io.Serializable{
	private static final long serialVersionUID = -2507862306246743822L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "90", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id")
	private Long id;  

	@Column(name = "fk_real_con_bcplan_sid")
	private Long realContractBillandRecePlan; //实际合同计划系统号
	

	@Column(name = "fk_apply_bill_sid")
	private Long hongChongApplyBill;			 //红冲发票申请单号
	
	@Column(name = "fk_relate_amount") 
	private Double relateAmount;	//每个计划中减去多少申请金额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRealContractBillandRecePlan() {
		return realContractBillandRecePlan;
	}

	public void setRealContractBillandRecePlan(Long realContractBillandRecePlan) {
		this.realContractBillandRecePlan = realContractBillandRecePlan;
	}

	public Long getHongChongApplyBill() {
		return hongChongApplyBill;
	}

	public void setHongChongApplyBill(Long hongChongApplyBill) {
		this.hongChongApplyBill = hongChongApplyBill;
	}

	public Double getRelateAmount() {
		return relateAmount;
	}

	public void setRelateAmount(Double relateAmount) {
		this.relateAmount = relateAmount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
