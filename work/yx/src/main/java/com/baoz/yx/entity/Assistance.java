package com.baoz.yx.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
@Entity
@Table(name = "yx_assistance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Assistance extends PriEntity implements Serializable{

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "18", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;												//关联系统号
	@Column(name = "ASSISTANCE_PAY_ID", nullable = true, length = 20)
	private Long				assistancePayId;								//外协付款系统号
	
	@ManyToOne
	@JoinColumn(name = "ASSISTANCE_TICKET_ID")									//外协发票系统号
	private AssistanceTicket 	ticket;

	@Column(name = "CON_MONEY")
	private Double				conMoney;										//关联本次支付金额
	
	
	/**
	 * 特别说明：该字段目前存的是发票和付款申请的关联金额不是预付款的金额
	 */
	@Column(name = "RELATION_PREP_PAY_AMOUNT")
	private Double               relationPrepPayAmount;                        //关联预付款金额
	
	@Column(name = "IS_FROM_SYS")
	private Boolean isFromSys;///是否系统导入
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAssistancePayId() {
		return assistancePayId;
	}
	public void setAssistancePayId(Long assistancePayId) {
		this.assistancePayId = assistancePayId;
	}
	public AssistanceTicket getTicket() {
		return ticket;
	}
	public void setTicket(AssistanceTicket ticket) {
		this.ticket = ticket;
	}
	public Double getConMoney() {
		return conMoney;
	}
	public void setConMoney(Double conMoney) {
		this.conMoney = conMoney;
	}
	public Double getRelationPrepPayAmount() {
		return relationPrepPayAmount;
	}
	public void setRelationPrepPayAmount(Double relationPrepPayAmount) {
		this.relationPrepPayAmount = relationPrepPayAmount;
	}
	public Boolean getIsFromSys() {
		return isFromSys;
	}
	public void setIsFromSys(Boolean isFromSys) {
		this.isFromSys = isFromSys;
	}
	
}
