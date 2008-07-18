package com.baoz.yx.action.harvestMeansManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;




/**
 * 收款管理查询
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/harvestMeansSearch.jsp")
public class HarvestMeansSearchShowAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	private PageInfo info;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private List<YXTypeManage> billTypeName;// 开票类型名称
	private List<Object> listExp; // 查询显示所有的销售员
	private List<Department> groupList;// 查询小组
	private List<Object> listCli; // 查询显示所有的客户

	private ReveInfo ri;
	List<List<ReveInfo>> reveList = new ArrayList<List<ReveInfo>>();
	
	@Override
	@SuppressWarnings("unchecked")
	
	public String doDefault() throws Exception {
		this.logger.info("初始化收款管理查询页面");

		 billTypeName= typeManageService.getYXTypeManage(1004L);// 开票类型
			String expHql = "from Employee d where d.id not in(0) and d.is_active!=0";
			// 先得到当前登入销售员的id,通过该id取出该销售员对应的客户
			listExp = new ArrayList<Object>();
			groupList =  UserUtils.getUserDetail().getDepartments();
			listCli = new ArrayList<Object>();
			return "queryList";

		
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

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}





	public List<Object> getListCli() {
		return listCli;
	}

	public void setListCli(List<Object> listCli) {
		this.listCli = listCli;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}



	public ReveInfo getRi() {
		return ri;
	}

	public void setRi(ReveInfo ri) {
		this.ri = ri;
	}

	public List<List<ReveInfo>> getReveList() {
		return reveList;
	}

	public void setReveList(List<List<ReveInfo>> reveList) {
		this.reveList = reveList;
	}

	public List<YXTypeManage> getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(List<YXTypeManage> billTypeName) {
		this.billTypeName = billTypeName;
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



}
