package com.baoz.yx.action.statistics;

import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.vo.Department;


//客户统计
@Results( {	
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/CustomerStatisticsList.jsp"),
	@Result(name = "popQuery", value = "/WEB-INF/jsp/statistics/CustomerStatisticsQuery.jsp")
})
public class CustomerContractStatAction extends DispatchAction {

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

	private Long rownum; //合同量

	private String custom; //客户简称

	private Long customId;

	private Long contractType; //合同类型

	private String startDate;

	private String endDate;

	private String clientNature; //客户性质

	private List<Double> totalInfo;

	private List<Employee> 	empList;

	private List<YXTypeManage> clientNatureList; 	//客户性质

	private List<YXTypeManage> contractTypeList;    //合同性质

	private String				exportX;   //1导出

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"customerContractParameters", this, new String[] { "clientNature", "contractType","custom","rownum",
					"startDate","endDate"});

	@Override
	public String doDefault()  throws Exception  {
		logger.info("客户统计");
		holdTool.clearStore();

		return "SUCCESS";
	}

	public String popQuery() throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		logger.info("客户统计查询条件");
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		clientNatureList = typeManageService.getYXTypeManage(1001L); //客户性质
		contractTypeList = typeManageService.getYXTypeManage(1020L); //合同性质

		return 	"popQuery";
	}	

	@SuppressWarnings("unchecked")
	public String queryList() throws Exception{
		logger.info("客户统计查询列表");

		ParameterUtils.prepareParameters(holdTool);

		String sql = "  select distinct(cli.id), " +
		"cli.full_name,"+
		"(select sum(c.con_tax_tamount) from yx_con_main_info c where c.con_customer = cli.id and c.con_state >= 4) as taxMoney ,"+  
		"(select sum(c.no_tax_tamount) from yx_con_main_info c where c.con_customer = cli.id and c.con_state >= 4) as noTaxMoney " +
		" from yx_client cli "+
		" where exists (select 1 from yx_con_main_info c where c.con_customer = cli.id and c.con_state >= 4   " ;

		//合同性质
		if(contractType!=null){
			sql = sql +" and c.CONTRACT_TYPE = '"+contractType+"' ";
		}
		//日期
		if(!startDate.trim().equals("")){
			sql = sql +" and to_date('"+startDate+"','yyyy-MM-dd')<=c.con_sign_date ";
		}
		if(!endDate.trim().equals("")){
			sql = sql +" and to_date('"+endDate+"','yyyy-MM-dd')>=c.con_sign_date ";
		} 
		sql+=" )";
		//客户性质
		if(!clientNature.equals("")){
			sql = sql + " and cli.khxz_id = " +clientNature+" ";
		}

		//客户
		if(customId == null&&!custom.equals("")){
			sql = sql + " and cli.full_name like '%"+custom+"%' ";
		}else{
			if(!custom.equals("")){
				sql = sql + " and cli.id = "+customId+" " ;
			}
		}

		sql = sql +" order by  taxMoney desc ";
		sql = " select rownum,tb.full_name,tb.taxMoney,tb.noTaxMoney,tb.id from ( "+sql+") tb ";
		if(rownum!=null){
			sql = sql +"  where rownum <= "+rownum+" ";
		}

		String countSql =  "select count(*) from ( "+sql+") ";
		String totalMoneySql = " select sum(taxMoney),sum(noTaxMoney) from ( "+sql+" ) ";
		totalInfo = yxQueryService.listQueryNoPage(totalMoneySql);

		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = yxQueryService.listQueryNoPage(sql);
			this.processExport(objList);
			return null;
		}
		else{
			info = yxQueryService.listQueryResultBySql(countSql, sql, info); 
			return "SUCCESS";
		}  	
	}

	private  void processExport(List<Object[]> oArray) {		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"客户名称","含税总金额","不含税总金额" });
		export.setTableName("客户统计");
		for(Object[] obj:oArray){
			export.addRow(new Object[]{obj[1],obj[2],obj[3] });
		}
		OutputStream os = DownloadUtils.getResponseOutput("客户统计.xls");
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

	public Long getContractType() {
		return contractType;
	}

	public void setContractType(Long contractType) {
		this.contractType = contractType;
	}


	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
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

	public String getClientNature() {
		return clientNature;
	}

	public void setClientNature(String clientNature) {
		this.clientNature = clientNature;
	}

	public List<YXTypeManage> getClientNatureList() {
		return clientNatureList;
	}

	public void setClientNatureList(List<YXTypeManage> clientNatureList) {
		this.clientNatureList = clientNatureList;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public List<Double> getTotalInfo() {
		return totalInfo;
	}

	public void setTotalInfo(List<Double> totalInfo) {
		this.totalInfo = totalInfo;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}




}
