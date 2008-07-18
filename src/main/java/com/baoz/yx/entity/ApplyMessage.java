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
 * 申购采购信息表
 * </p>
 */

@Entity
@Table(name = "yx_apply_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ApplyMessage extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "31", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long id; // 申购单系统号
	@Column(name = "MIAIN_ID", length = 20)
	private Long mainId; // 主体合同系统号 
	@Column(name = "EVENT_ID", length = 20)     //说明申购分两种一种对应主合同号，一种对应项目号
	private String eventId; // 主项目号
	@Column(name = "ASSIGNMENT_ID", length = 20)
	private String assignmentId; // 任务号
	@Column(name = "SELLMAN_ID", length = 20)
	private Long sellmanId; // 销售人员代码
	@Column(name = "APPLY_ID", length = 50)
	private String applyId; // 申购单号
	@Column(name = "APPLY_DATE", length = 10)
	private Date applyDate; // 申购日期
	@Column(name = "OUT_STATE", length = 2)
	private String outState; // 出库状态 0-未出库；1-出库；
	@Column(name = "OUT_ID", length = 20)
	private String outId; // 出库开票编号
	@Column(name = "ESTIMATE_DATE", length = 50)
	private Date estimateDate; // 预计合同签订日期
	@Column(name = "APPLY_TYPE", length = 2)
	private String applyType; // 申购类型（0－工程类；1－集成类）
	@Column(name = "CUSTOMER_ID", length = 20)
	private Long customerId; // 客户代码
	@Column(name = "TICKET_APPLY_ID", length = 20)
	private Long ticketApplyId; // 开票申请编号
	@Column(name = "BUGGET", length = 20)
	private String bugget; // 设备预算
	@Column(name = "APPLY_STATE", length = 2)
	private String applyState; // 申购状态（0－合同未签申购；1－合同已签申购）
	@Column(name = "LINKMAN", length = 20)
	private String linkman; // 联系人
	@Column(name = "APPLY_CONTENT", length = 100)
	private String applyContent; // 申购内容
	@Column(name = "REMARK", length = 100)
	private String remark; // 备注
	@Column(name = "PROJECT_NAME", length = 30)
	private String projectName; // 项目名称
	@Column(name = "INFORM_STATE", length = 2)
	private String informState; // 通知状态 1-到齐通知；2-分批通知；3-全部
	@Column(name = "AFFIRM_STATE", length = 2)
	private String affirmState; // 确认状态 0-草稿；1-待确认；2-确认通过；3-确认退回
	@Column(name = "APPLY_MONEY")
	private Double applymoney;	 //申购金额
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public Long getSellmanId() {
		return sellmanId;
	}
	public void setSellmanId(Long sellmanId) {
		this.sellmanId = sellmanId;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getOutState() {
		return outState;
	}
	public void setOutState(String outState) {
		this.outState = outState;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public Date getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getTicketApplyId() {
		return ticketApplyId;
	}
	public void setTicketApplyId(Long ticketApplyId) {
		this.ticketApplyId = ticketApplyId;
	}
	public String getBugget() {
		return bugget;
	}
	public void setBugget(String bugget) {
		this.bugget = bugget;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getApplyContent() {
		return applyContent;
	}
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getInformState() {
		return informState;
	}
	public void setInformState(String informState) {
		this.informState = informState;
	}
	public String getAffirmState() {
		return affirmState;
	}
	public void setAffirmState(String affirmState) {
		this.affirmState = affirmState;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Double getApplymoney() {
		return applymoney;
	}
	public void setApplymoney(Double applymoney) {
		this.applymoney = applymoney;
	}



	
}
