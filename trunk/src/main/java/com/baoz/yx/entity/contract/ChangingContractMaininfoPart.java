package com.baoz.yx.entity.contract;

import java.math.BigDecimal;

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

@Entity
@Table(name = "YX_CHANGING_CON_MAININFO_PART")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ChangingContractMaininfoPart implements java.io.Serializable {
	private static final long serialVersionUID = 5602038236028748313L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "75", allocationSize = 1)
	@Column(name="id",length = 20)
	private Long id;      //系统号
	
	@Column(name = "contract_main_id")
	private Long contractmainid;  //主合同号
	
	@Column(name = "money",length = 20,scale = 2)
	private Double money; //金额（含税）
	
	
	@Column(name="money_type")
	private String moneytype;//金额名称 对应类型表  项目内容 


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getContractmainid() {
		return contractmainid;
	}


	public void setContractmainid(Long contractmainid) {
		this.contractmainid = contractmainid;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public String getMoneytype() {
		return moneytype;
	}


	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	
	
}
