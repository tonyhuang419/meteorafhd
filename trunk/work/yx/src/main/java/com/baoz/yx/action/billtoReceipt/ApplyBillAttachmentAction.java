package com.baoz.yx.action.billtoReceipt;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.yx.utils.TemporaryFileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/billtoReceipt/attachmentMain.jsp")
	
})
public class ApplyBillAttachmentAction extends ActionSupport{

	private File myFile;
	
	private String fileName;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		String extension=FilenameUtils.getExtension(fileName);
		File f = TemporaryFileUtils.newTemporaryFile(extension);
		FileUtils.copyFile(myFile, f);
		
		ActionContext.getContext().getSession().put("applyBillFilePath", f.getAbsolutePath());
		ActionContext.getContext().getSession().put("applyBillFileName", fileName);
		
		return SUCCESS;
	}
    public void setMyFileFileName(String fileName)  {
        this.fileName = fileName;
   } 
    public void setMyFile(File myFile)  {
        this.myFile = myFile;
   } 
	
}
