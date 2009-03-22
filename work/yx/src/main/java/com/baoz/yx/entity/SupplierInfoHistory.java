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
@Table(name = "YX_SUPPLIER_INFO_HIS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class SupplierInfoHistory extends PriEntity implements Serializable {
	private static final long serialVersionUID = -6993156257640890684L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "ID", valueColumnName = "tableid", pkColumnValue = "supplierInfoHistory", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	
	@Column(name="ID",length = 20)
	private Long id;
	
	@Column(name = "SUP_ID", nullable = true, length = 20)
	private Long supId;       // 被修改供应商ID
	
	@Column(name = "TYPE", nullable = true, length = 50)
	private String type; // 变更类型名称
	
	@Column(name = "ORIGINAL", nullable = true, length = 500)
	private String original; // 原来内容

	@Column(name = "PRESENT", nullable = true, length = 500)
	private String present; // 现在内容
	
	@Column(name = "GROUP_ID", nullable = true, length = 20)
	private Long groupId;       // 同一次变更标识ID

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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}
	
	

	
}
