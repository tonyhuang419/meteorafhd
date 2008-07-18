package com.baoz.yx.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 通知()(YX_NOTICE)
 */
@Entity
@Table(name = "YX_NOTICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class Notice extends PriEntity implements Serializable {
	private static final long serialVersionUID = -8222092391619874532L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "7", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "CONTENT", nullable = true, length = 1000)
	private String content; // 公告内容
	
	@Column(name = "EMPLOYEEID", nullable = true, length = 20)
	private Long emplyeeId; // 通知人
	
	@Column(name = "TIME_START", nullable = true, length = 20)
	private Date timestart; // 开始时间
	
	@Column(name = "TIME_END", nullable = true, length = 20)
	private Date timeend; // 结束时间


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public Date getTimestart() {
		return timestart;
	}

	public void setTimestart(Date timestart) {
		this.timestart = timestart;
	}

	public Date getTimeend() {
		return timeend;
	}

	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}

	public Long getEmplyeeId() {
		return emplyeeId;
	}

	public void setEmplyeeId(Long emplyeeId) {
		this.emplyeeId = emplyeeId;
	}
	
}

