package com.baoz.yx.entity.contract;

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

/**
 * 类ContractItemStagePlan.java的实现描述：合同阶段计划
 * @author xurong Jul 10, 2008 5:21:55 PM
 */
@Entity
@Table(name = "YX_CON_ITEM_STAGE_PLAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ContractItemStagePlan {
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "77", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="ID", length = 20, nullable = false)
	private Long id;  //主键号
	
	@Column(name = "CON_MAIN_INFO_SID")
	private Long contractMainSid;  //合同主体信息系统号
	
	@Column(name = "MAKE_INVOICE_DATE")
	private Date makeInvoiceDate;  //预计开票日期
	
	@Column(name = "GATHERING_DATE") 
	private Date gatheringDate; //预计收款日期
	
	@Column(name="STAGE_AMOUT",length = 20,scale = 2)
	private Double stageAmout;  //阶段金额
	
	@ManyToOne
	@JoinColumn(name = "CONTRACT_ITEM_STAGE_ID")
	private ContractItemStage contractItemStage; // 阶段表
	
	@ManyToOne
	@JoinColumn(name = "CONTRACT_MAININFO_PART_ID")
	private ContractMaininfoPart contractMaininfoPart; //合同费用组成

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractMainSid() {
		return contractMainSid;
	}

	public void setContractMainSid(Long contractMainSid) {
		this.contractMainSid = contractMainSid;
	}

	public Date getMakeInvoiceDate() {
		return makeInvoiceDate;
	}

	public void setMakeInvoiceDate(Date makeInvoiceDate) {
		this.makeInvoiceDate = makeInvoiceDate;
	}

	public Date getGatheringDate() {
		return gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}

	public Double getStageAmout() {
		return stageAmout;
	}

	public void setStageAmout(Double stageAmout) {
		this.stageAmout = stageAmout;
	}

	public ContractItemStage getContractItemStage() {
		return contractItemStage;
	}

	public void setContractItemStage(ContractItemStage contractItemStage) {
		this.contractItemStage = contractItemStage;
	}

	public ContractMaininfoPart getContractMaininfoPart() {
		return contractMaininfoPart;
	}

	public void setContractMaininfoPart(ContractMaininfoPart contractMaininfoPart) {
		this.contractMaininfoPart = contractMaininfoPart;
	}
}
