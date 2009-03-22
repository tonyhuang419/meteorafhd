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
 * 公告()(YX_GONGGAO)
 */
@Entity
@Table(name = "YX_GONGGAO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Gonggao extends PriEntity implements Serializable {
	private static final long serialVersionUID = -8222092391619874532L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "7", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "CONTENT", nullable = true, length = 2000)
	private String content; // 公告内容
	
	@Column(name = "G_DAYS", nullable = true, length = 20)
	private Long gdays; // 公告天数
	
	@Column(name = "DEL_LEVEL", nullable = true)
	private Boolean  delLevel;  //只有系统管理员才能删除
	
	@Column(name = "TOP")
	private Boolean top;   //置顶

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getGdays() {
		return gdays;
	}

	public void setGdays(Long gdays) {
		this.gdays = gdays;
	}

	public Boolean getDelLevel() {
		return delLevel;
	}

	public void setDelLevel(Boolean delLevel) {
		this.delLevel = delLevel;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

}
