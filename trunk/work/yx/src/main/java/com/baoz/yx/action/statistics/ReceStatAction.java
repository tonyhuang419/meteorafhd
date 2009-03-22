package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;

//收款统计
@Results( {
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/mainReceStatistics.jsp"),
	@Result(name = "leftQuery", value = "/WEB-INF/jsp/statistics/receStatisticsLeftQuery.jsp"),
	@Result(name = "mainInfo", value = "/WEB-INF/jsp/statistics/receStatisticsMainInfo.jsp"),
	@Result(name = "nextSix", value = "/WEB-INF/jsp/statistics/receStatisticsNextSix.jsp")})
	public class ReceStatAction extends DispatchAction {

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

	private List<Department> groupList = new ArrayList<Department>();// 组别list

	private String groupId;// 组别

	private Long saleMan;// 销售员

	private String preReceStartDate;// 计划收款日期开始

	private String preReceEndDate;// 计划收款日期结束

	private String[] receSituation;// 收款情况

	private String receSituation0;   //
	private String receSituation1;
	private String receSituation2;

	private Long customer;// 客户名称

	private String customerName;// 客户全称

	private String contractNo;// 合同号

	private String itemNo;// 项目号

	private List<Employee> empList = new ArrayList<Employee>();// 销售员

	private PageInfo info;

	private String nextSix; //查询下6个月

	List<Object[]> oArray;

	private String				exportX;   //1导出

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"receStatParameters", this, new String[] { "groupId", "saleMan",
					"preReceStartDate", "preReceEndDate", "receSituation0","receSituation1","receSituation2",
					"customerName", "customer", "contractNo", "itemNo","nextSix" });

	private BigDecimal totalPreRece;// 应收款总额

	private BigDecimal totalRealRece;// 已收款总额

	private BigDecimal accumulatePreRece;//累计应收款总额

	private BigDecimal accumulateRealRece;//累计收款总额

	private BigDecimal balanceRece; //累计应收余额

	private BigDecimal billInvoiceAmount;  // 已开票金额

	@Override
	public String doDefault() throws Exception {
		holdTool.clearStore();
		return "mainInfo";
	}

	public String popQuery() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		groupList = statisticsService.getDepartment();
		empList = statisticsService.getEmployee();
		return "leftQuery";
	}

	@SuppressWarnings("unchecked")
	public String showMainInfo() throws Exception {
		ParameterUtils.prepareParameters(holdTool);

		if(nextSix!=null && nextSix.equals("true")){
			String hql = " select trunc(r.realPredReceDate, 'month'), "+
			" cmi.ContractType, "+
			" sum(nvl(r.realTaxReceAmount, 0) - nvl(r.realArriveAmount, 0)) "+
			" from ContractMainInfo cmi, RealContractBillandRecePlan r "+
			" where r.conMainInfoSid = cmi.conMainInfoSid " +
			" and r.realPredReceDate >= ?"+
			" and r.realPredReceDate < ?"+
			" group by trunc(r.realPredReceDate, 'month'), cmi.ContractType "+
			" order by trunc(r.realPredReceDate, 'month') ";
			oArray =  commonService.list(hql,
					DateUtils.truncate(DateUtils.addMonths(new Date(),1) , Calendar.MONTH), 
					DateUtils.truncate(DateUtils.addMonths(new Date(),7) , Calendar.MONTH));

			oArray = this.processDate(oArray);

			if(exportX!=null && exportX.equals("1")){
				this.processExport2(oArray);
				return null;
			}
			else{
				return "nextSix";
			}		
		}
		else{
			StringBuffer sql1 = new StringBuffer();
			StringBuffer conditionsHql = new StringBuffer();
			sql1.append("select emp.name," + "con.con_id,"
					+ "(select item.con_item_id from "
					+ "yx_con_item_minfo item "
					+ "where p.fk_con_item_minfo_sid = item.con_item_minfo_sid),"
					+ "con.con_name,"
					+ "p.real_pred_rece_date,"
					+ "tm.type_name,"
					+ "(p.real_tax_rece_amount-nvl((select sum(info.amount)from yx_plan_rece_info info where info.plan_id = p.real_con_billpro_sid and info.amount_time <trunc(p.real_pred_rece_date, 'month')),0)),"
					+ "(nvl(p.real_arrive_amount,0)-nvl((select sum(info.amount) from yx_plan_rece_info info where info.plan_id = p.real_con_billpro_sid and info.amount_time <trunc(p.real_pred_rece_date, 'month')),0)),"
					+ "p.real_tax_rece_amount,"
					+ "nvl(p.real_arrive_amount,0)," 
					+ "yx.NAME as customerName , "  
					+ "con.con_main_info_sid, "
					+ "yx.full_name," 
					+ "nvl(p.BILL_INVOICE_AMOUNT,0) ");
					
	conditionsHql.append(" from yx_exployee emp," 
					+ "yx_con_main_info con,"
					+ "yx_real_con_bc_plan p,"
					+ "yx_client yx,"
					+ "yx_type_manage tm "
					+ "where p.fk_con_main_sid = con.con_main_info_sid "
					+ "and tm.type_small = p.fk_bill_type "
					+ "and tm.type_big = 1004 "
					+ "and con.con_customer = yx.id "
					+ "and emp.id = con.sale_man ");

			if (groupId != null) {
				conditionsHql
				.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
						+ groupId + "%') ");
			}
			if (saleMan != null) {
				conditionsHql.append("and emp.id=" + saleMan + " ");
			}
			if (preReceStartDate != null && preReceStartDate.trim().length() > 0) {
				conditionsHql.append(" and to_date('" + preReceStartDate
						+ "','yyyy-MM-dd')<= trunc(p.real_pred_rece_date,'dd') ");
			}
			if (preReceEndDate != null && preReceEndDate.trim().length() > 0) {
				conditionsHql.append(" and to_date('" + preReceEndDate
						+ "','yyyy-MM-dd')>= trunc(p.real_pred_rece_date,'dd') ");
			}
			//选择checkbooxlist
			/*if (receSituation != null && receSituation.length > 0) {
			conditionsHql.append(" and ( 1=2 ");
			for(int i=0;i<receSituation.length;i++){
				if (receSituation[i].equals("0")) {// 0代表部分收款
					conditionsHql.append(" or (p.real_arrive_amount > 0 and p.real_arrive_amount < p.real_tax_rece_amount)");
				} else if (receSituation[i].equals("1")) {// 1代表未收
						conditionsHql
						.append(" or (p.real_arrive_amount=0 or p.real_arrive_amount is null) ");
				}else if(receSituation[i].equals("2")){//2全部收款
						conditionsHql.append(" or (p.real_arrive_amount >= p.real_tax_rece_amount) ");
				}
			}
			conditionsHql.append(" ) ");
		}*/
			//三个checkbox
			if(receSituation0 != null || receSituation1 != null || receSituation2 != null){
				conditionsHql.append(" and ( 1=2 ");
				if (receSituation0 != null) {// 0代表部分收款
					conditionsHql.append(" or (p.real_arrive_amount > 0 and p.real_arrive_amount < p.real_tax_rece_amount)");
				}
				if (receSituation1 != null) {// 1代表未收
					conditionsHql.append(" or (p.real_arrive_amount=0 or p.real_arrive_amount is null) ");
				}
				if(receSituation2 != null){//2全部收款
					conditionsHql.append(" or (p.real_arrive_amount >= p.real_tax_rece_amount) ");
				}
				conditionsHql.append(" ) ");
			}
			if (customer != null) {
				conditionsHql.append(" and con.con_customer=" + customer + " ");
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
			conditionsHql.append(" order by con.sale_man,con.con_id,p.real_pred_rece_date ");
			StringBuffer allHql = new StringBuffer();
			allHql.append(sql1);
			allHql.append(conditionsHql);
			String sqlCount = "select count(*) from (" + allHql + " )";
			logger.info(allHql.toString());
			StringBuffer totalSql = new StringBuffer();
			totalSql.append("select sum(p.real_tax_rece_amount-nvl((select sum(info.amount)from yx_plan_rece_info info where info.plan_id = p.real_con_billpro_sid and info.amount_time <trunc(p.real_pred_rece_date, 'month')),0))," +
					"sum(nvl(p.real_arrive_amount,0)-nvl((select sum(info.amount)from yx_plan_rece_info info where info.plan_id = p.real_con_billpro_sid and info.amount_time <trunc(p.real_pred_rece_date, 'month')),0)),"
					+ "sum(p.real_tax_rece_amount),"
					+ "sum(nvl(p.real_arrive_amount,0))," +
					"sum(p.real_tax_rece_amount - p.real_arrive_amount)," +
					"sum(p.BILL_INVOICE_AMOUNT) "
					);
			StringBuffer totalAllSql=new StringBuffer();
			totalAllSql.append(totalSql);
			totalAllSql.append(conditionsHql);
			List<Object[]> totalList=yxQueryService.listQueryNoPage(totalAllSql.toString());
			if(totalList!=null&&totalList.size()>0){
				Object[] d=totalList.get(0);
				totalPreRece=(BigDecimal)d[0];
				totalRealRece=(BigDecimal)d[1];
				accumulatePreRece=(BigDecimal)d[2];
				accumulateRealRece=(BigDecimal)d[3];
				balanceRece=(BigDecimal)d[4];
				billInvoiceAmount = (BigDecimal) d[5];
			}

			if(exportX!=null && exportX.equals("1")){
				List<Object[]> objList  = yxQueryService.listQueryNoPage( allHql.toString());
				this.processExport(objList);
				return null;
			}
			else{
				info = yxQueryService.listQueryResultBySql(sqlCount, allHql.toString(),	info);
			}
			return "mainInfo";
		}
	}

	private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","客户名称","计划收款日期","计划收款金额","已到款金额","收款余额" });
		export.setTableName("预计收款统计");
		for(Object[] obj:oArray){
			Object[] rowObj = new Object[]{obj[0],obj[1],obj[2],obj[3],obj[12],obj[4],obj[8],obj[9],
					null};
			Double obj8 = obj[8] != null ? ((BigDecimal)obj[8]).doubleValue() : 0.0;
			Double obj9 = obj[9] != null ? ((BigDecimal)obj[9]).doubleValue() : 0.0;
			rowObj[8]=obj8-obj9;
			export.addRow(rowObj);
		}
		OutputStream os = DownloadUtils.getResponseOutput("预计收款统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	
	private  void processExport2(List<Object[]> oArray) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
		TableExport export = TableExportFactory.createExcelTableExport();
		export.setTableName("预计收款统计");
		int i=0;
		for(Object[] obj:oArray){
			if(i==0){
				export.addTitle( new String[]{null,dateFormat.format(obj[1]),
						dateFormat.format(obj[2]),dateFormat.format(obj[3]),
						dateFormat.format(obj[4]),dateFormat.format(obj[5]),
								dateFormat.format(obj[6]) } );
			}
			else{
				export.addRow(new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[6] });
			}
			System.out.print(obj[1]+" ");
			System.out.print(obj[2]+" ");
			System.out.print(obj[3]+" ");
			System.out.print(obj[4]+" ");
			System.out.print(obj[5]+" ");
			System.out.println(obj[6]+" ");
			i++;
		}
		OutputStream os = DownloadUtils.getResponseOutput("预计收款统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}



	private List<Object[]> processDate(List<Object[]> oArray ){
		List<Object[]> objArray = new ArrayList<Object[]>();

		Object[] mouthList = new Object[7];
		mouthList[0] = null;
		for(int i=0;i<6;i++){
			mouthList[i+1] = (DateUtils.truncate(DateUtils.addMonths(new Date(),i+1) , Calendar.MONTH));
		}
		objArray.add(mouthList);

		Object[] dataInfoItem = new Object[7];
		Object[] dataInfoIntegrated = new Object[7];
		Object[] dataSum = new Object[7];
		dataInfoItem[0] = "项目类：";
		dataInfoIntegrated[0] = "集成类：";
		dataSum[0] = "合计：";
		int seq;
		for(Object[] o: oArray){
			if(o[1].equals("1")){    //项目类
				seq = getDateSeq(mouthList,o[0]);
				if(seq != -1){
					dataInfoItem[seq] = o[2];
				}
			}
			else{
				seq = getDateSeq(mouthList,o[0]);
				if(seq != -1){
					dataInfoIntegrated[seq] = o[2];
				}
			}
		}
		objArray.add(dataInfoItem);
		objArray.add(dataInfoIntegrated);

		for(int i=1; i<dataSum.length; i++){
			dataSum[i] = new BigDecimal(dataInfoItem[i].toString()).add(new BigDecimal( dataInfoIntegrated[i].toString()));
		}
		objArray.add(dataSum);
		return objArray;
	}

	private int getDateSeq( Object[] mouthList , Object date ){
		int i = 0;
		for(Object obj:mouthList ){
			if(obj!=null && obj.equals(date)){
				return i;
			}
			i++;
		}
		return -1;
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

	public String getPreReceStartDate() {
		return preReceStartDate;
	}

	public void setPreReceStartDate(String preReceStartDate) {
		this.preReceStartDate = preReceStartDate;
	}

	public String getPreReceEndDate() {
		return preReceEndDate;
	}

	public void setPreReceEndDate(String preReceEndDate) {
		this.preReceEndDate = preReceEndDate;
	}

	public String[] getReceSituation() {
		return receSituation;
	}

	public void setReceSituation(String[] receSituation) {
		this.receSituation = receSituation;
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
	public BigDecimal getTotalPreRece() {
		return totalPreRece;
	}

	public void setTotalPreRece(BigDecimal totalPreRece) {
		this.totalPreRece = totalPreRece;
	}

	public BigDecimal getTotalRealRece() {
		return totalRealRece;
	}

	public void setTotalRealRece(BigDecimal totalRealRece) {
		this.totalRealRece = totalRealRece;
	}

	public BigDecimal getAccumulatePreRece() {
		return accumulatePreRece;
	}

	public void setAccumulatePreRece(BigDecimal accumulatePreRece) {
		this.accumulatePreRece = accumulatePreRece;
	}

	public BigDecimal getAccumulateRealRece() {
		return accumulateRealRece;
	}

	public void setAccumulateRealRece(BigDecimal accumulateRealRece) {
		this.accumulateRealRece = accumulateRealRece;
	}

	public BigDecimal getBalanceRece() {
		return balanceRece;
	}

	public void setBalanceRece(BigDecimal balanceRece) {
		this.balanceRece = balanceRece;
	}

	public String getReceSituation0() {
		return receSituation0;
	}

	public void setReceSituation0(String receSituation0) {
		this.receSituation0 = receSituation0;
	}

	public String getReceSituation1() {
		return receSituation1;
	}

	public void setReceSituation1(String receSituation1) {
		this.receSituation1 = receSituation1;
	}

	public String getReceSituation2() {
		return receSituation2;
	}

	public void setReceSituation2(String receSituation2) {
		this.receSituation2 = receSituation2;
	}

	public String getNextSix() {
		return nextSix;
	}

	public void setNextSix(String nextSix) {
		this.nextSix = nextSix;
	}

	public List<Object[]> getOArray() {
		return oArray;
	}

	public void setOArray(List<Object[]> array) {
		oArray = array;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

	public BigDecimal getBillInvoiceAmount() {
		return billInvoiceAmount;
	}

	public void setBillInvoiceAmount(BigDecimal billInvoiceAmount) {
		this.billInvoiceAmount = billInvoiceAmount;
	}
}