package com.baoz.yx.action.fileManager;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.baoz.yx.entity.YXFileManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	根据查询条件显示文件管理
 *  
 */
@Results({
	@Result(name = "queryList1", value = "/WEB-INF/jsp/filemanage/fileDownList.jsp"),
	@Result(name = "queryList2", value = "/WEB-INF/jsp/filemanage/fileDownList.jsp")})

public class FileDownSelectAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 				systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo 					info;
    private String						filetype;
    private Long						clientcode;
    private Long						personcode;
    private String                      filename;
    private String				uploadDate1;
    private String				uploadDate2;
    private String              filecontent;
    
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
    
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"FileDownSelectParameter",this,new String[]{"filetype","clientcode","personcode"
    				,"filename","uploadDate1","uploadDate2","filecontent"});
    
    private ServletRequest request;

	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append("select fm,cc.name,ee.name from YXFileManage fm,YXClientCode cc,Employee ee where fm.clientcode=cc.id and fm.personcode=ee.id ");
		if (StringUtils.isNotBlank(filetype)) {
			str.append(" and fm.filetype = '").append(filetype).append("'");
		}
		if (clientcode != null) {
			str.append(" and fm.clientcode =").append(clientcode);
		}
		if (personcode != null) {
			str.append(" and fm.personcode =").append(personcode);
		}
		if (StringUtils.isNotBlank(filename)){
			str.append(" and fm.filename like '%").append(filename).append("%'");
		}
		if (StringUtils.isNotBlank(filecontent)) {
			str.append(" and fm.remarks like '%").append(filecontent).append("%'");
		}
		if (StringUtils.isNotBlank(uploadDate1)){
			str.append(" and fm.filedate >= to_date('"+uploadDate1+"','yyyy-MM-dd')");
		}
		if (StringUtils.isNotBlank(uploadDate2)){
			str.append(" and fm.filedate < to_date('"+uploadDate2+"','yyyy-MM-dd')+1 ");
		}
		str.append(" order by fm.filedate desc");	
		
		info = queryService.listQueryResult(str.toString(), info);
		return "queryList1";
	}

    public String fileSearch() throws Exception{
	    String typeSmall = request.getParameter("typeSmall");	
	    String clientcode = request.getParameter("clientcode");	
	    StringBuffer str = new StringBuffer();
		str.append("select fm,cc from YXFileManage fm,YXClientCode cc where fm.clientcode=cc.id ");
		if (StringUtils.isNotBlank(typeSmall)) {
			str.append(" and fm.filetype = '").append(typeSmall).append("'");
		}
		if (StringUtils.isNotBlank(clientcode)) {
			str.append(" and cc.id = ").append(clientcode);
		}
		info = queryService.listQueryResult(str.toString(), info);
	    return "queryList2";
    }
	
    public void setServletRequest(HttpServletRequest arg0) {
	    this.request=arg0;		
    }
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Long getClientcode() {
		return clientcode;
	}

	public void setClientcode(Long clientcode) {
		this.clientcode = clientcode;
	}

	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUploadDate1() {
		return uploadDate1;
	}

	public void setUploadDate1(String uploadDate1) {
		this.uploadDate1 = uploadDate1;
	}

	public String getUploadDate2() {
		return uploadDate2;
	}

	public void setUploadDate2(String uploadDate2) {
		this.uploadDate2 = uploadDate2;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}
	
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public Long getPersoncode() {
		return personcode;
	}

	public void setPersoncode(Long personcode) {
		this.personcode = personcode;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}

