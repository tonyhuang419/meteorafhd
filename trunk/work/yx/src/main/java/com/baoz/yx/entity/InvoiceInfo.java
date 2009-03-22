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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * 发票明细表
 * 
 * @author ye peng 
 */
@Entity
@Table(name = "yx_invoice_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class InvoiceInfo extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= -5630912558873071306L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "42", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="invoice_info_id")
	private Long						id;							//发票、收据明细系统号

	@Column(name = "apply_invoice_id")
	private Long				applyInvoiceId;                   	 //开票申请系统号

	@Column(name = "invoice_no",  length = 200)
	private String				invoiceNo;							//发票号、收据号
	
	
	@Column(name = "invoice_amount" )
	private Double				invoiceAmount;						//发票、收据金额....也就是开票金额
	
	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_date")
	private Date				invoiceDate;						//开票时间

	@Column(name = "record_date")
	private Date				recordDate;						//记录录入时间

	@Column(name = "aging" )
	private Long				aging;					     	//账龄
	
	@Column(name = "rece_state")
	private String				receState;						//收款状态
	
	@Column(name = "rece_amount")
	private Double				receAmount;						//到款总金额
	  
	@Column(name = "type")
	private String				type;						   //收据和发票的状态标志    判断发票类型,
	
	@Column(name = "change_state",length=1,nullable=true)
	private String				changeState;						   //收据转发票的状态标志   "1" 为全部转完,
	
	@Column(name = "fk_con_main_info_sid")
	private Long 				contractMainSid;                //合同主体信息系统号
	
	@Column(name = "input_state")
	private String 				inputState;                //确认录入标志    0未确认  1已确认
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入

	@Column(name = "hongchong_state")
	private String hongChongState; //是否红冲标示
	
	public String getHongChongState() {
		return hongChongState;
	}

	public void setHongChongState(String hongChongState) {
		this.hongChongState = hongChongState;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplyInvoiceId() {
		return applyInvoiceId;
	}

	public void setApplyInvoiceId(Long applyInvoiceId) {
		this.applyInvoiceId = applyInvoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getAging() {
		return aging;
	}

	public void setAging(Long aging) {
		this.aging = aging;
	}


	public String getReceState() {
		return receState;
	}

	public void setReceState(String receState) {
		this.receState = receState;
	}



	public Double getReceAmount() {
		return receAmount;
	}

	public void setReceAmount(Double receAmount) {
		this.receAmount = receAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getContractMainSid() {
		return contractMainSid;
	}

	public void setContractMainSid(Long contractMainSid) {
		this.contractMainSid = contractMainSid;
	}

	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	public String getInputState() {
		return inputState;
	}

	public void setInputState(String inputState) {
		this.inputState = inputState;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}



}
