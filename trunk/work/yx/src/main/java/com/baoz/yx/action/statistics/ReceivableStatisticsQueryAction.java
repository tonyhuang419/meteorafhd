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
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;

/**
 * 应收统计
 * @author yepeng
 *
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/statistics/receivableStatisticsList.jsp"),
	@Result(name = "showQuery", value = "/WEB-INF/jsp/statistics/receivableStatisticsQuery.jsp"),
	@Result(name = "calAccountAgeTotal", value = "/WEB-INF/jsp/statistics/receivableAgeTotal.jsp"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/statistics/success.jsp")
})
public class ReceivableStatisticsQueryAction  extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("statisticsService")	
	private IStatisticsService 			statisticsService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;

	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;
	
	private PageInfo					info;

	private List<Department>			groupList;	
	private List<Employee> 				empList; 			// 查询显示销售员
	private List<YXTypeManage>      	stageList;

	private Long						groupId;

	private Long 						saleMan;		//销售员

	private String 						contractNum;	//合同号

	private String 						itemId;			//项目号

	private String 						conStage;		//合同阶段

	private String 						auxiliaryStage;  //辅助阶段

	private Integer						minLogicDayAccountAge;    //逻辑帐龄开始天数

	private Integer						maxLogicDayAccountAge;    //逻辑帐龄结束天数

	private Integer						minLogicMonthAccountAge;    //逻辑帐龄开始月份

	private Integer						maxLogicMonthAccountAge;    //逻辑帐龄结束月份

	private Integer						minRealDayAccountAge;    //实际帐龄开始天数

	private Integer						maxRealDayAccountAge;    //世界帐龄结束天数

	private Integer						minRealMonthAccountAge;    //实际帐龄开始月份

	private Integer						maxRealMonthAccountAge;    //世界帐龄结束月份

	private String 						historyType;  		//变更类型    不确定名称

	private BigDecimal						sumRealBillAmount;    //实际开票金额

	private BigDecimal						sumRealReceAmount;    //实际收款金额

	private BigDecimal						sumRealTaxBillAmount;

	private BigDecimal						sumRealTaxReceAmount;

	private BigDecimal						sumShouldReceAmount;

	private BigDecimal						sumRealArriveAmount;

	private Long 				customer;            // 客户名称ID

	private String 				customerName;    //客户名称

	private String 				conIdBy;    //按合同号排序

	private String 				accountAgeBy; //按帐龄排序

	private String				exportX;   //1导出

	private Date todayTime;
	
	private List billType;   //开票类型
	
	private List<YXTypeManage>      	billTypeList;
	
	private String  accountAgeTotal;
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"receivableStatisticsQueryParameters",this,new String[]{"groupId","saleMan","customerName","customer","contractNum","itemId","conStage","auxiliaryStage","minLogicDayAccountAge","maxLogicDayAccountAge",
					"minLogicMonthAccountAge","maxLogicMonthAccountAge","minRealDayAccountAge","maxRealDayAccountAge","minRealMonthAccountAge",
					"maxRealMonthAccountAge","historyType","conIdBy","accountAgeBy","accountAgeTotal","billType"}); 
	@Override
	public String doDefault()  throws Exception  {
		holdTool.clearStore();
		return SUCCESS;
	}
	public String showQueryList(){
		ParameterUtils.prepareParameters(holdTool);
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		stageList = typeManageService.getYXTypeManage(1023L);
		billTypeList = typeManageService.getYXTypeManage(1004L);
		return 	"showQuery";
	}
	
	public String getShouldTicketType(Long realPlanId){
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realPlanId);
		
		if("4".equals(plan.getBillType())){
			//如果是收据，先判断是否关联发票。
			String isRelationHql = "select count(*) from RelationBillAndReceipt  where receiptRealId = ?";
			Number isRelationCount = (Number)commonService.uniqueResult(isRelationHql, plan.getRealConBillproSid());
			if(isRelationCount.longValue() != 0){
				//如果关联发票了看是否开票，
				String returnType="";
				String realPlanHql= "select realplan from RelationBillAndReceipt relation,RealContractBillandRecePlan realplan where relation.billRealId = realplan.realConBillproSid and relation.receiptRealId = ?";
				List<RealContractBillandRecePlan> realList = commonService.list(realPlanHql, plan.getRealConBillproSid());
				for (RealContractBillandRecePlan realContractBillandRecePlan : realList) {
					//如果开票了显示关联过的发票的票据类型，如果没有开票显示收据
					if(realContractBillandRecePlan.getBillInvoiceAmount()!=null&&realContractBillandRecePlan.getBillInvoiceAmount()!=0){
						YXTypeManage tm = (YXTypeManage)commonService.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = ?", realContractBillandRecePlan.getBillType());
						returnType += tm.getTypeName()+"/";
					}else{
						YXTypeManage tm = (YXTypeManage)commonService.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = '4' ");
						returnType += tm.getTypeName()+"/";
					}
				}
				
				returnType = StringUtils.substring(returnType, 0, returnType.length()-1);
				return returnType;
			}else{
				//如果没有关联发票，返回收据
				YXTypeManage tm = (YXTypeManage)commonService.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = ?", plan.getBillType());
				return tm.getTypeName();
			}
		}else{
			/**
			 * 如果不是收据直接返回票据类型
			 */
			YXTypeManage tm = (YXTypeManage)commonService.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = ?", plan.getBillType());
			return tm.getTypeName();
		}
	}

	public String queryList(){
		holdTool.clearStore();
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String queryResult(){
		logger.info(billType);
		ParameterUtils.prepareParameters(holdTool);
		String select = " select " +
				"real.real_con_billpro_sid as realId," +  //计划ID      1
				"(select ym.TYPE_NAME from yx_type_manage ym where ym.TYPE_BIG = 1004 and ym.TYPE_SMALL = real.fk_bill_type) as billType," +  //开票类型 2
				"nvl(real.real_tax_bill_amount,0) as billAmount," +  //预计开票金额  3
				"nvl(real.BILL_INVOICE_AMOUNT,0) as billIIAmount,"+//实际开票金额    4
				"nvl(real.real_tax_rece_amount,0) as realReceAmount,"+ //计划收款金额  5
				"nvl(real.real_arrive_amount,0) as arriveAmount," +  //实际收款金额   6
				"(nvl(real.SHOULD_RECE_AMOUNT,0) - nvl(real.real_arrive_amount,0) ) ,"+   //应收余额   7
				"real.logic_day_account_age,"+  //逻辑天   8 
				"real.logic_month_account_age,"+  //逻辑月 9
				"real.real_day_account_age,"+  //实际天10
				"real.real_month_account_age,"+  //实际月 11
				"cm.con_main_info_sid as conId," +   // 合同 id  12
				"cm.con_id as conNo," +   // 合同号   13  
				"cm.con_name as conName," +   // 合同名称   14
				"(select im.con_item_id from yx_con_item_minfo im where real.fk_con_item_minfo_sid = im.con_item_minfo_sid) as itemNo," +  //项目号 15
				"emp.name empName," +   //销售员  16
				"yc.name as ycName," +//客户名称   17
				"cm.party_a_con_id ";   //甲方合同号  18
		
		StringBuffer sql = new StringBuffer();
		sql.append(" from yx_real_con_bc_plan real,yx_con_main_info cm,yx_exployee emp,yx_client yc where " +
				"cm.con_main_info_sid = real.fk_con_main_sid " +
				" and emp.id = cm.sale_man " +
				"and real.SHOULD_RECE_AMOUNT - decode(real.real_arrive_amount,null,0,real.real_arrive_amount) > 0 " +
				"and real.SHOULD_RECE_AMOUNT is not null " +
		"and yc.id = cm.con_customer ");
		//组别
		if (groupId!=null&&!"".equals(groupId)) {
			sql.append("and emp.position in (select tr.id from yx_organization_tree tr where tr.organization_code like '"+groupId+"%') ");
		}
		//销售员
		if (saleMan!=null&&!"".equals(saleMan)) {
			sql.append(" and cm.sale_man = "+saleMan);
		}
		if(StringUtils.isNotBlank(customerName)){
			YXClientCode yc = (YXClientCode) commonService.uniqueResult(" from YXClientCode yc where yc.fullName = '"+customerName+"'");
			if(yc == null){
				customer = null;
				if (StringUtils.isNotBlank(customerName)) {
					sql.append(" and yc.FULL_NAME like '%").append(customerName).append("%'");
				}
			}
		}
		//客户ID
		if(customer!=null&&!"".equals(customer)) {
			sql.append(" and yc.id = " + customer);
		}
		//合同号
		if (StringUtils.isNotBlank(contractNum) && StringUtils.isNotBlank(contractNum)) {
			sql.append(" and lower(cm.con_id) like '%").append(StringUtils.lowerCase(StringUtils.trim(contractNum))).append("%'");
		}
		//项目号
		if (StringUtils.isNotBlank(itemId) && StringUtils.isNotBlank(itemId)) {
			sql.append(" and exists (select 1 from yx_con_item_minfo im where lower(im.con_item_id) like '%"+StringUtils.lowerCase(StringUtils.trim(itemId))+"%' and real.fk_con_item_minfo_sid = im.con_item_minfo_sid ) ");
		}
		//合同阶段
		if (StringUtils.isNotBlank(conStage) && StringUtils.isNotBlank(conStage)) {
			sql.append(" and exists (select 1 from yx_con_item_stage cs where cs.item_stage_name ="+conStage+" and real.fk_con_i_stage_sid = cs.con_item_stage_sid ) ");
		}
		//合同辅助阶段
		if (StringUtils.isNotBlank(auxiliaryStage) && StringUtils.isNotBlank(auxiliaryStage)) {
			sql.append(" and exists (select 1 from yx_con_item_stage cs,YX_CON_ITEM_STAGE_PLAN csp where lower(csp.STAGE_REMARK) like '%"+StringUtils.lowerCase(StringUtils.trim(auxiliaryStage))+"%' and cs.con_item_stage_sid = real.fk_con_i_stage_sid and csp.CONTRACT_ITEM_STAGE_ID = cs.con_item_stage_sid ) ");
		}
		//逻辑帐龄开始天数
		if (minLogicDayAccountAge!=null && !"".equals(minLogicDayAccountAge)) {
			sql.append(" and real.logic_day_account_age >= ").append(minLogicDayAccountAge);
		}
		//逻辑帐龄结束天数
		if (maxLogicDayAccountAge!=null && !"".equals(maxLogicDayAccountAge)) {
			sql.append(" and real.logic_day_account_age <= ").append(maxLogicDayAccountAge);
		}
		//逻辑帐龄开始月份
		if (minLogicMonthAccountAge!=null && !"".equals(minLogicMonthAccountAge)) {
			sql.append(" and real.logic_month_account_age >= ").append(minLogicMonthAccountAge);
		}
		//逻辑帐龄结束月份
		if (maxLogicMonthAccountAge!=null && !"".equals(maxLogicMonthAccountAge)) {
			sql.append(" and real.logic_month_account_age <= ").append(maxLogicMonthAccountAge);
		}
		//实际帐龄开始天数
		if (minRealDayAccountAge!=null && !"".equals(minRealDayAccountAge)) {
			sql.append(" and real.real_day_account_age >= ").append(minRealDayAccountAge);
		}
		//实际帐龄结束天数
		if (maxRealDayAccountAge!=null && !"".equals(maxRealDayAccountAge)) {
			sql.append(" and real.real_day_account_age <= ").append(maxRealDayAccountAge);
		}
		//实际帐龄开始月份
		if (minRealMonthAccountAge!=null && !"".equals(minRealMonthAccountAge)) {
			sql.append(" and real.real_month_account_age >= ").append(minRealMonthAccountAge);
		}
		//实际帐龄结束月份
		if (maxRealMonthAccountAge!=null && !"".equals(maxRealMonthAccountAge)) {
			sql.append(" and real.real_month_account_age <= ").append(maxRealMonthAccountAge);
		}
		if(billType!=null){
			sql.append(" and real.fk_bill_type in (").append(StringUtils.join(billType,",")).append(") ") ;
		}
		//排序
		if(StringUtils.isNotBlank(conIdBy) || StringUtils.isNotBlank(accountAgeBy)){
			StringBuffer orderBy = new StringBuffer();
			if(StringUtils.isNotBlank(conIdBy)){
				if(orderBy.length() > 0){
					orderBy.append(" , ");
				}
				orderBy.append(" cm.con_id");
			}
			if(StringUtils.isNotBlank(accountAgeBy)){
				if(orderBy.length() > 0){
					orderBy.append(" , ");
				}
				orderBy.append(" decode(real.real_day_account_age,null,0,real.real_day_account_age) desc ");
			}
			sql.append(" order by ").append(orderBy);
		}
		else{
			sql.append(" order by cm.con_id,decode(real.real_day_account_age,null,0,real.real_day_account_age) desc ");
		}
		////////////////计算统计字段//////////////////////////////////////
		StringBuffer totalSql = new StringBuffer();
		totalSql.append("select " +
				"sum(real.BILL_INVOICE_AMOUNT)," +
				"sum(real.real_arrive_amount),"+ 
				"sum(real.real_tax_bill_amount),"+ 
				"sum(nvl(real.real_tax_rece_amount,0))," +
				"sum(real.SHOULD_RECE_AMOUNT)," +
				"sum(real.real_arrive_amount) "
				);
		StringBuffer totalAllSql=new StringBuffer();
		totalAllSql.append(totalSql);
		totalAllSql.append(sql);
		logger.info(totalAllSql.toString());
		List<Object[]> totalList=yxQueryService.listQueryNoPage(totalAllSql.toString());
		if(totalList!=null&&totalList.size()>0){
			Object[] d=totalList.get(0);
			sumRealBillAmount=(BigDecimal) d[0];
			sumRealReceAmount=(BigDecimal)d[1];
			sumRealTaxBillAmount=(BigDecimal)d[2];
			sumRealTaxReceAmount=(BigDecimal)d[3];
			sumShouldReceAmount=(BigDecimal) d[4];
			sumRealArriveAmount = (BigDecimal) d[5];
		}
		
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage( select+sql.toString());
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql("select count(*) "+sql.toString(), select+sql.toString(),	info);
		}
		return "success";
	}
	
	public String calAccountAgeTotal(){
		todayTime = new Date();
		return "calAccountAgeTotal";
	}

	private  void processExport(List<Object[]> oArray) {	
			
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","甲方合同号","客户名称","开票类型","应收票据类型","计划开票金额"
				,"实际开票金额","计划收款金额","实际收款金额","应收余额","逻天","逻月","实天","实月"});
		export.setTableName("应收统计款");
		for(Object[] obj:oArray){
			export.addRow(new Object[]{obj[15],obj[12],obj[14],obj[13],obj[17],obj[16],obj[1],
					this.getShouldTicketType(((BigDecimal)obj[0]).longValue()),
					obj[2],obj[3],obj[4],obj[5],obj[6],
					returnNullOrLong(obj[7]),
					returnNullOrLong(obj[8]),
					returnNullOrLong(obj[9]),
					returnNullOrLong(obj[10])
			});
		}
		OutputStream os = DownloadUtils.getResponseOutput("应收款统计导出.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	
	public Object returnNullOrLong(Object val){
		Object objTemp = null;
		if(val==null){
			objTemp = "";
		}
		else{
			objTemp = ((BigDecimal)val).longValue();
		}
		return objTemp;
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
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXTypeManage> getStageList() {
		return stageList;
	}
	public void setStageList(List<YXTypeManage> stageList) {
		this.stageList = stageList;
	}
	public Long getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getConStage() {
		return conStage;
	}
	public void setConStage(String conStage) {
		this.conStage = conStage;
	}
	public String getAuxiliaryStage() {
		return auxiliaryStage;
	}
	public void setAuxiliaryStage(String auxiliaryStage) {
		this.auxiliaryStage = auxiliaryStage;
	}

	public Integer getMinLogicDayAccountAge() {
		return minLogicDayAccountAge;
	}
	public void setMinLogicDayAccountAge(Integer minLogicDayAccountAge) {
		this.minLogicDayAccountAge = minLogicDayAccountAge;
	}
	public Integer getMaxLogicDayAccountAge() {
		return maxLogicDayAccountAge;
	}
	public void setMaxLogicDayAccountAge(Integer maxLogicDayAccountAge) {
		this.maxLogicDayAccountAge = maxLogicDayAccountAge;
	}
	public Integer getMinLogicMonthAccountAge() {
		return minLogicMonthAccountAge;
	}
	public void setMinLogicMonthAccountAge(Integer minLogicMonthAccountAge) {
		this.minLogicMonthAccountAge = minLogicMonthAccountAge;
	}
	public Integer getMaxLogicMonthAccountAge() {
		return maxLogicMonthAccountAge;
	}
	public void setMaxLogicMonthAccountAge(Integer maxLogicMonthAccountAge) {
		this.maxLogicMonthAccountAge = maxLogicMonthAccountAge;
	}
	public Integer getMinRealDayAccountAge() {
		return minRealDayAccountAge;
	}
	public void setMinRealDayAccountAge(Integer minRealDayAccountAge) {
		this.minRealDayAccountAge = minRealDayAccountAge;
	}
	public Integer getMaxRealDayAccountAge() {
		return maxRealDayAccountAge;
	}
	public void setMaxRealDayAccountAge(Integer maxRealDayAccountAge) {
		this.maxRealDayAccountAge = maxRealDayAccountAge;
	}
	public Integer getMinRealMonthAccountAge() {
		return minRealMonthAccountAge;
	}
	public void setMinRealMonthAccountAge(Integer minRealMonthAccountAge) {
		this.minRealMonthAccountAge = minRealMonthAccountAge;
	}
	public Integer getMaxRealMonthAccountAge() {
		return maxRealMonthAccountAge;
	}
	public void setMaxRealMonthAccountAge(Integer maxRealMonthAccountAge) {
		this.maxRealMonthAccountAge = maxRealMonthAccountAge;
	}
	public String getHistoryType() {
		return historyType;
	}
	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public BigDecimal getSumRealTaxBillAmount() {
		return sumRealTaxBillAmount;
	}
	public void setSumRealTaxBillAmount(BigDecimal sumRealTaxBillAmount) {
		this.sumRealTaxBillAmount = sumRealTaxBillAmount;
	}
	public BigDecimal getSumRealTaxReceAmount() {
		return sumRealTaxReceAmount;
	}
	public void setSumRealTaxReceAmount(BigDecimal sumRealTaxReceAmount) {
		this.sumRealTaxReceAmount = sumRealTaxReceAmount;
	}


	public BigDecimal getSumRealArriveAmount() {
		return sumRealArriveAmount;
	}
	public void setSumRealArriveAmount(BigDecimal sumRealArriveAmount) {
		this.sumRealArriveAmount = sumRealArriveAmount;
	}

	public BigDecimal getSumRealReceAmount() {
		return sumRealReceAmount;
	}
	public void setSumRealReceAmount(BigDecimal sumRealReceAmount) {
		this.sumRealReceAmount = sumRealReceAmount;
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
	public String getConIdBy() {
		return conIdBy;
	}
	public void setConIdBy(String conIdBy) {
		this.conIdBy = conIdBy;
	}
	public String getAccountAgeBy() {
		return accountAgeBy;
	}
	public void setAccountAgeBy(String accountAgeBy) {
		this.accountAgeBy = accountAgeBy;
	}
	public String getExportX() {
		return exportX;
	}
	public void setExportX(String exportX) {
		this.exportX = exportX;
	}
	public Date getTodayTime() {
		return todayTime;
	}
	public void setTodayTime(Date todayTime) {
		this.todayTime = todayTime;
	}
	public IApplyBillService getBillService() {
		return billService;
	}
	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
	}
	public String getAccountAgeTotal() {
		return accountAgeTotal;
	}
	public void setAccountAgeTotal(String accountAgeTotal) {
		this.accountAgeTotal = accountAgeTotal;
	}
	
	public List getBillType() {
		return billType;
	}
	public void setBillType(List billType) {
		this.billType = billType;
	}
	public List<YXTypeManage> getBillTypeList() {
		return billTypeList;
	}
	public void setBillTypeList(List<YXTypeManage> billTypeList) {
		this.billTypeList = billTypeList;
	}
	public BigDecimal getSumRealBillAmount() {
		return sumRealBillAmount;
	}
	public void setSumRealBillAmount(BigDecimal sumRealBillAmount) {
		this.sumRealBillAmount = sumRealBillAmount;
	}
	public BigDecimal getSumShouldReceAmount() {
		return sumShouldReceAmount;
	}
	public void setSumShouldReceAmount(BigDecimal sumShouldReceAmount) {
		this.sumShouldReceAmount = sumShouldReceAmount;
	}
}
