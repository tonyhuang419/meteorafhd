package com.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "OrderInfo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class OrderInfo extends PriEntity implements Serializable {
	private static final long serialVersionUID = -5144766674327857274L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	private Long  id;

	@ManyToOne ( cascade=CascadeType.ALL,  fetch = FetchType.LAZY ) 
	@JoinColumn(name="fkOrderId")
	private Orders fkOrderId;

	@Column(name = "bookDealPrice", length = 20 , scale = 2)
	private BigDecimal bookDealPrice;

	@Column(name = "sendAddress", length = 100)
	private String sendAddress;

	@ManyToOne ( cascade=CascadeType.ALL,  fetch = FetchType.LAZY )
	@JoinColumn(name="fkBookId")
	private Book fkBookId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Orders getFkOrderId() {
		return fkOrderId;
	}

	public void setFkOrderId(Orders fkOrderId) {
		this.fkOrderId = fkOrderId;
	}

	public BigDecimal getBookDealPrice() {
		return bookDealPrice;
	}

	public void setBookDealPrice(BigDecimal bookDealPrice) {
		this.bookDealPrice = bookDealPrice;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public Book getFkBookId() {
		return fkBookId;
	}

	public void setFkBookId(Book fkBookId) {
		this.fkBookId = fkBookId;
	}

	
	
}
