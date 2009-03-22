package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;


//到款统计
@Results( {	
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/ArriveStatisticsList.jsp"),
	@Result(name = "popQuery", value = "/WEB-INF/jsp/statistics/ArriveStatisticsQuery.jsp")
})
public class ArriveStatAction extends DispatchAction {
	
	@Autowired
	@Qualifier("statisticsService")
	private IStatisticsService statisticsService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;

	private List<Department> groupList;
	
	private PageInfo info;
	
	private String groupId;

	private Long saleMan;
	
	private String contractNo;
	
	private String itemNo;
	
	private String custom; //客户简称
	
	private Long customId;
	
	private String startDate;
	
	private String endDate;
	
	private List<Double> totalInfo;
	
	private List<Employee> 			  empList; 		
	
	private String				exportX;   //1导出
	private BigDecimal 			totalAmount;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"formalContractStatParameters", this, new String[] { "groupId", "saleMan",
					"contractNo", "itemNo","custom",
					"startDate","endDate","customId"});
	
	
	@Override
	public String doDefault()  throws Exception  {
		holdTool.clearStore();
		return "SUCCESS";
	}
	
    public String popQuery() throws Exception{
    	logger.info("到款统计查询条件");
    	ParameterUtils.prepareParameters(holdTool);
    	empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
    	return 	"popQuery";
    }	
    
    @SuppressWarnings("unchecked")
	public String queryList() throws Exception{
    	logger.info("到款统计查询列表");
    	ParameterUtils.prepareParameters(holdTool);
    	/**
    	 *已签合同收款
    	 */
    	StringBuffer reveSql = new StringBuffer();
    	String selectSql = " select emp.NAME as empName," +  // 0 销售员
    			"cm.con_id," +				// 1 合同号
    			"(select im.con_item_id from yx_con_item_minfo im where im.con_item_minfo_sid = reve.bill_sid)," +          //2 项目号
    			"cm.con_name," +		     //3 合同名称
    			"yc.name as yxName," +			         //4 客户名称
    			"reve.amount_time," +         //5 到款日期
    			"reve.amount,"+           //6 到款金额
    			"cm.con_main_info_sid ";   //7 合同号
    	reveSql.append("from yx_reve_info reve,yx_exployee emp,yx_con_main_info cm,yx_client yc " +
    			"where reve.FK_CON_MAIN_INFO_SID = cm.con_main_info_sid " +
    			"and cm.sale_man = emp.id " +
    			"and cm.con_customer = yc.id ");
    	/**
    	 * 无合同收款
    	 */
    	StringBuffer noReveSql = new StringBuffer();
    	String noSelectSql="select emp.NAME," +   //0 销售员
    			"null," +						//1 合同号      没有
    			"null," +						//2 项目号      没有
    			"null," +						//3 合同名称    没有
    			"yc.name," +					//4 客户名称
    			"noreve.recevie_date," +		//5 到款日期
    			"noreve.recevie_amount,"+		//6 到款金额
    			"null ";							//7 合同号
    	noReveSql.append("from yx_nocontract_recevie_amount noreve,yx_exployee emp,yx_client yc " +
    			"where noreve.sale_man = emp.id " +
    			"and noreve.customerid = yc.id " +
    			"and noreve.state in (0,2) ");
    	/**
    	 * 查询条件
    	 */
    	if (groupId != null) {   //组别
    		reveSql.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
					+ groupId + "%') ");
    		
    		noReveSql.append("and emp.position in(select tr.id from yx_organization_tree tr where tr.organization_code like '"
    				+ groupId + "%') ");
		}
		if (saleMan != null) {  //销售员
			reveSql.append("and emp.id=" + saleMan + " ");

			noReveSql.append("and noreve.sale_man = " + saleMan + " ");
			
		}
		if (contractNo != null && contractNo.trim().length() > 0) {  //合同号
			reveSql.append(" and cm.con_id = '" + contractNo + "' ");
			noReveSql.append(" and 1=2 ");
		}
		if (itemNo != null && itemNo.trim().length() > 0) {  //项目号
			reveSql
			.append("and exists (select 1 from "
					+ "yx_con_item_minfo item "
					+ "where reve.bill_sid = item.con_item_minfo_sid and item.con_item_id = '"
					+ itemNo + "') ");
			noReveSql.append(" and 1=2 ");
			
		}
		if (customId != null) {   //客户
			reveSql.append(" and cm.con_customer = " + customId + " ");
			
			noReveSql.append(" and noreve.customerid = " + customId + " ");
		}
		if (startDate != null && startDate.trim().length() > 0) {  //查询到款日期开始
			reveSql.append(" and to_date('" + startDate
					+ "','yyyy-MM-dd')<= trunc(reve.amount_time) ");
			
			noReveSql.append(" and to_date('" + startDate
					+ "','yyyy-MM-dd')<= trunc(noreve.recevie_date) ");
		}

		if (endDate != null && endDate.trim().length() > 0) {  //查询到款日期结束
			reveSql.append(" and to_date('" + endDate
					+ "','yyyy-MM-dd')>= trunc(reve.amount_time)");
			
			noReveSql.append(" and to_date('" + endDate
					+ "','yyyy-MM-dd')>= trunc(noreve.recevie_date)");
		}
		String orderBy = " order by 6 DESC,4,1 ";
		StringBuffer allSql=new StringBuffer();
		allSql.append(selectSql+reveSql);//前半部分
		allSql.append(" union all ");//连接
		allSql.append(noSelectSql+noReveSql+orderBy);//后半部分
		logger.info(allSql);
		String sqlCount = "select count(*) from (" + allSql + " )";
		logger.info(sqlCount);
		
		String totalreveSql = "select sum(reve.amount) ";
		String totalnoReveSql = "select sum(noreve.recevie_amount) ";
		
		List<BigDecimal> totalReceInfo = yxQueryService.listQueryNoPage(totalreveSql+reveSql.toString());
		List<BigDecimal> totalNoReceInfo = yxQueryService.listQueryNoPage(totalnoReveSql+noReveSql.toString());
		BigDecimal totalreveAmount = new BigDecimal(0);
		BigDecimal totalnoreveAmount = new BigDecimal(0);
		if(totalReceInfo.get(0)!=null){
			totalreveAmount = totalReceInfo.get(0);
		}
		if(totalNoReceInfo.get(0)!=null){
			totalnoreveAmount = totalNoReceInfo.get(0);
		}
		totalAmount = totalreveAmount.add(totalnoreveAmount);
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(allSql.toString());
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql(sqlCount,allSql.toString(), info);
			return "SUCCESS";
		}  	
    }
    
    private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","客户简称","到款日期",
				"到款金额" });
		export.setTableName("实际到款统计");
		for(Object[] obj:oArray){
			export.addRow(new Object[]{obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[6] });
		}
		OutputStream os = DownloadUtils.getResponseOutput("实际到款统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
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

	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
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

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IYXQueryService getYxQueryService() {
		return yxQueryService;
	}

	public void setYxQueryService(IYXQueryService yxQueryService) {
		this.yxQueryService = yxQueryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public Long getCustomId() {
		return customId;
	}

	public void setCustomId(Long customId) {
		this.customId = customId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Double> getTotalInfo() {
		return totalInfo;
	}

	public void setTotalInfo(List<Double> totalInfo) {
		this.totalInfo = totalInfo;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
}
