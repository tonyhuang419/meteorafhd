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
@Table(name = "YX_ZYCP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class SelfProduction extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "16", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;
	@Column(name = "PID", nullable = true, length = 50)
	private String				pid;  //产品代码
	@Column(name = "PNAME", nullable = true, length = 100)
	private String				registerName;  //注册名称
	@Column(name = "GRADE", nullable = true, length = 100)
	private String				certificateNum;  //证书标号
	@Column(name = "RDATE", nullable = true, length = 20 )
	private Date				registerDate;   //注册日期
	@Column(name = "YXQ", nullable = true, length = 50)
	private Date				validateTime;  //有效期
	
/*	
	@Column(name = "REGISTER_NAME", nullable = true, length = 100)
	private String				registerName;
	@Column(name = "CERTIFICATE_NUM", nullable = true, length = 100)
	private String				certificateNum;
	@Column(name = "REGISTER_DATE", nullable = true)
	private Date				registerDate;
	@Column(name = "VALIDATE_TIME", nullable = true, length = 50)
	private String				validateTime;
	@Column(name = "IS_VALIDATE", nullable = true, length = 2)
	private int				isValidate;
	@Column(name = "MODIFY_TIME")
	private Date				modifyTime;
	@Column(name = "MODIFY_PEOPLE", nullable = true, length = 20)
	private String				modifyPeople;
*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getValidateTime() {
		return validateTime;
	}
	public void setValidateTime(Date validateTime) {
		this.validateTime = validateTime;
	}

/*	public int getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(int isValidate) {
		this.isValidate = isValidate;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyPeople() {
		return modifyPeople;
	}
	public void setModifyPeople(String modifyPeople) {
		this.modifyPeople = modifyPeople;
	}
*/	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
