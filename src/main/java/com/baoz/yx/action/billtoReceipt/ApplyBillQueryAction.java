package com.baoz.yx.action.billtoReceipt;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

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
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/onApplyBill.jsp"),
	@Result(name = "showApply", value = "/WEB-INF/jsp/billtoReceipt/onApplyList.jsp")
})
public class ApplyBillQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo					info;          
	private ServletRequest				request;
	private List<Object>				billList;
	private RealContractBillandRecePlan realPlan;
	private List<Object>				conStageList;
	private String 						yearSel;
	private String 						monthSel;
	private Map<String,String> yearMap = new TreeMap<String, String>();
	
	private Long[] monthlyBillproSids;
	@Override
	public String doDefault() throws Exception {
		selectMonth();
		return SUCCESS;
	}
	/*
	 *  根据select中选择查看后几个月内的月度开票
	 *  
	 *  @return String
	 *  
	 */
	public String selectMonth()
	{
		logger.info(yearSel);
		logger.info(monthSel);
		Long uid = new UserUtils().getUser().getId();
		String select = "select r,yc,cm,(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),(select count(*) from ApplyBill b where b.realPlanId = r.realConBillproSid ) ";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.saleMan = "+ uid);
		if (StringUtils.isNotBlank(monthSel) && StringUtils.isNotBlank(yearSel)) {
			Calendar calendar = Calendar.getInstance();
			int defaultYear = calendar.get(Calendar.YEAR);
			yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
			yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
			yearMap.put((defaultYear+1)+"",(defaultYear+1)+"");
			yearMap.put((defaultYear+2)+"",(defaultYear+2)+"");
			yearMap.put(defaultYear+"",defaultYear+"");
			logger.info("选择的是"+yearSel+"年"+monthSel+"月!");
			from.append(" and to_char(r.realPredBillDate,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		else{
			Calendar calendar = Calendar.getInstance();
			int defaultYear = calendar.get(Calendar.YEAR);
			yearSel = defaultYear+"";
			int defaultMonth = calendar.get(Calendar.MONTH)+1;
			monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
			DecimalFormat d=new DecimalFormat("00");
			String month=d.format(defaultMonth);
			logger.info("月是:"+month);
			yearMap.put(defaultYear+"",defaultYear+"");
			yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
			yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
			yearMap.put((defaultYear+1)+"",(defaultYear+1)+"");
			yearMap.put((defaultYear+2)+"",(defaultYear+2)+"");
			logger.info("默认为7月!");
			from.append(" and to_char(r.realPredBillDate,'yyyy-mm')= '").append(defaultYear).append("-").append(month).append("'");
		}
		from.append(" order by r.realConBillproSid");	
		info=queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);
		return SUCCESS;
	}
	
	
	/*
	 * 
	 * 根据选中的月度开票查看信息,跳转到添加金额页面
	 * 
	 * @return String
	 * 
	 */
	public String showApply()
	{
		logger.info("弹出选中的开票申请");
		if (monthlyBillproSids != null) {
				logger.info(StringUtils.join(monthlyBillproSids,","));
				StringBuffer hql = new StringBuffer();
				hql.append("select r,yc,cm from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
						"cm.conCustomer = yc.id " +
						"and r.conMainInfoSid = cm.conMainInfoSid ");
				hql.append(" and r.realConBillproSid IN (").append(StringUtils.join(monthlyBillproSids,",")).append(") ");
				hql.append(" order by r.realConBillproSid");	
				info=new PageInfo();
				info.setResult(service.list(hql.toString()));
		}
		else {
			logger.info("monthlyBillproSids是空的");
		}
		return "showApply";
	}
	
	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public String getYearSel() {
		return yearSel;
	}
	public void setYearSel(String yearSel) {
		this.yearSel = yearSel;
	}
	public String getMonthSel() {
		return monthSel;
	}
	public void setMonthSel(String monthSel) {
		this.monthSel = monthSel;
	}
	public Map<String, String> getYearMap() {
		return yearMap;
	}

	public void setYearMap(Map<String, String> yearMap) {
		this.yearMap = yearMap;
	}

	public Long[] getMonthlyBillproSids() {
		return monthlyBillproSids;
	}

	public void setMonthlyBillproSids(Long[] monthlyBillproSids) {
		this.monthlyBillproSids = monthlyBillproSids;
	}
	public List<Object> getBillList() {
		return billList;
	}
	public void setBillList(List<Object> billList) {
		this.billList = billList;
	}
	public RealContractBillandRecePlan getRealPlan() {
		return realPlan;
	}
	public void setRealPlan(RealContractBillandRecePlan realPlan) {
		this.realPlan = realPlan;
	}
	public List<Object> getConStageList() {
		return conStageList;
	}
	public void setConStageList(List<Object> conStageList) {
		this.conStageList = conStageList;
	}
}
