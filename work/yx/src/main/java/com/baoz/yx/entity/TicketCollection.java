package com.baoz.yx.entity;

import java.util.List;

public class TicketCollection {
	private String id;///id自己赋值的
	private Double money;///金额
	private Long ticketId;///发票id
	private List<AssistanceTicket> at;///发票的对象
	private Double prepAmount;////关联的预付款金额
	public Double getPrepAmount() {
		return prepAmount;
	}
	public void setPrepAmount(Double prepAmount) {
		this.prepAmount = prepAmount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<AssistanceTicket> getAt() {
		return at;
	}
	public void setAt(List<AssistanceTicket> at) {
		this.at = at;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
}
