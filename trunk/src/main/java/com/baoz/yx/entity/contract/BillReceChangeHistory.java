package com.baoz.yx.entity.contract;

import java.io.Serializable;
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

//合同开票计划变更履历表

@Entity
@Table(name = "yx_con_bcplan_change_his")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BillReceChangeHistory implements Serializable {
	private static final long serialVersionUID = -1012710714958268727L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "81", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name = "billpro_change_his_sid", length = 20)
	private Long billproChangeHisSid; // 开票计划变更履历系统号

	@ManyToOne
	@JoinColumn(name = "fk_real_con_billpro_sid")
	private RealContractBillandRecePlan realContractBillandRecePlan; // 实际合同开票计划系统号

	@Temporal(TemporalType.DATE)
	@Column(name = "before_bill_date")
	private Date beforBillDate; // 变更前开票时间

	@Temporal(TemporalType.DATE)
	@Column(name = "after_bill_date")
	private Date afterChangeDate; // 变更后开票时间

	@Temporal(TemporalType.DATE)
	@Column(name = "before_rece_date")
	private Date beforReceDate; // 变更前收款时间

	@Temporal(TemporalType.DATE)
	@Column(name = "after_rece_date")
	private Date afterReceDate; // 变更后收款时间

	@Column(name = "change_exp", length = 500)
	private String changeExp; // 变更说明

	@Column(name = "change_time", length = 20)
	private Date changeTime; // 变更操作时间

	@Column(name = "change_people", length = 20)
	private Long changePeople; // 变更操作人

	public Long getBillproChangeHisSid() {
		return billproChangeHisSid;
	}

	public void setBillproChangeHisSid(Long billproChangeHisSid) {
		this.billproChangeHisSid = billproChangeHisSid;
	}

	public RealContractBillandRecePlan getRealContractBillandRecePlan() {
		return realContractBillandRecePlan;
	}

	public void setRealContractBillandRecePlan(
			RealContractBillandRecePlan realContractBillandRecePlan) {
		this.realContractBillandRecePlan = realContractBillandRecePlan;
	}

	public Date getBeforBillDate() {
		return beforBillDate;
	}

	public void setBeforBillDate(Date beforBillDate) {
		this.beforBillDate = beforBillDate;
	}

	public Date getAfterChangeDate() {
		return afterChangeDate;
	}

	public void setAfterChangeDate(Date afterChangeDate) {
		this.afterChangeDate = afterChangeDate;
	}

	public Date getBeforReceDate() {
		return beforReceDate;
	}

	public void setBeforReceDate(Date beforReceDate) {
		this.beforReceDate = beforReceDate;
	}

	public Date getAfterReceDate() {
		return afterReceDate;
	}

	public void setAfterReceDate(Date afterReceDate) {
		this.afterReceDate = afterReceDate;
	}

	public String getChangeExp() {
		return changeExp;
	}

	public void setChangeExp(String changeExp) {
		this.changeExp = changeExp;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Long getChangePeople() {
		return changePeople;
	}

	public void setChangePeople(Long changePeople) {
		this.changePeople = changePeople;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	

}
