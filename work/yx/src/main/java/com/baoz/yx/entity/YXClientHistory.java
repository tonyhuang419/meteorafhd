package com.baoz.yx.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.util.Date;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * 客户代码变更表(KHDM)(
 * 
 */
@Entity
@Table(name = "yx_client_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXClientHistory implements Serializable {
	private static final long serialVersionUID = -8222092391619874531L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "8", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "TYPE", nullable = true, length = 50)
	private String type; // 变更类型名称
	
	@Column(name = "ORIGINAL", nullable = true, length = 100)
	private String original; // 原来名称

	@Column(name = "PRESENT", nullable = true, length = 100)
	private String present; // 现在名称

	@Column(name = "UPDATEBY", nullable = true, length = 20)
	private Date updateby; // 变更日期

	@Column(name = "BYID", nullable = true, length = 20)
	private Long byId;       // 变更ID
	
	@Column(name = "BYNAME", nullable = true, length = 50)
	private String byName; // 变更姓名
	
	@Column(name = "CLIENT_ID", nullable = true, length = 20)
	private Long clientId;       // 变更客户ID
	
	@Column(name = "GROUP_ID", nullable = true, length = 20)
	private Long groupId;       // 同一次变更标识ID

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public Long getById() {
		return byId;
	}

	public void setById(Long byId) {
		this.byId = byId;
	}

	public String getByName() {
		return byName;
	}

	public void setByName(String byName) {
		this.byName = byName;
	}

	public Date getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Date updateby) {
		this.updateby = updateby;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}


	
}
