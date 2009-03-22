package com.baoz.yx.action.billtoReceipt;


import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;



/**
 *	显示月度开票计划
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/moonBillProject.jsp"),
	@Result(name = "showselect", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp"),
	@Result(name = "showTotalBill", value = "/WEB-INF/jsp/billtoReceipt/showTotalBill.jsp"),
	@Result(name = "billPlanRemindMain", value = "/WEB-INF/jsp/billtoReceipt/billPlanRemindFrame.jsp"),
	@Result(name = "billPlanRemindCond", value = "/WEB-INF/jsp/billtoReceipt/billPlanRemindCondtion.jsp"),
	@Result(name = "nextMonthBillList", value = "/WEB-INF/jsp/billtoReceipt/nextMonthbillPlanList.jsp"),
	@Result(name = "nextMonthBillPlanRemindMain", value = "/WEB-INF/jsp/billtoReceipt/nextMonthbillPlanFrame.jsp"),
	@Result(name = "nextMonthbillPlanRemindCond", value = "/WEB-INF/jsp/billtoReceipt/nextMonthbillPlanCondtion.jsp"),
	@Result(name = "monthBillPlanMain", value = "/WEB-INF/jsp/billtoReceipt/monthBillPlanMain.jsp"),
	@Result(name = "monthBillPlanLeftQuery", value = "/WEB-INF/jsp/billtoReceipt/monthBillPlanLeftQuery.jsp")
})

public class ShowMoonBillQueryAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;
	
	
	private PageInfo					info;          
	private ServletRequest				request;

	private String 						yearSel;
	private String 						monthSel;
	private Map<String,String> yearMap = new TreeMap<String, String>();
	private Long 						selectType;
	//////////
	private String 						groupId;
	private Long 						expId;		
	private String						billState;  //开票状态
	private Integer						inPlan;     //计划内/外
	private Integer						hasApply;     //申请状态
	private Integer						hasModify;     //修改状态
	private Date 						billStartDate;
	private Date 						billEndDate;
	private Object[] 					sumArray;
	
	private Object[]				totalArray;//统计字段
	
	private String contractNo;
	
	private String  itemNo;
	
	private BigDecimal sumPlanInvoice;       //总计计划开票
	private Double sumHasInvoice;     		 //总计已开票
	
	
	private Double sumPlanInvoice2;     //总计计划开票(月度收款)
	private Double sumHasInvoice2;     //总计已开金额(月度收款)
	
	private String exportX;//是否导入1为导入
	
	/////////
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"showMoonBillParameters",this,new String[]{"expId","groupId","billState","inPlan","billStartDate","billEndDate","hasApply","hasModify"}); 

	private ObjectPropertySessionHoldTool nextMonthHoldTool = new ObjectPropertySessionHoldTool(
			"showMoonBillParameters",this,new String[]{"expId","groupId","billState","inPlan","billStartDate","billEndDate","hasApply","hasModify"});
	
	private ObjectPropertySessionHoldTool monthBillPlanTool = new ObjectPropertySessionHoldTool(
			"monthBillPlanParameters",this,new String[]{"expId","groupId","billState","inPlan","yearSel","monthSel","contractNo","itemNo"}
	);
	private List<Department>			groupList;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("月度开票收据管理");
		/**
		 * m  代表MonthlyBillpro 月度开票计划表                        0
		 * r  代表RealContractBillandRecePlan  实际合同开票计划表       1
		 * yc 代表YXClientCode    客户表                              2
		 * cm 代表ContractMainInfo   合同主体信息表                    3
		 *  
		 */
		logger.info(yearSel);
		logger.info(monthSel);
		
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearSel = defaultYear+"";
		int defaultMonth = calendar.get(Calendar.MONTH)+1;
		monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
		DecimalFormat d=new DecimalFormat("00");
		String month=d.format(defaultMonth);		
		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		String select = "select m,r,yc,cm, " +
				"m.planBillAmount,"+
				"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
				"(select e.name from Employee e where e.id = cm.saleMan),"+
				"(select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo) "; 
		StringBuffer fromWhere = new StringBuffer();
		fromWhere.append(" from MonthlyBillpro m,RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"m.realContractBillandRecePlan = r.realConBillproSid " +
				"and cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and m.isInsidePlan = 0 ");
		
		fromWhere.append(" and to_char(m.billproMonth,'yyyy-mm')= '").append(defaultYear).append("-").append(month).append("'");
		//销售员
		if (expId != null) {
			fromWhere.append(" and cm.saleMan = " + expId + " ");
		}
		//小组
		if (groupId != null && !"".equals(groupId)) {
			fromWhere
			.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = cm.saleMan ) ");
		}
		fromWhere.append(" order by m.monthlyBillproSid DESC");
		if(exportX!=null&&exportX.equals("1")){
			select+=",(select ot.organizationName from OrganizationTree ot,Employee e  where ot.id =  e.position and e.id = cm.saleMan  and rownum <=1) ";
			List<Object[]>	list = service.list(select+fromWhere.toString());
			processExport(list);
			return null;
		}else{
			StringBuffer totalHql=new StringBuffer();
			totalHql.append("select sum(r.realTaxBillAmount),sum(m.planBillAmount) ");
			Object[] totalArray=(Object[])service.uniqueResult(totalHql.toString()+fromWhere.toString());
			info=queryService.listQueryResult("select count(*) "+fromWhere.toString(),select+fromWhere.toString(), info);
			return SUCCESS;
		}
	}
	
	private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"合同号","项目号","合同名称","客户名称","负责部门","开票性质",
				"发票类型","计划开票日期","计划开票金额","已开票金额" ,"计划状态" ,"销售员","组别" });
		export.setTableName("月度开票计划");
		for(Object[] obj:oArray){
			Object[] row= new Object[13];
			ContractMainInfo contract=(ContractMainInfo)obj[3];
			row[0] =contract.getConId();
			row[1] = obj[5];
			row[2] =((ContractMainInfo)obj[3]).getConName();
			row[3] =((YXClientCode)obj[2]).getName();
			if(contract.getContractType().equals("2")){//集成类
				row[4] = typeManageService.getYXTypeManage(1018L,contract.getMainItemDept()).getTypeName();
			}else if(contract.getContractType().equals("1")){
				row[4] = typeManageService.getYXTypeManage(1018L,(String)obj[7]).getTypeName();
			}
			
			RealContractBillandRecePlan plan = (RealContractBillandRecePlan)obj[1];
			row[5] =typeManageService.getYXTypeManage(1012L,plan.getBillNature()).getTypeName();
			row[6] =typeManageService.getYXTypeManage(1004L,plan.getBillType()).getTypeName();
			row[7] = plan.getRealPredBillDate();
			MonthlyBillpro monthly = (MonthlyBillpro)obj[0];
			row[8] =monthly.getBillTaxAmount();
			if(monthly.getPlanBillAmount()==null){
				row[9] =  0.00;
			}else{
				row[9] = monthly.getPlanBillAmount();
			}
			if(monthly.getPlanBillAmount()!=null&&monthly.getPlanBillAmount()>0){
				row[10] ="已开票";
			}else{
				row[10] = "未开票";
			}
			row[11] =obj[6];
			row[12] = obj[8];
			export.addRow(row);
		}
		OutputStream os = DownloadUtils.getResponseOutput("月度开票计划.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	
	

	public String selMonth()
	{
		ParameterUtils.prepareParameters(monthBillPlanTool);
		logger.info("选择月份查询显示");
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
		if(StringUtils.isBlank(yearSel)){
			yearSel = defaultYear+"";
			int defaultMonth = calendar.get(Calendar.MONTH)+1;
			monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
		}
		String select = "select m,r,yc,cm,m.planBillAmount," +
				"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
				"(select e.name from Employee e where e.id = cm.saleMan)," +
				"(select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo) ";
		StringBuffer fromWhere = new StringBuffer();
		fromWhere.append(" from MonthlyBillpro m,RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"m.realContractBillandRecePlan = r.realConBillproSid " +
				"and cm.conCustomer = yc.id " +
		"and r.conMainInfoSid = cm.conMainInfoSid ");
		fromWhere.append(" and to_char(m.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		//计划外
		if (new Integer(1).equals(inPlan)) {
			fromWhere.append(" and m.isInsidePlan = 0 ");
		}else if(new Integer(0).equals(inPlan)){ //计划内
			fromWhere.append(" and m.isInsidePlan = 1 ");
		}
		//开票状态
		if("1".equals(billState)){
			fromWhere.append(" and (m.planBillAmount is null or m.planBillAmount = 0) ");
		}else if("2".equals(billState)){
			fromWhere.append(" and (m.planBillAmount > 0)");
		}
		//销售员
		if (expId != null) {
			fromWhere.append(" and cm.saleMan = " + expId + " ");
		}
		//小组
		if (groupId != null && !"".equals(groupId)) {
			fromWhere
			.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = cm.saleMan ) ");
		}
		//项目号
		if(!StringUtils.isBlank(contractNo)){
			fromWhere.append(" and cm.conId = '"+contractNo+"'");
			
		}
		if(!StringUtils.isBlank(itemNo)){
			fromWhere.append(" and exists(select 1 from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo and im.conItemId = '"+itemNo+"')");
		}
		
		
//		如果需要加入部门查询，可如下
//		and ( ( contractma3_.con_type=1 and exists (select 1 from yx_con_item_minfo c where c.item_res_dept='P0' )  )
//			    or  ( contractma3_.con_type=2 and contractma3_.main_item_department ='P0' ) )     
			    
		fromWhere.append(" order by   decode(m.planBillAmount , null , 0 , 0 , 0 ,1 ) ,r.realPredBillDate " );	
		StringBuffer totalHql=new StringBuffer();
		totalHql.append("select sum(r.realTaxBillAmount),sum(m.planBillAmount) ");
		Object[] totalArray=(Object[])service.uniqueResult(totalHql.toString()+fromWhere.toString());
		info = queryService.listQueryResult("select count(*) "+fromWhere.toString(),select+fromWhere.toString(), info);
		
		sumPlanInvoice2  =  (Double)service.uniqueResult("select sum(m.billTaxAmount) " + fromWhere.toString());
 		sumHasInvoice2 =  (Double)service.uniqueResult("select sum(m.planBillAmount) " + fromWhere.toString());
		
		return SUCCESS;
	}
	public String showMonthBillPlanMain()throws Exception{
			monthBillPlanTool.clearStore();
			return "monthBillPlanMain";
	}
	
	public String monthBillPlanLeftQuery()throws Exception{
		groupList = UserUtils.getUserDetail().getDepartments();
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearSel = defaultYear+"";
		int defaultMonth = calendar.get(Calendar.MONTH)+1;
		monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
		DecimalFormat d=new DecimalFormat("00");
		String month=d.format(defaultMonth);
		logger.info("月是:"+month);
		yearMap.put(defaultYear+"",defaultYear+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		return "monthBillPlanLeftQuery";
	}
	
	/********************************************* 首页提醒 ***************************************************/
	public String enterBillQueryPage(){
		return "billPlanRemindMain";
	}
	public String billQueryCondition(){
		groupList = UserUtils.getUserDetail().getDepartments();
		return "billPlanRemindCond";
	}
	
	public String enterNexMonthBillQueryPage(){
		return "nextMonthBillPlanRemindMain";
	}
	
	public String nexMonthQueryCondition(){
		groupList = UserUtils.getUserDetail().getDepartments();
		return "nextMonthbillPlanRemindCond";
	}
	
	/**
	 * 在首页上显示全额开票计划明细
	 * @return
	 */
	public String showTotalBill(){
		logger.info("在首页显示全额开票计划");
		
		ParameterUtils.prepareParameters(holdTool);
		
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "showTotalBillPage");
		Object[] retArray = firstPageService.queryShowTotalBill(info, inPlan, billState, 
				billStartDate , billEndDate , groupId , expId , hasApply , hasModify);
		info = 	(PageInfo) retArray[0];
		sumArray = (Object[]) retArray[1];
		return "showTotalBill";
	}
	
	public String showNextMonthBill(){
		logger.info("在首页显示全额开票计划");
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
				" (select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and ( r.billInvoiceAmount is null or r.billInvoiceAmount < r.realTaxBillAmount )   " +
				"  and r.realBillAmount > 0 ") ;
		////
		if("1".equals(billState)){
			from.append(" and r.billInvoiceAmount > 0 ");
		}
		if("0".equals(billState)){
			from.append(" and ( r.billInvoiceAmount is null or r.billInvoiceAmount = 0 ) ");
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
			from.append(" and r.realPredBillDate >= to_date('"+DateUtil.format(billStartDate, "yyyy-MM-dd")+"','yyyy-mm-dd') ");
		}
		if(billEndDate != null){
			//小于第二天0点0分0秒
			from.append(" and r.realPredBillDate < to_date('"+DateUtil.format(DateUtils.addDays(billEndDate, 1), "yyyy-MM-dd")+"','yyyy-mm-dd')  ");
		}
		from.append(" order by r.realPredBillDate");
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "showTotalBillPage");
		info = queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);
		
//		logger.info("select sum(r.realBillAmount) " + from.toString());
		sumPlanInvoice  =  (BigDecimal)service.uniqueResult(" select sum(r.realBillAmount) " + from.toString() );    		
		sumHasInvoice   =  (Double)service.uniqueResult(" select sum(r.billInvoiceAmount) " + from.toString() ); 
//		logger.info(sumPlanInvoice);
//		logger.info(sumHasInvoice);
		return "nextMonthBillList";
	}
	/********************************************* 首页提醒 ***************************************************/
	
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

	public String getYearSel() {
		return yearSel;
	}

	public void setYearSel(String yearSel) {
		this.yearSel = yearSel;
	}

	public String getMonthSel() {
		return monthSel;
	}

	public void setMonthSel(String monthSel) {
		this.monthSel = monthSel;
	}


	public Map<String, String> getYearMap() {
		return yearMap;
	}


	public void setYearMap(Map<String, String> yearMap) {
		this.yearMap = yearMap;
	}

	public Long getSelectType() {
		return selectType;
	}

	public void setSelectType(Long selectType) {
		this.selectType = selectType;
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

	public BigDecimal getSumPlanInvoice() {
		return sumPlanInvoice;
	}

	public void setSumPlanInvoice(BigDecimal sumPlanInvoice) {
		this.sumPlanInvoice = sumPlanInvoice;
	}

	public Double getSumHasInvoice() {
		return sumHasInvoice;
	}

	public void setSumHasInvoice(Double sumHasInvoice) {
		this.sumHasInvoice = sumHasInvoice;
	}

	public IFirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(IFirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Object[] getTotalArray() {
		return totalArray;
	}

	public void setTotalArray(Object[] totalArray) {
		this.totalArray = totalArray;
	}

	public Double getSumPlanInvoice2() {
		return sumPlanInvoice2;
	}

	public void setSumPlanInvoice2(Double sumPlanInvoice2) {
		this.sumPlanInvoice2 = sumPlanInvoice2;
	}

	public Double getSumHasInvoice2() {
		return sumHasInvoice2;
	}

	public void setSumHasInvoice2(Double sumHasInvoice2) {
		this.sumHasInvoice2 = sumHasInvoice2;
	}

	public Integer getHasApply() {
		return hasApply;
	}

	public void setHasApply(Integer hasApply) {
		this.hasApply = hasApply;
	}

	public Integer getHasModify() {
		return hasModify;
	}

	public void setHasModify(Integer hasModify) {
		this.hasModify = hasModify;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

}

