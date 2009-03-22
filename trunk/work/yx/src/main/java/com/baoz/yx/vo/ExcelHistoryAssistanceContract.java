package com.baoz.yx.vo;

import java.util.Date;

public class ExcelHistoryAssistanceContract {
	private String AssistanceContractCode;			//合同编号	
	private String MainProjectCode;					//项目代码
	private String MainProjectName;					//项目名称
	private String SupplyName;						//外协单位名称
	private Double TotalMoney;						//合同总额
	private String ContractMaker;					//合同经办人
	private Date SignDate;							//签定日期
	private Date Expiration;						//合同有效期
	private String PayMethod;						//付款方式
	private String Remark;							//备注

	public String getAssistanceContractCode() {
		return AssistanceContractCode;
	}
	public void setAssistanceContractCode(String assistanceContractCode) {
		AssistanceContractCode = assistanceContractCode;
	}

	public String getMainProjectCode() {
		return MainProjectCode;
	}
	public void setMainProjectCode(String mainProjectCode) {
		MainProjectCode = mainProjectCode;
	}
	public String getMainProjectName() {
		return MainProjectName;
	}
	public void setMainProjectName(String mainProjectName) {
		MainProjectName = mainProjectName;
	}
	public Double getTotalMoney() {
		return TotalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		TotalMoney = totalMoney;
	}
	public String getContractMaker() {
		return ContractMaker;
	}
	public void setContractMaker(String contractMaker) {
		ContractMaker = contractMaker;
	}
	public Date getSignDate() {
		return SignDate;
	}
	public void setSignDate(Date signDate) {
		SignDate = signDate;
	}
	public Date getExpiration() {
		return Expiration;
	}
	public void setExpiration(Date expiration) {
		Expiration = expiration;
	}
	public String getPayMethod() {
		return PayMethod;
	}
	public void setPayMethod(String payMethod) {
		PayMethod = payMethod;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getSupplyName() {
		return SupplyName;
	}
	public void setSupplyName(String supplyName) {
		SupplyName = supplyName;
	}
}
