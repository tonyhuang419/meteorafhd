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


/**
 * <p>
 * 工程经济组成代码表
 * </p>
 */

@Entity
@Table(name = "yx_projecteconomycode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ProjectEconomyCode extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "21", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;
	
	@Column(name = "fareTypename", nullable = true, length = 20)
	private String fareTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFareTypeName() {
		return fareTypeName;
	}

	public void setFareTypeName(String fareTypeName) {
		this.fareTypeName = fareTypeName;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
