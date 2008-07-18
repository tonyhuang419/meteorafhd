package com.baoz.yx.entity.contract;

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

//合同项目阶段表 

@Entity
@Table(name = "yx_con_item_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractItemStage  implements java.io.Serializable {
	private static final long serialVersionUID = 2352304546958163694L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "76", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_item_stage_sid", length = 20, nullable = false)
	private Long conIStageSid;  //合同项目阶段系统号
	
	@Column(name = "fk_con_main_info_sid")
	private Long contractMainSid;  //合同主体信息系统号
	
	@Column(name = "item_stage_name", length=100 )
	private String itemStageName;  //项目阶段名称

	@Column(name = "last_amount")
	private Boolean lastAmount; //尾款标志
	
	
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getItemStageName() {
		return itemStageName;
	}

	public void setItemStageName(String itemStageName) {
		this.itemStageName = itemStageName;
	}

	public void setLastAmount(Boolean lastAmount) {
		this.lastAmount = lastAmount;
	}

	public Boolean getLastAmount() {
		return lastAmount;
	}
}