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
@Entity
@Table(name = "YX_FILE_MANAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXFileManage implements Serializable {
	private static final long	serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "16", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;
	@Column(name = "FILE_NAME", nullable = true, length = 100)
	private String				filename;
	@Column(name = "FILE_ROUTE", nullable = true, length = 100)
	private String				fileroute;
	@Column(name = "PERSON_CODE", nullable = true, length = 20)
	private Long				personcode;
	@Column(name = "FILE_TYPE", nullable = true, length = 20)
	private String				filetype;
	@Column(name = "CLIENT_CODE", nullable = true, length = 20)
	private Long				clientcode;	
	@Column(name = "REMARKS", nullable = true, length = 500)
	private String				remarks;
	@Column(name = "FILE_SIZE", nullable = true, length = 10 )
	private Long			    filesize;
	@Column(name = "FILE_DATE", nullable = true, length = 50)
	private Date				filedate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileroute() {
		return fileroute;
	}
	public void setFileroute(String fileroute) {
		this.fileroute = fileroute;
	}
	public Long getPersoncode() {
		return personcode;
	}
	public void setPersoncode(Long personcode) {
		this.personcode = personcode;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public Long getClientcode() {
		return clientcode;
	}
	public void setClientcode(Long clientcode) {
		this.clientcode = clientcode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public Date getFiledate() {
		return filedate;
	}
	public void setFiledate(Date filedate) {
		this.filedate = filedate;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
