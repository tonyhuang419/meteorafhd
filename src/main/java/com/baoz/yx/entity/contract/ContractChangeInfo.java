package com.baoz.yx.entity.contract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * <p>
 * 合同变更履历表
 * </p>
 * 
 *
 */
@Entity
@Table(name = "yx_con_change_his")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractChangeInfo implements Serializable {
	private static final long	serialVersionUID	= 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "74", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="con_c_his_sid", length = 20, nullable = false)
	private Long  cid;  //合同变更履历系统号
	
	@Column(name = "fk_con_main_info_sid",nullable = false)
	private Long contractMainSid;  //合同主体信息系统号
	
	
	@Column(name = "fk_change_explain_id")
    private Long changeInfoId;     //变更说明系统号
	
	
	@Column(name = "change_event")
	private String changeEvent;    //变更项
	
	
	@Column(name = "before_change_info")
	private String beforechangeinfo;  //变更前信息
	
	
	@Column(name = "behind_change_info")
	private String behindchangeinfo;   //变更后信息
	
	
/*	@Column(name="C_INFO",length=4000)
	private String changeInfo;  //变更内容
	
	
	@Column(name="CON_C_EXP",length=4000)
	private String changeExplain;  //变更说明
*/	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
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

	public Long getChangeInfoId() {
		return changeInfoId;
	}

	public void setChangeInfoId(Long changeInfoId) {
		this.changeInfoId = changeInfoId;
	}

	public String getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(String changeEvent) {
		this.changeEvent = changeEvent;
	}

	public String getBeforechangeinfo() {
		return beforechangeinfo;
	}

	public void setBeforechangeinfo(String beforechangeinfo) {
		this.beforechangeinfo = beforechangeinfo;
	}

	public String getBehindchangeinfo() {
		return behindchangeinfo;
	}

	public void setBehindchangeinfo(String behindchangeinfo) {
		this.behindchangeinfo = behindchangeinfo;
	}


}
