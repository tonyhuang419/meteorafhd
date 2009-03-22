package com.baoz.yx.entity.contract;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Column(name="id",length = 20)
    private Long id;      //系统号
	
	@Column(name = "contract_main_id")
	private Long contractmainid;  //主合同号
	
	@Column(name = "money",length = 20,scale = 2)
	private Double money; //金额（含税）
	
	
	@Column(name="money_type")
	private String moneytype;//金额名称 对应类型表  项目内容 
	
	@Column(name="ticket_type")
	private String ticketType;//开票类型
	
	@Transient
	private List<ChangingContractItemInfo> itemInfoList;//这个变量是用来在循环的时候显示项目费用不直接和数据库表中的字段进行交互
	

	public List<ChangingContractItemInfo> getItemInfoList() {
		return itemInfoList;
	}


	public void setItemInfoList(List<ChangingContractItemInfo> itemInfoList) {
		this.itemInfoList = itemInfoList;
	}


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


	public String getTicketType() {
		return ticketType;
	}


	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}


	
	
}
