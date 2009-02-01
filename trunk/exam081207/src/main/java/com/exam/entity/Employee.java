package com.exam.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Employee  extends PriEntity implements Serializable {
	private static final long serialVersionUID = 1828194845641757871L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 20)
	private Long id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "password", length = 100)
	private String password;

	@Column(name = "jobNum", length = 100)
	private String jobNum;

	@Column(name = "role", length = 2)
	private Long  role;

	@OneToMany(mappedBy="fkEmployeeId" ,
			cascade=CascadeType.ALL,   fetch = FetchType.LAZY )
	private Set<Orders> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}


}
