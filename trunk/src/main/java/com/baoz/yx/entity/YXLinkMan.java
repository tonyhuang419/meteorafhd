
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
 * @author Administrator
 *
 */
@Entity
@Table(name = "yx_client_linkman")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXLinkMan extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "7", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;

	@Column(name = "CLIENT_ID", nullable = true, length = 20)
	private Long				clientId;									//所属客户代码

	@Column(name = "LINKMAN_NAME", nullable = true, length = 100)
	private String				name;									//联系人名称

	@Column(name = "ADDRESS", nullable = true, length = 100)
	private String				address;

	@Column(name = "CALLPHONE", nullable = true, length = 50)
	private String				callPhone;									//手机

	@Column(name = "PHONE", nullable = true, length = 50)
	private String				phone;										//电话

	@Column(name = "OTHER_PHONE", nullable = true, length = 30)
	private String				otherPhone;								//其他联系方式


	@Column(name = "NATRUE_ID", nullable = true, length = 20)
	private String					natureId;//联系人性质代码
	
	@Column(name = "EMAIL", nullable = true, length = 50)//邮件地址
	private String				email;


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

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNatureId() {
		return natureId;
	}

	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
