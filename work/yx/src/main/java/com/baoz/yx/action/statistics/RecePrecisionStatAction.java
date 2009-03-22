package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.text.DecimalFormat;
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
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;

/**
 * 收款精度统计
 * @author yepeng
 *
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/statistics/receAccuracyStatisticsList.jsp"),
	@Result(name = "showQuery", value = "/WEB-INF/jsp/statistics/receAccuracyStatisticsQuery.jsp"),
	@Result(name = "showReceDetial", value = "/WEB-INF/jsp/statistics/receAccuracyStatisticsDetial.jsp")
})
public class RecePrecisionStatAction  extends DispatchAction {
	
	@Autowired
	@Qualifier("statisticsService")	
	private IStatisticsService 			statisticsService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 		applyBillService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;
	
	private PageInfo					info;
	
	private List<Department>			groupList;	         //组别
	private List<Employee> 				empList; 			// 查询显示销售员
	
	private Long						groupId;		//组别
	
	private Long 						saleMan;		//销售员
	
	private String						yearSel;   //年
	
	private String						monthSel;   //月
	
	private String 						curMonthFirstDay;
	private String 						curMonthLastDay;
	
	private List<Object>						countPlan;           //计算计划内开票总计

	private List<Object>						totalReal;           //计算实际开票总计
	
	private List<Object>						totalRealPlan;      //计算实际计划开票总计
	
	private String				exportX;   //1导出
	
	private Map<String,String> yearMap = new TreeMap<String, String>();
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"recePrecisionStatParameters",this,new String[]{"groupId","saleMan","yearSel","monthSel"}); 
	@Override
	public String doDefault()  throws Exception  {
		holdTool.clearStore();
		return SUCCESS;
	}
	/**
	 * 显示收款查询内容
	 * @return
	 */
	public String showQueryList(){
		ParameterUtils.prepareParameters(holdTool);
		
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		if(yearSel == null){
			yearSel = defaultYear+"";
		}
		int defaultMonth = calendar.get(Calendar.MONTH)+1;
		if(monthSel == null){
			monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
		}
		yearMap.put((defaultYear+1)+"",(defaultYear+1)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		return 	"showQuery";
	}

	/**
	 * 查询出结果
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryResult(){
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer select = new StringBuffer();
		select.append(" select emp," );
		/**
		 * * *************************************************************************************************************
		 *  计算计划内预计笔数   1
		 */
		select.append(" (select count(*) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm where " +
				" t.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.saleMan = emp.id " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" ),");
		// 计算计划额度    2
		select.append(" (select sum(mb.receTaxAmount) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.saleMan = emp.id " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" ),");
		/**
		 * *************************************************************************************************************
		 * 实际开票笔数   3
		 */
		select.append(" (select count(*) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid and mb.alreadyArrivedAmount > 0  " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and cm.saleMan = emp.id ");
		//按照实际卡票时间来查询
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" ),");
		//实际开票额度      4
		select.append(" (select sum(mb.alreadyArrivedAmount) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid and mb.alreadyArrivedAmount > 0 " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and cm.saleMan = emp.id ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" ),");
		//////////////////
		/**
		 * * *************************************************************************************************************
		 * 计算计划内实际笔数    5
		 */
		select.append(" (select count(*) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where mb.alreadyArrivedAmount > 0 " +
				"and t.conMainInfoSid = cm.conMainInfoSid  " +
				"and cm.saleMan = emp.id " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" ),");
		// 计算计划内实际额度       6
		select.append(" (select sum(mb.alreadyArrivedAmount) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where mb.alreadyArrivedAmount > 0 " +
				"and t.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.saleMan = emp.id " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
				"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			select.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		select.append(" )");
		StringAppender sql = new StringAppender();
		sql.append(" from Employee emp ");
		sql.append(" where emp.position in(select tr.id from OrganizationTree tr where tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			sql.append(" and emp.id = " + saleMan);
		}
		sql.append(" and  (select count(*) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid  " +
				"and cm.saleMan = emp.id " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid ");
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			sql.append("and to_char(mb.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		sql.append(") > 0 ");
		
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = commonService.list( select.toString()+sql.toString() );
			this.processExport(objList);
			return null;
		}
		else{
			info = queryService.listQueryResult("select count(*) "+sql.toString(),select.toString()+sql.toString(), info);
		}
		countPlan = applyBillService.totalRecePlaninside(groupId, saleMan, yearSel, monthSel);
		totalReal = applyBillService.totalReceReal(groupId, saleMan, yearSel, monthSel);
		totalRealPlan = applyBillService.totalReceRealInside(groupId, saleMan, yearSel, monthSel);
		return "success";
	}
	
	/**
	 * 显示收款精度明细页面
	 * @return
	 */
	public String showReceDetial(){
		info = applyBillService.receDetialQuery(info, saleMan, yearSel, monthSel);
		return "showReceDetial";
	}
	
	
	private  void processExport(List<Object[]> oArray) {	
		Employee emp = null;
		Double inPlanNum;   		//inPlan笔数
		Double inPlanLimit;  		 //inPlan额度
		Double outNumDivInNum;  	//计划外笔数/计划内笔数%
		Double inLimitDivPlanLimit;   //计划外额度/计划内额度%
		DecimalFormat decimalFormat = new DecimalFormat("###.##");
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","计划内收款笔数","计划内收款额度","实际计划内收款笔数","实际计划内额度","实际收款笔数","实际额度"
				,"计划外收款笔数","计划外额度","计划内笔数%","计划内额度%","计划外笔数/计划内笔数%","计划外额度/计划内额度%" });
		export.setTableName("收款精度统计");
		for(Object[] obj:oArray){
			emp = (Employee)obj[0];
			if(obj[1]==null){
				obj[1] = 0;
			}
			if(obj[2]==null){
				obj[2] = 0;
			}
			if(obj[3]==null){
				obj[3] = 0;
			}
			if(obj[4]==null){
				obj[4] = 0d;
			}
			if(obj[5]==null){
				obj[5] = 0;
			}
			if(obj[6]==null){
				obj[6] = 0d;
			}
			
			//inPlan笔数
			if( !obj[1].equals(0L) ){
				inPlanNum =  new Double(obj[5].toString()) / new Double(obj[1].toString()) * 100;
			}
			else{
				inPlanNum = 0.0d;
			}
			//inPlan额度
			if( !obj[2].equals(0) ){
				inPlanLimit =  new Double(obj[6].toString()) / new Double(obj[2].toString()) * 100;
			}
			else{
				inPlanLimit = 0.0d;
			}
			//计划外笔数/计划内笔数%
			if( !obj[1].equals(0L) ){
				outNumDivInNum =  (new Double(obj[3].toString()) -  new Double(obj[5].toString()))  / new Double(obj[1].toString()) * 100;
			}
			else{
				outNumDivInNum = 0.0d;
			}	
			//计划外额度/计划内额度%
			if( !obj[2].equals(0) ){
				inLimitDivPlanLimit =  (new Double(obj[4].toString())- new Double(obj[6].toString())) / new Double(obj[2].toString()) * 100;
			}
			else{
				inLimitDivPlanLimit = 0.0d;
			}
			
			export.addRow(new Object[]{ emp.getName(),obj[1],obj[2],obj[5],obj[6],obj[3],obj[4],
					new Integer(obj[3].toString()) - new Integer(obj[5].toString()), 
					new Double(obj[4].toString()) - new Double(obj[6].toString()), 
					decimalFormat.format(inPlanNum)+"%",
					decimalFormat.format(inPlanLimit)+"%",
					decimalFormat.format(outNumDivInNum)+"%",
					decimalFormat.format(inLimitDivPlanLimit)+"%" });
		}
		OutputStream os = DownloadUtils.getResponseOutput("收款精度统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	
	
	
	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}
	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
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
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public List<Employee> getEmpList() {
		return empList;
	}
	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
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
	public String getCurMonthFirstDay() {
		return curMonthFirstDay;
	}
	public void setCurMonthFirstDay(String curMonthFirstDay) {
		this.curMonthFirstDay = curMonthFirstDay;
	}
	public String getCurMonthLastDay() {
		return curMonthLastDay;
	}
	public void setCurMonthLastDay(String curMonthLastDay) {
		this.curMonthLastDay = curMonthLastDay;
	}
	public List<Object> getCountPlan() {
		return countPlan;
	}
	public void setCountPlan(List<Object> countPlan) {
		this.countPlan = countPlan;
	}
	public List<Object> getTotalReal() {
		return totalReal;
	}
	public void setTotalReal(List<Object> totalReal) {
		this.totalReal = totalReal;
	}
	public List<Object> getTotalRealPlan() {
		return totalRealPlan;
	}
	public void setTotalRealPlan(List<Object> totalRealPlan) {
		this.totalRealPlan = totalRealPlan;
	}
	public IApplyBillService getApplyBillService() {
		return applyBillService;
	}
	public void setApplyBillService(IApplyBillService applyBillService) {
		this.applyBillService = applyBillService;
	}
	public String getExportX() {
		return exportX;
	}
	public void setExportX(String exportX) {
		this.exportX = exportX;
	}
	
}

