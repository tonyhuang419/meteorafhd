package com.baoz.yx.entity.contract.bak;

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

@Entity
@Table(name = "yx_bak_con_maininfo_part")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BakContractMaininfoPart implements Serializable {

	private static final long serialVersionUID = 7597925951408347503L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "BakContractMaininfoPart", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name = "sid")
	private Long sid;// 主键

	@Column(name = "confirm_date")
	private Date confirmDate;// 确认变更时间

	@Column(name = "confirm_people")
	private Long confirmPeople;// 变更确认人

	private Long id; // 系统号

	@Column(name = "contract_main_id")
	private Long contractmainid; // 主合同号

	@Column(name = "money", length = 20, scale = 2)
	private Double money; // 金额

	@Column(name = "money_type")
	private String moneytype;// 金额名称 对应类型表 项目内容

	@Column(name = "ticket_type")
	private String ticketType;// 开票类型
	
	@Column(name = "change_batch")
	private Long changeBatch;//变更批次

	public Long getChangeBatch() {
		return changeBatch;
	}

	public void setChangeBatch(Long changeBatch) {
		this.changeBatch = changeBatch;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Long getConfirmPeople() {
		return confirmPeople;
	}

	public void setConfirmPeople(Long confirmPeople) {
		this.confirmPeople = confirmPeople;
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

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
}
