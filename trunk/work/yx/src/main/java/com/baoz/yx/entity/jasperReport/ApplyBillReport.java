package com.baoz.yx.entity.jasperReport;

import java.math.BigDecimal;
import java.util.Date;

public class ApplyBillReport implements java.io.Serializable {

	private static final long serialVersionUID = -2073890220303897970L;

	private Long billApplyId; // �?票申请系统号

	private String billApplyNum; // �?票申请编�?

	private String contactName; // 合同名称(未签使用，已签再�?)

	private Long contractMainInfo; // 合同主体信息系统�?(已签�?票用)

	private Long employeeId; // 员工代码系统�?

	private String employeeName; // 申请人姓名

	private String employeeDepartment; // 申请人部门

	private Long customerId; // 客户代码系统�?

	private String clientName; // 客户名称，开票单位

	private String clientBillBank;// 开户银行

	private String clientBillAdd;// 客户地址

	private String clientAccount;// 客户帐号

	private String clientBillPhone;// 客户联系电话

	private String clientTaxNumber;// 税号

	private String projectNo;// 项目号

	private String conId;// 正式合同号

	private Long billCustomer; // �?票客户系统号

	private Date applyId; // 申请日期

	private BigDecimal billAmountTax; // �?票金�?(含税)
	
	private String lowerBillAmountTax;//开票金额(含税)中文大写

	private BigDecimal billAmountNotax; // �?票金�?(不含�?)
	
	private String lowerBillAmountNotax; // �?票金�?(不含�?)中文大写

	private Long base; // 基准,是否含税 0-不含税；1-含税�?

	private String billContent; // �?票内�?

	private String billNature; // �?票�?�质(连接类型管理�?)

	private String billType; // �?票类�?(连接类型管理�?)

	private String remarks; // 备注

	private String billSpot; // 取票地点

	private Boolean oneOut; // 是否�?次出库�?��?��?��?�true�? false不是
	
	private String isOneOut;//是否一次出库的中文
	
	private Long applyBillState; // �?票申请状�? 1保存 2待确�? 3确认通过 4确认�?�?

	private Boolean isNoContract; // 是否未签申请...true未签 false已签

	private Boolean sign; // 是否签收 0未签收（false�? 1已签�?(true)
	
	private String 	stockOrg; //库存组织

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

	public BigDecimal getBillAmountTax() {
		return billAmountTax;
	}

	public void setBillAmountTax(BigDecimal billAmountTax) {
		this.billAmountTax = billAmountTax;
	}

	public BigDecimal getBillAmountNotax() {
		return billAmountNotax;
	}

	public void setBillAmountNotax(BigDecimal billAmountNotax) {
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

	public String getClientAccount() {
		return clientAccount;
	}

	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}

	public String getClientBillAdd() {
		return clientBillAdd;
	}

	public void setClientBillAdd(String clientBillAdd) {
		this.clientBillAdd = clientBillAdd;
	}

	public String getClientBillBank() {
		return clientBillBank;
	}

	public void setClientBillBank(String clientBillBank) {
		this.clientBillBank = clientBillBank;
	}

	public String getClientBillPhone() {
		return clientBillPhone;
	}

	public void setClientBillPhone(String clientBillPhone) {
		this.clientBillPhone = clientBillPhone;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientTaxNumber() {
		return clientTaxNumber;
	}

	public void setClientTaxNumber(String clientTaxNumber) {
		this.clientTaxNumber = clientTaxNumber;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getEmployeeDepartment() {
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getLowerBillAmountNotax() {
		return lowerBillAmountNotax;
	}

	public void setLowerBillAmountNotax(String lowerBillAmountNotax) {
		this.lowerBillAmountNotax = lowerBillAmountNotax;
	}

	public String getLowerBillAmountTax() {
		return lowerBillAmountTax;
	}

	public void setLowerBillAmountTax(String lowerBillAmountTax) {
		this.lowerBillAmountTax = lowerBillAmountTax;
	}

	public String getIsOneOut() {
		return isOneOut;
	}

	public void setIsOneOut(String isOneOut) {
		this.isOneOut = isOneOut;
	}

	public String getStockOrg() {
		return stockOrg;
	}

	public void setStockOrg(String stockOrg) {
		this.stockOrg = stockOrg;
	}

}