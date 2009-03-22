package com.baoz.yx.action.statistics;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
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


//正式合同统计
@Results( {	
	@Result(name = "SUCCESS", value = "/WEB-INF/jsp/statistics/FormalContractStatisticsList.jsp"),
	@Result(name = "popQuery", value = "/WEB-INF/jsp/statistics/FormalContractStatisticsQuery.jsp")
})
public class FormalContractStatAction extends DispatchAction {
	
	
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
	
	private Long contractType; //合同类型
	
	private Long finalAccount;//预决算信息
	
	private String department; //负责部门
	
	private String businessType; //行业类别
	
	private String businessArea; //行业市场
	
	private String startDate;
	
	private String endDate;
	
	private String handInStartDate;//合同交货日期
	
	private String handInEndDate;//合同交货日期
	
	private String readyCon;    //预合同状态
	
	private List<Double> totalInfo;
	private List<Double> totalNoxInfo;
	private List<Double> totalContractInfo; //由于合同重复 所以合同含税和不含税总金额重新计算
	
	private List<Employee> 			  empList; 	
	
    private List<YXTypeManage>        businessTypeList; //行业类别
    
    private List<YXTypeManage>        businessAreaList; //销售组织 即行业市场
    
    private List<YXTypeManage>        dutydepartmentlist;//工程部门
    
    private List<YXTypeManage> clientNature; //客户性质
    
    private List<YXTypeManage>       contractTypeList;

    private List<YXTypeManage>       contractNatureList;
    
    private String conType;  //合同性质查询
    
    private String clientNID;
    
    private Long isFromCustom ; 
    
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"formalContractStatParameters", this, new String[] { "groupId", "saleMan",
					"contractNo", "itemNo","custom","customId","contractType","department","businessType","businessArea",
					"startDate","endDate","finalAccount","handInStartDate","handInEndDate","readyCon","clientNID"});

	@Override
	public String doDefault()  throws Exception  {
        logger.info("新签合同统计");
        holdTool.clearStore();
		return "SUCCESS";
	}
	
    public String popQuery() throws Exception{
    	logger.info("客户统计查询条件");
    	ParameterUtils.prepareParameters(holdTool);
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		businessTypeList = typeManageService.getYXTypeManage(1002L);// 显示所有行业类型
		businessAreaList = typeManageService.getYXTypeManage(1016L);// 显示所有行业市场
		dutydepartmentlist = typeManageService.getYXTypeManage(1018L);//显示所有工程部门
		contractTypeList = typeManageService.getYXTypeManage(1020L); //合同类型
		clientNature = typeManageService.getYXTypeManage(1001L);// 显示所有客户性质
		contractNatureList = typeManageService.getYXTypeManage(1019L);  //显示合同性质
    	return 	"popQuery";
    }	
    
    public String queryList() throws Exception{
    	//从客户统计中跳转至此页面，加查询条件时先设默认值
    	if(isFromCustom!=null){
    		groupId = "";
    		contractNo ="";
    		itemNo = "";
    		department = "";
    		businessType="";
    		businessArea="";
    		//由于判断语句问题 这里随便设了一个CUSTOM名称 判断会更具CUSTOMID来进行查找
    		//不设会报空指针
    		custom = "1";
    		startDate="";
    		endDate="";
    		handInEndDate="";
    		handInStartDate="";
    		readyCon="false";
    		clientNID="";
    	}
    	
    	
    	logger.info("新签订合同统计查询列表");
    	ParameterUtils.prepareParameters(holdTool);
    	
    	String sql = new String();
 
    	if(contractType == null ||contractType == 1 ){
	       		sql = sql + " select   e.name,"+
	            " c.con_id,"+
	            " i.con_item_id,"+
	            " c.con_name,"+
	            " c.con_tax_tamount,"+
	            " c.no_tax_tamount,"+
	            " cli.name as cliName ,"+
	            " i.item_res_dept,";
	       		
				// 预合同
				if (readyCon.equals("true")) {
					sql = sql +" (select sum(ii.init_tax_bill_amount) from yx_init_con_billpro ii where ii.fk_bill_type !=4 and ii.fk_item_info_sid = i.con_item_minfo_sid ) as itemAllMoney ," ;
				}
				else{
					sql = sql +" (select sum(r.real_bill_amount) from yx_real_con_bc_plan r where r.fk_bill_type != 4 and r.fk_con_item_minfo_sid = i.con_item_minfo_sid) as itemAllMoney ," ;
				}
	       		
	       		sql = sql + " c.active_date, " +
    			" c.con_main_info_sid," +
    			"(select sum(ci.con_item_amount_with_notax) from yx_con_item_info ci where ci.fk_con_item_minfo_sid = i.con_item_minfo_sid) as itemNoxAmount," +
    			 " c.con_type as contype " +
    			" from yx_con_main_info c, yx_con_item_minfo i, yx_exployee e,yx_organization_tree t,yx_client cli " +
    			" where i.fk_con_main_info_sid = c.con_main_info_sid and e.id = c.sale_man  " +
    			" and cli.id = c.con_customer " +
    			" and c.contract_type = 1"+
    			" and t.id = e.position ";
	       	if(!groupId.equals("")){
    			sql = sql +" and  t.organization_code like '"+groupId+"%' ";
	       	}
    	    if(!contractNo.equals("")){
    			sql = sql + " and lower(c.con_id) like '%"+contractNo.toLowerCase()+"%' " ;
    	    }
    	    if(!itemNo.equals("")){
    	    	sql = sql +" and lower(i.con_item_id) like '%"+itemNo.toLowerCase()+"%' ";
    	    }
    	    if(!department.equals("")){
    	    	sql = sql +" and i.item_res_dept = '"+department+"' ";
    	    }
       		if(saleMan!=null){
       			sql = sql + " and c.sale_man = '"+saleMan+"'"; 
       		}
    		if(!businessType.equals("")){	
    			sql = sql + " and cli.hylx_id = '"+businessType+"' ";
    		}
    		if(!businessArea.equals("")){
    			sql = sql + " and cli.hysc_id = '"+businessArea+"' ";
    		}
 			if(contractType!=null&&contractType==1){
				sql = sql +" and c.contract_type = '1'";
			}
 			//合同性质
 			if(StringUtils.isNotBlank(conType)){
 				sql = sql + " and c.con_type = " + conType;
 			}
    		if(!clientNID.equals("")){
    			sql= sql + " and cli.khxz_id = '"+clientNID+"' ";
    		}
       		if(finalAccount!=null){
       			sql = sql + " and c.final_account = "+finalAccount+" " ;
       		}
       	    if(customId == null&&!custom.equals("")){
       	    	sql = sql + " and cli.full_name like '%"+custom+"%' ";
       	    }else{
       	    	if(!custom.equals("")){
       	    		sql = sql + " and cli.id = "+customId+" " ;
       	    	}
       	    }
    		if(!startDate.trim().equals("")){
    			sql = sql +" and to_date('"+startDate+"','yyyy-MM-dd')<=c.active_date ";
			}
			if(!endDate.trim().equals("")){
				sql = sql +" and to_date('"+endDate+"','yyyy-MM-dd')>=c.active_date ";
			} 
			
			if(!handInStartDate.trim().equals("")||!handInEndDate.trim().equals("")) {
			    sql = sql +" and exists " + 
			    		" (select 1  from yx_con_other_info coi " +
			    		" where coi.fk_con_main_info_sid = c.con_main_info_sid ";
				if(!handInStartDate.trim().equals("")){
					sql = sql +" and to_date('"+handInStartDate+"','yyyy-MM-dd')<= coi.CON_DELIVERY_DATE ";
				}
				if(!handInEndDate.trim().equals("")){
					sql = sql +" and to_date('"+handInEndDate+"','yyyy-MM-dd')>=coi.CON_DELIVERY_DATE ";
				}
				sql = sql + ")";
			}
			
			// 预合同
			if (readyCon.equals("true")) {
				sql = sql + " and c.con_state = 3 ";
			}
			else{
				sql = sql +  "and c.con_state > 3"; 
			}
			
        }
    
    	if(contractType == null){
    		if(itemNo.equals("")){
    			sql = sql + " union all ";
    		}
    	}
    	
    	if(contractType == null||contractType == 2 ){
    		if(itemNo.equals("")){
	    		sql = sql +	" select e.name,"+
	            " c.con_id,"+
	            " null as null1,"+
	            " c.con_name ,"+
	            " c.con_tax_tamount,"+
	            " c.no_tax_tamount,"+
	            " cli.name as cliName,"+
	            " c.main_item_department as null2," +
	            " c.con_tax_tamount as itemAllMoney," +
	            " c.active_date," +
	            " c.con_main_info_sid," +
	            " c.no_tax_tamount as itemNoxAmount," +
	            " c.con_type as contype " +
    			" from yx_con_main_info c, yx_exployee e,yx_organization_tree t,yx_client cli " +
    			" where e.id = c.sale_man  " +
    			" and cli.id = c.con_customer " +
    			" and t.id = e.position " +
    			" and c.contract_type = 2 " ;
		       	if(!groupId.equals("")){
	    			sql = sql +" and  t.organization_code like '"+groupId+"%' ";
		       	}
    			if(!contractNo.equals("")){
    				sql = sql +" and lower(c.con_id) like '%"+contractNo.toLowerCase()+"%'  " ;
    			}
    			if(contractType!=null&&contractType==2){
    				sql = sql +" and c.contract_type = '2'";
    			}
    			//合同性质
    			if(StringUtils.isNotBlank(conType)){
     				sql = sql + " and c.con_type = " + conType;
     			}
    	  	    if(!department.equals("")){
        	    	sql = sql +" and c.main_item_department = '"+department+"' ";
        	    }
           		if(saleMan!=null){
           			sql = sql + " and c.sale_man = '"+saleMan+"'"; 
           		}
        		if(!businessType.equals("")){	
        			sql = sql + " and cli.hylx_id = '"+businessType+"' ";
        		}
        		if(!businessArea.equals("")){
        			sql = sql + " and cli.hysc_id = '"+businessArea+"' ";
        		}
        		if(!clientNID.equals("")){
        			sql= sql + " and cli.khxz_id = '"+clientNID+"' ";
        		}
           		if(finalAccount!=null){
           			sql = sql +" and c.final_account = "+finalAccount+" " ;
           		}
           	    if(customId == null&&!custom.equals("")){
           	    	sql = sql + " and cli.full_name like '%"+custom+"%' ";
           	    }else{
           	    	if(!custom.equals("")){
           	    		sql = sql + " and cli.id = "+customId+" " ;
           	    	}
           	    }
        		if(!startDate.trim().equals("")){
        			sql = sql +" and to_date('"+startDate+"','yyyy-MM-dd')<=c.active_date ";
    			}
    			if(!endDate.trim().equals("")){
    				sql = sql +" and to_date('"+endDate+"','yyyy-MM-dd')>=c.active_date ";
    			} 
    			if(!handInStartDate.trim().equals("")||!handInEndDate.trim().equals("")) {
    			    sql = sql +" and exists " + 
    			    		" (select 1  from yx_con_other_info coi " +
    			    		" where coi.fk_con_main_info_sid = c.con_main_info_sid ";
    				if(!handInStartDate.trim().equals("")){
    					sql = sql +" and to_date('"+handInStartDate+"','yyyy-MM-dd')<= coi.CON_DELIVERY_DATE ";
    				}
    				if(!handInEndDate.trim().equals("")){
    					sql = sql +" and to_date('"+handInEndDate+"','yyyy-MM-dd')>=coi.CON_DELIVERY_DATE ";
    				}
    				sql = sql + ")";
    			}
    			
    			// 预合同
    			if (readyCon.equals("true")) {
    				sql = sql + " and c.con_state = 3 ";
    			}
    			else{
    				sql = sql + "and c.con_state > 3";
    			}
    			
    	    }
    	}
    	if(contractType!=null&&contractType==2){
    		if(itemNo.equals("")){
		    	sql = sql +" order by 1 ";
		    	String countSql =  "select count(*) from ( "+sql+") ";
		    	String totalMoneySqlItem = " select sum(itemAllMoney) from ( "+sql+" ) ";
		    	String totalNoMoneySqlItem = " select sum(itemNoxAmount) from ( "+sql+" ) ";
		    	String totalMoneySqlContract =" select sum(distb.con_tax_tamount),sum(distb.no_tax_tamount) from ( " +
		    			" select distinct(yx.con_id),yx.con_tax_tamount,yx.no_tax_tamount from ("+sql+" ) yx ) distb ";
		    	totalInfo = yxQueryService.listQueryNoPage(totalMoneySqlItem);
		    	totalNoxInfo = yxQueryService.listQueryNoPage(totalNoMoneySqlItem);
		    	totalContractInfo = yxQueryService.listQueryNoPage(totalMoneySqlContract);
		    	if("true".equals(ServletActionContext.getRequest().getParameter("isExport"))){
		    		List<Object[]> data = yxQueryService.listQueryNoPage(sql);
		    		exportToExcel(data,totalInfo,totalContractInfo);
		    		return null;
		    	}else{
		    		info = yxQueryService.listQueryResultBySql(countSql, sql, info); 
		    	}
    		}
    	}else{
	    	sql = sql +" order by 1 ";
	    	String countSql =  "select count(*) from ( "+sql+") ";
	    	String totalMoneySqlItem = " select sum(itemAllMoney) from ( "+sql+" ) ";
	    	String totalNoMoneySqlItem = " select sum(itemNoxAmount) from ( "+sql+" ) ";   
	    	String totalMoneySqlContract =" select sum(distb.con_tax_tamount),sum(distb.no_tax_tamount) from ( " +
			" select distinct(yx.con_id),yx.con_tax_tamount,yx.no_tax_tamount from ("+sql+" ) yx ) distb ";
         	totalInfo = yxQueryService.listQueryNoPage(totalMoneySqlItem);
         	totalNoxInfo = yxQueryService.listQueryNoPage(totalNoMoneySqlItem);  //项目不含税
         	totalContractInfo = yxQueryService.listQueryNoPage(totalMoneySqlContract);
	    	if("true".equals(ServletActionContext.getRequest().getParameter("isExport"))){
	    		List<Object[]> data = yxQueryService.listQueryNoPage(sql);
	    		exportToExcel(data,totalInfo,totalContractInfo);
	    		return null;
	    	}else{
	    		info = yxQueryService.listQueryResultBySql(countSql, sql, info); 
	    	}
    	}
    	return "SUCCESS";
    }

	private void exportToExcel(List<Object[]> data,List<Double> totalInfo,List<Double> totalContractInfo) {
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","合同号","项目号","合同名称","合同金额","合同不含税金额","客户简称","项目部门","项目含税金额","项目不含税金额","转正式合同日期"});
		for (Object[] objects: data) {
			export.addRow(new Object[]{objects[0],objects[1],objects[2],objects[3],objects[4],objects[5],objects[6],objects[7],objects[8],objects[11],objects[9]});
		}
		export.setTableName("新签合同统计.xls");
		export.export(DownloadUtils.getResponseOutput("新签合同统计.xls"));
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



	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
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

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public List<YXTypeManage> getBusinessTypeList() {
		return businessTypeList;
	}

	public void setBusinessTypeList(List<YXTypeManage> businessTypeList) {
		this.businessTypeList = businessTypeList;
	}

	public List<YXTypeManage> getBusinessAreaList() {
		return businessAreaList;
	}

	public void setBusinessAreaList(List<YXTypeManage> businessAreaList) {
		this.businessAreaList = businessAreaList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getDutydepartmentlist() {
		return dutydepartmentlist;
	}

	public void setDutydepartmentlist(List<YXTypeManage> dutydepartmentlist) {
		this.dutydepartmentlist = dutydepartmentlist;
	}

	public Long getContractType() {
		return contractType;
	}

	public void setContractType(Long contractType) {
		this.contractType = contractType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
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

	public Long getIsFromCustom() {
		return isFromCustom;
	}

	public void setIsFromCustom(Long isFromCustom) {
		this.isFromCustom = isFromCustom;
	}

	public Long getFinalAccount() {
		return finalAccount;
	}

	public void setFinalAccount(Long finalAccount) {
		this.finalAccount = finalAccount;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public List<Double> getTotalContractInfo() {
		return totalContractInfo;
	}

	public void setTotalContractInfo(List<Double> totalContractInfo) {
		this.totalContractInfo = totalContractInfo;
	}

	public String getHandInStartDate() {
		return handInStartDate;
	}

	public void setHandInStartDate(String handInStartDate) {
		this.handInStartDate = handInStartDate;
	}

	public String getHandInEndDate() {
		return handInEndDate;
	}

	public void setHandInEndDate(String handInEndDate) {
		this.handInEndDate = handInEndDate;
	}

	public String getReadyCon() {
		return readyCon;
	}

	public void setReadyCon(String readyCon) {
		this.readyCon = readyCon;
	}

	public List<YXTypeManage> getClientNature() {
		return clientNature;
	}

	public void setClientNature(List<YXTypeManage> clientNature) {
		this.clientNature = clientNature;
	}

	public String getClientNID() {
		return clientNID;
	}

	public void setClientNID(String clientNID) {
		this.clientNID = clientNID;
	}

	public List<Double> getTotalNoxInfo() {
		return totalNoxInfo;
	}

	public void setTotalNoxInfo(List<Double> totalNoxInfo) {
		this.totalNoxInfo = totalNoxInfo;
	}

	public List<YXTypeManage> getContractNatureList() {
		return contractNatureList;
	}

	public void setContractNatureList(List<YXTypeManage> contractNatureList) {
		this.contractNatureList = contractNatureList;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}
	
	
}
