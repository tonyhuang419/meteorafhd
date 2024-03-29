package com.baoz.yx.entity.bill;

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

//开票申请表

@Entity
@Table(name = "yx_apply_bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.Entity(selectBeforeUpdate = true, dynamicInsert = true, dynamicUpdate = true)
@Proxy(lazy = false)
public class ApplyBill implements java.io.Serializable {
	private static final long serialVersionUID = -7655112414185255156L;

	@Id
	@TableGenerator(name = "sid", table = "sysid", pkColumnName = "id", valueColumnName = "tableid", pkColumnValue = "85", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sid")
	@Column(name = "apply_bill_sid", length = 20)
	private Long billApplyId; // 开票申请系统号

	@Column(name = "bill_apply_num", length = 20)
	private String billApplyNum; // 开票申请编号

	@Column(name = "contact_name", length = 100)
	private String contactName; // 合同名称

	@Column(name = "first_receive_man", length = 100)
	private String firstReceiveMan; // 甲方接收人

	@Column(name = "fk_realPlan_Id", length = 100)
	private Long realPlanId; // 实际表系统号

	@Column(name = "fk_con_main_info_sid")
	private Long contractMainInfo; // 合同主体信息系统号

	@Column(name = "fk_employeeId")
	private Long employeeId; // 员工代码系统号

	@Column(name = "fk_customerId")
	private Long customerId; // 客户代码系统号

	@Column(name = "fk_bill_customer")
	private Long billCustomer; // 开票客户系统号

	@Column(name = "apply_date")
	private Date applyId; // 申请日期

	@Column(name = "bill_amount_tax", length = 20, scale = 2)
	private Double billAmountTax; // 开票金额(含税)

	@Column(name = "bill_amount_notax", length = 20, scale = 2)
	private Double billAmountNotax; // 开票金额(不含税)

	@Column(name = "base")
	private Long base; // 基准,是否含税 0-不含税；1-含税；

	@Column(name = "bill_content", length = 400)
	private String billContent; // 开票内容

	@Column(name = "fk_bill_nature")
	private String billNature; // 开票性质(连接类型管理表)

	@Column(name = "fk_bill_type", length = 100)
	private String billType; // 开票类型(连接类型管理表)

	@Column(name = "remarks", length = 500)
	private String remarks; // 备注

	@Column(name = "return_reason", length = 500)
	private String returnReason; // 退回理由

	@Column(name = "return_date")
	private Date returnDate; // 退回理由,退回时间

	@Column(name = "one_out")
	private Boolean oneOut; // 是否一次出库。。。。true是 false不是

	@Column(name = "apply_bill_state", length = 1)
	private Long applyBillState; // 开票申请状态 1保存 2待确认 3确认通过 4确认退回 5 已开票 7已处理 6 已签收（处理完的才签收的）

	@Column(name = "is_no_contract")
	private Boolean isNoContract; // 是否未签申请...true 1 未签 false 0已签

	@Column(name = "is_sign", length = 2)
	private Boolean sign; // 是否签收 0未签收（false） 1已签收(true)

	@Column(name = "stock_org", length = 20)
	private String stockOrg; // 库存组织

	@Column(name = "bill_spot", length = 100)
	private String billSpot; // 取票地点

	/*
	 * 开票申请最初时 申请类型
	 */
	@Column(name = "init_is_no_contract")
	private Long initIsNoContract; // 0 已签申请 1 未签申请

	/*
	 * 开票申请最初时 申请入口
	 */
	@Column(name = "apply_way")
	private Long applyWay; // 0 预决算开票申请 1 合同已签开票申请 2 正式合同管理——收据申请

	@Column(name = "item_sid")
	private Long itemSid; // 项目主体系统。。。。
	
	@Column(name = "IS_IMPORT_FROM_FILE")
	private Boolean importFromFile; //是否从文件导入
	
	public Boolean getImportFromFile() {
		return importFromFile;
	}

	public void setImportFromFile(Boolean importFromFile) {
		this.importFromFile = importFromFile;
	}

	public Long getInitIsNoContract() {
		return initIsNoContract;
	}

	public void setInitIsNoContract(Long initIsNoContract) {
		this.initIsNoContract = initIsNoContract;
	}

	public String getStockOrg() {
		return stockOrg;
	}

	public void setStockOrg(String stockOrg) {
		this.stockOrg = stockOrg;
	}

	public Boolean getSign() {
		return sign;
	}

	public void setSign(Boolean sign) {
		this.sign = sign;
	}

	public Long getBillApplyId() {
		return billApplyId;
	}

	public void setBillApplyId(Long billApplyId) {
		this.billApplyId = billApplyId;
	}

	public String getBillApplyNum() {
		return billApplyNum;
	}

	public void setBillApplyNum(String billApplyNum) {
		this.billApplyNum = billApplyNum;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Long getContractMainInfo() {
		return contractMainInfo;
	}

	public void setContractMainInfo(Long contractMainInfo) {
		this.contractMainInfo = contractMainInfo;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getApplyId() {
		return applyId;
	}

	public void setApplyId(Date applyId) {
		this.applyId = applyId;
	}

	public Double getBillAmountTax() {
		return billAmountTax;
	}

	public void setBillAmountTax(Double billAmountTax) {
		this.billAmountTax = billAmountTax;
	}

	public Double getBillAmountNotax() {
		return billAmountNotax;
	}

	public void setBillAmountNotax(Double billAmountNotax) {
		this.billAmountNotax = billAmountNotax;
	}

	public Long getBase() {
		return base;
	}

	public void setBase(Long base) {
		this.base = base;
	}

	public String getBillContent() {
		return billContent;
	}

	public void setBillContent(String billContent) {
		this.billContent = billContent;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBillSpot() {
		return billSpot;
	}

	public void setBillSpot(String billSpot) {
		this.billSpot = billSpot;
	}

	public Boolean getOneOut() {
		return oneOut;
	}

	public void setOneOut(Boolean oneOut) {
		this.oneOut = oneOut;
	}

	public Long getApplyBillState() {
		return applyBillState;
	}

	public void setApplyBillState(Long applyBillState) {
		this.applyBillState = applyBillState;
	}

	public Boolean getIsNoContract() {
		return isNoContract;
	}

	public void setIsNoContract(Boolean isNoContract) {
		this.isNoContract = isNoContract;
	}

	public String getBillNature() {
		return billNature;
	}

	public void setBillNature(String billNature) {
		this.billNature = billNature;
	}

	public Long getBillCustomer() {
		return billCustomer;
	}

	public void setBillCustomer(Long billCustomer) {
		this.billCustomer = billCustomer;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getRealPlanId() {
		return realPlanId;
	}

	public void setRealPlanId(Long realPlanId) {
		this.realPlanId = realPlanId;
	}

	public Long getItemSid() {
		return itemSid;
	}

	public void setItemSid(Long itemSid) {
		this.itemSid = itemSid;
	}

	public Long getApplyWay() {
		return applyWay;
	}

	public void setApplyWay(Long applyWay) {
		this.applyWay = applyWay;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getFirstReceiveMan() {
		return firstReceiveMan;
	}

	public void setFirstReceiveMan(String firstReceiveMan) {
		this.firstReceiveMan = firstReceiveMan;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

}