package com.baoz.yx.action.harvestMeansManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 收款管理查询
 * 
 * @author ye peng (yepeng@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/moonHarvestProgram.jsp")
public class MoonHarvestProgramAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("monthHarvestPlanService")
	private MonthHarvestPlanService monthHarvestPlanService;
	private PageInfo info;
	private List list;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private List<YXTypeManage> receTypetrans;
	private ReveInfo ri;	
	private List<YXTypeManage> clientNames;
	
	private String month; 
	private String year;

	private Map<String,String> yearMap = new TreeMap<String, String>();

	
//	 private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
//	    		"SelectPerQueryParameters",this,new String[]{"sid","id","invoiceID","billType","receState","STime","LTime"});	
	
		  
	@Override
	@SuppressWarnings("unchecked")
	
	public String doDefault() throws Exception {
		this.logger.info("根据当前月份查询月收款计划");
		
//		receTypetrans= typeManageService.getYXTypeManage(1017L);// 到款方式
		// 年份
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		if(year == null){
			year = defaultYear+"";
		}
		int defaultMonth = calendar.get(Calendar.MONTH);
		if(month == null){
			month = StringUtils.leftPad((defaultMonth+1)+"", 2 ,"0");
		}
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
//		ParameterUtils.prepareParameters(holdTool);
		//RealContractBillandRecePlan 合同开票收款计划表  rb
		//ContractMainInfo 合同主体信息表                  cm
		//ContractItemMaininfo 合同项目主体信息表          ci
		//MonthlyRecepro 合月度收款计划表
		String selectSql = "select (select c.conId from ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid )," //合同号
			+"(select cc.name from ContractMainInfo c ,YXClientCode cc where c.itemCustomer = cc.id and c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid),"//客户
			+"(select c.mainItemDept from ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid ),"//负责部门
			+"mr.realContractBillandRecePlan.realPredReceDate,mr.realContractBillandRecePlan.realReceAmount,"+//计划收款日期，计划收款金额
			"(select sum(ii.receAmount) from InvoiceInfo ii ,BillandProRelaion bp where bp.relateAmount = ii.applyInvoiceId and bp.realContractBillandRecePlan = mr.realContractBillandRecePlan.realConBillproSid) ";//收款状态
		StringBuffer fromWhere = new StringBuffer();
		fromWhere.append(" from MonthlyRecepro mr where 1=1 ");
		if ((year!= null && !"".equals(year))&&( month!= null && !"".equals(month)))
		{
			fromWhere.append(" and mr.billproMonth = to_date('").append(year+"-"+month).append("','yyyy-mm') "); // 把传过来的年进行比较
		}
		fromWhere.append(" order by mr.id");
		
		info = queryService.listQueryResult("select count(*) "+fromWhere.toString(),selectSql+fromWhere.toString(), info);
		logger.info("查询完毕");
		return "queryList";
	}
	public String generateMonthPlan() throws Exception {		
		//判断本月是否生成过计划(是当月是否生成当月的计划，而不是上个月是否生成当月的计划)
		if(monthHarvestPlanService.isGeneratedCurrentMonthPlan()){
			//已经生成过，不重复生成
			ActionContext.getContext().put("repeatGenerate", true);
			return doDefault();
		}else{
			monthHarvestPlanService.generateCurrentMonthPlan();
			ActionContext.getContext().put("generateSuccess", true);
			return doDefault();
		}
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map getYearMap() {
		return yearMap;
	}

	public void setYearMap(Map yearMap) {
		this.yearMap = yearMap;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getReceTypetrans() {
		return receTypetrans;
	}

	public void setReceTypetrans(List<YXTypeManage> receTypetrans) {
		this.receTypetrans = receTypetrans;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public ReveInfo getRi() {
		return ri;
	}

	public void setRi(ReveInfo ri) {
		this.ri = ri;
	}


	public List<YXTypeManage> getClientNames() {
		return clientNames;
	}

	public void setClientNames(List<YXTypeManage> clientNames) {
		this.clientNames = clientNames;
	}
	public MonthHarvestPlanService getMonthHarvestPlanService() {
		return monthHarvestPlanService;
	}
	public void setMonthHarvestPlanService(
			MonthHarvestPlanService monthHarvestPlanService) {
		this.monthHarvestPlanService = monthHarvestPlanService;
	}
}
