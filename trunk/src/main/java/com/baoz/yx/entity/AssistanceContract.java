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
/**
 * Alvin (mixb@baoz.com.cn)
 * <p>
 * 外协合同信息表
 * </p>
 */
@Entity
@Table(name = "yx_assistance_contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class AssistanceContract extends PriEntity implements Serializable {
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "34", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long				id;								//外协系统号
	@Column(name = "MIAIN_PROJECT_ID", length = 20)
	private String 				mainProjectId;					//主体项目号
	@Column(name = "MIAIN_PROJECT_NAME", length = 50)
	private String 				mainProjectName;				//主体项目名称//不需要字段(先不删避免影响开发)
	@Column(name = "ASSISTANCE_ID", length = 20)
	private String 				assistanceId;					//外协合同号
	@Column(name = "ASSISTANCE_NAME", length = 50)
	private String				assistanceName;					//外协合同名称
	@Column(name = "SUPPLY_ID", length = 20)
	private Long 				supplyId;						//供应商代码
	@Column(name = "CONTRACT_CONTENT", length = 100)
	private String 				contractContent;				//分包合同内容描述
	@Column(name = "CONTRACT_DATE", length = 20)
	private Date 				contractDate;					//合同签订日期
	@Column(name = "END_DATE", length = 20)
	private Date 				endDate;						//预计合同结束日期
	@Column(name = "CONTRACT_MONEY", length = 50)
	private Double 				contractMoney;					//合同金额
	@Column(name = "ASSISTANCE_TYPE", length = 20)
	private String 				assistanceType;					//外协付款状态(0-未付款；1-部分付款；2-全额付款)
	@Column(name = "ASSISTANCE_CONTRACT_TYPE", length = 20)
	private String 				assistanceContractType;			//外协合同状态(0-新建；1-待确认；2-确认通过；3-确认退回；4-部分付款；5-执行结束)
	@Column(name = "TICKET_MONEY", length = 20)
	private Double 				ticketMoney;					//已开票金额
	@Column(name="con_main_info_sid",length=20)
	private Long              	contractId;						//合同主体信息系统号
	@Column(name = "TICKET_ID", length = 50)
	private Long 				ticketId;						//开票系统号
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile;                             //是否从文件导入	 
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMainProjectId() {
		return mainProjectId;
	}
	public void setMainProjectId(String mainProjectId) {
		this.mainProjectId = mainProjectId;
	}
	public String getMainProjectName() {
		return mainProjectName;
	}
	public void setMainProjectName(String mainProjectName) {
		this.mainProjectName = mainProjectName;
	}
	public String getAssistanceId() {
		return assistanceId;
	}
	public void setAssistanceId(String assistanceId) {
		this.assistanceId = assistanceId;
	}
	public String getContractContent() {
		return contractContent;
	}
	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getAssistanceType() {
		return assistanceType;
	}
	public void setAssistanceType(String assistanceType) {
		this.assistanceType = assistanceType;
	}
	public String getAssistanceContractType() {
		return assistanceContractType;
	}
	public void setAssistanceContractType(String assistanceContractType) {
		this.assistanceContractType = assistanceContractType;
	}
	public Double getTicketMoney() {
		return ticketMoney;
	}
	public void setTicketMoney(Double ticketMoney) {
		this.ticketMoney = ticketMoney;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAssistanceName() {
		return assistanceName;
	}
	public void setAssistanceName(String assistanceName) {
		this.assistanceName = assistanceName;
	}
	public Long getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}
	public Boolean getImportFromFile() {
		return importFromFile;
	}
	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}
	
}
