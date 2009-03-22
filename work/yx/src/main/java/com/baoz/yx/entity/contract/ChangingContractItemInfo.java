package com.baoz.yx.entity.contract;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "yx_changing_con_item_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ChangingContractItemInfo implements Serializable {

	private static final long serialVersionUID = 5602038236028748313L;

	@Id
	@Column(name="con_item_info_sid",length = 20)
	private Long conItemInfoSid;      //合同项目内容系统号
	
	@Column(name = "fk_con_item_minfo_sid")
	private Long contractItemMaininfo;  //合同项目主体信息系统号
	
	@Column(name = "con_item_amount_with_tax",length = 20,scale = 2)
	private BigDecimal conItemAmountWithTax; //金额
	
	@Column(name = "con_item_amount_with_notax",length = 20,scale = 2)
	private BigDecimal conItemAmountWithNoTax;///不含税金额
	
	@Column(name = "is_con_mod_info")
	private Boolean ConModInfo;  //是否属于合同变更信息
	
	@Column(name="item_contrent")
	private String itemcontrent;//金额名称 对应类型表  项目内容 //该字段在录入的时候没有插入值，该值从mainInfoPartId中的moneyType来
	
	@Column(name="item_mainInfoPart_Id")
	private Long mainInfoPartId;//合同费用表关联
	
	@Transient
	private ChangingContractItemMaininfo itemMainInfo;//这个是通过项目费用查询项目信息

	public Long getConItemInfoSid() {
		return conItemInfoSid;
	}

	public void setConItemInfoSid(Long conItemInfoSid) {
		this.conItemInfoSid = conItemInfoSid;
	}

	public Long getContractItemMaininfo() {
		return contractItemMaininfo;
	}

	public void setContractItemMaininfo(Long contractItemMaininfo) {
		this.contractItemMaininfo = contractItemMaininfo;
	}

	public BigDecimal getConItemAmountWithTax() {
		return conItemAmountWithTax;
	}

	public void setConItemAmountWithTax(BigDecimal conItemAmountWithTax) {
		this.conItemAmountWithTax = conItemAmountWithTax;
	}

	public Boolean getConModInfo() {
		return ConModInfo;
	}

	public void setConModInfo(Boolean conModInfo) {
		ConModInfo = conModInfo;
	}

	public String getItemcontrent() {
		return itemcontrent;
	}

	public void setItemcontrent(String itemcontrent) {
		this.itemcontrent = itemcontrent;
	}

	public Long getMainInfoPartId() {
		return mainInfoPartId;
	}

	public void setMainInfoPartId(Long mainInfoPartId) {
		this.mainInfoPartId = mainInfoPartId;
	}

	public ChangingContractItemMaininfo getItemMainInfo() {
		return itemMainInfo;
	}

	public void setItemMainInfo(ChangingContractItemMaininfo itemMainInfo) {
		this.itemMainInfo = itemMainInfo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getConItemAmountWithNoTax() {
		return conItemAmountWithNoTax;
	}

	public void setConItemAmountWithNoTax(BigDecimal conItemAmountWithNoTax) {
		this.conItemAmountWithNoTax = conItemAmountWithNoTax;
	}

}
