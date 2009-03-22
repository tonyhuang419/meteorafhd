package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.vo.Department;


//售前统计

@Results( {	
	@Result(name = "search", value = "/WEB-INF/jsp/statistics/sellBeforeStatistics/sellBeforeStatisticsQuery.jsp"),
	@Result(name = "searchCondition", value = "/WEB-INF/jsp/statistics/sellBeforeStatistics/searchCondition.jsp")
})
public class SellBeforeStatAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;	
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("statisticsService")	
	private IStatisticsService 			statisticsService;

	private PageInfo			 				  info;
	private List<Employee> 					   listExp; 			// 查询显示所有的销售员
	private List<Department>				groupList;	
	private List<YXTypeManage>		dutyDepartmentIdList;				//工程责任部门list
	private List<YXTypeManage>		projectStateFollowList;             //项目跟踪状态List
	private List<YXTypeManage>      itemTrackList;						//项目跟踪结果List
	private List<YXTypeManage>      clientNList;						//客户性质LIST

	private Long 				customer;          		   // 客户名称ID
	private String 				customerName;  		   //客户名称
	private Long			 	groupId;     				   //组别
	private Long 				expId;     					    //销售员
	private String		        dutyDepartmentId;		//工程责任部门
	private String   			projectStateFollowId;   //项目跟踪状态
	private String 				itemTraceResult;         	//项目跟踪结果
	private String				projectStateSelect;       //项目跟踪标记
	private Double 			projectSumGt;				//预计金额(小于)
	private Double 			projectSumLt;				//预计金额(大于)
	private Long				dayAfter;						//最后变更时间
	private String 				estimateSignDateStart;    //合同签订日期开始
	private String 				estimateSignDateEnd;    //合同签订日期结束
	private String				importantItem;          //是否重点项目
	private String              partyAProId;            //甲方工程编号
	private BigDecimal				expectedSum;    //预计金额总计;
	private String              clientNId;      //客户性质
	//保存页面下拉框下标
	private String 				dutyDepartmentIdSS;		//工程责任部门
	private String 				projectStateFollowIdSS;	//项目跟踪状态
	private String 				itemTraceResultSS;			//项目跟踪结果
	
	private String				exportX;   //1导出
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"sellBeforeStat",this,new String[]{"customer","customerName","groupId","expId","dutyDepartmentId","projectStateFollowId","itemTraceResult"
					,"projectStateSelect","projectSumGt","projectSumLt","dutyDepartmentIdSS","projectStateFollowIdSS","itemTraceResultSS",
					"dayAfter","estimateSignDateStart","estimateSignDateEnd","importantItem","partyAProId","clientNId"}); 

	@Override
	public String doDefault(){
		holdTool.clearStore();
		return "search";
	}
	/**
	 * 显示售前合同统计查询页面
	 * @return
	 */
	public String searchCondition(){
		ParameterUtils.prepareParameters(holdTool);
		groupList = statisticsService.getDepartment();
		listExp = statisticsService.getEmployee();
		dutyDepartmentIdList=typeManageService.getYXTypeManage(1018L);
		itemTrackList = typeManageService.getYXTypeManage(1026L);
		projectStateFollowList=typeManageService.getYXTypeManage(1009L);
		clientNList = typeManageService.getYXTypeManage(1001L);
		return 	"searchCondition";
	}

	@SuppressWarnings("unchecked")
	public String queryResult(){
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer hql = new StringBuffer();
		String qsql = " select " +
				"cbs.PROJECT_NAME as a0," +       //项目名称       1
				"cbs.IMPORTANT_ITEM as a1," +     //是否重点项目       2
				"cbs.PROJECT_SUM as a2," +        //预计金额      3
				"cbs.party_a_pro_id as a3," +     //甲方工程编号      4
				"(select tm.TYPE_NAME from yx_type_manage tm where tm.TYPE_BIG = 1009 and tm.TYPE_SMALL = cbs.PROJECT_STATE_FOLLOW_ID) as a4," +  //跟踪状态    5
				"cbs.updateBy as a5," +           //最后更新时间      6
				"cbs.PROJECT_STATE_SELECT as a6," +//结果       7
				"(select tm.TYPE_NAME from yx_type_manage tm where tm.TYPE_BIG = 1001 and tm.TYPE_SMALL = client.KHXZ_ID) as a7," +    //客户性质   8
				"client.name as a8,"+      //客户名称名称       9
				"emp.name as a9," +        //销售员        10
				"cbs.SELL_BEFORE_ID as a10 ," +   // 11
				"(select ot.organization_name from yx_organization_tree ot where ot.id=emp.position ) as o ";  // 12
		
		hql.append("from yx_contract_before_sell cbs  , yx_client client , yx_exployee emp " +
			" where cbs.CUSTOMER_ID = client.id and emp.id= cbs.employee_id ");

		if (groupId!=null&&!"".equals(groupId)) {
			hql.append("and emp.position in (select tr.id from yx_organization_tree tr where tr.organization_code like '"+groupId+"%') ");
		}
		if (expId!=null&&!"".equals(expId)) {
			hql.append(" and cbs.employee_id = "+expId);
		}
		//条件：客户
		if(StringUtils.isNotBlank(customerName)){
			hql.append(" and client.FULL_NAME like '%").append(customerName).append("%'");
		}
		else if( null !=  customer && !"".equals(customer)) {
			hql.append(" and cbs.CUSTOMER_ID = " + customer);
		}	
		if (dutyDepartmentId!=null&&!"".equals(dutyDepartmentId)) {
			hql.append(" and cbs.DUTY_DEPARTMENT_ID ='"+dutyDepartmentId +"' ");
		}
		if (projectStateFollowId!=null&&!"".equals(projectStateFollowId)) {
			hql.append(" and cbs.PROJECT_STATE_FOLLOW_ID =  '"+ projectStateFollowId +"' ");
		}
		if (itemTraceResultSS!=null&&!"".equals(itemTraceResultSS)) {
			hql.append(" and cbs.ITEM_TRACERESULT = '"+ itemTraceResultSS +"' ");
		}
		if (projectStateSelect!=null&&!"".equals(projectStateSelect) &&  !"0".equals(projectStateSelect) ) {
			hql.append(" and cbs.PROJECT_STATE_SELECT = '"+ projectStateSelect +"' ");
		}
		
		//客户性质
		if(StringUtils.isNotBlank(clientNId)){  
			hql.append(" and client.KHXZ_ID = ").append(clientNId);
		}
		//是否是重点项目
		if(StringUtils.isNotBlank(importantItem)){
			if(importantItem.equals("1")){
				hql.append(" and cbs.IMPORTANT_ITEM = 1 ");
			}
			if(importantItem.equals("2")){
				hql.append(" and cbs.IMPORTANT_ITEM = 0 ");
			}
		}
		//甲方工程编号
		if(StringUtils.isNotBlank(partyAProId)){
			hql.append(" and cbs.party_a_pro_id = ").append(partyAProId);
		}
		//合同签订日期
		if (StringUtils.isNotBlank(estimateSignDateStart)) {
			hql.append("and cbs.ESTIMATE_SIGN_DATE >= ").append("to_date('"+estimateSignDateStart+"','yyyy-MM-dd') ");
		}
		if (StringUtils.isNotBlank(estimateSignDateEnd)) {
			hql.append("and cbs.ESTIMATE_SIGN_DATE <= ").append("to_date('"+estimateSignDateEnd+"','yyyy-MM-dd') ");
		}
		if ( null != projectSumLt ) {
			hql.append(" and cbs.PROJECT_SUM <= ").append(projectSumLt);
		}
		if ( null!= projectSumGt ) {
			hql.append(" and cbs.PROJECT_SUM >= ").append(projectSumGt);
		}
		
		if ( null!= dayAfter ) {
			hql.append(" and ( to_date('" + DateUtil.format(new Date(), "yyyy-MM-dd") + "','yyyy-MM-dd' ) - trunc(cbs.updateBy,'dd') ) <=  " +dayAfter );
		}
		
		hql.append( " order by cbs.updateBy desc , cbs.SELL_BEFORE_ID desc " );
		
		String sql = "select rownum , tb.a0 , tb.a1 , tb.a2 , tb.a3 , tb.a4, tb.a5, tb.a6, tb.a7, tb.a8 ,tb.a9 ,tb.a10, tb.o from ("+qsql+hql.toString()+") tb";
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(sql);
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql(SqlUtils.getCountSql(sql), sql, info); 
			String totalsql = "select sum(cbs.PROJECT_SUM) " + hql.toString();
			List a = yxQueryService.listQueryNoPage(totalsql);
			expectedSum = (BigDecimal) a.get(0);
			return "search";
		}
	}
	
	private  void processExport(List<Object[]> oArray) {	
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"序号","组别","销售员","客户简称","客户性质","项目名称","重点项目",
				"预计金额","甲方工程编号","跟踪状态","最后更新时间","结果" });
		export.setTableName("售前合同统计");
		String importantItem = null;
		String projectStateSelect = null;
		for(Object[] obj:oArray){
			if(obj[2].equals("1")){
				importantItem = "Y";
			}
			if(obj[2].equals("0")){
				importantItem = "N";
			}
			if(obj[7].equals("1")){
				projectStateSelect = "ON";
			}
			if(obj[7].equals("2")){
				projectStateSelect = "OFF";
			}
			export.addRow(new Object[]{((BigDecimal)obj[0]).longValue(),obj[12],obj[10],obj[9],obj[8],obj[1],importantItem,obj[3],obj[4],
					obj[5],obj[6],projectStateSelect				
			});
		}
		OutputStream os = DownloadUtils.getResponseOutput("售前合同统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
		
	}


	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

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


	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public List<YXTypeManage> getDutyDepartmentIdList() {
		return dutyDepartmentIdList;
	}

	public void setDutyDepartmentIdList(List<YXTypeManage> dutyDepartmentIdList) {
		this.dutyDepartmentIdList = dutyDepartmentIdList;
	}

	public String getDutyDepartmentId() {
		return dutyDepartmentId;
	}

	public void setDutyDepartmentId(String dutyDepartmentId) {
		this.dutyDepartmentId = dutyDepartmentId;
	}

	public List<YXTypeManage> getItemTrackList() {
		return itemTrackList;
	}

	public void setItemTrackList(List<YXTypeManage> itemTrackList) {
		this.itemTrackList = itemTrackList;
	}

	public List<YXTypeManage> getProjectStateFollowList() {
		return projectStateFollowList;
	}

	public void setProjectStateFollowList(List<YXTypeManage> projectStateFollowList) {
		this.projectStateFollowList = projectStateFollowList;
	}

	public String getProjectStateFollowId() {
		return projectStateFollowId;
	}

	public void setProjectStateFollowId(String projectStateFollowId) {
		this.projectStateFollowId = projectStateFollowId;
	}

	public String getItemTraceResult() {
		return itemTraceResult;
	}

	public void setItemTraceResult(String itemTraceResult) {
		this.itemTraceResult = itemTraceResult;
	}

	public String getProjectStateSelect() {
		return projectStateSelect;
	}

	public void setProjectStateSelect(String projectStateSelect) {
		this.projectStateSelect = projectStateSelect;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public List<Employee> getListExp() {
		return listExp;
	}

	public void setListExp(List<Employee> listExp) {
		this.listExp = listExp;
	}

	public Double getProjectSumGt() {
		return projectSumGt;
	}

	public void setProjectSumGt(Double projectSumGt) {
		this.projectSumGt = projectSumGt;
	}

	public Double getProjectSumLt() {
		return projectSumLt;
	}

	public void setProjectSumLt(Double projectSumLt) {
		this.projectSumLt = projectSumLt;
	}

	public String getDutyDepartmentIdSS() {
		return dutyDepartmentIdSS;
	}

	public void setDutyDepartmentIdSS(String dutyDepartmentIdSS) {
		this.dutyDepartmentIdSS = dutyDepartmentIdSS;
	}

	public String getProjectStateFollowIdSS() {
		return projectStateFollowIdSS;
	}

	public void setProjectStateFollowIdSS(String projectStateFollowIdSS) {
		this.projectStateFollowIdSS = projectStateFollowIdSS;
	}

	public String getItemTraceResultSS() {
		return itemTraceResultSS;
	}

	public void setItemTraceResultSS(String itemTraceResultSS) {
		this.itemTraceResultSS = itemTraceResultSS;
	}

	public Long getDayAfter() {
		return dayAfter;
	}

	public void setDayAfter(Long dayAfter) {
		this.dayAfter = dayAfter;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}


	public BigDecimal getExpectedSum() {
		return expectedSum;
	}
	public void setExpectedSum(BigDecimal expectedSum) {
		this.expectedSum = expectedSum;
	}
	public List<YXTypeManage> getClientNList() {
		return clientNList;
	}
	public void setClientNList(List<YXTypeManage> clientNList) {
		this.clientNList = clientNList;
	}

	public String getEstimateSignDateStart() {
		return estimateSignDateStart;
	}
	public void setEstimateSignDateStart(String estimateSignDateStart) {
		this.estimateSignDateStart = estimateSignDateStart;
	}
	public String getEstimateSignDateEnd() {
		return estimateSignDateEnd;
	}
	public void setEstimateSignDateEnd(String estimateSignDateEnd) {
		this.estimateSignDateEnd = estimateSignDateEnd;
	}
	public String getImportantItem() {
		return importantItem;
	}
	public void setImportantItem(String importantItem) {
		this.importantItem = importantItem;
	}
	public String getPartyAProId() {
		return partyAProId;
	}
	public void setPartyAProId(String partyAProId) {
		this.partyAProId = partyAProId;
	}
	public String getClientNId() {
		return clientNId;
	}
	public void setClientNId(String clientNId) {
		this.clientNId = clientNId;
	}
	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}
	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

}
