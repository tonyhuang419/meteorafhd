package com.baoz.yx.entity.bill;

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
 * 红冲发票存根表
 * @author 
 *
 */

@Entity
@Table(name = "yx_hongchong_invoice_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class HongChongInvoiceInfo implements java.io.Serializable {
	private static final long serialVersionUID = -7655112414185255156L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "89", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id")
	private Long						id;							//发票、收据明细系统号

	@Column(name = "fk_invoiceid")
	private Long				invoiceId;                   	 //原开票信息信息id
	
	@Column(name = "fk_hongChong_billId")
	private Long				hongChongBIllId;                   	 //开票申请系统号

	@Column(name = "invoice_no",  length = 200)
	private String				invoiceNo;							//发票号、收据号
		
	@Column(name = "invoice_amount", length = 22, scale = 2)
	private Double invoiceAmount; 								//发票金额
	
	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_date")
	private Date				invoiceDate;						//开票时间

	@Column(name = "record_date")
	private Date				recordDate;						//记录录入时间
	
	@Column(name = "fk_con_main_info_sid")
	private Long 				contractMainSid;                //合同主体信息系统号
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHongChongBIllId() {
		return hongChongBIllId;
	}

	public void setHongChongBIllId(Long hongChongBIllId) {
		this.hongChongBIllId = hongChongBIllId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Long getContractMainSid() {
		return contractMainSid;
	}

	public void setContractMainSid(Long contractMainSid) {
		this.contractMainSid = contractMainSid;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
}