package com.exam.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Orders extends PriEntity implements Serializable {
	private static final long serialVersionUID = -307737562389872475L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	private Long  id;

	@Column(name = "orderNum", length = 100)
	private String orderNum;

	@Column(name = "customerName", length = 100)
	private String customerName;

	@Temporal(TemporalType.TIMESTAMP)  
	@Column(name = "orderDate")
	private Date orderDate;

	@Temporal(TemporalType.TIME)  
	@Column(name = "sendDate")
	private Date sendDate;
	
	@OneToMany(mappedBy="fkOrderId" , 
			cascade=CascadeType.ALL,  fetch = FetchType.LAZY )
	private Set<OrderInfo> orderInfo;

	@ManyToOne ( cascade=CascadeType.ALL,  fetch = FetchType.LAZY ) 
	@JoinColumn(name="fkCustomerId")
	private Customer fkCustomerId;
	
	@ManyToOne ( cascade=CascadeType.ALL,  fetch = FetchType.LAZY ) 
	@JoinColumn(name="fkEmployeeId")
	private Employee fkEmployeeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Customer getFkCustomerId() {
		return fkCustomerId;
	}

	public void setFkCustomerId(Customer fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}

	public Employee getFkEmployeeId() {
		return fkEmployeeId;
	}

	public void setFkEmployeeId(Employee fkEmployeeId) {
		this.fkEmployeeId = fkEmployeeId;
	}

	public Set<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Set<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

}
