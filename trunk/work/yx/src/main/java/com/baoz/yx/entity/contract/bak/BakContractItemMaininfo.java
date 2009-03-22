package com.baoz.yx.entity.contract.bak;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import com.baoz.yx.entity.contract.ContractItemInfo;

@Entity
@Table(name = "yx_bak_con_item_minfo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class BakContractItemMaininfo implements Serializable{

	
	private static final long serialVersionUID = 1760212489164700658L;

	@Id
	@TableGenerator(name="sid",table="sysid",pkColumnName="id",valueColumnName="tableid",pkColumnValue="BakContractItemMaininfo",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="sid")
	@Column(name = "sid")
	private Long sid;//主键
	
	@Column(name="confirm_date")
	private Date confirmDate;//确认变更时间
	
	@Column(name = "confirm_people")
	private Long confirmPeople;//变更确认人
	
	@Column(name="con_item_minfo_sid",length = 20, nullable = false)
	private Long conItemMinfoSid;   //合同项目主体信息系统号
		

	@Column(name = "fk_con_main_info_sid",nullable = false)
	private Long contractMainInfo;  //合同主体信息系统号
	
	
	@Column(name="con_item_id",length = 20 )
	private String conItemId; 		 //合同项目编号//外键关联外协合同
	
	@Column(name="is_war",length = 20 )
	private Boolean War;			 //是否WAR项目
	
	@Column(name="item_res_dept",length = 100 )
	private String itemResDept; 		 //项目负责部门
	
	@Column(name="item_res_dept_p",length = 100 )
	private String itemResDeptP;		 //项目负责人
	
	@Column(name="con_item_cost_sure")
	private Long conItemCostSure; 		//合同项目成本确认状态   //0未录入 1已录入 2待确认 3确认通过 4确认退回,5签收确认，6保留

	@Column(name="con_item_state")
	private Long conItemState; 		//合同项目状态   //0或null未关闭 1建议关闭 2关闭
	
	@Column(name="is_con_mod_info")
	private Boolean ConModInfo;  		//是否属于合同变更信息
	
	@Column(name="account_money")
	private BigDecimal accountmoney;		//总金额,已经不用,通过sum子金额来获得总金额
	
	@Column(name="remain_bill")
	private Double remainBill;		//导入的剩余发票....项目成本确认用....导入时如果不存在，-1标识
	
	@Column(name="remain_assistance")
	private Double remainAssistance;		//导入的剩余外协....项目成本确认用....导入时如果不存在，-1标识

	@Column(name="sys_remain_bill")
	private Double sysRemainBill;		//本系统内的剩余发票....项目成本确认用....导入时如果不存在，-1标识
	
	@Column(name="sys_remain_assistance")
	private Double sysRemainAssistance;		//本系统内的剩余外协....项目成本确认用....导入时如果不存在，-1标识
	
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	

	
	@Column(name="CON_ITEM_RETURNREASON",length=500)
	private String returnReason;//确认退回的时候要添加退回理由
	
	
	///////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////
	@Column(name="BILL_RECEIPT_AMOUNT",length=20 , scale = 2)
	private Double billReceiptAmount;				//已开收据总金额
	
	
	@Column(name="BILL_INVOICE_AMOUNT",length=20 , scale = 2)
	private Double billInvoiceAmount;				//已开票总金额
	
	@Column(name = "real_arrive_amount",length = 20 , scale = 2)
	private Double realArriveAmount;  				//到款总额
		
	@Column(name="SHOULD_RECE_AMOUNT",length=20 , scale = 2)
	private Double shouldReceAmount;				//应收总额
	
	@Column(name = "change_batch")
	private Long changeBatch;//变更批次

	public Long getChangeBatch() {
		return changeBatch;
	}

	public void setChangeBatch(Long changeBatch) {
		this.changeBatch = changeBatch;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Long getConfirmPeople() {
		return confirmPeople;
	}

	public void setConfirmPeople(Long confirmPeople) {
		this.confirmPeople = confirmPeople;
	}

	public Long getConItemMinfoSid() {
		return conItemMinfoSid;
	}

	public void setConItemMinfoSid(Long conItemMinfoSid) {
		this.conItemMinfoSid = conItemMinfoSid;
	}

	public Long getContractMainInfo() {
		return contractMainInfo;
	}

	public void setContractMainInfo(Long contractMainInfo) {
		this.contractMainInfo = contractMainInfo;
	}

	public String getConItemId() {
		return conItemId;
	}

	public void setConItemId(String conItemId) {
		this.conItemId = conItemId;
	}

	public Boolean getWar() {
		return War;
	}

	public void setWar(Boolean war) {
		War = war;
	}

	public String getItemResDept() {
		return itemResDept;
	}

	public void setItemResDept(String itemResDept) {
		this.itemResDept = itemResDept;
	}

	public String getItemResDeptP() {
		return itemResDeptP;
	}

	public void setItemResDeptP(String itemResDeptP) {
		this.itemResDeptP = itemResDeptP;
	}

	public Long getConItemCostSure() {
		return conItemCostSure;
	}

	public void setConItemCostSure(Long conItemCostSure) {
		this.conItemCostSure = conItemCostSure;
	}

	public Long getConItemState() {
		return conItemState;
	}

	public void setConItemState(Long conItemState) {
		this.conItemState = conItemState;
	}

	public Boolean getConModInfo() {
		return ConModInfo;
	}

	public void setConModInfo(Boolean conModInfo) {
		ConModInfo = conModInfo;
	}

	public BigDecimal getAccountmoney() {
		return accountmoney;
	}

	public void setAccountmoney(BigDecimal accountmoney) {
		this.accountmoney = accountmoney;
	}

	public Double getRemainBill() {
		return remainBill;
	}

	public void setRemainBill(Double remainBill) {
		this.remainBill = remainBill;
	}

	public Double getRemainAssistance() {
		return remainAssistance;
	}

	public void setRemainAssistance(Double remainAssistance) {
		this.remainAssistance = remainAssistance;
	}

	public Double getSysRemainBill() {
		return sysRemainBill;
	}

	public void setSysRemainBill(Double sysRemainBill) {
		this.sysRemainBill = sysRemainBill;
	}

	public Double getSysRemainAssistance() {
		return sysRemainAssistance;
	}

	public void setSysRemainAssistance(Double sysRemainAssistance) {
		this.sysRemainAssistance = sysRemainAssistance;
	}

	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}


	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Double getBillReceiptAmount() {
		return billReceiptAmount;
	}

	public void setBillReceiptAmount(Double billReceiptAmount) {
		this.billReceiptAmount = billReceiptAmount;
	}

	public Double getBillInvoiceAmount() {
		return billInvoiceAmount;
	}

	public void setBillInvoiceAmount(Double billInvoiceAmount) {
		this.billInvoiceAmount = billInvoiceAmount;
	}

	public Double getRealArriveAmount() {
		return realArriveAmount;
	}

	public void setRealArriveAmount(Double realArriveAmount) {
		this.realArriveAmount = realArriveAmount;
	}

	public Double getShouldReceAmount() {
		return shouldReceAmount;
	}

	public void setShouldReceAmount(Double shouldReceAmount) {
		this.shouldReceAmount = shouldReceAmount;
	}
}
