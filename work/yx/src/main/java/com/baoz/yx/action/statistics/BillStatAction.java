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
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;


//开票统计
@Results( {	
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/mainBillStatistics.jsp"),
	@Result(name = "popQuery",value="/WEB-INF/jsp/statistics/billStatisticsLeftQuery.jsp"),
	@Result(name = "mainInfo",value="/WEB-INF/jsp/statistics/billStatisticsMainInfo.jsp")
})
public class BillStatAction extends DispatchAction {

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

	private List<Department> groupList=new ArrayList<Department>();//组别list

	private String groupId;//组别

	private Long saleMan;//销售员

	private String preBillStartDate;//计划开票日期开始

	private String preBillEndDate;//计划开票日期结束

	private String billSituation;//开票情况

	private Long customer;//客户名称

	private String customerName;//客户全称

	private String contractNo;//合同号

	private String itemNo;//项目号


	private Double totalPreBill;//预计开票总计

	private Double totalRealBill;//已开票总计
	private Double totalRealArriveAmount;//已到款总计

	private List<Employee> empList=new ArrayList<Employee>();//销售员

	private  PageInfo 					info;

	private String				exportX;   //1导出

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"billStatParameters",this,new String[]{"groupId","saleMan","preBillStartDate",
					"preBillEndDate","billSituation","customerName","customer",
					"contractNo","itemNo"});    

	@Override
	public String doDefault()  throws Exception  {
		holdTool.clearStore();
		return "mainInfo";
	}
	public String popQuery()throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		groupList=statisticsService.getDepartment();
		empList=statisticsService.getEmployee();
		return "popQuery";
	}

	public String showMaininfo()throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sql1=new StringBuffer();
		StringBuffer conditionsHql=new StringBuffer();
		sql1.append("select emp.name,"
				+"con.con_id,"
				+"(select item.con_item_id from "
				+"yx_con_item_minfo item "
				+"where p.fk_con_item_minfo_sid = item.con_item_minfo_sid),"
				+"con.con_name,"
				+"p.real_pred_bill_date,"
				+"tm.type_name,"
				+"p.real_tax_bill_amount,"
				+"p.Bill_Invoice_Amount ,"
				+"con.con_main_info_sid," +
						"nvl(p.real_arrive_amount,0) "
				+"from yx_exployee emp,"
				+"yx_con_main_info con,"
				+"yx_real_con_bc_plan p,"
				+"yx_type_manage tm "
				+"where p.fk_con_main_sid = con.con_main_info_sid "
				+"and tm.type_small = p.fk_bill_type "
				+"and tm.type_big = 1004 "
				+"and emp.id = con.sale_man " 
				+ "and p.real_tax_bill_amount>0 ");


		if(groupId!=null){
			conditionsHql.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"+groupId+"%') ");
		}
		if(saleMan!=null){
			conditionsHql.append("and emp.id="+saleMan+" ");
		}
		if(preBillStartDate!=null&&preBillStartDate.trim().length()>0){
			conditionsHql.append(" and to_date('"+preBillStartDate+"','yyyy-MM-dd')<= trunc(p.real_pred_bill_date,'dd') ");
		}
		if(preBillEndDate!=null&&preBillEndDate.trim().length()>0){
			conditionsHql.append(" and to_date('"+preBillEndDate+"','yyyy-MM-dd')>= trunc(p.real_pred_bill_date,'dd') ");
		}
		if(billSituation!=null&&billSituation.trim().length()>0){
			if(StringUtils.equals(billSituation, "0")){//0代表部分开票
				conditionsHql.append(" and p.BILL_INVOICE_AMOUNT>0 and p.BILL_INVOICE_AMOUNT<p.real_tax_bill_amount");
			}else if(StringUtils.equals(billSituation, "1")){//1代表未开
				conditionsHql.append(" and (p.bill_invoice_amount=0 or p.BILL_INVOICE_AMOUNT is null) ");
			}else if(StringUtils.equals(billSituation, "2")){//全部开票
				conditionsHql.append(" and (p.bill_invoice_amount=p.real_tax_bill_amount and p.BILL_INVOICE_AMOUNT is not null) ");
			}
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
		allHql.append(sql1);
		allHql.append(conditionsHql);
		String  sqlCount = "select count(*) from ("+ allHql +" )";
		StringBuffer totalSql=new StringBuffer();
		totalSql.append("select sum(p.real_tax_bill_amount),sum(p.Bill_Invoice_Amount),sum(p.real_arrive_amount)"
				+"from yx_exployee emp,"
				+"yx_con_main_info con,"
				+"yx_real_con_bc_plan p,"
				+"yx_type_manage tm "
				+"where p.fk_con_main_sid = con.con_main_info_sid "
				+"and tm.type_small = p.fk_bill_type "
				+"and tm.type_big = 1004 "
				+"and emp.id = con.sale_man ");
		StringBuffer totalAllSql=new StringBuffer();
		totalAllSql.append(totalSql);
		totalAllSql.append(conditionsHql);
		List<Object[]> totalList=yxQueryService.listQueryNoPage(totalAllSql.toString());
		if(totalList!=null&&totalList.size()>0){
			Object[] d=totalList.get(0);
			totalPreBill=((BigDecimal)d[0])==null ? 0.00:((BigDecimal)d[0]).doubleValue();
			totalRealBill=((BigDecimal)d[1])==null ? 0.00:((BigDecimal)d[1]).doubleValue();
			totalRealArriveAmount=((BigDecimal)d[2])==null ? 0.00:((BigDecimal)d[2]).doubleValue();
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

    private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","计划开票日期","票据类型","预计开票金额","已开票金额" });
		export.setTableName("预计开票统计");
		for(Object[] obj:oArray){
			if(obj[7]==null){
				obj[7]=0d;
			}
			export.addRow(new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[6],obj[7] });
		}
		OutputStream os = DownloadUtils.getResponseOutput("预计开票统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
    
	
	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
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

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
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
	public Double getTotalPreBill() {
		return totalPreBill;
	}
	public void setTotalPreBill(Double totalPreBill) {
		this.totalPreBill = totalPreBill;
	}
	public Double getTotalRealBill() {
		return totalRealBill;
	}
	public void setTotalRealBill(Double totalRealBill) {
		this.totalRealBill = totalRealBill;
	}
	public String getExportX() {
		return exportX;
	}
	public void setExportX(String exportX) {
		this.exportX = exportX;
	}
	public Double getTotalRealArriveAmount() {
		return totalRealArriveAmount;
	}
	public void setTotalRealArriveAmount(Double totalRealArriveAmount) {
		this.totalRealArriveAmount = totalRealArriveAmount;
	}

}