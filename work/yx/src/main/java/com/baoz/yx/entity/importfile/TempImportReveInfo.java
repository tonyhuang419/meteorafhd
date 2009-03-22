package com.baoz.yx.entity.importfile;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name="YX_TEMP_IMPORT_REVEINFO")
@org.hibernate.annotations.Entity(selectBeforeUpdate=true,dynamicInsert=true,dynamicUpdate=true)
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Proxy(lazy=false)
public class TempImportReveInfo implements Serializable {

	private static final long serialVersionUID = 7721L;
	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "tempImportReveInfo", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name="sid",length=20)
	private Long sid;//系统id
	
	@Column(name="REVEDATE")
	private Date reveDate;//收款日期
	
	@Column(name="CUSTOMERNAME")
	private String customerName;//客户名称
	
	@Column(name="REVEAMOUNT")
	private BigDecimal reveAmount;//收款金额
	
	@Column(name="CONNO")
	private String conNo;//合同号
	
	@Column(name="ITEMNO")
	private String itemNo;//项目号
	
	@Column(name="SALEMAN")
	private String saleMan;//销售员姓名
	
	@Column(name="ARRIVETYPE")
	private String arriveType;//到款方式
	
	@Column(name="ERRORSTATE")
	private Long errorState;//标示状态0，代表可以确认，1代表不能确认
	
	@Column(name="ERRORMSG")
	private String errorMsg;//提示信息，errorState为0的时候提示到款的几种方式，1的时候提示错误信息
	
	@Column(name="AUTHORID")
	private Long authorId;//创建者
	
	@Column(name="IS_ACTIVE")
	private Long isActive;//是否可用，确认以后的都是0，待确认的都是1
	
	@Column(name="BATCH")
	private Long batch;//批次号
	
	@Column(name="EXCEL_ROW_NO")
	private Integer excelRowNo;//Excel文件中对应的行号
	

	@Column(name="IS_PER_ARRIVE")
	private String isPerArrive;	//是否预收
	
	public Integer getExcelRowNo() {
		return excelRowNo;
	}

	public void setExcelRowNo(Integer excelRowNo) {
		this.excelRowNo = excelRowNo;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Date getReveDate() {
		return reveDate;
	}

	public void setReveDate(Date reveDate) {
		this.reveDate = reveDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getReveAmount() {
		return reveAmount;
	}

	public void setReveAmount(BigDecimal reveAmount) {
		this.reveAmount = reveAmount;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getArriveType() {
		return arriveType;
	}

	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	public Long getErrorState() {
		return errorState;
	}

	public void setErrorState(Long errorState) {
		this.errorState = errorState;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Long getIsActive() {
		return isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public String getIsPerArrive() {
		return isPerArrive;
	}

	public void setIsPerArrive(String isPerArrive) {
		this.isPerArrive = isPerArrive;
	}

}
