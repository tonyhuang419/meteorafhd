package com.baoz.yx.action.fileManager;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXFileManage;
import com.baoz.yx.utils.UploadUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;

@Results({
@Result(name = "success", type =ServletRedirectResult.class, value = "/fileManager/fileUpLoadQuery.action?isSuccess=true"),
@Result(name = "input", value = "/WEB-INF/jsp/filemanage/fileUpfail.jsp")
})
 
 public class FileUpLoadAction extends ActionSupport{
     private static final long serialVersionUID = 572146812454l ;
     private static final int BUFFER_SIZE = 16 * 1024 ;
    
     @Autowired
 	 @Qualifier("commonService")
 	 private ICommonService service;
 	 @Qualifier("queryService")
 	 private IQueryService queryService;
 	 private PageInfo info;
 	 private ServletRequest request;
     
     private File myFile;
     private String contentType;
     private String fileName;
     private String imageFileName;
     private String caption;
 	 private YXFileManage fm;
    
     public String execute() throws Exception {
    	 if(myFile!=null && myFile.exists()){    	 
	    	 imageFileName = new Date().getTime() + "_"+ fileName;
	    	 InputStream stream = TestfileuploadAction.class.getClassLoader().getResourceAsStream("/yx.properties");
			 Properties p = new Properties();
			 p.load(stream);
	         File imageFile = new File(UploadUtils.getUploadDir()+"/"+ UploadUtils.getUploadFileOpPath(imageFileName));
	         System.out.println("copy file:"+myFile+" to "+imageFile);
	         FileUtils.copyFile(myFile, imageFile);
	         
	         fm.setFileroute(UploadUtils.getUploadFileOpPath(imageFileName));
	         fm.setFilesize(imageFile.length());
			 Employee user=UserUtils.getUser(); 
			 fm.setPersoncode(user.getId());
		     fm.setFiledate(new Date());
			 service.save(fm);
			 ActionContext.getContext().put("isSuccess", "true");
    	 }    	 
         return "success";
 	}
          
     public void setMyFileContentType(String contentType)  {
         this.contentType = contentType;
    } 
    
     public void setMyFileFileName(String fileName)  {
         this.fileName = fileName;
    } 
        
     public void setMyFile(File myFile)  {
         this.myFile = myFile;
    } 
    
     public String getImageFileName()  {
         return imageFileName;
    } 
    
     public String getCaption()  {
         return caption;
    } 
 
      public void setCaption(String caption)  {
         this.caption = caption;
    }

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public YXFileManage getFm() {
		return fm;
	}

	public void setFm(YXFileManage fm) {
		this.fm = fm;
	} 

} 