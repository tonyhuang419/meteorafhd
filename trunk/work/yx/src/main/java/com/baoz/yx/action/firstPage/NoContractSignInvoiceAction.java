package com.baoz.yx.action.firstPage;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;



@Results( {
	@Result(name = "noContractInvoiceFrame", value = "/WEB-INF/jsp/firstPage/noContractInvoice/noContractInvoiceFrame.jsp"),
	@Result(name = "noContractInvoiceSearch", value = "/WEB-INF/jsp/firstPage/noContractInvoice/noContractInvoiceSearch.jsp"),
	@Result(name = "noContractInvoiceInfo", value = "/WEB-INF/jsp/firstPage/noContractInvoice/noContractInvoiceInfo.jsp")
})
public class NoContractSignInvoiceAction extends DispatchAction {
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;
	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	
	private List<Object>        	yxClientCodeList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	
	
	private PageInfo info;
	private String 	groupId = null;      //组别
	private Long 	expId  = null;      //销售员

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"noContractInvoiceSearchInfo",this,new String[]{"groupId","expId"});
	
	

	public String frame() throws Exception {
		return "noContractInvoiceFrame";
	}


	@Override
	public String doDefault() throws Exception {
		yxClientCodeList = new ArrayList<Object>();
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();	
		return "noContractInvoiceSearch";
	}
	
	
	public String right() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "fpNoContractInvoice");
		info = firstPageService.countNoSignInvoiceInfo(info,  groupId, expId);
		return "noContractInvoiceInfo";
	}
	

	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public Long getExpId() {
		return expId;
	}


	public void setExpId(Long expId) {
		this.expId = expId;
	}


	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IFirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(IFirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}


	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}


	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}


	
}
