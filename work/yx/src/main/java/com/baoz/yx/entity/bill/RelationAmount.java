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

//金额关联表


@Entity
@Table(name = "yx_relation_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class RelationAmount implements java.io.Serializable {

	private static final long serialVersionUID = -2073890220303897970L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "77", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="relation_amount_id",length = 20)
	private Long 					relationAmountId;  			//金额关联系统id

	@Column(name = "invoice_to_inovice",length=20)
	private Long 					invoiceToInvoice;			    //发票明细系统号(发票)
	
	@Column(name = "invoice_to_receipt",length = 100)
	private Long 					invoiceToReceip;    			//发票明细系统号(收据)
	
	
	@Column(name = "relate_amount",length=20)
	private Double 					relateAmount;					//关联金额

	@Column(name = "op_people" )
	private Long 					opId;         					//操作人
	
	@Column(name = "op_date" )	
	private Date 					opDate;          				//操作时间
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getRelationAmountId() {
		return relationAmountId;
	}

	public void setRelationAmountId(Long relationAmountId) {
		this.relationAmountId = relationAmountId;
	}


	public Long getInvoiceToInvoice() {
		return invoiceToInvoice;
	}

	public void setInvoiceToInvoice(Long invoiceToInvoice) {
		this.invoiceToInvoice = invoiceToInvoice;
	}

	public Long getInvoiceToReceip() {
		return invoiceToReceip;
	}

	public void setInvoiceToReceip(Long invoiceToReceip) {
		this.invoiceToReceip = invoiceToReceip;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Double getRelateAmount() {
		return relateAmount;
	}

	public void setRelateAmount(Double relateAmount) {
		this.relateAmount = relateAmount;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}
	
}