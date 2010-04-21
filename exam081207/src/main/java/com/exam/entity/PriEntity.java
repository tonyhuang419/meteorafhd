package com.exam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class PriEntity {

	@Column(updatable=false, length=20)
	private Long created;

	@Temporal(TemporalType.TIMESTAMP)  
	@Column(updatable=false)
	private Date createdby;

	@Column(length=20)
	private Long updated;

	@Temporal(TemporalType.TIMESTAMP)  
	private Date updatedby;

	@Column(nullable=true, length=2)
	private String isActive;

	public PriEntity() {}
	
	public PriEntity(Long created, Date createdby, Long updated,
			Date updatedby, String isActive) {
		super();
		this.created = created;
		this.createdby = createdby;
		this.updated = updated;
		this.updatedby = updatedby;
		this.isActive = isActive;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Date getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Date createdby) {
		this.createdby = createdby;
	}

	public Long getUpdated() {
		return updated;
	}

	public void setUpdated(Long updated) {
		this.updated = updated;
	}

	public Date getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Date updatedby) {
		this.updatedby = updatedby;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
