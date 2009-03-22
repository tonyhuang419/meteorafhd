package com.baoz.yx.entity.bill;

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
 * 发票收据计划关联表
 * @author wq
 *
 */


@Entity
@Table(name = "yx_relation_bill_receipt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class RelationBillAndReceipt implements java.io.Serializable {

	private static final long serialVersionUID = -2073890220303897970L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "63", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id",length = 20)
	private Long 					id;  							//关联id

	@Column(name = "relation_amount")
	private Double 					relationAmount;			    //关联金额
	
	@Column(name = "bill_real_id",length = 20)
	private Long 					billRealId;    					//发票计划系统号
	
	@Column(name = "receipt_real_id",length=20)
	private Long 					receiptRealId;					//收据计划系统号

	@Column(name = "rece_relation_amount" )
	private Double 					receRelationAmount;         	//应收关联金额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRelationAmount() {
		return relationAmount;
	}

	public void setRelationAmount(Double relationAmount) {
		this.relationAmount = relationAmount;
	}

	public Long getBillRealId() {
		return billRealId;
	}

	public void setBillRealId(Long billRealId) {
		this.billRealId = billRealId;
	}

	public Long getReceiptRealId() {
		return receiptRealId;
	}

	public void setReceiptRealId(Long receiptRealId) {
		this.receiptRealId = receiptRealId;
	}

	public Double getReceRelationAmount() {
		return receRelationAmount;
	}

	public void setReceRelationAmount(Double receRelationAmount) {
		this.receRelationAmount = receRelationAmount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}