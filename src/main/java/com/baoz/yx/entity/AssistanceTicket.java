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
 * 外协开票表
 * </p>
 */
@Entity
@Table(name = "yx_assistance_ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class AssistanceTicket extends PriEntity implements Serializable {
	private static final long serialVersionUID = 6348641281692587064L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "42", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(length = 20)
	private Long 				id;							//外协开票系统号 
	@Column(name = "ASSISTANCE_PAY_INFO_ID", length = 20)
	private Long 				assistancPayInfoId;			//外协支付系统号
	@Column(name = "CUSTOMER_ID", length = 20)
	private Long 				customerId;					//客户代码
	@Column(name = "TYPE", length = 20)
	private String 				type;						//类型代码(发票类型)
	@Column(name = "NUM", length = 20)
	private String 				num;						//发票号
	@Column(name = "MONEY", length = 20)
	private Double 				money;						//发票金额
	@Column(name = "TICKET_TIME", length = 20)
	private Date 				ticket_Time;				//开票时间
	@Column(name = "PERSON", length = 20)
	private String 				person;						//操作人
	@Column(name = "DONE_TIME", length = 20)
	private Date 				doneTime;					//录入时间
	@Column(name = "REMARK", length = 100)
	private String 				remark;						//备注
	@Column(name = "DONE_MONEY", length = 20)
	private Double 				doneMoney;					//已付款金额
	@Column(name = "CONTRACT_ID", length = 20)
	private Long 				contractId;					//外协合同号
	@Column(name = "PAY_INFO_ID", length = 20)
	private Long				payInfoId;					//付款申请ID
	@Column(name = "IS_RELATED", length = 20)
	private String				is_related;
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
	public Long getAssistancPayInfoId() {
		return assistancPayInfoId;
	}
	public void setAssistancPayInfoId(Long assistancPayInfoId) {
		this.assistancPayInfoId = assistancPayInfoId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
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
	public Date getTicket_Time() {
		return ticket_Time;
	}
	public void setTicket_Time(Date ticket_Time) {
		this.ticket_Time = ticket_Time;
	}
	public Date getDoneTime() {
		return doneTime;
	}
	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}
	public Long getPayInfoId() {
		return payInfoId;
	}
	public void setPayInfoId(Long payInfoId) {
		this.payInfoId = payInfoId;
	}
	public Double getDoneMoney() {
		return doneMoney;
	}
	public void setDoneMoney(Double doneMoney) {
		this.doneMoney = doneMoney;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getIs_related() {
		return is_related;
	}
	public void setIs_related(String is_related) {
		this.is_related = is_related;
	}
}
