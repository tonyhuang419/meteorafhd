package com.baoz.yx.action.billtoReceipt;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/onApplyBill.jsp"),
	@Result(name = "showApply", value = "/WEB-INF/jsp/billtoReceipt/onApplyList.jsp"),
	@Result(name = "showQuery", value = "/WEB-INF/jsp/billtoReceipt/onApplyBillQuery.jsp"),
	@Result(name = "showMain", value = "/WEB-INF/jsp/billtoReceipt/onApplyBillMain.jsp"),
	@Result(name = "togetherBill", value = "/WEB-INF/jsp/billtoReceipt/onApplyListToGether.jsp"),
	@Result(name = "showRelation", value = "/WEB-INF/jsp/billtoReceipt/onRelationReceipt.jsp"),
	@Result(name = "updateSeccess",type = ServletRedirectResult.class, value = "/billtoReceipt/applyBillQuery.action"),
	@Result(name = "merge", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
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
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;
	private PageInfo					info;          
	private ServletRequest				request;
	private List<Object>				billList;
	private RealContractBillandRecePlan realPlan;
	private List<Object>				conStageList;
	private String 						yearSel;
	private String 						monthSel;
	private Map<String,String> yearMap = new TreeMap<String, String>();
	private List<Object>				receiptList;
	private String 						succSave;
	
	private Long 				customer;            // 客户名称ID
	
	private String 				customerName;    //客户名称
	
	private Double				minRealTaxBillAmount;	//含税金额
	private Double				maxRealTaxBillAmount;	//含税金额
	
	private Double				minRealBillAmount;   // 不含税金额
	private Double				maxRealBillAmount;   // 不含税金额
	
	private Double 				minConAmount;        //合同金额
	private Double 				maxConAmount;        //合同金额
	
	private String 				conId;   			//合同号
	private String 				itemId;				//项目号
	private List<YXTypeManage>				stockOrgList;
	
	private String startDate;//预计开票开始日期
	
	private String endDate;//预计开票结束日期
	private Long[] monthlyBillproSids;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"applyBillQueryParameters",this,new String[]{"fromMenu","conId",
    				"itemId","customerName","customer","minRealTaxBillAmount",
    				"maxRealTaxBillAmount","minRealBillAmount","maxRealBillAmount",
    				"monthSel","yearSel","minConAmount",
    				"startDate","endDate","maxConAmount"}); 
	
	private boolean isFromMenu = false;
	@Override
	public String doDefault() throws Exception {
		isFromMenu = true;
		selectMonth();
		return SUCCESS;
	}
	/**
	 *  根据select中选择查看后几个月内的月度开票
	 *  @return String
	 */
	public String selectMonth()
	{	
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		ParameterUtils.prepareParameters(holdTool);
		logger.info(yearSel);
		logger.info(monthSel);
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "aPage");
		info=queryService.listQueryResult(
				billService.showApplyBillQuery(isFromMenu, conId, itemId, customerName, customer, minRealTaxBillAmount, maxRealTaxBillAmount, minRealBillAmount, maxRealBillAmount, monthSel, yearSel,minConAmount,maxConAmount,startDate,endDate)[0],
				billService.showApplyBillQuery(isFromMenu, conId, itemId, customerName, customer, minRealTaxBillAmount, maxRealTaxBillAmount, minRealBillAmount, maxRealBillAmount, monthSel, yearSel,minConAmount,maxConAmount,startDate,endDate)[1], info);
		return SUCCESS;
	}
	
	
	/**
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
			if(billService.judgementBill(monthlyBillproSids)){
				billList = service.list(billService.showSelectApplyBillQuery(monthlyBillproSids));
				stockOrgList = this.typeManageService.getYXTypeManage(1021L);
				return "showApply";
			}
			else{
				logger.info(billService.showSelectRealReceipt(monthlyBillproSids));
				return showRecitpQuery();
			}
		}
		return "showApply";
	}
	/**
	 * 跳到关联收据页面
	 * @return
	 */
	public String showRecitpQuery() {
		billList = service.list(billService.showSelectRealReceipt(monthlyBillproSids));
		String[] billNature=new String[billList.size()];
		Long[] conId = new Long[billList.size()];
		for(int i=0;i<billList.size();i++){
			Object[] realPlan=(Object[])billList.get(i);
			billNature[i]=((RealContractBillandRecePlan)realPlan[0]).getBillNature();
			conId[i]=((RealContractBillandRecePlan)realPlan[0]).getConMainInfoSid();
		}
		if(billNature != null){
			receiptList = service.list(billService.showRecitpQuery(billNature, conId));
		}
		return "showRelation";
	}
	
	/**
	 * 判断合并开票是否可以合并开票
	 * @return
	 */
	public String togetherBill()
	{
		logger.info("弹出选中的开票申请");
		if (monthlyBillproSids != null) {
			if(billService.judgementBill(monthlyBillproSids)){
				logger.info(StringUtils.join(monthlyBillproSids,","));
				billList = service.list(billService.showSelectApplyBillQuery(monthlyBillproSids));
				stockOrgList = this.typeManageService.getYXTypeManage(1021L);
				String[] con=new String[billList.size()];
				String[] billNature=new String[billList.size()];
				String[] billType=new String[billList.size()];
				
				int conFlag = 0;
				int natureFlag = 0;
				int typeFlag = 0;
				for(int i=0;i<billList.size();i++){
					Object[] conMain=(Object[])billList.get(i);
					con[i]=((ContractMainInfo)conMain[2]).getConId();
					billNature[i]=((RealContractBillandRecePlan)conMain[0]).getBillNature();
					billType[i]=((RealContractBillandRecePlan)conMain[0]).getBillType();
					logger.info("合同主题信息的合同号:++"+((ContractMainInfo)conMain[2]).getConId());
				}
				//验证合同是否相同
				for(int j=0;j<con.length;j++)
				{
					String temp=con[j];
					for(int o=0;o<con.length;o++)
					{
						if(con != null){
							if(!temp.equals(con[o])){
								conFlag=conFlag+1;
								break;
							}
						}
						else{
							succSave = "3";
							ActionContext.getContext().getSession().put("succSave", succSave);
							return SUCCESS;
						}
					}
				}
				//验证开票性质是否相同
				/*for(int j=0;j<billNature.length;j++)
				{
					String temp=billNature[j];
					for(int o=0;o<billNature.length;o++)
					{
						if(con != null){
							if(!temp.equals(billNature[o])){
								natureFlag=natureFlag+1;
								break;
							}
						}
						else{
							succSave = "3";
							ActionContext.getContext().getSession().put("succSave", succSave);
							return SUCCESS;
						}
						
					}
				}*/
				// 验证开票类型是否相同
				for(int j=0;j<billType.length;j++)
				{
					String temp=billType[j];
					for(int o=0;o<billType.length;o++)
					{
						if(billType != null){
							if(!temp.equals(billType[o])){
								typeFlag=typeFlag+1;
								break;
							}
						}
						else{
							succSave = "3";
							ActionContext.getContext().getSession().put("succSave", succSave);
							return SUCCESS;
						}
					}
				}
				if(conFlag == 0 && natureFlag == 0 && typeFlag == 0){
					logger.info("全部相等");
					return "togetherBill";
				}
				
				else if(conFlag != 0){
					logger.info("合同不相等!");
					succSave = "1";
					ActionContext.getContext().getSession().put("succSave", succSave);
					return "updateSeccess";
				}
				else if(natureFlag != 0){
					logger.info("开票性质不相等!");
					succSave = "4";
					ActionContext.getContext().getSession().put("succSave", succSave);
					return "updateSeccess";
				}
				else if(typeFlag != 0){
					logger.info("开票类型不相等!");
					succSave = "5";
					ActionContext.getContext().getSession().put("succSave", succSave);
					return "updateSeccess";
				}
				else{
					succSave = "1";
					ActionContext.getContext().getSession().put("succSave", succSave);
					return "updateSeccess";
				}
			}
			else{
				return showRecitpQuery();
			}
		}
		else {
			logger.info("monthlyBillproSids是空的");
			return "togetherBill";
		}
	}
	/**
	 * 合并计划
	 * @return
	 */
	public String mergePlan(){
		if(billService.isSameReal(monthlyBillproSids)){
			billService.mergeRealPlan(monthlyBillproSids);
			succSave = "7";
			ActionContext.getContext().getSession().put("succSave", succSave);
			return "merge";
		}
		else{
			succSave = "6";
			ActionContext.getContext().getSession().put("succSave", succSave);
			return "updateSeccess";
		}
	}
	/**
	 * 
	 * 显示查询页面
	 * @return
	 */
	public String showQuery(){
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put((defaultYear+1)+"",(defaultYear+1)+"");
		yearMap.put((defaultYear+2)+"",(defaultYear+2)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
		return "showQuery";
	}
	/**
	 * 显示主main页面
	 * @return
	 */
	public String showMain(){
		
		return "showMain";
	}
	/**
	 * 显示合同号一样的
	 * @return
	 */
	public String showCon()
	{
		conStageList = service.list(billService.showTheSameConId(conId));
		return SUCCESS;
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
	public String getSuccSave() {
		return succSave;
	}
	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
	public String getConId() {
		return conId;
	}
	public void setConId(String conId) {
		this.conId = conId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public boolean isFromMenu() {
		return isFromMenu;
	}
	public void setFromMenu(boolean isFromMenu) {
		this.isFromMenu = isFromMenu;
	}
	public List<YXTypeManage> getStockOrgList() {
		return stockOrgList;
	}
	public void setStockOrgList(List<YXTypeManage> stockOrgList) {
		this.stockOrgList = stockOrgList;
	}
	public List<Object> getReceiptList() {
		return receiptList;
	}
	public void setReceiptList(List<Object> receiptList) {
		this.receiptList = receiptList;
	}
	public IApplyBillService getBillService() {
		return billService;
	}
	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
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
	public Double getMinRealTaxBillAmount() {
		return minRealTaxBillAmount;
	}
	public void setMinRealTaxBillAmount(Double minRealTaxBillAmount) {
		this.minRealTaxBillAmount = minRealTaxBillAmount;
	}
	public Double getMaxRealTaxBillAmount() {
		return maxRealTaxBillAmount;
	}
	public void setMaxRealTaxBillAmount(Double maxRealTaxBillAmount) {
		this.maxRealTaxBillAmount = maxRealTaxBillAmount;
	}
	public Double getMinRealBillAmount() {
		return minRealBillAmount;
	}
	public void setMinRealBillAmount(Double minRealBillAmount) {
		this.minRealBillAmount = minRealBillAmount;
	}
	public Double getMaxRealBillAmount() {
		return maxRealBillAmount;
	}
	public void setMaxRealBillAmount(Double maxRealBillAmount) {
		this.maxRealBillAmount = maxRealBillAmount;
	}
	public Double getMinConAmount() {
		return minConAmount;
	}
	public void setMinConAmount(Double minConAmount) {
		this.minConAmount = minConAmount;
	}
	public Double getMaxConAmount() {
		return maxConAmount;
	}
	public void setMaxConAmount(Double maxConAmount) {
		this.maxConAmount = maxConAmount;
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

}
