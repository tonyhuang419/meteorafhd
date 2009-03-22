package com.baoz.yx.action.harvestMeansManager;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

/**
 * 当前月度收款计划查询
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Results( {	
	@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/CorentMouthHarvestProgram.jsp"),
	@Result(name = "recePlanRemindCond", value = "/WEB-INF/jsp/harvestMeansManager/recePlanRemindCondition.jsp"),
	@Result(name = "recePlanRemindMain", value = "/WEB-INF/jsp/harvestMeansManager/recePlanRemindFrame.jsp"),
	@Result(name = "nextMonthReceList", value = "/WEB-INF/jsp/harvestMeansManager/nextMonthReceList.jsp"),
	@Result(name = "nextMonthRecePlanCond", value = "/WEB-INF/jsp/harvestMeansManager/nextMonthReceCondition.jsp"),
	@Result(name = "nextMonthRecePlanMain", value = "/WEB-INF/jsp/harvestMeansManager/nextMonthReceFrame.jsp")
})
public class CorentMouthHarvestProgramAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	@Autowired
	@Qualifier("monthHarvestPlanService")
	private MonthHarvestPlanService monthHarvestPlanService;
	private PageInfo info;
	private List list;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private List<YXTypeManage> receTypetrans;
	private ReveInfo ri;	
	private List<YXTypeManage> clientNames;
	
	private String month; 
	private String year;
	
	//要查询的状态0为未收款 1为全额收款 2为部分收款
	private String stage;
	
	////////
	private List<Department>			groupList;
    private String 						groupId;
    private Long 						expId;		
    private String						billState;
    private Integer						inPlan;
    private Date 						billStartDate;
    private Date 						billEndDate;
    private Integer 					hasModify; //修改状态
    private Object[] 					sumArray;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"showMoonBillParameters",this,new String[]{"expId","groupId","inPlan","billState","billStartDate","billEndDate","hasModify"}); 
	///////
	private ObjectPropertySessionHoldTool nextMonthHoldTool = new ObjectPropertySessionHoldTool(
    		"showMoonBillParameters",this,new String[]{"expId","groupId","inPlan","billState","billStartDate","billEndDate"});
	
	private Map<String,String> yearMap = new TreeMap<String, String>();

	
//	 private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
//	    		"SelectPerQueryParameters",this,new String[]{"sid","id","invoiceID","billType","receState","STime","LTime"});	
	
		  
	@Override
	@SuppressWarnings("unchecked")	
	public String doDefault() throws Exception {
		this.logger.info("根据当前月份查询月收款计划");
		UserDetail user = UserUtils.getUserDetail();
		ParameterUtils.prepareParameters(holdTool);
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		String select = "select r,yc,cm ," +
				"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
				"(select e.name from Employee e where e.id = cm.saleMan)," +
				" month , (select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,MonthlyRecepro month,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and r.realConBillproSid = month.realContractBillandRecePlan ");
		/////
		if(new Integer(1).equals(inPlan)){
			from.append(" and month.isInsidePlan = 0 ");//计划内
			///////
			if(billStartDate != null){
				from.append(" and month.billproMonth >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd') ");
			}
			if(billEndDate != null){
				//小于第二天0点0分0秒
				from.append(" and month.billproMonth < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd')");
			}
		}else if(new Integer(0).equals(inPlan)){
			from.append(" and month.isInsidePlan = 1  ");//计划外
			///////
			if(billStartDate != null){
				from.append(" and month.actualArrivedDate >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd') ");
			}
			if(billEndDate != null){
				//小于第二天0点0分0秒
				from.append(" and month.actualArrivedDate < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd') ");
			}
		}else{
			if(billStartDate != null){
				from.append(" and ( month.recePlanDate >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd')" +
						" or  month.actualArrivedDate >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd') )");
			}
			if(billEndDate != null){
				//小于第二天0点0分0秒
				from.append(" and ( month.recePlanDate < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd') " +
						"or    month.actualArrivedDate < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd') )");
			}
		}
		
		if("1".equals(billState)){
			from.append(" and month.alreadyArrivedAmount > 0 ");
		}
		if("0".equals(billState)){
			from.append(" and ( month.alreadyArrivedAmount is null or month.alreadyArrivedAmount = 0 ) ");
		}
		
		// 已修改
		if (new Integer(1).equals(hasModify)) {
			from.append(" and trunc(r.realPredReceDate,'mm') >  trunc(month.billproMonth,'mm')  ");
		}
		// 未修改
		else if (new Integer(0).equals(hasModify)) {
			from.append(" and trunc(r.realPredReceDate,'mm') <=  trunc(month.billproMonth,'mm') ");
		}
		
		////
		if (expId != null ) {
			from.append(" and cm.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			from.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = cm.saleMan ) ");
		}
		from.append(" order by r.realPredReceDate");
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "showTotalBillPage");
		info=queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);
		//////累计
		sumArray = (Object[]) commonService.uniqueResult("select sum(month.receTaxAmount),sum(month.alreadyArrivedAmount) "+from.toString());
		logger.info("查询完毕");
		return "queryList";
	}
	public String receQueryCondition(){
		groupList = UserUtils.getUserDetail().getDepartments();
		return "recePlanRemindCond";
	}
	
	public String enterReceQueryPage(){
		return "recePlanRemindMain";
	}
	
	public String nextMonthReceQueryCondition(){
		groupList = UserUtils.getUserDetail().getDepartments();
		return "nextMonthRecePlanCond";
	}
	
	public String nextMonthReceQueryPage(){
		return "nextMonthRecePlanMain";
	}
	
	public String nextMonthReceList(){
		UserDetail user = UserUtils.getUserDetail();
		ParameterUtils.prepareParameters(nextMonthHoldTool);
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		String select = "select r,yc,cm ," +
				"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
				"(select e.name from Employee e where e.id = cm.saleMan)," +
				"( r.realArriveAmount ) ," +
				"(select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and ( r.realArriveAmount is null or r.realArriveAmount < r.realTaxReceAmount ) ");
		/////
		if("1".equals(billState)){
			from.append(" and r.realArriveAmount > 0 ");
		}
		if("0".equals(billState)){
			from.append(" and ( r.realArriveAmount is null or r.realArriveAmount = 0 ) ");
		}
		////
		if (expId != null ) {
			from.append(" and cm.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			from.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = cm.saleMan ) ");
		}
		///////
		if(billStartDate != null){
			from.append(" and r.realPredReceDate >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd') ");
		}
		if(billEndDate != null){
			//小于第二天0点0分0秒
			from.append(" and r.realPredReceDate < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd')  ");
		}
		from.append(" order by r.realPredReceDate");
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "showTotalBillPage");
		logger.info(select+from.toString());
		info = queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);
		//////累计
		sumArray = (Object[]) commonService.uniqueResult("select sum(r.realTaxReceAmount - decode(r.realArriveAmount,null,0,r.realArriveAmount)) , sum(0.0) "+from.toString());
//		logger.info("查询完毕");
		return "nextMonthReceList";
	}
	
	public String generateMonthPlan() throws Exception {		
		//判断本月是否生成过计划(是当月是否生成当月的计划，而不是上个月是否生成当月的计划)
		if(monthHarvestPlanService.isGeneratedCurrentMonthPlan()){
			//已经生成过，不重复生成
			ActionContext.getContext().put("repeatGenerate", true);
			return doDefault();
		}else{
			monthHarvestPlanService.generateCurrentMonthPlan();
			ActionContext.getContext().put("generateSuccess", true);
			return doDefault();
		}
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map getYearMap() {
		return yearMap;
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

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getReceTypetrans() {
		return receTypetrans;
	}

	public void setReceTypetrans(List<YXTypeManage> receTypetrans) {
		this.receTypetrans = receTypetrans;
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


	public List<YXTypeManage> getClientNames() {
		return clientNames;
	}

	public void setClientNames(List<YXTypeManage> clientNames) {
		this.clientNames = clientNames;
	}
	public MonthHarvestPlanService getMonthHarvestPlanService() {
		return monthHarvestPlanService;
	}
	public void setMonthHarvestPlanService(
			MonthHarvestPlanService monthHarvestPlanService) {
		this.monthHarvestPlanService = monthHarvestPlanService;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public void setYearMap(Map<String, String> yearMap) {
		this.yearMap = yearMap;
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
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
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public Date getBillStartDate() {
		return billStartDate;
	}
	public void setBillStartDate(Date billStartDate) {
		this.billStartDate = billStartDate;
	}
	public Date getBillEndDate() {
		return billEndDate;
	}
	public void setBillEndDate(Date billEndDate) {
		this.billEndDate = billEndDate;
	}
	public Integer getInPlan() {
		return inPlan;
	}
	public void setInPlan(Integer inPlan) {
		this.inPlan = inPlan;
	}
	public Object[] getSumArray() {
		return sumArray;
	}
	public void setSumArray(Object[] sumArray) {
		this.sumArray = sumArray;
	}
	public Integer getHasModify() {
		return hasModify;
	}
	public void setHasModify(Integer hasModify) {
		this.hasModify = hasModify;
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	
}
