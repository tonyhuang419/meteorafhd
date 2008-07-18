package com.baoz.yx.action.programEconomy;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

/**
 * 初始化部分查询信息,点击次action条到条件查询页
 * 
 * @author yang yuan
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/programEconomy/enterQuery.jsp")
// 跳转到输入查询条件页
public class EnQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private List<YXTypeManage> sectionTypeManageList;

	
	
	private PageInfo 				info;

	private List<YXTypeManage>      contractTypeList;

	
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;	
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}

	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}

	public List<YXTypeManage> getSectionTypeManageList() {
		return sectionTypeManageList;
	}

	public void setSectionTypeManageList(
			List<YXTypeManage> sectionTypeManageList) {
		this.sectionTypeManageList = sectionTypeManageList;
	}

	private List<Object> listCli; // 查询显示所有的客户

 
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		sectionTypeManageList = typeManageService.getYXTypeManage(1011l);
		// 查询条件 销售员,审购类型（2种）,客户,审购日期
		/*String expHql = "from Employee d where d.id not in(0) and d.is_active!=0";
		// 先得到当前登入销售员的id,通过该id取出该销售员对应的客户
		Long exployeeId = 8l;
		String cliHql = "from YXClientCode d where d.id not in(0) and d.is_active!=0";
		listExp = service.list(expHql);
		listCli = service.list("from YXClientCode d where d.id not in(0) and d.is_active=1");

		//groupList = UserUtils.getUserDetail().getDepartments();
*/		
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yxClientCodeList = new ArrayList<Object>();
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		listExp = new ArrayList<Object>();
		
		groupList = UserUtils.getUserDetail().getDepartments();
		
		return "queryList";
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Object> getListCli() {
		return listCli;
	}

	public void setListCli(List<Object> listCli) {
		this.listCli = listCli;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

}
