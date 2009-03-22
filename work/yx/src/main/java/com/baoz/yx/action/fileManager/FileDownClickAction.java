package com.baoz.yx.action.fileManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXFileManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.opensymphony.xwork2.ActionContext;




/**
 * 文件下载弹出页面
 * 
 * @author 
 */
@Results({
	@Result(name = "queryList", value = "/WEB-INF/jsp/filemanage/fileDownClickForm.jsp")})

public class FileDownClickAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	private ServletRequest request;
	private YXFileManage fm;
	private Object[] fileInfo;
	
	//////////////////////
	private String inputPath; // 指定要被下载的文件路径 
	private InputStream inputStream;
	private String fileName;
	/////////////////////////////

	public InputStream getInputStream() throws Exception {
		inputStream = new FileInputStream(inputPath);
		// 通过 ServletContext，也就是application 来读取数据 
		return inputStream ; 
	} 
	
	 public String downLoad() throws Exception{
		 String idss = request.getParameter("id");
		 Long id = Long.valueOf(idss);
		 YXFileManage o = (YXFileManage) service.uniqueResult("from YXFileManage obj where obj.id='" + id + "'");
		 InputStream stream = TestfileuploadAction.class.getClassLoader().getResourceAsStream("/yx.properties");
		 Properties p = new Properties();
		 p.load(stream);
		 inputPath=p.getProperty("upload_dir")+"/"+o.getFileroute();
		 String orgName = FilenameUtils.getBaseName(o.getFilename())+"."+FilenameUtils.getExtension(o.getFileroute());
		 fileName = new String(orgName.getBytes("GBK"),"iso-8859-1");
	     return SUCCESS;
	 }

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;		
	}

	public YXFileManage getFm() {
		return fm;
	}

	public void setFm(YXFileManage fm) {
		this.fm = fm;
	}
	
	public String enterUpdate() throws Exception {		
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);	
		fileInfo = (Object[])service.uniqueResult("select fm,cc.name,ee.name from YXFileManage fm,YXClientCode cc,Employee ee where fm.clientcode=cc.id and fm.personcode=ee.id and fm.id='" + id + "'");
//		YXFileManage o = (YXFileManage) service
//				.uniqueResult("from YXFileManage obj where obj.id='" + id + "'");
//		this.fm = o;
		return "queryList";
	}
	
	

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
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

	public Object[] getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(Object[] fileInfo) {
		this.fileInfo = fileInfo;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	
}
