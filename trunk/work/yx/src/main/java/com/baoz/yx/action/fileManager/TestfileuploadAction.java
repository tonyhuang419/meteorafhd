package com.baoz.yx.action.fileManager;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.utils.DateUtil;
import com.baoz.core.web.struts2.DispatchAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;

@Results({
@Result(name = "success", value = "/WEB-INF/jsp/fileUpDown/testfile.jsp"),
@Result(name = "input", value = "/WEB-INF/jsp/fileUpDown/testfilesuccess.jsp")
})
 
 public class TestfileuploadAction extends DispatchAction{
     private static final long serialVersionUID = 572146812454l ;
     private static final int BUFFER_SIZE = 16 * 1024 ;
    
     private File myFile;
     private String contentType;
     private String fileName;
     private String imageFileName;
     private String caption;
    
     public String doUpload() throws Exception {
    	 if(myFile!=null && myFile.exists()){
    	 
	    	 imageFileName = new Date().getTime() + "_"+ fileName;
	    	 InputStream stream = TestfileuploadAction.class.getClassLoader().getResourceAsStream("/yx.properties");
			 Properties p = new Properties();
			 p.load(stream);
	         File imageFile = new File(p.getProperty("upload_dir")+"/"+ DateUtil.format(new Date(), "yyyy-MM-dd")+ "/" + imageFileName);
//	         System.out.println("copy file:"+myFile+" to "+imageFile);
	         FileUtils.copyFile(myFile, imageFile);
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

} 