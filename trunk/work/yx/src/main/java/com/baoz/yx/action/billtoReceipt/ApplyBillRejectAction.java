package com.baoz.yx.action.billtoReceipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;


@Results( {
	@Result(name = "applyBillRejectionFrame", value = "/WEB-INF/jsp/invoiceManagement/applyBillReject/applyBillRejectFrame.jsp"),
	@Result(name = "applyBillRejectionSearch", value = "/WEB-INF/jsp/invoiceManagement/applyBillReject/applyBillRejectSearch.jsp"),
	@Result(name = "applyBillRejectionInfo", value = "/WEB-INF/jsp/invoiceManagement/applyBillReject/applyBillRejectInfo.jsp")
})
public class ApplyBillRejectAction extends DispatchAction {

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	

	private PageInfo info;
	private String 	groupId;      //组别
	private Long 	expId;        //销售员
	
	private Map<Long,List<String>> itemNoList;   //开票申请系统号，项目号


	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"searchNoContractReveInfo",this,new String[]{"groupId","expId"});

	public String frame() throws Exception {
		return "applyBillRejectionFrame";
	}


	@Override
	public String doDefault() throws Exception {
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();	
		return "applyBillRejectionSearch";
	}

	public String right() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "applyBillReject");
		info = invoiceService.queryApplyBillRejectInfo(info, groupId, expId);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "applyBillRejectionInfo";
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


	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}


	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}


	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}


	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}





}
