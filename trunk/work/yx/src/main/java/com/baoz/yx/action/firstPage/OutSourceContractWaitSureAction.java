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
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;



@Results( {
	@Result(name = "outSourceContractWaitSureFrame", value = "/WEB-INF/jsp/firstPage/outSourceContractWaitSure/outSourceContractWaitSureFrame.jsp"),
	@Result(name = "outSourceContractWaitSureSearch", value = "/WEB-INF/jsp/firstPage/outSourceContractWaitSure/outSourceContractWaitSureSearch.jsp"),
	@Result(name = "outSourceContractWaitSureInfo", value = "/WEB-INF/jsp/firstPage/outSourceContractWaitSure/outSourceContractWaitSureInfo.jsp")
})
public class OutSourceContractWaitSureAction extends DispatchAction {
	
	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;
	
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	
	
	private PageInfo info;
	private String 	groupId = null;      //组别
	private Long 	expId  = null;      //销售员

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"outSourceContractWaitSureInfo",this,new String[]{"groupId","expId"});
	

	public String frame() throws Exception {
		return "outSourceContractWaitSureFrame";
	}

	@Override
	public String doDefault() throws Exception {
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();	
		return "outSourceContractWaitSureSearch";
	}
	
	public String right() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "fpQueryOutSourceContractWaitSure");
		info = firstPageService.queryOutSourceContractWaitSure(info,  groupId, expId);
		return "outSourceContractWaitSureInfo";
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

	public IFirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(IFirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}


	
}
