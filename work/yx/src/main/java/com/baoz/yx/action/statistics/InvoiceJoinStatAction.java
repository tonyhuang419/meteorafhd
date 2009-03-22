package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.math.BigDecimal;
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
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;

@Results( {
	@Result(name = "popQuery", value = "/WEB-INF/jsp/statistics/invoiceJoinLeftQuery.jsp"),
	@Result(name = "mainInfo", value = "/WEB-INF/jsp/statistics/invoiceJoinMain.jsp") })
	public class InvoiceJoinStatAction extends DispatchAction {

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

	private PageInfo info;

	private String conNo;

	private String invoiceType;

	private String itemNo;

	private Long customerId;

	private String customerName;

	private String beginInvoiceDate;

	private String endInvoiceDate;

	private List<YXTypeManage> invoiceTypeList;//发票类型

	private BigDecimal totalInvoiceAmount;//统计字段

	private String				exportX;   //1导出

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"invoiceJoinStatParameters", this, new String[] { "conNo",
					"invoiceType", "customerId", "itemNo", "customerName",
					"beginInvoiceDate", "endInvoiceDate" });

	public String doDefault() throws Exception {
		holdTool.clearStore();
		return "mainInfo";
	}

	public String popQuery() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		invoiceTypeList = yxTypeManageService.getYXTypeManage(1004L);
		return "popQuery";
	}

	public String queryMain() throws Exception {

		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sql =new StringBuffer( "select apply.bill_apply_num as no,"+
				"   ( select  minfo.con_id  from  yx_con_main_info minfo  where minfo.con_main_info_sid = apply.fk_con_main_info_sid) as conNo,  "+
				"invoice.invoice_info_id as invoiceId,"+
				"invoice.invoice_no as invoiceNo,"+
				"invoice.invoice_date   as invoiceDate,"+
				"invoice.invoice_amount as invoiceAmount,"+
				"tm.type_name as invoiceType,"+
				"client.name as customerName," +
				"client.full_name as customerFullName," +
				"emp.name as empName ," +
				"apply.is_sign as applySign "
		);
		StringBuffer fromwhere = new StringBuffer("from yx_invoice_info  invoice,"+
				"yx_apply_bill    apply,"+
				"yx_client client,"+
				"yx_type_manage   tm," +
				"yx_exployee emp "+
				"where apply.fk_customerid = client.id "+
				"and invoice.apply_invoice_id = apply.apply_bill_sid "+
				"and invoice.type = tm.type_small "+
				"and tm.type_big = '1004' " +
				"and emp.id = apply.fk_employeeid " +
				"and invoice.input_state = '1' "
		);
		if(StringUtils.isNotBlank(conNo)){
			
			fromwhere.append(" and exists (select 1 from yx_con_main_info minfo where minfo.con_main_info_sid = apply.fk_con_main_info_sid and minfo.con_id = '"+conNo+"') ");
		}
		if(StringUtils.isNotBlank(itemNo)){
			fromwhere.append("and exists (select 1 from yx_billandpro_relaion relation,yx_apply_bill bill, yx_real_con_bc_plan plan,yx_con_item_minfo item "+
					"where relation.fk_real_con_bcplan_sid = plan.real_con_billpro_sid and relation.fk_apply_bill_sid = bill.apply_bill_sid "+
					"and plan.fk_con_item_minfo_sid = item.con_item_minfo_sid and bill.apply_bill_sid = apply.apply_bill_sid and item.con_item_id = '"+itemNo+"') ");
		}
		if(customerId!=null){
			fromwhere.append("and client.id = "+customerId+" ");
		}
		if(StringUtils.isNotBlank(invoiceType)){
			fromwhere.append("and tm.type_small = '"+invoiceType+"' ");
		}
		if(StringUtils.isNotBlank(beginInvoiceDate)){
			fromwhere.append(" and  to_date('"+beginInvoiceDate+"','yyyy-MM-dd')<= trunc(invoice.invoice_date,'dd') ");
		}
		if(StringUtils.isNotBlank(endInvoiceDate)){
			fromwhere.append(" and  to_date('"+endInvoiceDate+"','yyyy-MM-dd')>= trunc(invoice.invoice_date,'dd') ");
		}
		fromwhere.append(" order by invoice.invoice_date desc");
		String finalSql = sql+fromwhere.toString();

		String countSql="select count(*) from ("+finalSql+")";
		info = yxQueryService.listQueryResultBySql(countSql, finalSql, info);
		String totalSql ="select sum(invoice.invoice_amount) "+fromwhere;
		List<BigDecimal> list = yxQueryService.listQueryNoPage(totalSql);
		if(list!=null&&list.size()>0){
			totalInvoiceAmount = list.get(0);
		}
		if(StringUtils.equals(exportX, "1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(finalSql);
			this.processExport(objList);
			return null;
		}else{
			return "mainInfo";
		}

	}
	private  void processExport(List<Object[]> oArray) {		
		String signNum;
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"发票号","发票类型","开票金额","开票日期","项目号",
				"合同号","客户名称" ,"经办人","签收单编号"});
		export.setTableName("发票交接统计");
		for(Object[] obj:oArray){
			
			if( obj[10].toString().equals("1") ){
				if (  obj[1] !=null){ 
					signNum =  obj[1].toString()+"_"+obj[0].toString();
				}
				else{
					signNum = obj[0].toString();
				}
			}
			else{
				signNum = "未签收";
			}
			
			export.addRow(new Object[]{obj[3],obj[6],obj[5],obj[4],getItemNoByInvoiceIdOnBack(((BigDecimal)obj[2]).longValue()),
					obj[1],obj[7],obj[9],  signNum     });
		}
		OutputStream os = DownloadUtils.getResponseOutput("发票交接统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}

	public String getItemNoByInvoiceId(Long invoiceId){

		String sql = "select distinct(item.con_item_id) from yx_billandpro_relaion relation,yx_apply_bill bill, yx_real_con_bc_plan plan,yx_con_item_minfo item,yx_invoice_info  invoice "+
		"where relation.fk_real_con_bcplan_sid = plan.real_con_billpro_sid and relation.fk_apply_bill_sid = bill.apply_bill_sid "+
		"and plan.fk_con_item_minfo_sid = item.con_item_minfo_sid and bill.apply_bill_sid = invoice.apply_invoice_id and invoice.invoice_info_id = ?";
		List<String> list = yxQueryService.listQueryNoPage(sql, invoiceId);
		String conItemNo = "";
		for (String string : list) {
			conItemNo+=string+"</br>";
		}
		return conItemNo;
	}

	public String getItemNoByInvoiceIdOnBack(Long invoiceId){

		String sql = "select distinct(item.con_item_id) from yx_billandpro_relaion relation,yx_apply_bill bill, yx_real_con_bc_plan plan,yx_con_item_minfo item,yx_invoice_info  invoice "+
		"where relation.fk_real_con_bcplan_sid = plan.real_con_billpro_sid and relation.fk_apply_bill_sid = bill.apply_bill_sid "+
		"and plan.fk_con_item_minfo_sid = item.con_item_minfo_sid and bill.apply_bill_sid = invoice.apply_invoice_id and invoice.invoice_info_id = ?";
		List<String> list = yxQueryService.listQueryNoPage(sql, invoiceId);
		String conItemNo = "";
		for (String string : list) {
			conItemNo+=string+"/";
		}
		if(conItemNo.length()>1){
			conItemNo = conItemNo.substring(0, conItemNo.length()-1);
		}
		return conItemNo;
	}
	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBeginInvoiceDate() {
		return beginInvoiceDate;
	}

	public void setBeginInvoiceDate(String beginInvoiceDate) {
		this.beginInvoiceDate = beginInvoiceDate;
	}

	public String getEndInvoiceDate() {
		return endInvoiceDate;
	}

	public void setEndInvoiceDate(String endInvoiceDate) {
		this.endInvoiceDate = endInvoiceDate;
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

	public IYXTypeManageService getYxTypeManageService() {
		return yxTypeManageService;
	}

	public void setYxTypeManageService(IYXTypeManageService yxTypeManageService) {
		this.yxTypeManageService = yxTypeManageService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<YXTypeManage> getInvoiceTypeList() {
		return invoiceTypeList;
	}

	public void setInvoiceTypeList(List<YXTypeManage> invoiceTypeList) {
		this.invoiceTypeList = invoiceTypeList;
	}

	public BigDecimal getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(BigDecimal totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}
}
