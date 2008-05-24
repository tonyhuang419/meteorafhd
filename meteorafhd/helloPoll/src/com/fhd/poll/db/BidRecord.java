package com.fhd.poll.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "BidRecord")
public class BidRecord
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "BidID") 
	private int id;

	@Column(name = "MJID")
	private int mjid;

	@Column(name = "Price") 
	private long price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BidDate") 
	private Date bidDate;

	@Column(name = "Whobuy",length = 20)
	private String whoBuy;


	public BidRecord(){
	}


	public String getWhoBuy() {
		return whoBuy;
	}

	public void setWhoBuy(String whoBuy) {
		this.whoBuy = whoBuy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public int getMjid() {
		return mjid;
	}

	public void setMjid(int mjid) {
		this.mjid = mjid;
	}
}

