package com.baoz.yx.entity;

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

//外协阶段、付款申请关联表

@Entity
@Table(name = "yx_sectionandpayinfo_relation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class SectionAndPayInfoRelation implements java.io.Serializable{
	private static final long serialVersionUID = -2507862306246743822L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "yx_sectionandpayinfo_relaion", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id", nullable = false)
	private Long id;  //系统号
	

	@Column(name = "fk_section_id")
	private Long sectionId; //外协阶段系统号
	

	@Column(name = "fk_payinfo_id")
	private Long payInfoId;			 //外协付款申请系统号
	
	@Column(name = "fk_assistance_contract_id")
	private Long assistanceContractId;//外协合同id
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getSectionId() {
		return sectionId;
	}


	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}


	public Long getPayInfoId() {
		return payInfoId;
	}


	public void setPayInfoId(Long payInfoId) {
		this.payInfoId = payInfoId;
	}


	public Boolean getImportFromFile() {
		return importFromFile;
	}


	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Long getAssistanceContractId() {
		return assistanceContractId;
	}


	public void setAssistanceContractId(Long assistanceContractId) {
		this.assistanceContractId = assistanceContractId;
	}



	

}
