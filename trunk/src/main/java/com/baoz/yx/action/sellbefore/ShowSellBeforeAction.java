package com.baoz.yx.action.sellbefore;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;

/**
 * 显示所属客户售前合同信息以及操作
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/sellbefore/selectSellBefore.action"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/sellbefore/addSellBeforeManager.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/sellbefore/updateSellBeforeManager.jsp"),
		@Result(name = "showInfo", value = "/WEB-INF/jsp/sellbefore/sellBeforeInfo.jsp"),
		@Result(name = "changeContract",type = ServletRedirectResult.class,value = "/sellbefore/selectSellBefore.action")
		})
public class ShowSellBeforeAction extends DispatchAction implements ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService 			systemService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService			queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private PageInfo				info;
    private ContractBeforeSell  	cbs;
	private ServletRequest			request;
	private String[] 				idsss;
	private YXClientCode			ycc;
	private List<Object>			yxLinkManList;
	private List<Object>       		yxClientCodeList;			
	private List<Object>			projectDutyList;					
	private List<YXTypeManage>		projectTypeList;					//客户项目类型LIST
	private List<YXTypeManage>		businessTypeList;					//行业类别LIST
	private List<YXTypeManage>		dutyDepartmentIdList;				//工程责任部门list
	private Long 					id;
	private List<YXTypeManage>		projectStateFollowList;             //项目跟踪状态List
	private List<YXTypeManage>      contractTypeList;
	private List<YXTypeManage>      projectStateList;
	private Long 					sellBeforeID;
	private Long 					cusId;
	private Long 					customerId;
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public String doDefault() throws Exception {
		logger.info("新增售前合同");
		return ENTER_SAVE;
	}

	/**
	 * 保存工程经济组成费用名称
	 */
	public String saveCBS() throws Exception {
		logger.info("保存售前合同");
		Long uid = new UserUtils().getUser().getId();
		cbs.setById(uid);
		cbs.setUpdateBy(new Date());
		service.save(cbs);
		return "success";
	}
	/**
	 * 获取售前合同管理更新页面
	 * 
	 * @return String
	 * 
	 */
	public String selectUpdate()
	{
		updateQuery(id);
		return "enterUpdate";
	}

	/**
	 * 获取售前合同管理更新页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		String ids = request.getParameter("ids");
		Long id = Long.valueOf(ids);
		logger.info(id);
		updateQuery(id);
		
		return "enterUpdate";

	}

	public void updateQuery(Long id)
	{
		Long uid = new UserUtils().getUser().getId();
		
		yxClientCodeList = service.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		yxLinkManList=service.list("from YXLinkMan emp where emp.is_active=1");
		projectTypeList=typeManageService.getYXTypeManage(1007L);
		businessTypeList=typeManageService.getYXTypeManage(1002L);
		dutyDepartmentIdList=typeManageService.getYXTypeManage(1018L);
		projectStateFollowList=typeManageService.getYXTypeManage(1009L);
		contractTypeList=typeManageService.getYXTypeManage(1008L);
		projectStateList=typeManageService.getYXTypeManage(1006L);
		
		info=queryService.listQueryResult(" select cbs,yc from ContractBeforeSell cbs,YXClientCode yc where cbs.is_active=1 and  cbs.id='" + id + "' and cbs.customerId=yc.id", info);
		cbs = (ContractBeforeSell)((Object[])((List)info.getResult()).get(0))[0];
		
	}
	
	/**
	 * 执行对售前合同修改功能
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateSellBefore() throws Exception {		
		logger.info("修改售前合同信息");	
		ContractBeforeSell toUpdate = (ContractBeforeSell) service.load(ContractBeforeSell.class, sellBeforeID);	
		toUpdate.setCustomerId(customerId);
		logger.info(customerId);
		toUpdate.setBusinessTypeId(cbs.getBusinessTypeId());
		toUpdate.setCustomerProjectTypeId(cbs.getCustomerProjectTypeId());
		toUpdate.setLinkManId(cbs.getLinkManId());
		toUpdate.setProjectName(cbs.getProjectName());
		toUpdate.setProjectStateFollowId(cbs.getProjectStateFollowId());
		toUpdate.setMainProjectContent(cbs.getMainProjectContent());
		toUpdate.setDescProjectFollow(cbs.getDescProjectFollow());
		toUpdate.setProjectTypeId(cbs.getProjectTypeId());
		toUpdate.setProjectStateId(cbs.getProjectStateId());
		toUpdate.setDutyDepartmentId(cbs.getDutyDepartmentId());
		toUpdate.setProjectManId(cbs.getProjectManId());
		toUpdate.setProjectSum(cbs.getProjectSum());
		toUpdate.setBidSum(cbs.getBidSum());
		toUpdate.setOwnSum(cbs.getOwnSum());
		toUpdate.setBidDate(cbs.getBidDate());
		toUpdate.setProjectDate(cbs.getProjectDate());
		toUpdate.setEstimateProjectDate(cbs.getEstimateProjectDate());
		toUpdate.setEstimateSignDate(cbs.getEstimateSignDate());
		toUpdate.setOwnProbability(cbs.getOwnProbability());
		toUpdate.setOwnFactory(cbs.getOwnFactory());
		toUpdate.setProjectStateSelect(cbs.getProjectStateSelect());
		toUpdate.setCompeteInfo(cbs.getCompeteInfo());
		toUpdate.setRemark(cbs.getRemark());
		toUpdate.setById(UserUtils.getUser().getId());
		toUpdate.setUpdateBy(new Date());
		service.update(toUpdate);
		return "success";
		
	}
	
	/**
	 * 执行对售前合同管理删除
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String del() {
		logger.info("删除售前合同信息");
		String ids = request.getParameter("ids");	
		logger.info(ids);
		String hql=" update ContractBeforeSell obj set obj.is_active='0' ";
		int a = systemService.deleteChose(ids, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}
	/**
	 * 显示售前合同管理明细
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String showInfo() throws Exception{
		logger.info("显示售前管理合同信息");
		logger.info(id);
		info = new PageInfo();
		info.setResult(service.list(" select cbs,yc,(select emp.name from YXLinkMan emp where cbs.linkManId = emp.id) from ContractBeforeSell cbs,YXClientCode yc where cbs.is_active=1 and  cbs.id='" + id + "' and cbs.customerId = yc.id "));
		return "showInfo";
	}
	
	/**
	 * 售前合同转为正式合同
	 * 
	 * @return String
	 * @throws Exception
	 */
		
	public String changeContract() throws Exception
	{
		logger.info("售前合同转正式");
		logger.info("Id为  "+id+"   的售前合同转正式合同");
		ContractMainInfo cm = new ContractMainInfo();
		
		ContractBeforeSell cbSll = (ContractBeforeSell) service.load(ContractBeforeSell.class, id);
		
		logger.info("售前的客户id是"+cbSll.getCustomerId());
		Long uid = new UserUtils().getUser().getId();
		cm.setPreConSysid(cbSll.getID());                           // 添加售前合同系统号
		cm.setSaleMan(uid);						 // 添加销售员系统号
		cm.setConName(cbSll.getProjectName());
		cm.setMainItemDept(cbSll.getDutyDepartmentId());
		cm.setConCustomer(cbSll.getCustomerId());
		cm.setLinkManId(cbSll.getEmployeeId());
		cm.setCustomereventtype(cbSll.getCustomerProjectTypeId());
		cm.setMainItemDept(cbSll.getDutyDepartmentId());
		cm.setConSignDate(cbSll.getEstimateSignDate());
		cm.setConStartDate(cbSll.getProjectDate());
		cm.setConTaxTamount(cbSll.getProjectSum());
		cm.setMainItemPeople(cbSll.getProjectManId());
		cm.setConState(0L);
		service.save(cm);
		return "changeContract";
	}
	
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request; 
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public ContractBeforeSell getCbs() {
		return cbs;
	}

	public void setCbs(ContractBeforeSell cbs) {
		this.cbs = cbs;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public YXClientCode getYcc() {
		return ycc;
	}

	public void setYcc(YXClientCode ycc) {
		this.ycc = ycc;
	}

	public List<Object> getYxLinkManList() {
		return yxLinkManList;
	}

	public void setYxLinkManList(List<Object> yxLinkManList) {
		this.yxLinkManList = yxLinkManList;
	}

	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<Object> getProjectDutyList() {
		return projectDutyList;
	}

	public void setProjectDutyList(List<Object> projectDutyList) {
		this.projectDutyList = projectDutyList;
	}

	public List<YXTypeManage> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<YXTypeManage> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}

	public List<YXTypeManage> getBusinessTypeList() {
		return businessTypeList;
	}

	public void setBusinessTypeList(List<YXTypeManage> businessTypeList) {
		this.businessTypeList = businessTypeList;
	}

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}

	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public List<YXTypeManage> getProjectStateFollowList() {
		return projectStateFollowList;
	}

	public void setProjectStateFollowList(List<YXTypeManage> projectStateFollowList) {
		this.projectStateFollowList = projectStateFollowList;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public List<YXTypeManage> getProjectStateList() {
		return projectStateList;
	}

	public void setProjectStateList(List<YXTypeManage> projectStateList) {
		this.projectStateList = projectStateList;
	}

	public String[] getIdsss() {
		return idsss;
	}

	public void setIdsss(String[] idsss) {
		this.idsss = idsss;
	}

	public Long getSellBeforeID() {
		return sellBeforeID;
	}

	public void setSellBeforeID(Long sellBeforeID) {
		this.sellBeforeID = sellBeforeID;
	}

}
