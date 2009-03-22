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
 * 
 * 无合同收款信息表
 *
 */
@Entity
@Table(name = "yx_nocontract_recevie_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy=false)
public class NoContractRecevieAmount implements Serializable  {
	private static final long	serialVersionUID	= -5630912558873071306L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "91", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="id")
    private Long						id;							//系统号
	
	@Column(name = "recevie_amount")
	private Double                      recevieAmount;              //收款金额
	
	@Temporal(TemporalType.DATE)
	@Column(name = "recevie_date")
	private Date                        recevieDate;                //收款日期
	
	@Column(name = "state")
    private String                      state;                      //状态 初始未0表示未核销，1表示已核销，2历史核销

	@Column(name = "customerid")
	private Long                        customerid;                 //客户系统号
	
	@Column(name = "op_person")
	private Long                        opPerson;                   //录入人
	
	@Column(name = "op_time")
	private Date                        opTime;                     //录入时间
	
	@Column(name="sale_man")
	private Long 						saleMan;//销售员
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean					    importFromFile; //是否从文件导入
	
	@Column(name = "REMARK")
	private String                      remark;  //备注
	
	@Column(name = "IS_PER_ARRIVE")
	private Long                        isPerArrive;  //是否预收款(1,表示预收,0,表示非预收)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRecevieAmount() {
		return recevieAmount;
	}

	public void setRecevieAmount(Double recevieAmount) {
		this.recevieAmount = recevieAmount;
	}

	public Date getRecevieDate() {
		return recevieDate;
	}

	public void setRecevieDate(Date recevieDate) {
		this.recevieDate = recevieDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Long getOpPerson() {
		return opPerson;
	}

	public void setOpPerson(Long opPerson) {
		this.opPerson = opPerson;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getIsPerArrive() {
		return isPerArrive;
	}

	public void setIsPerArrive(Long isPerArrive) {
		this.isPerArrive = isPerArrive;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	

}
