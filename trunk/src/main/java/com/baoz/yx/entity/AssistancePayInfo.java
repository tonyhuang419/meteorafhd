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
 * 外协付款信息表
 * </p>
 */

@Entity
@Table(name = "yx_assistance_pay_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class AssistancePayInfo extends PriEntity implements Serializable {    //外协付款信息表
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "37", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long 				id; 						//外协系统支付号（流水号）
	@Column(name = "ASSISTANCE_ID", length = 20)
	private Long 				assistanceId;				//外协系统号
	@Column(name = "ASSIGN_TICKET_ID", length = 20)
	private String 				assignTicketId;				//外协开票系统号
	@Column(name = "ASSIGNMENT_ID", length = 20)
	private String 				assignmentId; 				//任务号
	@Column(name = "APPLY_DEPT", length = 20)
	private String 				applyDept;					//申请部门
	@Column(name = "PAY_NUM", length = 20)
	private Double 				payNum;						//付款金额
	@Column(name = "PAY_STATE", length = 2)
	private String 				payState;					//付款申请状态(0-新建；1-待确认；2-确认通过；3-确认退回；4-付款完成)			
	@Column(name = "RECEIVE_NUM", length = 20)
	private String 				receivNum;					//接收号
	@Column(name = "REMARK", length = 100)
	private String 				remark; 					//备注
	@Column(name = "INFO", length = 100)					
	private String 				info;						//付款说明
	@Column(name = "APPLY_DATE", length = 20)
	private Date 				applyDate;					//申请日期
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAssignTicketId() {
		return assignTicketId;
	}
	public void setAssignTicketId(String assignTicketId) {
		this.assignTicketId = assignTicketId;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public Double getPayNum() {
		return payNum;
	}
	public void setPayNum(Double payNum) {
		this.payNum = payNum;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getReceivNum() {
		return receivNum;
	}
	public void setReceivNum(String receivNum) {
		this.receivNum = receivNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Long getAssistanceId() {
		return assistanceId;
	}
	public void setAssistanceId(Long assistanceId) {
		this.assistanceId = assistanceId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

}
