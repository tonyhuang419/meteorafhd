package com.baoz.yx.entity.contract;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.springframework.core.annotation.Order;

//原始合同开票计划表

//开票性质，开票内容 未作关联
@Entity
@Table(name = "yx_init_con_billpro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class InitContractBillpro implements java.io.Serializable {

	private static final long serialVersionUID = 2287260677442294428L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "77", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="init_con_billpro_sid",length = 20)
	private Long initConBillproSid; //原始合同开票计划系统号
	
	@Column(name = "fk_item_info_sid")
	private Long conItemInfo;  //合同项目系统号 main
	
	@Column(name = "fk_con_i_stage_sid")
	@OrderBy(value = "conItemStage")
	private Long conItemStage; //合同阶段系统号
	
	@Column(name = "bill_recv_sign")
	private Boolean billRecvSign;  //开票确定收入标志
	
	@Column(name = "bill_info",length=100)
	private String billInfo;   //开票内容
	
	@Column(name = "base")
	private Long base;  //基准,是否含税 0-不含税；1-含税；
	
	@Column(name = "init_bill_amount",length = 20,scale = 2)
	private BigDecimal initBillAmount;    //原始计划开票金额
	
	@Column(name = "is_unify_bill")
	private Boolean UnifyBill;   //是否统一开票
	
	@Temporal(TemporalType.DATE)
	@Column(name = "init_bill_date")
	private Date initBillDate;  //原始预计开票日期  
	
	@Temporal(TemporalType.DATE)
	@Column(name = "init_rece_date")
	private Date initReceDate;  //原始预计收款日期  
	
	@Column(name = "fk_bill_nature")
	private String billNature;   //开票性质(连接类型管理表)
	
	@Column(name = "fk_bill_type")
	private String billType;    //开票类型(连接类型管理表)
	
	@Column(name = "fk_con_main_sid")
	private Long conMainInfoSid;//合同主体信息系统号
	
	public Long getInitConBillproSid() {
		return initConBillproSid;
	}

	public void setInitConBillproSid(Long initConBillproSid) {
		this.initConBillproSid = initConBillproSid;
	}

	public Long getConItemInfo() {
		return conItemInfo;
	}

	public void setConItemInfo(Long conItemInfo) {
		this.conItemInfo = conItemInfo;
	}

	public Long getConItemStage() {
		return conItemStage;
	}

	public void setConItemStage(Long conItemStage) {
		this.conItemStage = conItemStage;
	}

	public boolean isBillRecvSign() {
		return billRecvSign;
	}

	public void setBillRecvSign(boolean billRecvSign) {
		this.billRecvSign = billRecvSign;
	}

	public String getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(String billInfo) {
		this.billInfo = billInfo;
	}


	public Boolean getBillRecvSign() {
		return billRecvSign;
	}

	public void setBillRecvSign(Boolean billRecvSign) {
		this.billRecvSign = billRecvSign;
	}

	public BigDecimal getInitBillAmount() {
		return initBillAmount;
	}

	public void setInitBillAmount(BigDecimal initBillAmount) {
		this.initBillAmount = initBillAmount;
	}

	public Boolean getUnifyBill() {
		return UnifyBill;
	}

	public void setUnifyBill(Boolean unifyBill) {
		UnifyBill = unifyBill;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Date getInitBillDate() {
		return initBillDate;
	}

	public void setInitBillDate(Date initBillDate) {
		this.initBillDate = initBillDate;
	}

	public String getBillNature() {
		return billNature;
	}

	public void setBillNature(String billNature) {
		this.billNature = billNature;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Long getConMainInfoSid() {
		return conMainInfoSid;
	}

	public void setConMainInfoSid(Long conMainInfoSid) {
		this.conMainInfoSid = conMainInfoSid;
	}

	public Long getBase() {
		return base;
	}

	public void setBase(Long base) {
		this.base = base;
	}

	public Date getInitReceDate() {
		return initReceDate;
	}

	public void setInitReceDate(Date initReceDate) {
		this.initReceDate = initReceDate;
	}
 
}