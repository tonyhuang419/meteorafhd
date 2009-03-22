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
@Table(name = "yx_contract_material_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractMaterialSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "ID", valueColumnName = "tableid", pkColumnValue = "19", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id",length = 19)
	private Long				id;	//id主键，自动增长
	
	@Column(name="CUSTOMER_NAME",length=19)
	private Long customerName;//客户名称
	
	@Column(name="CUSTOMER_PROJECT_TYPE",length=20)
	private String customerProjectType;//客户项目类型
	
	@Column(name="CUSTOMER_MATERIAL",length=100)
	private String customerMaterial;//应交材料列表。用逗号隔开

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Long customerName) {
		this.customerName = customerName;
	}

	public String getCustomerProjectType() {
		return customerProjectType;
	}

	public void setCustomerProjectType(String customerProjectType) {
		this.customerProjectType = customerProjectType;
	}

	public String getCustomerMaterial() {
		return customerMaterial;
	}

	public void setCustomerMaterial(String customerMaterial) {
		this.customerMaterial = customerMaterial;
	}
}
