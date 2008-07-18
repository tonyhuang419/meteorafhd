package com.baoz.yx.entity.jasperReport;

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

import com.baoz.yx.entity.PriEntity;

/**
 * Alvin (mixb@baoz.com.cn)
 * <p>
 * 外协付款信息表
 * </p>
 */

public class AssistanceContractReport extends PriEntity implements Serializable { // 外协付款信息表
	private static final long serialVersionUID = 6348641281692587064L;

	private Long id; // 外协系统支付号（流水号）

	private Long assistanceId; // 外协系统号

	private String mainProjectId; // 主体项目号

	private String mainProjectName; // 主体项目名称//不需要字段(先不删避免影响开发)

	private String assistanceInfoId; // 外协合同号

	private Long supplyId; // 供应商代码

	private String supplierName; // 供应商名称

	private String supplierCode;// 供应商代码

	private String billBank;// 开户银行

	private String billAccount;// 开户帐号

	private String mainItemDept; // 成本中心，项目主体信息表中的项目负责部门

	private String assignTicketId; // 外协开票系统号

	private String assignmentId; // 任务号

	private String applyDept; // 申请部门

	private double  payNum; // 付款金额
	
	private String UpPayNum;//付款金额大写
	
	private String payState; // 付款申请状态(0-新建；1-待确认；2-确认通过；3-确认退回；4-付款完成)

	private String receivNum; // 接收号

	private String remark; // 备注

	private String info; // 付款说明

	private Date applyDate; // 申请日期
	
	private String year;//申请日期的年

	private String month;//申请日期的月

	private String day;//申请日期的日
	
	private String payInfoType;//发票类型
	
	private String ticketNo;//发票号
	

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getPayInfoType() {
		return payInfoType;
	}

	public void setPayInfoType(String payInfoType) {
		this.payInfoType = payInfoType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

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

	public double getPayNum() {
		return payNum;
	}

	public void setPayNum(double payNum) {
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

	public String getAssistanceInfoId() {
		return assistanceInfoId;
	}

	public void setAssistanceInfoId(String assistanceInfoId) {
		this.assistanceInfoId = assistanceInfoId;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getBillBank() {
		return billBank;
	}

	public void setBillBank(String billBank) {
		this.billBank = billBank;
	}

	public String getBillAccount() {
		return billAccount;
	}

	public void setBillAccount(String billAccount) {
		this.billAccount = billAccount;
	}

	public String getMainItemDept() {
		return mainItemDept;
	}

	public void setMainItemDept(String mainItemDept) {
		this.mainItemDept = mainItemDept;
	}

	public String getUpPayNum() {
		return UpPayNum;
	}

	public void setUpPayNum(String upPayNum) {
		UpPayNum = upPayNum;
	}

}
