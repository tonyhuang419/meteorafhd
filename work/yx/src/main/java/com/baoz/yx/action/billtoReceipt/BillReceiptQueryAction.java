package com.baoz.yx.action.billtoReceipt;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

/**
 *	开票收据管理
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/billReceiptManager.jsp"),
	@Result(name = "newSuccess", value = "/WEB-INF/jsp/billtoReceipt/newBillReceipt.jsp"),
	@Result(name = "newInsert", value = "/WEB-INF/jsp/billtoReceipt/insertBill.jsp"),
	@Result(name = "showQueryInput", value = "/WEB-INF/jsp/billtoReceipt/billReceiptQuery.jsp"),
	@Result(name = "showMain", value = "/WEB-INF/jsp/billtoReceipt/billReceiptMain.jsp"),
	@Result(name = "showselect", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp")})

public class BillReceiptQueryAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;								
	
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 		billService;	
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	
	private PageInfo 			info;
	private ServletRequest		request;
	private List<InvoiceInfo> 	invoiceList;
	private List<ApplyBill>		billList;
	private Long				selectState;
	private Long 				confirm;
	private Long 				billId;
	private Double 				balance;
	private Double 				totalAmount;
	private String 				state;
	private String 				defaultDate;
	private List<Department>			groupList;	
	private List<Object> 			listExp; 	
	private String 						contractNo;   			//合同号
	private String 						itemNo;				//项目号
	private String 						invoiceNo;				//发票号
	private String						groupId;		//组别
	private Long 						saleMan;		//销售员
	private Map<Long,List<String>> itemNoList;   //开票申请系统号，项目号
	private Double						billAmountTax;   //开票金额
	private String 						hasSigned; 					//已签收
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"billReceiptQueryParameters",this,new String[]{"confirm","selectState","groupId","saleMan","contractNo","itemNo","billAmountTax","invoiceNo","hasSigned"}); 
	@Override
	public String doDefault() throws Exception {
		this.logger.info("开票收据管理");
		ParameterUtils.prepareParameters(holdTool);
		logger.info(confirm);
		logger.info(selectState);
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "billReceiptQueryPage");
		info = queryService.listQueryResult(SqlUtils.getCountSql(billService.showBillReceiptQuery(confirm, selectState)),billService.showBillReceiptQuery(confirm, selectState),info);
		invoiceList=service.list(" from InvoiceInfo invoice");
		logger.info("=================info="+info);
		return SUCCESS;
	}
	/**
	 * 开票收据管理主页面
	 * @return
	 */
	public String showMain(){
		return "showMain";
	}
	/**
	 * 卡票收据管理查询页面
	 * @return
	 */
	public String showQueryInput(){
		confirm = 2L;
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = null;
		return "showQueryInput";
	}
	
	/**
	 * 显示新做的开票收据管理页面
	 * @return
	 */
	public String newDefault(){
		this.logger.info("开票收据管理");
		if(confirm == null){
			confirm = 2L;
		}
		if(confirm == -1){
			confirm = null;
		}
		ParameterUtils.prepareParameters(holdTool);
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "billReceiptQueryPage");
		info = queryService.listQueryResult(
				SqlUtils.getCountSql(billService.showBillReceiptQuery(confirm, groupId, saleMan, contractNo, itemNo, billAmountTax,invoiceNo,hasSigned )),
				billService.showBillReceiptQuery(confirm, groupId, saleMan, contractNo, itemNo, billAmountTax,invoiceNo,hasSigned),info);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			//proDateMap = invoiceService.getProDateFromApplyBill(applyBillList);
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "newSuccess";
	}
//	/**
//	 * 显示录入&修改页面操作
//	 * @return
//	 */
//	public String showInsertOrUpdate(){
//		logger.info(billId);
//		invoiceList= service.list(" from InvoiceInfo ii where ii.applyInvoiceId = " + billId + " order by ii.invoiceNo");
//		// 剩余余额
//		balance = billService.balanceInvoiceAmount(billId);
//		totalAmount = (Double)service.uniqueResult(" select bill.billAmountTax from ApplyBill bill where bill.billApplyId = "+billId);
//		defaultDate = NumberToTime.getDateFormat(new Date(), "yyyy-MM-dd");
//		logger.info(defaultDate);
//		return "newInsert";
//	}
	
	public  String selectState()
	{
		logger.info("根据选择");
		return SUCCESS;
	}
	/**
	 * 显示登陆人录入过的发票信息
	 * @return
	 */
	public String showAdmit(){
		billList = service.list(billService.showNoInputState());
		invoiceList=billService.findInvoiceInputState(UserUtils.getUser().getId());
		return SUCCESS;
	}
	/**
	 * 判断当前登陆人是否有过录入信息
	 */
	public void checkInputState(){
		invoiceList=billService.findInvoiceInputState(UserUtils.getUser().getId());
		if(invoiceList.size()==0){
			this.renderText("1");
		}
		else{
			this.renderText("0");
		}
	}
	
	
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<InvoiceInfo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceInfo> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public Long getSelectState() {
		return selectState;
	}

	public void setSelectState(Long selectState) {
		this.selectState = selectState;
	}

	public Long getConfirm() {
		return confirm;
	}

	public void setConfirm(Long confirm) {
		this.confirm = confirm;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<ApplyBill> getBillList() {
		return billList;
	}
	public void setBillList(List<ApplyBill> billList) {
		this.billList = billList;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
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
	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}
	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}
	public Double getBillAmountTax() {
		return billAmountTax;
	}
	public void setBillAmountTax(Double billAmountTax) {
		this.billAmountTax = billAmountTax;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getHasSigned() {
		return hasSigned;
	}
	public void setHasSigned(String hasSigned) {
		this.hasSigned = hasSigned;
	}





}

