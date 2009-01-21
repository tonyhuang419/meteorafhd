package com.exam.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类ProcessResult.java的实现描述：用于返回处理结果
 * @author xurong Oct 9, 2008 3:36:38 PM
 */
public class ProcessResult {
	private int errorCode;
	private List<String> errorMessages = new ArrayList<String>();
	private List<String> successMessages = new ArrayList<String>();
	private boolean isSuccess = false;
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void addErrorMessage(String message){
		errorMessages.add(message);
	}
	public void addSuccessMessage(String message){
		successMessages.add(message);
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public List<String> getSuccessMessages() {
		return successMessages;
	}

}
