package com.baoz.yx.entity.contract;

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

@Entity
@Table(name = "YX_CHANGING_CON_STAGE_PLAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ChangingContractItemStagePlan implements Serializable {

	private static final long serialVersionUID = -6763316129490938313L;

	@Id
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
	
	@Column(name="REVE_AMOUNT",length = 20,scale = 2)
	private Double reveAmount;  //阶段收款金额,生成计划时会安比例分到每个计划
	
	@Column(name="TICKET_TYPE")
	private String ticketType;  //开票类型,默认费用组成里的类型
	
	@Column(name = "STAGE_REMARK")
	private String stageRemark;    //阶段辅助说明
	
	@ManyToOne
	@JoinColumn(name = "CONTRACT_ITEM_STAGE_ID")
	private ChangingContractItemStage ccontractItemStage; // 阶段表
	
	@ManyToOne
	@JoinColumn(name = "CONTRACT_MAININFO_PART_ID")
	private ChangingContractMaininfoPart ccontractMaininfoPart; //合同费用组成

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

	public Double getReveAmount() {
		return reveAmount;
	}

	public void setReveAmount(Double reveAmount) {
		this.reveAmount = reveAmount;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getStageRemark() {
		return stageRemark;
	}

	public void setStageRemark(String stageRemark) {
		this.stageRemark = stageRemark;
	}





	public ChangingContractItemStage getCcontractItemStage() {
		return ccontractItemStage;
	}

	public void setCcontractItemStage(ChangingContractItemStage ccontractItemStage) {
		this.ccontractItemStage = ccontractItemStage;
	}

	public ChangingContractMaininfoPart getCcontractMaininfoPart() {
		return ccontractMaininfoPart;
	}

	public void setCcontractMaininfoPart(
			ChangingContractMaininfoPart ccontractMaininfoPart) {
		this.ccontractMaininfoPart = ccontractMaininfoPart;
	}

}
