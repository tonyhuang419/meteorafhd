package com.baoz.yx.action.billtoReceipt;

import java.io.File;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/billtoReceipt/attachmentMain.jsp")
	
})
public class ApplyBillAttachmentMainAction extends DispatchAction{

	private Long applyBillId;//申请单id
	
	@Override
	public String doDefault() throws Exception {
		
		/**
		 *进入页面
		 **/
		return SUCCESS;
	}

	public String deleteUploadFile()throws Exception{
		String filePath=(String)ActionContext.getContext().getSession().get("applyBillFilePath");
		//String fileName=(String)ActionContext.getContext().getSession().get("applyBillFileName");
		File myFile=new File(filePath);
		myFile.delete();
		ActionContext.getContext().getSession().remove("applyBillFilePath");
		ActionContext.getContext().getSession().remove("applyBillFileName");
		return SUCCESS;
	}

	public Long getApplyBillId() {
		return applyBillId;
	}

	public void setApplyBillId(Long applyBillId) {
		this.applyBillId = applyBillId;
	}
	
}
