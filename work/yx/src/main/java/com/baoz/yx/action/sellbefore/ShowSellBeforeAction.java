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
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 显示所属客户售前合同信息以及操作
 */
@Results( {
	@Result(name = "success", type = ServletRedirectResult.class, value = "/sellbefore/selectSellBefore.action"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/sellbefore/addSellBeforeManager.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/sellbefore/addSellBeforeManager.jsp"),
	@Result(name = "showInfo", value = "/WEB-INF/jsp/sellbefore/sellBeforeInfo.jsp"),	
	@Result(name = "xiugaibaocun",type = ServletRedirectResult.class,value = "/sellbefore/contractBeforeSellQuery.action"),
	@Result(name = "changeContract",type = ServletRedirectResult.class,value = "/sellbefore/selectSellBefore.action"),
	@Result(name = "showHistory",value = "/WEB-INF/jsp/sellbefore/showHistory.jsp")
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
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;
	@Autowired
	@Qualifier("beforeSellContractService")
	private IBeforeSellContractService beforeSellContractService;

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
	private List<YXTypeManage>      itemTrackList;						//项目跟踪结果List
	private List<YXTypeManage>      contractTypeList;
	private List<YXTypeManage>      projectStateList;
	private List<YXChargeMan>       chargeManList;						//项目负责人List
	private Long       				chargeMan;							//项目负责人
	private Long 					sellBeforeID;
	private Long 					cusId;
	private Long 					customerId;

	private Long[] 					ids;

	private String 					stageId;

	private List<Object>			mainConList;

	private String					proSave;

	private String 					selectExit;
	private Employee 			user;
	private String 					message;
	private String 					comeFrom;   //new 来自新增  mod来自修改  stat来自统计
	private Object[]                obj; //ContractBeforeSell,YXClientCode ,客户name

	private String 					customerLinkMan;                   	//客户联系人

	private List<Object[]> ccList;		//修改历史
	private String impName;
	private Long importantProjectId;


	public String doDefault() throws Exception {
		logger.info("新增售前合同");
		return ENTER_SAVE;
	}

	/**
	 * 保存工程经济组成费用名称
	 */
	public String saveCBS() throws Exception {
		logger.info("保存售前合同");
		Long uid = UserUtils.getUser().getId();
		cbs.setById(uid);
		cbs.setUpdateBy(new Date());
		service.save(cbs);
		systemService.addRelation(cbs.getCustomerId());
		return "success";
	}
	/**
	 * 树形的获取售前合同管理更新页面
	 * 
	 * @return String
	 * 
	 */
	public String selectUpdate() throws Exception 
	{
		if(ActionContext.getContext().getSession().get("selectExit")!=null){
			selectExit = (String)ActionContext.getContext().getSession().get("selectExit");
			ActionContext.getContext().getSession().remove("selectExit");
		}
		cbs = (ContractBeforeSell)service.load(ContractBeforeSell .class, id);
		if( cbs.getConState().equals("1")){
			return showInfo();
		}
		else{
			this.updateQuery(id);
			return "enterUpdate";
		}
	}
	/**
	 * 修改页面保存按钮
	 * 
	 * @return String
	 * 
	 */
	public String selectOrUpdate()
	{
		if(ActionContext.getContext().getSession().get("selectExit")!=null){
			selectExit = (String)ActionContext.getContext().getSession().get("selectExit");
			ActionContext.getContext().getSession().remove("selectExit");
		}
		updateSellBefore();
		this.updateQuery(id);
		return "enterUpdate";
	}

	/**
	 * 获取售前合同管理更新页面
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String enterUpdate() throws Exception {
		logger.info("进入修改页面");
		String ids = request.getParameter("ids");
		Long id = Long.valueOf(ids);
		//		logger.info(id);
		this.updateQuery(id);
		return "enterUpdate";

	}

	@SuppressWarnings("unchecked")
	public void updateQuery(Long id)
	{
		Long uid = UserUtils.getUser().getId();
		yxClientCodeList = service.list(" from YXOEmployeeClient yec where yec.exp.id = " + uid);
		//		yxLinkManList = service.list("from YXLinkMan emp where emp.is_active=1");
		projectTypeList=typeManageService.getYXTypeManage(1007L);
		businessTypeList=typeManageService.getYXTypeManage(1002L);
		dutyDepartmentIdList=typeManageService.getYXTypeManage(1018L);
		projectStateFollowList=typeManageService.getYXTypeManage(1009L);
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		projectStateList=typeManageService.getYXTypeManage(1006L);

		cbs = (ContractBeforeSell)service.uniqueResult(" from ContractBeforeSell cbs where  cbs.id= ? " , id  );
		if( cbs.getProjectStateSelect().equals("1")){
			itemTrackList = beforeSellContractService.loadTrackList("on");
		}
		else{
			itemTrackList = beforeSellContractService.loadTrackList("off");
		}

		Object[] oTemp = (Object[] )service.uniqueResult("  select ip.id ,  ip.projectName  " +
				" from ImpAndCbsRelation icr ,ImportantProject ip  where icr.impID = ip.id   and  icr.cbsId = ?  ", id);
		if(oTemp!=null){
			importantProjectId =  (Long)oTemp[0];
			impName = (String)oTemp[1];
		}


		user = (Employee)service.uniqueResult("from Employee e where e.id = ? ", cbs.getEmployeeId());
		yxLinkManList = service.list(" from  YXLinkMan yx where yx.clientId = ? ", cbs.getCustomerId());
		yxLinkManList.add(0, new YXLinkMan());
		chargeManList = service.list(" select c from YXChargemanDepartment cd , YXChargeMan c where c.is_active = '1' and cd.chargemanid = c.id and cd.departmentid = ? ", cbs.getDutyDepartmentId());
		chargeManList.add(0,new YXChargeMan());

		if(cbs.getLinkManId()!=null){
			customerLinkMan = (String)service.uniqueResult("select y.name from YXLinkMan y where y.id = ?", cbs.getLinkManId());
		}
		//		itemRespName;

		if(cbs.getProjectManId()!=null && cbs.getProjectManId()!=""){
			chargeMan = (Long)service.uniqueResult("select c.id  from YXChargeMan c  where c.name = ? ", cbs.getProjectManId());
		}

		if(comeFrom!=null && comeFrom.equals("new")){
			message = "售前合同已保存";
		}
	}

	/**
	 * 执行对售前合同修改功能
	 * 
	 * @return String
	 * @throws Exception
	 */
	@Deprecated
	public String updateSellBefore(){
		logger.info("修改售前合同信息");	

		ContractBeforeSell toUpdate = (ContractBeforeSell) service.load(ContractBeforeSell.class, sellBeforeID);	
		logger.info("客户id"+customerId);   
		toUpdate.setCustomerId(customerId);
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
		if(selectExit.equals("true"))
		{
			return "xiugaibaocun";
		}
		else
		{
			//验证修改成功返回管理页面
			proSave="4";
			ActionContext.getContext().getSession().put("proSave", proSave);
			return "success";
		}

	}

	/**
	 * 执行对售前合同管理删除
	 * 
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String del() {
		logger.info("删除售前合同信息");
		//		logger.info(StringUtils.join(ids,","));
		//		String hql=" update ContractBeforeSell obj set obj.is_active='0' where obj.ID in ("+StringUtils.join(ids,",")+") ";
		//		service.executeUpdate(hql);
		beforeSellContractService.delCBS( ids );
		proSave="5";
		ActionContext.getContext().getSession().put("proSave", proSave);
		logger.info("删除操作成功！");
		return "success";
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
		obj = (Object[])service.uniqueResult(" select cbs,yc, " +
				" ( select yc.name from YXClientCode yc where cbs.customerId = yc.id) ," +
				" ( select yx.name from YXLinkMan yx where cbs.linkManId = yx.id) ," +
				" ( select emp.name from Employee emp where cbs.employeeId = emp.id) ," +
				" ( select ip  from ImpAndCbsRelation icr ,ImportantProject ip  where icr.impID = ip.id   and  icr.cbsId = cbs.id )"+
				" from ContractBeforeSell cbs,YXClientCode yc where cbs.is_active=1 and  cbs.id= ? and cbs.customerId = yc.id ",id);
		return "showInfo";
	}

	/**
	 * 售前合同转为正式合同
	 * 
	 * @return String
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public String changeContract() throws Exception
	{
		logger.info("售前合同转正式");
		logger.info("Id为  "+id+"   的售前合同转正式合同");
		ContractMainInfo cm = new ContractMainInfo();

		ContractBeforeSell cbSll = (ContractBeforeSell) service.load(ContractBeforeSell.class, id);

		logger.info("售前的客户id是"+cbSll.getCustomerId());
		Long uid = UserUtils.getUser().getId();
		cm.setPreConSysid(cbSll.getID());                           // 添加售前合同系统号
		cm.setSaleMan(cbSll.getEmployeeId());						 // 添加销售员系统号
		cm.setConName(cbSll.getProjectName());
		cm.setMainItemDept(cbSll.getDutyDepartmentId());
		cm.setConCustomer(cbSll.getCustomerId());
		cm.setLinkManId(cbSll.getLinkManId());
		cm.setCustomereventtype(cbSll.getCustomerProjectTypeId());
		cm.setMainItemDept(cbSll.getDutyDepartmentId());
		cm.setConSignDate(cbSll.getEstimateSignDate());
		cm.setConStartDate(cbSll.getProjectDate());
		cm.setConTaxTamount(cbSll.getProjectSum());
		cm.setMainItemPeople(cbSll.getProjectManId());
		cm.setItemCustomer(cbSll.getCustomerId());
		cm.setPartyAProId(cbSll.getPartyAProId());
		cm.setConState(0L);                              // 草稿
		cm.setFinalAccount("0");           // 预决算  
		cm.setImportFromFile(Boolean.FALSE);
		cm.setOpTime(new Date());
		cm.setOpPeople(uid);
		cm.setBaserate(1D);
		cm.setCopeck("CNY");
		cm.setStandard("1");
		cm.setContractType("1");
		service.save(cm);
		// 修改售前状态
		//		cbSll.setProjectStateSelect("2");
		cbSll.setConState("1");
		cbSll.setRemark("已转正式合同关闭");
		service.update(cbSll);
		proSave="2";
		ActionContext.getContext().getSession().put("proSave", proSave);
		return "changeContract";
	}

	@SuppressWarnings("unchecked")
	public String showHistory( ){
		ccList = (List<Object[]>)service.list(" select c,e.name  from ContractBeforeSellHistory c , Employee e " +
				"  where  e.id = c.byId    and  c.cbsId = ? order by c.updateBy desc, c.id desc", id);	
		return "showHistory";
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

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public String getSelectExit() {
		return selectExit;
	}

	public void setSelectExit(String selectExit) {
		this.selectExit = selectExit;
	}

	public String getProSave() {
		return proSave;
	}

	public void setProSave(String proSave) {
		this.proSave = proSave;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

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

	public List<YXTypeManage> getItemTrackList() {
		return itemTrackList;
	}

	public void setItemTrackList(List<YXTypeManage> itemTrackList) {
		this.itemTrackList = itemTrackList;
	}

	public List<Object> getMainConList() {
		return mainConList;
	}

	public void setMainConList(List<Object> mainConList) {
		this.mainConList = mainConList;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public List<YXChargeMan> getChargeManList() {
		return chargeManList;
	}

	public void setChargeManList(List<YXChargeMan> chargeManList) {
		this.chargeManList = chargeManList;
	}

	public Long getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(Long chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Object[] getObj() {
		return obj;
	}

	public void setObj(Object[] obj) {
		this.obj = obj;
	}

	public String getCustomerLinkMan() {
		return customerLinkMan;
	}

	public void setCustomerLinkMan(String customerLinkMan) {
		this.customerLinkMan = customerLinkMan;
	}

	public List<Object[]> getCcList() {
		return ccList;
	}

	public void setCcList(List<Object[]> ccList) {
		this.ccList = ccList;
	}

	public IBeforeSellContractService getBeforeSellContractService() {
		return beforeSellContractService;
	}

	public void setBeforeSellContractService(
			IBeforeSellContractService beforeSellContractService) {
		this.beforeSellContractService = beforeSellContractService;
	}

	public String getImpName() {
		return impName;
	}

	public void setImpName(String impName) {
		this.impName = impName;
	}

	public Long getImportantProjectId() {
		return importantProjectId;
	}

	public void setImportantProjectId(Long importantProjectId) {
		this.importantProjectId = importantProjectId;
	}


}
