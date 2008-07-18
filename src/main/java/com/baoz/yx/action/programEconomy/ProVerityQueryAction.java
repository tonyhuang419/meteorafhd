package com.baoz.yx.action.programEconomy;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

/**
 * 初始化部分查询信息,点击次action条到条件查询页
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/programEconomy/proVerifyQuery.jsp")
// 跳转到输入查询条件页
public class ProVerityQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private List<Object> listExp; // 查询显示所有的销售员
	private List<Object> listCli; // 查询显示所有的客户
	private List<Department> groupList;
	
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private List<YXTypeManage>      contractTypeList;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		// 查询条件 销售员,审购类型（2种）,客户,审购日期
	/*	String expHql = "from Employee d where d.id not in(0) and d.is_active!=0";
		String cliHql = "from YXClientCode d where d.id not in(0) and d.is_active!=0";
		Long uid = UserUtils.getUser().getId();
		listExp = service.list(expHql);
		listCli = service
				.list("from YXClientCode d where d.id not in(0) and d.is_active=1");*/
		//groupList = UserUtils.getUserDetail().getDepartments();
		
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

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
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

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

}
