package com.fhd.poll.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity@Table(name = "Poll",
		uniqueConstraints = {@UniqueConstraint(columnNames={"MJName"})})
public class Poll
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "MJID")
	private int id;
	
	@Column(name = "MJName",length = 20) 
	private String name;
	
	@Column(name = "MJMaxPrice") 
	private long price;
	
	@Column(name = "deal",columnDefinition="BOOLEAN")
	private boolean deal;
	

	public Poll(){
		//this.deal = false;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

	public boolean isDeal() {
		return deal;
	}
	public void setDeal(boolean deal) {
		this.deal = deal;
	}

}

