package com.fhdone.demo2012.entity;

import java.io.Serializable;
import java.util.Date;



public class UserLog implements Serializable {

	private static final long serialVersionUID = -2838133393991085585L;
	private Long id;
    private String companyCd;
    private String userCd;
    private String actionName;
    private Date operationTime;
    private String parameterInfo;
	
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getParameterInfo() {
		return parameterInfo;
	}
	public void setParameterInfo(String parameterInfo) {
		this.parameterInfo = parameterInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



  
}
