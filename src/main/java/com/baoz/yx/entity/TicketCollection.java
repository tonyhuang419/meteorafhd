package com.baoz.yx.entity;

import java.util.List;

public class TicketCollection {
	private String id;
	private Double money;
	private Long ticketId;
	private List<AssistanceTicket> at;
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
