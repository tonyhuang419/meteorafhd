package com.baoz.yx.entity.contract.bak;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "yx_bak_con_item_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BakContractItemInfo implements Serializable{


	private static final long serialVersionUID = 7992258392322447639L;

	@Id
	@TableGenerator(name="sid",table="sysid",pkColumnName="id",valueColumnName="tableid",pkColumnValue="BakContractItemInfo",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="sid")
	@Column(name = "sid")
	private Long sid;//主键
	
	@Column(name="confirm_date")
	private Date confirmDate;//确认变更时间
	
	@Column(name = "confirm_people")
	private Long confirmPeople;//变更确认人
	
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
	
	@Column(name = "change_batch")
	private Long changeBatch;//变更批次

	public Long getChangeBatch() {
		return changeBatch;
	}

	public void setChangeBatch(Long changeBatch) {
		this.changeBatch = changeBatch;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Long getConfirmPeople() {
		return confirmPeople;
	}

	public void setConfirmPeople(Long confirmPeople) {
		this.confirmPeople = confirmPeople;
	}

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

	public BigDecimal getConItemAmountWithNoTax() {
		return conItemAmountWithNoTax;
	}

	public void setConItemAmountWithNoTax(BigDecimal conItemAmountWithNoTax) {
		this.conItemAmountWithNoTax = conItemAmountWithNoTax;
	}
}
