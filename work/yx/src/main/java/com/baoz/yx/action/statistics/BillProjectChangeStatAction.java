package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;


//开票计划变更统计
@Results( {	
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/mainBillPlanChange.jsp"),
	@Result(name = "leftQuery", value = "/WEB-INF/jsp/statistics/billPlanChangeLeft.jsp"),
	@Result(name = "mainInfo", value = "/WEB-INF/jsp/statistics/billPlanChangeMainInfo.jsp"),
	@Result(name = "showDetails", value = "/WEB-INF/jsp/statistics/popShowBillPlanDetails.jsp")
})
public class BillProjectChangeStatAction  extends DispatchAction {
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("statisticsService")
	private IStatisticsService statisticsService;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService yxTypeManageService;
	
	private List<Department> groupList=new ArrayList<Department>();//组别list
	
	private List<YXTypeManage> changeTypeList=new ArrayList<YXTypeManage>();//变更类型
	
	private String groupId;//组别
	
	private Long saleMan;//销售员
	
	private Long changeCountStart;//变更次数开始
	
	private Long changeCountEnd;//变更次数结束
	
	private String changeDateStart;//变更日期范围开始
	
	private String changeDateEnd;//变更日期范围结束
	
	private String changeType;//变更类型
	
	private Long customer;//客户名称
	
	private String customerName;//客户全称
	
	private String contractNo;//合同号
	
	private String itemNo;//项目号
	
	private String				exportX;   //1导出
	
	private Long planId;//计划id
	private List<Employee> empList=new ArrayList<Employee>();//销售员
	
	private List<BillReceChangeHistory> historyList=new ArrayList<BillReceChangeHistory>();
	
	private  PageInfo 					info;

	 private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
	    		"billProjectChangeStatParameters",this,new String[]{"groupId","saleMan","changeCountStart","changeCountEnd",
	    				"changeType","changeDateStart","changeDateEnd",
	    				"customerName","customer","contractNo","itemNo"});    

	 private BigDecimal totalPreBill;//预计开票金额总计
	 

	@Override
	public String doDefault()  throws Exception  {
		holdTool.clearStore();
		return "mainInfo";
	}
	public String popQuery()throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		groupList=statisticsService.getDepartment();
		empList=statisticsService.getEmployee();
		changeTypeList=yxTypeManageService.getYXTypeManage(1024L);
		return "leftQuery";
	}
	
	@SuppressWarnings("unchecked")
	public String showMainInfo()throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sql=new StringBuffer();
		StringBuffer conditionsHql=new StringBuffer();
		sql.append("select emp.name as eName," +
				"con.con_id," +
				"(select item.con_item_id from yx_con_item_minfo item where p.fk_con_item_minfo_sid = item.con_item_minfo_sid)," +
				"con.con_name," +
				"client.name as clientName," +
				"p.real_pred_bill_date," +
				"(select max(his.change_time) from yx_con_bcplan_change_his his where his.fk_real_con_billpro_sid=p.real_con_billpro_sid and his.history_type='0')," +
				"p.real_tax_bill_amount," +
				"p.bill_change_count," +
				"tm.type_name," +
				"(select sp.stage_remark from yx_con_item_stage_plan sp where sp.contract_item_stage_id=stage.con_item_stage_sid and part.id=sp.contract_maininfo_part_id and rownum=1)," +
				"p.real_con_billpro_sid,"+
				"(select his.before_bill_date from yx_con_bcplan_change_his his where his.fk_real_con_billpro_sid=p.real_con_billpro_sid and his.history_type='0' and his.billpro_change_his_sid=(select max(hs.billpro_change_his_sid) from yx_con_bcplan_change_his hs where hs.fk_real_con_billpro_sid=p.real_con_billpro_sid and hs.history_type='0' )) as actualBillDate , " +
				" con.con_main_info_sid  " +
				" from yx_exployee emp," +
				"yx_con_main_info con," +
				"yx_real_con_bc_plan p," +
				"yx_client client," +
				"yx_con_item_stage stage," +
				"yx_con_maininfo_part part," +
				"yx_type_manage tm " +
				"where p.fk_con_main_sid = con.con_main_info_sid " +
				"and emp.id =con.sale_man " +
				"and client.id=con.con_customer " +
				"and p.fk_con_i_stage_sid=stage.con_item_stage_sid " +
				"and part.money_type=p.fk_bill_nature " +
				"and part.contract_main_id=con.con_main_info_sid " +
				"and stage.item_stage_name=tm.type_small " +
				"and tm.type_big=1023 and p.bill_change_count>0 ");
		if(groupId!=null){
			conditionsHql.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"+groupId+"%') ");
		}
		if(saleMan!=null){
			conditionsHql.append("and emp.id="+saleMan+" ");
		}
		if(changeDateStart!=null&&changeDateStart.trim().length()>0){
			conditionsHql.append(" and to_date('"+changeDateStart+"','yyyy-MM-dd') <= trunc((select max(his.change_time) from yx_con_bcplan_change_his his where his.fk_real_con_billpro_sid = p.real_con_billpro_sid and his.history_type='0'),'dd')");
		}
		if(changeDateEnd!=null&&changeDateEnd.trim().length()>0){
			conditionsHql.append(" and to_date('"+changeDateEnd+"','yyyy-MM-dd') >= trunc((select min(his.change_time) from yx_con_bcplan_change_his his where his.fk_real_con_billpro_sid = p.real_con_billpro_sid and his.history_type='0'),'dd')");
		}
		if(changeCountStart!=null){
			conditionsHql.append(" and p.bill_change_count>="+changeCountStart+" ");
		}
		if(changeCountEnd!=null){
			conditionsHql.append(" and p.bill_change_count<="+changeCountEnd+" ");
		}
		if(changeType!=null&&changeType.trim().length()>0){//bill_change_type
			conditionsHql.append(" and (select count(*) from yx_con_bcplan_change_his his where his.fk_real_con_billpro_sid=p.real_con_billpro_sid and his.bill_change_type='"+changeType+"' and his.history_type='0')>0");
		}			
		if(customer!=null){
			conditionsHql.append(" and con.con_customer="+customer+" ");
		}
		if (contractNo != null && contractNo.trim().length() > 0) {
			conditionsHql.append(" and lower(con.con_id)='" + StringUtils.lowerCase(contractNo.trim()) + "' ");
		}
		if (itemNo != null && itemNo.trim().length() > 0) {
			conditionsHql
					.append("and exists(select 1 from "
							+ "yx_con_item_minfo item "
							+ "where p.fk_con_item_minfo_sid = item.con_item_minfo_sid and lower(item.con_item_id)='"
							+ StringUtils.lowerCase(itemNo) + "') ");
		}
		conditionsHql.append(" order by con.sale_man,con.con_id,p.real_pred_bill_date ");
		StringBuffer allHql=new StringBuffer();
		allHql.append(sql);
		allHql.append(conditionsHql);
		String  sqlCount = "select count(*) from ("+ allHql +" )";
		StringBuffer totalSql=new StringBuffer();
		totalSql.append("select sum(p.real_tax_bill_amount) " +
				"from yx_exployee emp," +
				"yx_con_main_info con," +
				"yx_real_con_bc_plan p," +
				"yx_client client," +
				"yx_con_item_stage stage," +
				"yx_con_maininfo_part part," +
				"yx_type_manage tm " +
				"where p.fk_con_main_sid = con.con_main_info_sid " +
				"and emp.id =con.sale_man " +
				"and client.id=con.con_customer " +
				"and p.fk_con_i_stage_sid=stage.con_item_stage_sid " +
				"and part.money_type=p.fk_bill_nature " +
				"and part.contract_main_id=con.con_main_info_sid " +
				"and stage.item_stage_name=tm.type_small " +
				"and tm.type_big=1023 and p.bill_change_count>0 ");
		StringBuffer totalAllSql=new StringBuffer();
		totalAllSql.append(totalSql);
		totalAllSql.append(conditionsHql);
		List<BigDecimal> totalList=yxQueryService.listQueryNoPage(totalAllSql.toString());
		if(totalList!=null&&totalList.size()>0){
			BigDecimal d = totalList.get(0);
			totalPreBill = d ;
		}
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(allHql.toString());
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql(sqlCount,allHql.toString(), info);
			return "mainInfo";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String popShowDetails()throws Exception{
		String ni="aaa";
		logger.info(ni);
		RealContractBillandRecePlan plan=(RealContractBillandRecePlan)commonService.load(RealContractBillandRecePlan.class,planId);
		String hql="from BillReceChangeHistory his where his.realContractBillandRecePlan=? and his.historyType='0' order by his.billproChangeHisSid";
		historyList=commonService.list(hql, plan);
		return "showDetails";
	}
	
	
	private  void processExport(List<Object[]> oArray) {
		String stageInfo;
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","客户名称","新开票日期",
				"原始开票日期","预计开票金额","变更数","合同阶段","变更日期" });
		export.setTableName("开票计划变更统计");
		for(Object[] obj:oArray){
			stageInfo = obj[9].toString();
			if(obj[10]!=null){
				stageInfo += stageInfo +"("+ obj[10].toString()  +")"; 
			}
			export.addRow(new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[12],
					obj[7],obj[8].toString(),stageInfo,obj[6] });
		}		
		OutputStream os = DownloadUtils.getResponseOutput("开票计划变更统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	
	
	public String getOpName(Long opId){
		Employee emp=(Employee)commonService.load(Employee.class, opId);
		return emp.getName();
	}
	public IYXTypeManageService getYxTypeManageService() {
		return yxTypeManageService;
	}
	public void setYxTypeManageService(IYXTypeManageService yxTypeManageService) {
		this.yxTypeManageService = yxTypeManageService;
	}
	public Long getChangeCountStart() {
		return changeCountStart;
	}
	public void setChangeCountStart(Long changeCountStart) {
		this.changeCountStart = changeCountStart;
	}
	public Long getChangeCountEnd() {
		return changeCountEnd;
	}
	public void setChangeCountEnd(Long changeCountEnd) {
		this.changeCountEnd = changeCountEnd;
	}
	public String getChangeDateStart() {
		return changeDateStart;
	}
	public void setChangeDateStart(String changeDateStart) {
		this.changeDateStart = changeDateStart;
	}
	public String getChangeDateEnd() {
		return changeDateEnd;
	}
	public void setChangeDateEnd(String changeDateEnd) {
		this.changeDateEnd = changeDateEnd;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}
	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public IYXQueryService getYxQueryService() {
		return yxQueryService;
	}
	public void setYxQueryService(IYXQueryService yxQueryService) {
		this.yxQueryService = yxQueryService;
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
	public Long getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}

	public Long getCustomer() {
		return customer;
	}
	public void setCustomer(Long customer) {
		this.customer = customer;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public List<Employee> getEmpList() {
		return empList;
	}
	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}
	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}
	public List<YXTypeManage> getChangeTypeList() {
		return changeTypeList;
	}
	public void setChangeTypeList(List<YXTypeManage> changeTypeList) {
		this.changeTypeList = changeTypeList;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public List<BillReceChangeHistory> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<BillReceChangeHistory> historyList) {
		this.historyList = historyList;
	}
	public BigDecimal getTotalPreBill() {
		return totalPreBill;
	}
	public void setTotalPreBill(BigDecimal totalPreBill) {
		this.totalPreBill = totalPreBill;
	}
	public String getExportX() {
		return exportX;
	}
	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

}