package com.baoz.yx.entity;

import java.io.Serializable;
import java.util.Date;

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

import com.baoz.yx.entity.PriEntity;

/**
 * <p>
 * 阶段信息表
 * </p>
 */

@Entity
@Table(name = "yx_assistance_section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class AssistanceSection implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "92", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long 					id; 
	@Column(name = "SECTION_NAME", length = 50)
	@Deprecated
	private String 					sectionName;                           // 阶段名称
	@Column(name = "section_amount", length = 5)
	private Double  				sectionAmount;                           //阶段金额
	@Column(name = "section_bill_time", length = 20)
	private Date 					sectionBillTime;                         //预计阶段收款日期
	
	@Column(name = "fk_assistance_stage_sid")
	private Long assistanceStageSId;//正式合同中的阶段id
	
	@Column(name = "fk_assistance_item_sid")
	private Long assistanceItemSId;//正式合同中的项目id
	
	@Column(name = "fk_assistance_con_sid")
	private Long assistanceConSId;//正式合同id
	
	@Column(name = "fk_assistance_contract")
	private Long assistanceId;  ///外协合同id 
	
	@Column(name = "section_remarks")
	private String sectionRemarks;///外协阶段说明
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Double getSectionAmount() {
		return sectionAmount;
	}

	public void setSectionAmount(Double sectionAmount) {
		this.sectionAmount = sectionAmount;
	}

	public Date getSectionBillTime() {
		return sectionBillTime;
	}

	public void setSectionBillTime(Date sectionBillTime) {
		this.sectionBillTime = sectionBillTime;
	}

	public Long getAssistanceId() {
		return assistanceId;
	}

	public void setAssistanceId(Long assistanceId) {
		this.assistanceId = assistanceId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getAssistanceStageSId() {
		return assistanceStageSId;
	}

	public void setAssistanceStageSId(Long assistanceStageSId) {
		this.assistanceStageSId = assistanceStageSId;
	}

	public Long getAssistanceItemSId() {
		return assistanceItemSId;
	}

	public void setAssistanceItemSId(Long assistanceItemSId) {
		this.assistanceItemSId = assistanceItemSId;
	}

	public Long getAssistanceConSId() {
		return assistanceConSId;
	}

	public void setAssistanceConSId(Long assistanceConSId) {
		this.assistanceConSId = assistanceConSId;
	}

	public String getSectionRemarks() {
		return sectionRemarks;
	}

	public void setSectionRemarks(String sectionRemarks) {
		this.sectionRemarks = sectionRemarks;
	}         
	
	
}

