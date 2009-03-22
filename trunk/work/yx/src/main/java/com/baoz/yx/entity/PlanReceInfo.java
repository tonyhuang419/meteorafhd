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
 * 类PlanReceInfo.java的实现描述：开票收款计划的收款日期
 * @author xurong Sep 18, 2008 5:51:42 PM
 */
@Entity
@Table(name = "yx_plan_rece_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class PlanReceInfo implements Serializable{
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "13", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;
	
	@Column(name = "plan_id")
	private Long				planId;                   	    //开票收款计划系统号
	
	@Column(name = "reve_info_id")
	private Long				reveInfoId;                   	//项目收款信息系统号
	
	@Column(name = "amount",length=20,scale = 2)
	private Double				amount;							//到款金额
		
	@Column(name = "amount_time" )
	private Date				amountTime;						//到款时间

	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getReveInfoId() {
		return reveInfoId;
	}

	public void setReveInfoId(Long reveInfoId) {
		this.reveInfoId = reveInfoId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Date amountTime) {
		this.amountTime = amountTime;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}
	
}
