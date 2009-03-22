package com.baoz.yx.action.statistics;

import java.io.OutputStream;
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
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/statistics/AssistanceStatList.jsp"),
	@Result(name = "showQuery", value = "/WEB-INF/jsp/statistics/AssistanceStatQuery.jsp")
})
public class AssistanceStatAction extends DispatchAction{
	
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;
	@Autowired
	@Qualifier("statisticsService")	
	private IStatisticsService 			statisticsService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	private PageInfo					info;

	private List<Department>			groupList;	
	private List<Employee> 				empList; 			// 查询显示销售员
	private String groupId;            //组别
	private Long saleMan;            //销售员
	private Long supplierid;         //供应商
	private String itemId;           //项目号
	private String conNum;           //合同号
	private String assistanceNo;           //外协合同号
	private String assistanceName;  //外协合同名称
	private Double assistanceAmountStart;  // 外协金额开始
	private Double assistanceAmountEnd;   //外协金额结束
	private String supplierName;//外协供应商名称
	private Double sumContractMoney;
	private Double sumPay;
	private String				exportX;   //1导出
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"assistanceStatQueryParameters",this,new String[]{"groupId","saleMan",
					"supplierid","itemId","conNum","assistanceNo",
					"assistanceName","assistanceAmountStart","assistanceAmountEnd","supplierName"}); 
	@Override
	public String doDefault() throws Exception {
		holdTool.clearStore();
		return SUCCESS;
	}
	
	public String showQueryList(){
		ParameterUtils.prepareParameters(holdTool);
		String t = foramlContractService.getOrganizationCodeByEid(UserUtils.getUser().getId());
		if(t.length()>4){
			groupId = t.substring(0, 4);
		}
		empList = foramlContractService.getSaleManByGroupId(t);
		groupList = UserUtils.getUserDetail().getDepartments();
		return 	"showQuery";
	}
	
	@SuppressWarnings("unchecked")
	public String queryResult(){
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb = new StringBuffer();
		StringBuffer from = new StringBuffer();
		String select = "select ac,si.supplierName,emp.name,cimi ";
		from.append(" from AssistanceContract ac, SupplierInfo si,ContractMainInfo cm,ContractItemMaininfo cimi  , Employee emp " +
				" where ac.is_active='1' and ac.supplyId=si.supplierid " +
				"and cm.saleMan = emp.id " +
				" and cimi.conItemMinfoSid = ac.conItemMainInfoSid " +
				"and cm.conMainInfoSid = ac.contractId " +
				" and ac.assistanceContractType not in('0','1','3')");
		if(supplierid!=null){
			sb.append(" and si.supplierid = '"+supplierid+"' " );
		}
		if(assistanceAmountStart!=null && !"".equals(assistanceAmountStart)){
			sb.append(" and ").append(assistanceAmountStart).append("<ac.contractMoney ");
		}
		if(assistanceAmountEnd!=null && !"".equals(assistanceAmountEnd)){
			sb.append(" and ac.contractMoney < ").append(assistanceAmountEnd);
		}
		if(assistanceName!=null && !"".equals(assistanceName)){
			sb.append(" and ac.assistanceName like '%").append(assistanceName).append("%' ");
		}
		if(StringUtils.isNotBlank(assistanceNo)){
			sb.append(" and lower(ac.assistanceId) = '"+ StringUtils.lowerCase(assistanceNo) +"' ");
		}
		if(StringUtils.isNotBlank(itemId)){
			sb.append(" and lower(ac.mainProjectId) = '"+StringUtils.lowerCase(itemId)+"' ");
		}
		if(StringUtils.isNotBlank(conNum)){
			sb.append(" and exists (select 1 from ContractMainInfo contract where contract.conMainInfoSid = ac.contractId and lower(contract.conId) = '"+StringUtils.lowerCase(conNum)+"') ");
		}
		//组别
		if (groupId!=null&&!"".equals(groupId)) {
			sb.append(" and emp.position in (select tr.id from OrganizationTree tr where tr.organizationCode like '"+groupId+"%') ");
		}
		
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleMan = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			groupId = user.getPosition().getOrganizationCode();
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and ac.contractId in(select mainInfo.conMainInfoSid from ContractMainInfo mainInfo where mainInfo.saleMan in (select e.id from Employee e where e.position in (select o.id from OrganizationTree o where o.organizationCode like '"+groupId+"%')))");
		}
		if (saleMan!=null&&!"".equals(saleMan)) {
			sb.append(" and cm.saleMan = "+saleMan);
			sb.append(" and ac.contractId in(select mainInfo.conMainInfoSid from ContractMainInfo mainInfo where mainInfo.saleMan ="+saleMan+")");
		}
		
		sb.append("order by ac.assistanceContractType,ac.id desc");
		
		
		String infoHql = select +from +sb;
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = commonService.list(infoHql);
			this.processExport(objList);
			return null;
		}
		else{
			info = queryService.listQueryResult("select count(*) "+ from+sb,infoHql, info);
		}
		StringBuffer sumConSelect = new StringBuffer();
		sumConSelect.append("select sum(ac.hasPayAmount) ");
		String sumConHql = sumConSelect+from.toString()+sb;
		sumPay= (Double) commonService.uniqueResult(sumConHql);
		String sumPayHql = "select sum(ac.contractMoney) ";
		sumContractMoney = (Double) commonService.uniqueResult(sumPayHql+from+sb.toString());
		
		return "success";
	}
	/**
	 * 
	 * @param oArray
	 */
	private  void processExport(List<Object[]> oArray) {	
		AssistanceContract ac = null;
		String supplierName = null;
		Double payAmount = 0.00;
		
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"销售员","外协合同号","外协合同名称","外协供应商","签订日期","预计结束日期","外协合同金额","已支付金额"
				,"余额"});
		export.setTableName("外协合同统计");
		for(Object[] obj:oArray){
			ac = (AssistanceContract)obj[0];
			supplierName = (String) obj[1];
			payAmount = (Double) ac.getHasPayAmount();
			if(payAmount == null){
				payAmount = 0.00;
			}
			export.addRow(new Object[]{obj[2],ac.getAssistanceId(),ac.getAssistanceName(),supplierName,ac.getContractDate(),
					ac.getEndDate(),ac.getContractMoney(),payAmount,ac.getContractMoney()-payAmount
			});
		}
		OutputStream os = DownloadUtils.getResponseOutput("外协合同统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public PageInfo getInfo() {
		return info;
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

	public Long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getConNum() {
		return conNum;
	}

	public void setConNum(String conNum) {
		this.conNum = conNum;
	}

	public String getAssistanceName() {
		return assistanceName;
	}

	public void setAssistanceName(String assistanceName) {
		this.assistanceName = assistanceName;
	}

	public Double getAssistanceAmountStart() {
		return assistanceAmountStart;
	}

	public void setAssistanceAmountStart(Double assistanceAmountStart) {
		this.assistanceAmountStart = assistanceAmountStart;
	}

	public Double getAssistanceAmountEnd() {
		return assistanceAmountEnd;
	}

	public void setAssistanceAmountEnd(Double assistanceAmountEnd) {
		this.assistanceAmountEnd = assistanceAmountEnd;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getAssistanceNo() {
		return assistanceNo;
	}

	public void setAssistanceNo(String assistanceNo) {
		this.assistanceNo = assistanceNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getSumContractMoney() {
		return sumContractMoney;
	}

	public void setSumContractMoney(Double sumContractMoney) {
		this.sumContractMoney = sumContractMoney;
	}

	public Double getSumPay() {
		return sumPay;
	}

	public void setSumPay(Double sumPay) {
		this.sumPay = sumPay;
	}

	public String getExportX() {
		return exportX;
	}

	public void setExportX(String exportX) {
		this.exportX = exportX;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}


}
