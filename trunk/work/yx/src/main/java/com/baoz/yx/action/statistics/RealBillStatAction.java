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
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;

@Results( {
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/mainBillStatistics.jsp"),
	@Result(name = "popQuery", value = "/WEB-INF/jsp/statistics/realBillStatisticsLeftQuery.jsp"),
	@Result(name = "mainInfo", value = "/WEB-INF/jsp/statistics/realBillStatisticsMainInfo.jsp"),
	@Result(name = "printInvoiceList",value="/WEB-INF/jsp/statistics/printInvoiceList.jsp")
}

)
public class RealBillStatAction extends DispatchAction {

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

	private String preBillStartDate;// 计划开票日期开始

	private String preBillEndDate;// 计划开票日期结束

	private String recordStartDate;// 录入开始日期

	private String recordEndDate;//发票录入结束日期

	private String billSituation;// 开票情况

	private Long customer;// 客户名称

	private String customerName;// 客户全称

	private String contractNo;// 合同号

	private String itemNo;// 项目号

	private BigDecimal totalPreBill;// 预计开票总计

	private BigDecimal totalRealBill;// 已开票总计

	private String opPersonNo;//操作人工号

	private Integer recordStartHour;

	private Integer recordStartMinute;

	private Integer recordEndHour;

	private Integer recordEndMinute;

	private List<Employee> empList = new ArrayList<Employee>();// 销售员

	private String				exportX;   //1导出

	private List<Object[]> printInvoiceList=new ArrayList<Object[]>();
	private PageInfo info;

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"billStatParameters", this, new String[] { "groupId", "saleMan",
					"preBillStartDate", "preBillEndDate", "recordStartDate", "recordEndDate", "billSituation",
					"customerName", "customer", "contractNo", "itemNo","opPersonNo","recordStartHour", "recordStartMinute", "recordEndHour","recordEndMinute"  });

	@Override
	public String doDefault() throws Exception {
		holdTool.clearStore();
		return "mainInfo";
	}

	public String popQuery() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		groupList = statisticsService.getDepartment();
		empList = statisticsService.getEmployee();
		return "popQuery";
	}
	@SuppressWarnings("unchecked")
	public String printInvoiceList()throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		String sql="select rownum, e.name," +
		"info.con_id,info.con_name," +
		"info.con_tax_tamount,invoice.invoice_no," +
		"invoice.invoice_amount,invoice.invoice_date," +
		"invoice.apply_invoice_id,client.name as customerName," +
		"apply.apply_date," +
		"tm.type_name," +
		"e.id  " +
		"from yx_exployee e, yx_con_main_info info," +
		"yx_invoice_info invoice,yx_client client,yx_apply_bill apply,yx_type_manage tm  " +
		"where invoice.fk_con_main_info_sid = info.con_main_info_sid and e.id=info.sale_man " +
		"and invoice.input_state=1 " +
		"and info.con_customer = client.id " +
		"and apply.apply_bill_sid=invoice.apply_invoice_id " +
		"and invoice.type = tm.type_small and  tm.type_big = 1004 " +
		"and apply.is_no_contract = 0 ";
	String unionSql = "select null,"+
	       "e.name,"+
	       "null,"+
	       "null,"+
	       "null,"+
	       "invoice.invoice_no,"+
	       "invoice.invoice_amount,"+
	       "invoice.invoice_date,"+
	       "invoice.apply_invoice_id,"+
	       "client.name as customerName,"+
	       "apply.apply_date,"+
	       "tm.type_name,"+
	       "e.id  "+
		   "from "+
		   " yx_exployee e,"+
	       "yx_invoice_info  invoice,"+
	       "yx_client        client,"+
	       "yx_apply_bill    apply, "+
	       "yx_type_manage   tm " +
	       "where " +
	       "e.id = apply.fk_employeeId "+
	       "and invoice.input_state = 1 "+
	       "and apply.fk_customerid = client.id "+
	       "and apply.apply_bill_sid = invoice.apply_invoice_id "+
	       "and invoice.type = tm.type_small "+
	       "and tm.type_big = 1004 "+
	       "and apply.is_no_contract = 1 ";
		StringBuffer conditionsSql=new StringBuffer();
		StringBuffer unionConditionsSql = new StringBuffer();
		if (groupId != null) {
			conditionsSql.append("and e.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
					+ groupId + "%') ");
			unionConditionsSql.append("and e.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
					+ groupId + "%') ");
		}
		if (saleMan != null) {
			conditionsSql.append("and e.id=" + saleMan + " ");
			unionConditionsSql.append("and e.id=" + saleMan + " ");
		}
		if (preBillStartDate != null && preBillStartDate.trim().length() > 0) {
			conditionsSql.append(" and to_date('" + preBillStartDate
					+ "','yyyy-MM-dd')<= trunc(invoice.invoice_date,'dd') ");
			unionConditionsSql.append(" and to_date('" + preBillStartDate
					+ "','yyyy-MM-dd')<= trunc(invoice.invoice_date,'dd') ");
		}

		if (preBillEndDate != null && preBillEndDate.trim().length() > 0) {
			conditionsSql.append(" and to_date('" + preBillEndDate
					+ "','yyyy-MM-dd')>= trunc(invoice.invoice_date,'dd')");
			unionConditionsSql.append(" and to_date('" + preBillEndDate
					+ "','yyyy-MM-dd')>= trunc(invoice.invoice_date,'dd')");
		}
		//录入时间查询recordStartDate", "recordEndDate
		if (recordStartDate != null && recordStartDate.trim().length() > 0) {
			recordStartDate += " "+getDefaultInteger(recordStartHour)+":"+getDefaultInteger(recordStartMinute);
			conditionsSql.append("and invoice.record_date >= ").append("to_date('"+recordStartDate+"','yyyy-MM-dd hh24:mi') ");
			unionConditionsSql.append("and invoice.record_date >= ").append("to_date('"+recordStartDate+"','yyyy-MM-dd hh24:mi') ");
		}
		if (recordEndDate != null && recordEndDate.trim().length() > 0) {
			recordEndDate += " "+(recordEndHour==null?23:recordEndHour)+":"+(recordEndMinute==null?59:recordEndMinute)+":59";
			conditionsSql.append("and invoice.record_date <= ").append("to_date('"+recordEndDate+"','yyyy-MM-dd hh24:mi:ss') ");
			unionConditionsSql.append("and invoice.record_date <= ").append("to_date('"+recordEndDate+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(!StringUtils.isBlank(opPersonNo)){
			conditionsSql.append(" and invoice.BYID in (select ex.id from yx_exployee ex where ex.workid='"+opPersonNo.trim()+"') ");
			unionConditionsSql.append(" and invoice.BYID in (select ex.id from yx_exployee ex where ex.workid='"+opPersonNo.trim()+"') ");
		}
		if (customer != null) {
			conditionsSql.append(" and client.con_customer=" + customer + " ");
			unionConditionsSql.append(" and client.con_customer=" + customer + " ");
		}
		if (contractNo != null && contractNo.trim().length() > 0) {
			conditionsSql.append(" and info.con_id='" + contractNo + "' ");
			unionConditionsSql.append(" and 1=2 ");
		}
		if (itemNo != null && itemNo.trim().length() > 0) {
			conditionsSql
			.append("and exists (select 1 from "
					+ "yx_con_item_minfo item "
					+ "where info.con_main_info_sid = item.fk_con_main_info_sid and item.con_item_id = '"
					+ itemNo + "') ");
			unionConditionsSql.append(" and 1=2 ");
		}
		String allOrder = " order by 13,3,6 ";//e.id
		StringBuffer allSql=new StringBuffer();
		allSql.append(sql+conditionsSql);//前半部分
		allSql.append(" union all ");//连接
		allSql.append(unionSql+unionConditionsSql);//后半部分
		allSql.append(allOrder);//排序
		printInvoiceList = yxQueryService.listQueryNoPage(allSql.toString());
		return "printInvoiceList";
	}

	private Integer getDefaultInteger(Integer integer){
		if(integer != null){
			return integer;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public String showItemNo(Long applyBillId){
		String sql="select distinct (item.con_item_id) " +
		"from yx_billandpro_relaion relation, " +
		"yx_real_con_bc_plan p,yx_con_item_minfo item " +
		"where relation.fk_real_con_bcplan_sid = p.real_con_billpro_sid " +
		"and p.fk_con_item_minfo_sid = item.con_item_minfo_sid  " +
		"and relation.fk_apply_bill_sid= ?" ;
		List<String> itemNoList=yxQueryService.listQueryNoPage(sql, applyBillId);
		String returnStr="";
		if(itemNoList!=null&&itemNoList.size()>0){
			for (String string : itemNoList) {
				returnStr+=string+" ";
			}
			if(returnStr.length()>1){
				returnStr=StringUtils.substring(returnStr, 0, returnStr.length()-1);
			}
		}
		return returnStr;
	}

	@SuppressWarnings("unchecked")
	public String showMaininfo() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sql1 = new StringBuffer();
		StringBuffer conditionsHql = new StringBuffer();
		sql1.append("select emp.name, con.con_id, "
				+ "(select item.con_item_id from  yx_con_item_minfo item "
				+ "where p.fk_con_item_minfo_sid = item.con_item_minfo_sid),"
				+ "con.con_name," + "p.real_pred_bill_date,"
				+ "tm.type_name," + "p.real_tax_bill_amount as taxBillAmount,"
				+ " p.bill_invoice_amount as nowTaxBillAmount ,p.real_now_bill_date , con.con_main_info_sid " 
				+",(select to_char(max(ii.record_date),'yyyy-MM-dd hh24:mi') from yx_billandpro_relaion br,yx_invoice_info ii,yx_apply_bill bill "
				+ "where br.fk_real_con_bcplan_sid = p.real_con_billpro_sid " 
				+"and br.fk_apply_bill_sid = bill.apply_bill_sid " 
				+"and bill.apply_bill_sid = ii.apply_invoice_id and ii.input_state = 1) as actualInputDate "
				+"from yx_exployee emp,"
				+ "yx_con_main_info con," + "yx_real_con_bc_plan p,"
				+ "yx_type_manage tm " 
				+ "where p.fk_con_main_sid = con.con_main_info_sid "
				+ "and tm.type_small = p.fk_bill_type "
				+ "and tm.type_big = 1004 " + "and emp.id = con.sale_man " ///后面这段代码是为了是没有确认录入的发票也不显示
				+ " and (select to_char(max(ii.record_date),'yyyy-MM-dd hh24:mi') from yx_billandpro_relaion br,yx_invoice_info ii,yx_apply_bill bill "
				+ "where br.fk_real_con_bcplan_sid = p.real_con_billpro_sid " 
				+"and br.fk_apply_bill_sid = bill.apply_bill_sid " 
				+"and bill.apply_bill_sid = ii.apply_invoice_id and ii.input_state = 1) is not null " );
		StringBuffer unionSql = new StringBuffer();
		StringBuffer unionConditionSql = new StringBuffer();
		unionSql.append("select e.name,"+
						"null,"+
						"null,"+
						" bill.contact_name,"+
						" null,"+
						" (select tm.type_name from yx_type_manage tm where tm.type_small = bill.fk_bill_type and tm.type_big = 1004 and rownum <= 1),"+
						" bill.bill_amount_tax ,"+
						" (select sum(ii.invoice_amount) from yx_invoice_info ii where ii.apply_invoice_id = bill.apply_bill_sid "+
						" and ii.input_state = 1 ),"+
						" (select max(ii.invoice_date) "+
						" from yx_invoice_info ii "+
						" where ii.apply_invoice_id = bill.apply_bill_sid "+
						" and ii.input_state = 1), "+
						"  bill.fk_con_main_info_sid , "+
						" (select to_char(max(ii.record_date), 'yyyy-MM-dd hh24:mi') "+
						" from yx_invoice_info ii "+
						" where ii.apply_invoice_id = bill.apply_bill_sid "+
						" and ii.input_state = 1) "+                 
						" from yx_apply_bill bill, yx_exployee e "+
						" where e.id = bill.fk_employeeid "+
						" and bill.fk_con_main_info_sid is null "+
						" and exists (select 1 from yx_invoice_info ii where ii.apply_invoice_id = bill.apply_bill_sid and ii.input_state =1) ");
		if (groupId != null) {
			conditionsHql
			.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
					+ groupId + "%') ");
			unionConditionSql.append("and e.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
					+ groupId + "%') ");
		}
		if (saleMan != null) {
			conditionsHql.append("and emp.id=" + saleMan + " ");
			unionConditionSql.append(" and e.id = '"+saleMan+"'");
		}
		if (preBillStartDate != null && preBillStartDate.trim().length() > 0) {
			conditionsHql.append(" and to_date('" + preBillStartDate
					+ "','yyyy-MM-dd')<= trunc(p.real_now_bill_date,'dd') ");
			unionConditionSql.append(" and 1=2 ");
		}
		if (preBillEndDate != null && preBillEndDate.trim().length() > 0) {
			conditionsHql.append(" and to_date('" + preBillEndDate
					+ "','yyyy-MM-dd')>= trunc(p.real_now_bill_date,'dd')");
			unionConditionSql.append(" and 1=2 ");
		}
		unionConditionSql.append(" and exists (select 1 from yx_invoice_info ii where ii.apply_invoice_id = bill.apply_bill_sid and ii.input_state =1 ");
		conditionsHql.append(" and exists(select 1 from "
				+ "yx_billandpro_relaion br,yx_invoice_info ii,yx_apply_bill bill "
				+ "where br.fk_real_con_bcplan_sid = p.real_con_billpro_sid " +
				"and br.fk_apply_bill_sid = bill.apply_bill_sid " +
		"and bill.apply_bill_sid = ii.apply_invoice_id ");
		//录入时间查询recordStartDate", "recordEndDate
		if (recordStartDate != null && recordStartDate.trim().length() > 0) {
			recordStartDate += " "+getDefaultInteger(recordStartHour)+":"+getDefaultInteger(recordStartMinute);
			conditionsHql.append("and ii.record_date >= ").append("to_date('"+recordStartDate+"','yyyy-MM-dd hh24:mi') ");
			unionConditionSql.append("and ii.record_date >= ").append("to_date('"+recordStartDate+"','yyyy-MM-dd hh24:mi') ");
		}
		if (recordEndDate != null && recordEndDate.trim().length() > 0) {
			recordEndDate += " "+(recordEndHour==null?23:recordEndHour)+":"+(recordEndMinute==null?59:recordEndMinute)+":59";
			conditionsHql.append("and ii.record_date <= ").append("to_date('"+recordEndDate+"','yyyy-MM-dd hh24:mi:ss') ");
			unionConditionSql.append("and ii.record_date <= ").append("to_date('"+recordEndDate+"','yyyy-MM-dd hh24:mi:ss') ");
		}
		if(!StringUtils.isBlank(opPersonNo)){
			conditionsHql.append(" and ii.BYID in (select ex.id from yx_exployee ex where ex.workid='"+opPersonNo.trim()+"') ");
			unionConditionSql.append(" and ii.BYID in (select ex.id from yx_exployee ex where ex.workid='"+opPersonNo.trim()+"') ");
		}
		unionConditionSql.append(") ");
		conditionsHql.append(") ");
		if (billSituation != null && billSituation.trim().length() > 0) {
			if (billSituation.equals("0")) {// 0代表已开票
				conditionsHql.append(" and p.bill_invoice_amount>0");
			} else if (billSituation.equals("1")) {// 1代表未开票
				conditionsHql
				.append(" and (p.bill_invoice_amount=0 or p.bill_invoice_amount is null) ");
			}
		}
		if (customer != null) {
			conditionsHql.append(" and con.con_customer=" + customer + " ");
			unionConditionSql.append("  and bill.fk_bill_customer ='"+ customer +"' ");
		}
		if (contractNo != null && contractNo.trim().length() > 0) {
			conditionsHql.append(" and lower(con.con_id)='" + StringUtils.lowerCase(contractNo.trim()) + "' ");
			unionConditionSql.append(" and 1=2 ");
		}
		if (itemNo != null && itemNo.trim().length() > 0) {
			conditionsHql
			.append("and exists(select 1 from "
					+ "yx_con_item_minfo item "
					+ "where p.fk_con_item_minfo_sid = item.con_item_minfo_sid and lower(item.con_item_id)='"
					+ StringUtils.lowerCase(itemNo) + "') ");
			unionConditionSql.append(" and 1=2 ");
		}
		StringBuffer orderBySql = new StringBuffer(" order by 1,2,6 ");
		StringBuffer allHql = new StringBuffer();
		allHql.append(sql1);
		allHql.append(conditionsHql);
		allHql.append(" union all ");
		allHql.append(unionSql);
		allHql.append(unionConditionSql);
		allHql.append(orderBySql);
		String sqlCount = "select count(*) from (" + allHql + " )";
		StringBuffer totalSql = new StringBuffer();
		totalSql.append("select sum(taxBillAmount),sum(nowTaxBillAmount) from ("+allHql+") ");
		StringBuffer totalAllSql = new StringBuffer();
		totalAllSql.append(totalSql);
		List<Object[]> totalList = yxQueryService.listQueryNoPage(totalAllSql.toString());
		if (totalList != null && totalList.size() > 0) {
			Object[] d = totalList.get(0);
			totalPreBill = (BigDecimal) d[0];
			totalRealBill = (BigDecimal) d[1];
		}
		logger.info(allHql);
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(allHql.toString());
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql(sqlCount, allHql.toString(), info);
			return "mainInfo";
		}
	}
	
	private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","计划开票日期","实际开票日期",
				"实际录入日期","票据类型","预计开票金额","已开票金额"  });
		export.setTableName("实际开票统计");
		for(Object[] obj:oArray){
			if(obj[7]==null){
				obj[7] = 0d;
			}
			export.addRow(new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[8],obj[10],obj[5],obj[6],obj[7] });
		}
		OutputStream os = DownloadUtils.getResponseOutput("实际开票统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
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

	public String getPreBillStartDate() {
		return preBillStartDate;
	}

	public void setPreBillStartDate(String preBillStartDate) {
		this.preBillStartDate = preBillStartDate;
	}

	public String getPreBillEndDate() {
		return preBillEndDate;
	}

	public void setPreBillEndDate(String preBillEndDate) {
		this.preBillEndDate = preBillEndDate;
	}

	public String getBillSituation() {
		return billSituation;
	}

	public void setBillSituation(String billSituation) {
		this.billSituation = billSituation;
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

	public BigDecimal getTotalPreBill() {
		return totalPreBill;
	}

	public void setTotalPreBill(BigDecimal totalPreBill) {
		this.totalPreBill = totalPreBill;
	}

	public BigDecimal getTotalRealBill() {
		return totalRealBill;
	}

	public void setTotalRealBill(BigDecimal totalRealBill) {
		this.totalRealBill = totalRealBill;
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

	public List<Object[]> getPrintInvoiceList() {
		return printInvoiceList;
	}

	public void setPrintInvoiceList(List<Object[]> printInvoiceList) {
		this.printInvoiceList = printInvoiceList;
	}

	public String getRecordStartDate() {
		return recordStartDate;
	}

	public void setRecordStartDate(String recordStartDate) {
		this.recordStartDate = recordStartDate;
	}

	public String getRecordEndDate() {
		return recordEndDate;
	}

	public void setRecordEndDate(String recordEndDate) {
		this.recordEndDate = recordEndDate;
	}

	public String getOpPersonNo() {
		return opPersonNo;
	}

	public void setOpPersonNo(String opPersonNo) {
		this.opPersonNo = opPersonNo;
	}

	public Integer getRecordStartHour() {
		return recordStartHour;
	}

	public void setRecordStartHour(Integer recordStartHour) {
		this.recordStartHour = recordStartHour;
	}

	public Integer getRecordStartMinute() {
		return recordStartMinute;
	}

	public void setRecordStartMinute(Integer recordStartMinute) {
		this.recordStartMinute = recordStartMinute;
	}

	public Integer getRecordEndHour() {
		return recordEndHour;
	}

	public void setRecordEndHour(Integer recordEndHour) {
		this.recordEndHour = recordEndHour;
	}

	public Integer getRecordEndMinute() {
		return recordEndMinute;
	}

	public void setRecordEndMinute(Integer recordEndMinute) {
		this.recordEndMinute = recordEndMinute;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

}
