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
 * <p>
 * 外协供应商信息表
 * </p>
 */
@Entity
@Table(name = "yx_supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class SupplierInfo extends PriEntity implements Serializable {
 
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "21", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long supplierid;
	
	@Column(name = "SUPPLIERNAME", length = 50)
	private String supplierName; // 供应商名称
	
	@Column(name = "SUPPLIERCODE",length=20)
	private String supplierCode;//供应商代码
	
	@Column(name="BILLBANK",length=50)
	private String billBank;//开户银行
	
	@Column(name="NAMEOFINOVICE",length=50)
	private String nameOfInovice;//开票名称
	
	@Column(name="ADDRESSOFINVOICE")
	private String addressOfInovice;//开票地址
	
	@Column(name="BILLACCOUNT",length=20)
	private String billAccount;//开户帐号
	
	@Column(name="DUTYPARAGRAPH",length=20)
	private String dutyParagraph;//税号
	
	@Column(name="PHONEOFINVOICE",length=20)
	private String phoneOfInovice;//开票电话
	
	@Column(name="AREACODE",length=20)
	private Long eareCode;//地域代码
	
	@Column(name="SUPPLIERTYPE",length=20)
	private String supplierType;//供应商类别


	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getBillBank() {
		return billBank;
	}

	public void setBillBank(String billBank) {
		this.billBank = billBank;
	}

	public String getNameOfInovice() {
		return nameOfInovice;
	}

	public void setNameOfInovice(String nameOfInovice) {
		this.nameOfInovice = nameOfInovice;
	}

	public String getAddressOfInovice() {
		return addressOfInovice;
	}

	public void setAddressOfInovice(String addressOfInovice) {
		this.addressOfInovice = addressOfInovice;
	}

	public String getBillAccount() {
		return billAccount;
	}

	public void setBillAccount(String billAccount) {
		this.billAccount = billAccount;
	}

	public String getDutyParagraph() {
		return dutyParagraph;
	}

	public void setDutyParagraph(String dutyParagraph) {
		this.dutyParagraph = dutyParagraph;
	}

	public String getPhoneOfInovice() {
		return phoneOfInovice;
	}

	public void setPhoneOfInovice(String phoneOfInovice) {
		this.phoneOfInovice = phoneOfInovice;
	}

	public Long getEareCode() {
		return eareCode;
	}

	public void setEareCode(Long eareCode) {
		this.eareCode = eareCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	
	
	
}
