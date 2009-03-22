package com.baoz.yx.action.firstPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.GetMonthTool;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;



@Results( {
	@Result(name = "newSignContractFrame", value = "/WEB-INF/jsp/firstPage/newSignContract/newSignContractFrame.jsp"),
	@Result(name = "newSignContractSearch", value = "/WEB-INF/jsp/firstPage/newSignContract/newSignContractSearch.jsp"),
	@Result(name = "newSignContractInfo", value = "/WEB-INF/jsp/firstPage/newSignContract/newSignContract.jsp")
})
public class NewSignContractAction extends DispatchAction {
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;
	

	private List<Object>        	yxClientCodeList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	
	
	private PageInfo info;
	private String 	groupId = null;      //组别
	private Long 	expId  = null;       //销售员
	
	private int contractType;         //合同类型 1项目类 2集成类
	
	private Date signStartDate;
	private Date signEndDate;
	
	private Double sumNewSignContract;  //新签合同总计


	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"searchNewSignContractInfo",this,new String[]{"contractType","signStartDate","signEndDate","groupId","expId"});
	
	public String frame() throws Exception {
		signStartDate = GetMonthTool.getFirstDayOfMonth();
		signEndDate = GetMonthTool.getLastDayOfMonth();
		return "newSignContractFrame";
	}

	@Override
	public String doDefault() throws Exception {
		yxClientCodeList = new ArrayList<Object>();
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();	
		return "newSignContractSearch";
	}
		
	public String right() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "fpNewSignContractClose");
		info = firstPageService.queryNewSignContractInfo(info, signStartDate, signEndDate, contractType, groupId, expId);
		
//		总计值
		sumNewSignContract = firstPageService.sumNewSignContractInfo( signStartDate, signEndDate, contractType, groupId, expId);
		
		return "newSignContractInfo";
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


	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	public Date getSignStartDate() {
		return signStartDate;
	}

	public void setSignStartDate(Date signStartDate) {
		this.signStartDate = signStartDate;
	}

	public Date getSignEndDate() {
		return signEndDate;
	}

	public void setSignEndDate(Date signEndDate) {
		this.signEndDate = signEndDate;
	}

	public Double getSumNewSignContract() {
		return sumNewSignContract;
	}

	public void setSumNewSignContract(Double sumNewSignContract) {
		this.sumNewSignContract = sumNewSignContract;
	}
	
}
