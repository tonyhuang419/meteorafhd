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
 * 
 * @author shaoyx
 * 
 * Jun 15, 2008, 1:07:36 PM
 * 
 * todo:
 * <p>
 * 组织树
 * </p>
 */

@Entity
@Table(name = "yx_organization_tree")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class OrganizationTree extends PriEntity implements Serializable {
	private static final long serialVersionUID = 5137810645137075511L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "53", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(nullable = true, length = 20)
	private Long fid;

	@Column(name="organization_code", nullable = true, length = 20)
	private String organizationCode;

	@Column(name = "organization_name", nullable = true, length = 200)
	private String organizationName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

}
