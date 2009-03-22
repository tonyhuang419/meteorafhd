package com.baoz.yx.action.billtoReceipt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.baoz.core.web.struts2.DispatchAction;
import com.opensymphony.xwork2.ActionContext;

public class ApplyBillShowUploadFileAction extends DispatchAction {

	private Long applyBillId;
	
	private String inputPath; // 指定要被下载的文件路径 
	private InputStream inputStream;
	private String fileName;
	

	public String downLoadFile()throws Exception{
		inputPath=(String)ActionContext.getContext().getSession().get("applyBillFilePath");
		String fname=(String)ActionContext.getContext().getSession().get("applyBillFileName");
		fileName=new String(fname.getBytes("GBK"),"iso-8859-1");
		return SUCCESS;
	}
	public InputStream getInputStream() throws Exception {
		inputStream = new FileInputStream(inputPath);
		// 通过 ServletContext，也就是application 来读取数据 
		return inputStream ; 
	} 
	
	public String getInputPath() {
		return inputPath;
	}


	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}




	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Long getApplyBillId() {
		return applyBillId;
	}


	public void setApplyBillId(Long applyBillId) {
		this.applyBillId = applyBillId;
	}

	
}
