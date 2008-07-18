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
 * 客户代码表(KHDM)(
 * 6月5日更新：增加IS_EVENTUNIT字段
 */
@Entity
@Table(name = "yx_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class YXClientCode extends PriEntity implements Serializable {
	private static final long serialVersionUID = -8222092391619874531L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "8", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id;

	@Column(name = "NAME", nullable = true, length = 100)
	private String name; // 客户名称

	@Column(name = "KHXZ_ID", nullable = true, length = 20)
	private String clientNID; // 客户性质代码

	@Column(name = "HYLX_ID", nullable = true, length = 20)
	private String businessID; // 行业类型代码

	@Column(name = "ADDRESS", nullable = true, length = 100)
	private String address; // 客户联系地址

	@Column(name = "BILL_NAME", nullable = true, length = 50)
	private String billName; // 客户开票名称

	@Column(name = "BILL_BANK", nullable = true, length = 100)
	private String billBank; // 客户开户银行

	@Column(name = "BILL_ACCOUNT", nullable = true, length = 100)
	private String account; // 开户帐号

	@Column(name = "TAXES_NUMBER", nullable = true, length = 100)
	private String taxNumber; // 税号

	@Column(name = "BILL_ADDRESS", nullable = true, length = 100)
	private String billAdd; // 客户开票地址

	@Column(name = "BILL_PHONE", nullable = true, length = 50)
	private String billPhone; // 客户开票电话

	@Column(name = "AREA_ID", nullable = true, length = 20)
	private String areaID; // 客户地域代码

	@Column(name="IS_EVENTUNIT",nullable = true,length=2)
	private String isEventUnit;//是否项目单位
	
	@Column(name = "HYSC_ID", nullable = true, length = 20)
	private String businessAreaID; // 行业市场代码
	
	@Column(name="USER_CODE",nullable = true,length=20)
	private String userCode;//ERP编号

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientNID() {
		return clientNID;
	}

	public void setClientNID(String clientNID) {
		this.clientNID = clientNID;
	}

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillBank() {
		return billBank;
	}

	public void setBillBank(String billBank) {
		this.billBank = billBank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getBillAdd() {
		return billAdd;
	}

	public void setBillAdd(String billAdd) {
		this.billAdd = billAdd;
	}

	public String getBillPhone() {
		return billPhone;
	}

	public void setBillPhone(String billPhone) {
		this.billPhone = billPhone;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getIsEventUnit() {
		return isEventUnit;
	}

	public void setIsEventUnit(String isEventUnit) {
		this.isEventUnit = isEventUnit;
	}

	public String getBusinessAreaID() {
		return businessAreaID;
	}

	public void setBusinessAreaID(String businessAreaID) {
		this.businessAreaID = businessAreaID;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
	
}
