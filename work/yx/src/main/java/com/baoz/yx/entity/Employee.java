package com.baoz.yx.entity;

import java.io.Serializable;

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

@Entity
@Table(name = "yx_exployee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Employee extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "6", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	// @Column(name = "GROUP_ID", nullable = true, length = 20)
	// private Long groupId; //组别代码

	@Column(name = "WORKID", nullable = true, length = 20)
	private String 										workId; // 工号

	@Column(name = "NAME", nullable = true, length = 50)
	private String 										name;	//用户名称

	@Column(name = "CALLPHONE", nullable = true, length = 50)
	private String 										callPhone; // 手机

	@Column(name = "PHONE", nullable = true, length = 50)
	private String 										phone; // 电话

	@Column(name = "OTHERPHONE", nullable = true, length = 30)
	private String 										otherPhone; // 其他联系方式

	@Column(name = "EMAIL", nullable = true, length = 50)
	private String 										email; // email

	@Column(name = "ZZDM_ID", nullable = true, length = 20)
	private Long 										zzdmId;

	@Column(name = "username", nullable = true, length = 20)
	private String 										username;		//登陆用户名
	
	@Column(name = "password", nullable = true, length = 20)
	private String 										password;	//密码

	// 对应部门
	@Column(name = "position", nullable = true, length = 20)
	private Long 										position;

	@Column(name = "user_code", nullable = true, length = 18)
	private String 										userCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getZzdmId() {
		return zzdmId;
	}

	public void setZzdmId(Long zzdmId) {
		this.zzdmId = zzdmId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
