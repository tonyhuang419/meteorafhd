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

//重点工程项目
@Entity
@Table(name = "YX_IMPORTANT_PROJECT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ImportantProject extends PriEntity implements Serializable {
	private static final long serialVersionUID = -5587253410878939483L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "importantProject", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "PROJECT_NUM", nullable = true, length = 100)
	private String 		projectNum; // 工程编号
	
	@Column(name = "CUSTOMER_ID", nullable = true, length = 20)
	private Long 			customerId; // 客户id
	
	@Column(name = "PROJECT_NAME",length = 100)
	private String				projectName;					//工程名称
	
	@Column(name="CREATE_TIME" )
	private Date    createTime;    //创建时间
	
	@Column(name="CREATE_EMPLOYEE_ID" ,  length = 20)
	private Long createEmployeeID; 				//创建人id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateEmployeeID() {
		return createEmployeeID;
	}

	public void setCreateEmployeeID(Long createEmployeeID) {
		this.createEmployeeID = createEmployeeID;
	}
	
}
