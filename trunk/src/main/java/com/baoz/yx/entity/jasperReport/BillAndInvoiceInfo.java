package com.baoz.yx.entity.jasperReport;

import java.util.Date;

public class BillAndInvoiceInfo implements java.io.Serializable{

	private String billApplyNum;//申请编号
	
	private String customerName;//通过customerId关联起来
	
	private Date applyId;//申请日期
	
	private String projectNos;//项目编号
	
	private String mainInfoName;//合同名称
	
	private String ticketNo;//发票号
	
	private String ticketMoney;//发票金额
	
	private String ticketType;//发票类型
	
	private String createTicketDate;//开票日期
	
	private String imgUrl;//图片路径
	
	
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getProjectNos() {
		return projectNos;
	}

	public void setProjectNos(String projectNos) {
		this.projectNos = projectNos;
	}

	public String getMainInfoName() {
		return mainInfoName;
	}

	public void setMainInfoName(String mainInfoName) {
		this.mainInfoName = mainInfoName;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTicketMoney() {
		return ticketMoney;
	}

	public void setTicketMoney(String ticketMoney) {
		this.ticketMoney = ticketMoney;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getCreateTicketDate() {
		return createTicketDate;
	}

	public void setCreateTicketDate(String createTicketDate) {
		this.createTicketDate = createTicketDate;
	}

	public String getBillApplyNum() {
		return billApplyNum;
	}

	public void setBillApplyNum(String billApplyNum) {
		this.billApplyNum = billApplyNum;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getApplyId() {
		return applyId;
	}

	public void setApplyId(Date applyId) {
		this.applyId = applyId;
	}
	
}
