package com.baoz.yx.entity;

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

/**
 * 客户联系人代码表(KHLXR)(YX_CLIENT_LINKMAN)
 */
@Entity
@Table(name = "yx_client_linkman")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ClientLinkMan extends PriEntity implements Serializable {
	private static final long serialVersionUID = -8222092391619874531L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "7", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "NAME", nullable = true, length = 100)
	private String name; // 联系人名称

	@Column(name = "CLIENT_ID", nullable = true, length = 20)
	private Long clientID; // 所属客户代码

	@Column(name = "PHONE", nullable = true, length = 50)
	private String phone;// 电话

	@Column(name = "CALLPHONE", nullable = true, length = 50)
	private String callPhone;// 手机

	@Column(name = "OTHER_PHONE", nullable = true, length = 30)
	private String otherPhone;// 其他联系方式

	@Column(name = "ADDRESS", nullable = true, length = 100)
	private String address;// 联系地址

	@Column(name = "NATRUE_ID", nullable = true, length = 20)
	private Long natureID; // 联系人性质代码

	@Column(name = "EMAIL", nullable = true, length = 20)
	private String email; // 联系人email
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public Long getClientID() {
		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getNatureID() {
		return natureID;
	}

	public void setNatureID(Long natureID) {
		this.natureID = natureID;
	}
}
