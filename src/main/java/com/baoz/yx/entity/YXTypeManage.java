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
@Table(name = "yx_type_manage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXTypeManage extends PriEntity implements Serializable {
	private static final long serialVersionUID = -4050908306322296801L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "40", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "TYPE_BIG", nullable = true, length = 20)
	private Long typeBig;

	@Column(name = "TYPE_SMALL", nullable = true, length = 20)
	private String typeSmall;

	@Column(name = "TYPE_NAME", nullable = true, length = 30)
	private String typeName;

	@Column(name = "INFO", nullable = true, length = 20)
	private String info;
	
	@Column(name = "ORDER_NUM", nullable = true, length = 10)
	private String orderNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getTypeBig() {
		return typeBig;
	}

	public void setTypeBig(Long typeBig) {
		this.typeBig = typeBig;
	}

	public String getTypeSmall() {
		return typeSmall;
	}

	public void setTypeSmall(String typeSmall) {
		this.typeSmall = typeSmall;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
