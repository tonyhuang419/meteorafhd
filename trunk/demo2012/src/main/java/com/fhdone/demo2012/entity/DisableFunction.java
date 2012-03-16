package com.fhdone.demo2012.entity;

import java.io.Serializable;

public class DisableFunction  implements Serializable {
   
	private static final long serialVersionUID = 8933635404720729080L;
	private Long id;
    private String companyCd;
    private String functionName;
    private UserLog userLog;
    
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
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public UserLog getUserLog() {
		return userLog;
	}
	public void setUserLog(UserLog userLog) {
		this.userLog = userLog;
	}

 
}
