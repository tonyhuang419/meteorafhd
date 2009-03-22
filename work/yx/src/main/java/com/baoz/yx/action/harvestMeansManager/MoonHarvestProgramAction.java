package com.baoz.yx.action.harvestMeansManager;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
import com.opensymphony.xwork2.ActionContext;

/**
 * 收款管理查询
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Results( {	
	@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/moonHarvestProgram.jsp"),
	@Result(name = "intoFrame", value = "/WEB-INF/jsp/harvestMeansManager/monthReceFrame.jsp"),
	@Result(name = "intoLeftPage", value = "/WEB-INF/jsp/harvestMeansManager/monthReceLeftPage.jsp")
})
public class MoonHarvestProgramAction extends DispatchAction {

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
	@Qualifier("userService")
	private IUserService userService;

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
	private List<Department>			groupList;
	private String 						groupId;
	private Long 						expId;		
	private String						billState;
	private Integer						inPlan;

	private Double						sumMonthRevePro; //计划金额
	private Double                      sumArrivalAmount; //到款金额
	private Double                      sumBillAmount; //已开票

	private String 						exportX;

	private Map<String,String> yearMap = new TreeMap<String, String>();

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"SelectPerQueryParameters",this,new String[]{"groupId","expId","billState","inPlan","year","month"});	

	private Boolean showMonthlyButton;//判断是否显示生成月度计划表，true表示不显示。false表示显示

	public String doDefault() throws Exception {
		holdTool.clearStore();
		return "intoFrame";
	}

	public String toLeftPage(){
		groupList = UserUtils.getUserDetail().getDepartments();
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		if(year == null){
			year = defaultYear+"";
		}
		int defaultMonth = calendar.get(Calendar.MONTH);
		if(month == null){
			month = StringUtils.leftPad((defaultMonth+1)+"", 2 ,"0");
		}
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
		return "intoLeftPage";
	}

	@SuppressWarnings("unchecked")
	public String queryList() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		this.logger.info("根据当前月份查询月收款计划");
//		receTypetrans= typeManageService.getYXTypeManage(1017L);// 到款方式
		// 年份

//		ParameterUtils.prepareParameters(holdTool);
		//RealContractBillandRecePlan 合同开票收款计划表  rb
		//ContractMainInfo 合同主体信息表                  cm
		//ContractItemMaininfo 合同项目主体信息表          ci
		//MonthlyRecepro 合月度收款计划表
		String selectSql = "select (select c from ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid )," //合同号
			+"(select cc.name from ContractMainInfo c ,YXClientCode cc where c.itemCustomer = cc.id and c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid),"//客户
			+" mr.realContractBillandRecePlan.realConBillproSid,"//负责部门
			+" mr.realContractBillandRecePlan.realPredReceDate,mr.receTaxAmount,"//计划收款日期，计划收款金额
			+" mr.alreadyArrivedAmount,"
			+" (select e.name from  Employee e  where c.saleMan = e.id )," 
			+" (select cim.conItemId from ContractItemMaininfo cim where mr.realContractBillandRecePlan.contractItemMaininfo = cim.conItemMinfoSid ), "
			+" (select cc.fullName from ContractMainInfo c ,YXClientCode cc where c.itemCustomer = cc.id and c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid)," //客户全称
			+" mr , (select ot.organizationName from OrganizationTree ot,Employee e where ot.id=e.position and e.id = c.saleMan )," +
					"nvl(mr.realContractBillandRecePlan.billInvoiceAmount,0),mr.realContractBillandRecePlan.realNowBillDate ";
		
		String selectSql2 =  " from MonthlyRecepro mr,ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid ";
		//计划内、计划外
		if(inPlan!=null){
			selectSql2 = selectSql2 + " and mr.isInsidePlan = "+inPlan+" ";
		}

		//收款状态
		if("1".equals(billState)){
			//部分到款
			selectSql2 = selectSql2 +" and (mr.alreadyArrivedAmount  > 0 and mr.alreadyArrivedAmount < mr.receTaxAmount) ";
		}else if("0".equals(billState)){
			//部未到款
			selectSql2 = selectSql2 +" and (mr.alreadyArrivedAmount  = 0 or mr.alreadyArrivedAmount is null) ";
		}else  if("2".equals(billState)){
			//全部到款
			selectSql2 = selectSql2 +" and (mr.alreadyArrivedAmount  = mr.receTaxAmount) ";
		}

		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())) {
			expId = user.getUser().getId();
		} else if(StringUtils.isBlank(groupId)){
			groupId = user.getPosition().getOrganizationCode();
		}
		if (expId != null && !"".equals(expId) ) {
			selectSql2 = selectSql2+" and c.saleMan = "+expId+" ";
		}
		if (groupId != null && !"".equals(groupId)) {
			selectSql2 = selectSql2 +" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '"+groupId+"%' and e.id = c.saleMan ) ";
		}

		Calendar now = Calendar.getInstance(); 
		if(year==null || year.equals("")){
			year = now.get(Calendar.YEAR)+"";
		}
		if(month==null || month.equals("")){
			month = now.get(Calendar.MONTH)+"";
			Integer monthInt = new Integer(month)+1;
			month = monthInt.toString();
		}

		selectSql2 = selectSql2 +" and mr.billproMonth = to_date('"+year+"-"+month+"','yyyy-mm') "; // 把传过来的年进行比较
		selectSql2 = selectSql2 + " order by decode(mr.alreadyArrivedAmount , null , 0 , 0 , 0 ,1 ) ,  mr.realContractBillandRecePlan.realPredReceDate ";

		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = commonService.list(selectSql+selectSql2);
			this.processExport(objList);
			return null;
		}
		else{
			info = queryService.listQueryResult("select count(*) "+selectSql2,selectSql+selectSql2, info);
			sumMonthRevePro = (Double)commonService.uniqueResult("select sum(mr.receTaxAmount) " + SqlUtils.getFromSql(selectSql+selectSql2));
			sumArrivalAmount = (Double)commonService.uniqueResult("select sum(mr.alreadyArrivedAmount) " + SqlUtils.getFromSql(selectSql+selectSql2));
			sumBillAmount = (Double)commonService.uniqueResult("select sum(mr.realContractBillandRecePlan.billInvoiceAmount) " + SqlUtils.getFromSql(selectSql+selectSql2));
		}
		showMonthlyButton = monthHarvestPlanService.checkPlanInThisMonth();
		
		logger.info("查询完毕");
		return "queryList";
	}
	public String generateMonthPlan() throws Exception {		
		//判断本月是否生成过计划(是当月是否生成当月的计划，而不是上个月是否生成当月的计划)
		if(monthHarvestPlanService.isGeneratedCurrentMonthPlan()){
			//已经生成过，不重复生成
			ActionContext.getContext().put("repeatGenerate", true);
			return queryList();
		}else{
			monthHarvestPlanService.generateCurrentMonthPlan();
			ActionContext.getContext().put("generateSuccess", true);
			return queryList();
		}
	}

	private  void processExport(List<Object[]> oArray) {		
		ContractMainInfo cmi = null;
		String reveState="";
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"合同号","项目号","销售员","合同名称","客户名称","负责部门","已开票金额","开票日期","计划收款日期",
				"计划金额","到款金额","收款状态" ,"实际收款日期" ,"组别"});
		export.setTableName("月度计划收款");
		for(Object[] obj:oArray){
			cmi = (ContractMainInfo)obj[0];
			MonthlyRecepro monthlyPro = (MonthlyRecepro)obj[9];
			monthHarvestPlanService.getDepName(6584L);
			if(obj[5]==null || (Double)obj[5]==0){
				reveState = "未收款";
			}else if((Double)obj[5]>0 &&(Double)obj[5]<monthlyPro.getReceTaxAmount()){
				reveState = "部分收款";
			}else if((Double)obj[5]==monthlyPro.getReceTaxAmount().doubleValue()){
				reveState = "全部收款";
			}
			
			export.addRow(new Object[]{	cmi.getConId(),obj[7],obj[6],cmi.getConName(),obj[8],
					monthHarvestPlanService.getDepName((Long)obj[2]),obj[11],obj[12],obj[3],obj[4],obj[5],reveState ,
					NumberToTime.getDateFormat(monthlyPro.getActualArrivedDate(), "yyyy-MM-dd") , obj[10] });
		}
		OutputStream os = DownloadUtils.getResponseOutput("月度计划收款.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
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

	public void setYearMap(Map yearMap) {
		this.yearMap = yearMap;
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
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
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

	public Integer getInPlan() {
		return inPlan;
	}

	public void setInPlan(Integer inPlan) {
		this.inPlan = inPlan;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public Double getSumMonthRevePro() {
		return sumMonthRevePro;
	}

	public void setSumMonthRevePro(Double sumMonthRevePro) {
		this.sumMonthRevePro = sumMonthRevePro;
	}

	public Double getSumArrivalAmount() {
		return sumArrivalAmount;
	}

	public void setSumArrivalAmount(Double sumArrivalAmount) {
		this.sumArrivalAmount = sumArrivalAmount;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

	public Boolean getShowMonthlyButton() {
		return showMonthlyButton;
	}

	public void setShowMonthlyButton(Boolean showMonthlyButton) {
		this.showMonthlyButton = showMonthlyButton;
	}

	public Double getSumBillAmount() {
		return sumBillAmount;
	}

	public void setSumBillAmount(Double sumBillAmount) {
		this.sumBillAmount = sumBillAmount;
	}
}
