package com.baoz.yx.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PriEntity {

	@Column(length = 20)
	private Date updateBy;   //最后修改时间
	@Column(length = 20)
	private Long byId; //最后修改人

	@Column(nullable = true, length = 2)
	private String is_active;
   

	public Date getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Date updateBy) {
		this.updateBy = updateBy;
	}

	public Long getById() {
		return byId;
	}

	public void setById(Long byId) {
		this.byId = byId;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	
}
