package com.baoz.yx.entity.contract.bak;

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

import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMaininfoPart;

@Entity
@Table(name = "YX_bak_CON_ITEM_STAGE_PLAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BakContractItemStagePlan implements Serializable{


	private static final long serialVersionUID = 7256513306227550049L;

	@Id
	@TableGenerator(name="sid",table="sysid",pkColumnName="id",valueColumnName="tableid",pkColumnValue="BakContractItemStagePlan",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="sid")
	@Column(name = "sid")
	private Long sid;//主键
	
	@Column(name="confirm_date")
	private Date confirmDate;//确认变更时间
	
	@Column(name = "confirm_people")
	private Long confirmPeople;//变更确认人
	
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
	
	
	@Column(name = "CONTRACT_ITEM_STAGE_ID")
	private Long contractItemStageId; // 阶段表
	
	
	@Column(name = "CONTRACT_MAININFO_PART_ID")
	private Long contractMaininfoPartId; //合同费用组成
	
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


	public Long getContractItemStageId() {
		return contractItemStageId;
	}


	public void setContractItemStageId(Long contractItemStageId) {
		this.contractItemStageId = contractItemStageId;
	}


	public Long getContractMaininfoPartId() {
		return contractMaininfoPartId;
	}


	public void setContractMaininfoPartId(Long contractMaininfoPartId) {
		this.contractMaininfoPartId = contractMaininfoPartId;
	}
}
