package com.baoz.yx.action.fileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXFileManage;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UploadUtils;
import com.baoz.yx.utils.UserUtils;

/**
 * 文件下载查询操作
 * 
 * @author 
 */
@Results({
@Result(name = "success", type =ServletRedirectResult.class, value = "/fileManager/fileDownSelect.action" ),
@Result(name = "queryList", value = "/WEB-INF/jsp/filemanage/fileDownList.jsp")})
public class FileDownListQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private PageInfo info;
	private YXFileManage fm;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	private List<YXFileManage> filemanage;

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<YXFileManage> getFilemanage() {
		return filemanage;
	}

	public void setFilemanage(List<YXFileManage> filemanage) {
		this.filemanage = filemanage;
	}

	@Override
	public String doDefault() throws Exception {
		this.logger.info("文件下载查询");
		info = queryService.listQueryResult("select fm,cc.name,ee.name from YXFileManage fm,YXClientCode cc,Employee ee where fm.clientcode=cc.id and fm.personcode=ee.id order by fm.filedate desc", info);
		return "queryList";
	}
	
	public String delete() {
		 logger.info("删除类型");	
		 if (filemanage != null && filemanage.size() > 0){
			for (YXFileManage delSelf:filemanage) 
			{
				if (delSelf.getId() != null)
				{
					YXFileManage toDel = (YXFileManage) service.load(YXFileManage.class, delSelf.getId());
					service.delete(toDel);
			        File imageFile = new File(UploadUtils.getUploadDir(),toDel.getFileroute());
			        if(imageFile.exists()){
			        	 imageFile.delete();
			        } 				    
				}
			}
		 logger.info("类型删除->成功");
		 }
		 return SUCCESS;
	}
	
	public void setIds(Long[] ids) {
		if (filemanage == null)
			filemanage = new ArrayList<YXFileManage>();
		for (Long id : ids) {
			YXFileManage file_manage = new YXFileManage();
			file_manage.setId(id);
			filemanage.add(file_manage);
		}
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

	public YXFileManage getFm() {
		return fm;
	}

	public void setFm(YXFileManage fm) {
		this.fm = fm;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
}
