package com.baoz.yx.action.harvestMeansManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/w_harvestMeansSearch.jsp")
public class W_HarvestMeansSearchShowAction extends DispatchAction {

	// @Autowired
	// @Qualifier("queryService")
	// private IQueryService queryService;
	// @Autowired
	// @Qualifier("systemService")
	// private ISystemService systemService;
	// @Autowired
	// @Qualifier("commonService")
	// private ICommonService commonService;
	// private PageInfo info;
	// @Autowired
	// @Qualifier("yxTypeManageService")
	// private IYXTypeManageService typeManageService;
	// private List<YXTypeManage> billTypeName;// 开票类型名称
	// private List<Object> listExp; // 查询显示所有的销售员
	// private List<Department> groupList;// 查询小组
	// private List<Object> listCli; // 查询显示所有的客户

	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	
	
	private ReveInfo ri;
	List<List<ReveInfo>> reveList = new ArrayList<List<ReveInfo>>();

	@Override
	public String doDefault() throws Exception {
		// this.logger.info("初始化收款管理查询页面");

		// billTypeName= typeManageService.getYXTypeManage(1004L);// 开票类型
		// String expHql = "from Employee d where d.id not in(0) and
		// d.is_active!=0";
	
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();	
		
		return "queryList";

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
