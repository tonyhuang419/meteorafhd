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
 * <p>
 * 收款金额表
 * </p>
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Entity
@Table(name = "yx_reve_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class ReveInfo extends PriEntity implements Serializable {
	private static final long	serialVersionUID	= -5630912558873071306L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "41", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="rece_id",length = 20)
	private Long				id;								//收款管理明细系统流水号
	
	@Column(name="FK_CON_MAIN_INFO_SID",length=20)
	private Long               conMainInfoSid;//合同主体信息系统号

	@Column(name = "bill_sid")
	private Long				billSid;                   	 //项目主体系统号
	
	@Column(name = "amount",length=20,scale = 2)
	private Double				amount;							//到款金额
		
	@Temporal(TemporalType.DATE)
	@Column(name = "amount_time" )
	private Date				amountTime;						//到款时间

	@Column(name = "rece_type", length = 20)
	private String				receType;						//到款方式(类型表关联)
	
	@Column(name = "hasSure", length = 2)
	private String				hasSure;						//收款确认   0未确认 1已确认
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	
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

	public Long getBillSid() {
		return billSid;
	}

	public void setBillSid(Long billSid) {
		this.billSid = billSid;
	}



	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Date amountTime) {
		this.amountTime = amountTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getReceType() {
		return receType;
	}

	public void setReceType(String receType) {
		this.receType = receType;
	}

	public String getHasSure() {
		return hasSure;
	}

	public void setHasSure(String hasSure) {
		this.hasSure = hasSure;
	}

	public Long getConMainInfoSid() {
		return conMainInfoSid;
	}

	public void setConMainInfoSid(Long conMainInfoSid) {
		this.conMainInfoSid = conMainInfoSid;
	}


}
