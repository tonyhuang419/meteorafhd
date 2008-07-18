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

import com.baoz.core.entity.PrimaryEntity;

/**
 * <p>
 * 用户求职意向/自我评价表
 * </p>
 * 
 * @author Chirs Zhou (chirs@zhoujin.com)
 */
@Entity
@Table(name = "yx_user_job_intent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class UserJobIntent extends PrimaryEntity implements Serializable {
	private static final long	serialVersionUID	= -14474935913692251L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "20", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long	id;

	@Column(name = "fk_user_id", nullable = true, length = 20)
	private Long	userId;

	@Column(name = "job_type", nullable = true, length = 20)
	private Long	jobType;

	@Column(nullable = true, length = 100)
	private String	areas;

	@Column(nullable = true, length = 100)
	private String	industrys;

	@Column(nullable = true, length = 100)
	private String	positions;

	@Column(nullable = true, length = 20)
	private Long	salary;

	@Column(name = "self_appraise", nullable = true, length = 200)
	private String	selfAppraise;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getJobType() {
		return jobType;
	}

	public void setJobType(Long jobType) {
		this.jobType = jobType;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getIndustrys() {
		return industrys;
	}

	public void setIndustrys(String industrys) {
		this.industrys = industrys;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public String getSelfAppraise() {
		return selfAppraise;
	}

	public void setSelfAppraise(String selfAppraise) {
		this.selfAppraise = selfAppraise;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


}
