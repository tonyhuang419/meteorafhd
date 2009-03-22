package com.baoz.yx.entity.contract.bak;

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
@Table(name = "yx_bak_con_item_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BakContractItemStage implements Serializable{

	
	private static final long serialVersionUID = -8614522445114627496L;

	@Id
	@TableGenerator(name="sid",table="sysid",pkColumnName="id",valueColumnName="tableid",pkColumnValue="BakContractItemStage",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="sid")
	@Column(name = "sid")
	private Long sid;//主键
	
	@Column(name="confirm_date")
	private Date confirmDate;//确认变更时间
	
	@Column(name = "confirm_people")
	private Long confirmPeople;//变更确认人
	
	@Column(name="con_item_stage_sid")
	private Long conIStageSid;  //合同项目阶段系统号
	
	@Column(name = "fk_con_main_info_sid")
	private Long contractMainSid;  //合同主体信息系统号
	
	@Column(name = "item_stage_name", length=100 )
	private String itemStageName;  //项目阶段名称

	@Column(name = "last_amount")
	private Boolean lastAmount; //尾款标志
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	
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

	public Long getConIStageSid() {
		return conIStageSid;
	}

	public void setConIStageSid(Long conIStageSid) {
		this.conIStageSid = conIStageSid;
	}

	public Long getContractMainSid() {
		return contractMainSid;
	}

	public void setContractMainSid(Long contractMainSid) {
		this.contractMainSid = contractMainSid;
	}

	public String getItemStageName() {
		return itemStageName;
	}

	public void setItemStageName(String itemStageName) {
		this.itemStageName = itemStageName;
	}

	public Boolean getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(Boolean lastAmount) {
		this.lastAmount = lastAmount;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}
}
