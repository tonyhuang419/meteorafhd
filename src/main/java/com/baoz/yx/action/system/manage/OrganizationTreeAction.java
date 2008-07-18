package com.baoz.yx.action.system.manage;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.OrganizationTree;

@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/system/manage/organizationTreeQuery.action"),
		@Result(name = "addForm", value = "/WEB-INF/jsp/system/manage/organizationTreeAddForm.jsp") })
public class OrganizationTreeAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	private Long organizationId;

	// 返回的值
	OrganizationTree organizationTree = null;

	// 要添加的子部门名称
	String subOrganizationName = null;

	/**
	 * 新增子结点，返回新增页面
	 */
	public String addChildNode() {

		// 根据organizationTreeId取得OrganizationTree对象模型
		this.organizationTree = (OrganizationTree) commonService.load(OrganizationTree.class, organizationId);

		return "addForm";
	}

	/**
	 * 
	 * todo: 根据父id(organizationId)来添加子部门
	 *
	 * return String
	 */
	public String saveChildNode() {
		
		OrganizationTree ot = (OrganizationTree) commonService.load(OrganizationTree.class, organizationId);
		
		String organizationCode = ot.getOrganizationCode(); 
		
		List tempList = commonService.list("from OrganizationTree ot where ot.organizationCode like ? order by ot.organizationCode", organizationCode + "__");
		
		String newCode = null;
		
		if (tempList.size() == 0) {
			newCode = organizationCode + "01";
		} else {
			String lastCode = ((OrganizationTree) tempList.get(tempList.size() - 1)).getOrganizationCode();
			String lastCodeNum = lastCode.substring(lastCode.length() - 2);
			int num = Integer.parseInt(lastCodeNum) + 1;
			String newCodeNum = StringUtils.leftPad(String.valueOf(num), 2, "0");
			newCode = organizationCode + newCodeNum;
		}
		
		OrganizationTree subOrganization = new OrganizationTree();
		subOrganization.setFid(organizationId);
		subOrganization.setIs_active("1");
		subOrganization.setOrganizationCode(newCode);
		subOrganization.setOrganizationName(subOrganizationName);

		commonService.save(subOrganization);
		
		return SUCCESS;

	}
	
	// 删除节点
	public String deleteNode() {

		// 删除以传入的organizationId开头的记录
		commonService.executeUpdate("update OrganizationTree ot set ot.is_active=0 where ot.id=?", organizationId);

		return SUCCESS;
	}

	

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public OrganizationTree getOrganizationTree() {
		return organizationTree;
	}

	public void setOrganizationTree(OrganizationTree organizationTree) {
		this.organizationTree = organizationTree;
	}

	public String getSubOrganizationName() {
		return subOrganizationName;
	}

	public void setSubOrganizationName(String subOrganizationName) {
		this.subOrganizationName = subOrganizationName;
	}

}
