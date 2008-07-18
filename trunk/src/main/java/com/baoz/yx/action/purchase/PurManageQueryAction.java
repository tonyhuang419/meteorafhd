package com.baoz.yx.action.purchase;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;

@Results( {
		@Result(name = "queryList", value = "/WEB-INF/jsp/purchase/purchaseManage.jsp"),
		@Result(name = "confirmPurchase", value = "/WEB-INF/jsp/purchase/confirmPurchase.jsp"),
		@Result(name = "confirmSearch", value = "/WEB-INF/jsp/purchase/purchaseConfirmSearch.jsp"),
		@Result(name = "manageSearch", value = "/WEB-INF/jsp/purchase/purchaseManageSearch.jsp"),
		@Result(name = "manage", value = "/WEB-INF/jsp/purchase/manageMain.jsp"),
		@Result(name = "confirm", value = "/WEB-INF/jsp/purchase/confirmMain.jsp")})
public class PurManageQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("purService")
	private IPurService pruService;

	private ApplyMessage am;
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	
	private List<YXTypeManage>      contractTypeList;
	


	private PageInfo 				info;
	private List<Object>        	yxClientCodeList;
	private List<YXTypeManage>		dutyDepartmentIdList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;	
	@Override
	/**
	 * 根据拼接的SQL语句查询需管理的申购采购
	 * pruService中的applyMessageOql()拼接字符串
	 * 需要传值可修改接口和实现
	 */
	
	public String doDefault() throws Exception {
		logger.info(pruService.applyMessageOql());
		pruService.search();
		info = queryService.listQueryResult(pruService.applyMessageOql()
				.toString(), info);
		return "queryList";
	}
	
	/**
	 * 根据拼接的SQL语句查询出需确认的申购采购
	 * pruService中的applyMessageOql1()拼接字符串
	 * 需要传值可修改接口和实现
	 * @return
	 * @throws Exception
	 */
	public String confirmPurchase() throws Exception {
		pruService.search();
		info = queryService.listQueryResult(pruService.applyMessageOql1()
				.toString(), info);
		return "confirmPurchase";
	}
	
	/**
	 * 跳转到管理页面的左侧的查询页
	 * @return
	 * @throws Exception
	 */
	public String manageSearch() throws Exception {
		System.out.print("跳还是没有跳？");
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yxClientCodeList = new ArrayList<Object>();
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		listExp = new ArrayList<Object>();
		
		groupList = UserUtils.getUserDetail().getDepartments();
		
		
		

		return "manageSearch";
	}

	/**
	 * 跳转到确认页面的左侧查询页
	 * @return
	 * @throws Exception
	 */
	public String confirmSearch() throws Exception {
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yxClientCodeList =new ArrayList<Object>();/* commonService.list("from YXClientCode d where d.id not in(0) and d.is_active=1");*/		
		dutyDepartmentIdList = typeManageService.getYXTypeManage(1018L);		
		listExp = new ArrayList<Object>();
		
		groupList = UserUtils.getUserDetail().getDepartments();
		return "confirmSearch";
	}
	
	/**
	 * 跳转申购采购管理主页面
	 * @return
	 * @throws Exception
	 */
	public String manage() throws Exception{
		return "manage";
	}
	/**
	 * 跳转申购采购确认主页面
	 * @return
	 * @throws Exception
	 */
	public String confirm() throws Exception{
		return "confirm";
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

	public IPurService getPruService() {
		return pruService;
	}

	public void setPruService(IPurService pruService) {
		this.pruService = pruService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ApplyMessage getAm() {
		return am;
	}

	public void setAm(ApplyMessage am) {
		this.am = am;
	}

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}

	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
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

	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
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

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

}
