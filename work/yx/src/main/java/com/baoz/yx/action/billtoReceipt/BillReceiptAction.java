package com.baoz.yx.action.billtoReceipt;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
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
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
/**
 *	开票收据管理
 *
 *  
 */
@Results( {	
	@Result(name = "success", type = ServletRedirectResult.class, value = "/billtoReceipt/billReceiptQuery.action"),
	@Result(name = "showSuccess", type = ServletRedirectResult.class, value = "/billtoReceipt/billReceiptQuery.action?method=showAdmit"),
//	@Result(name = "newSuccess", type = ServletRedirectResult.class, value = "/billtoReceipt/billReceiptQuery.action?method=showInsertOrUpdate&billId=${billId}"),
	@Result(name = "newInsert", value = "/WEB-INF/jsp/billtoReceipt/insertBill.jsp"),
	@Result(name = "billreceipt", value = "/WEB-INF/jsp/billtoReceipt/billReceiptManager.jsp"),
	@Result(name = "udpates", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp"),
	@Result(name = "confirmInput", value = "/WEB-INF/jsp/billtoReceipt/billReceiptManager.jsp"),
	@Result(name = "updateInvoiceNo", value = "/WEB-INF/jsp/billtoReceipt/updateInvoiceNo.jsp"),
	@Result(name = "showUpdate", value = "/WEB-INF/jsp/billtoReceipt/updateAmount.jsp")
	})

public class BillReceiptAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 		billService;
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	private PageInfo 			info;
	private ServletRequest		request;
	private InvoiceInfo         ii;
	private Long 				billId;			//开票申请系统号
	private Long				amountId;    //发票、收据系统号
	private Long 				iiId;
	private List<ApplyBill>		billList;
	private List<InvoiceInfo>	invoiceList;
	private Double				invoiceAmount;
	private String				invoiceNo;
	private Date				invoiceDate;
	private Long 				invoiceID;
	private String				billType;
	private Long				mainConId;
	private Double 			increaceAmount=0.;//总开票金额减去当前开票金额
	private Double 			appAmount=0.;//申请金额
	private Double 				balance;
	private Double 				totalAmount;
	private String 					defaultDate;
	private String 					isTrue;
	private  ProcessResult 	processResult;
	private StringBuffer errorInfo=new StringBuffer();
	
	public StringBuffer getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(StringBuffer errorInfo) {
		this.errorInfo = errorInfo;
	}

	@Override
	public String doDefault() throws Exception {
		this.logger.info("开票收据管理功能实现");
		return "billreceipt";
	}
	
	/**
	 * 
	 * 新增发票申请金额       new
	 * @return
	 */
	public String newSaveMount()
	{
		errorInfo.delete(0, errorInfo.length());
		logger.info("新增发票申请金额");
		ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, billId);
		logger.info(billId);
		String no=contractCommonService.checkInvoiceNoIsExists(ii.getInvoiceNo());
		if(no!=null){
			errorInfo.append(no+"在系统中已存在");
			return showInsertOrUpdate();
		}
		saveAmountPublic(bill);
		errorInfo.append("1");
		return showInsertOrUpdate();
	}
	
	/**
	 * 显示录入&修改页面操作
	 * @return
	 */
	public String showInsertOrUpdate(){
		logger.info(billId);
		invoiceList= service.list(" from InvoiceInfo ii where ii.applyInvoiceId = " + billId + " order by ii.invoiceNo");
		// 剩余余额
		balance = billService.balanceInvoiceAmount(billId);
		totalAmount = (Double)service.uniqueResult(" select bill.billAmountTax from ApplyBill bill where bill.billApplyId = "+billId);
		defaultDate = NumberToTime.getDateFormat(new Date(), "yyyy-MM-dd");
		logger.info(defaultDate);
		return "newInsert";
	}
	
	private void saveAmountPublic(ApplyBill bill) {
		Long uid = UserUtils.getUser().getId();
		ii.setById(uid);
		ii.setIs_active("1");
		ii.setUpdateBy(new Date());
		ii.setType(bill.getBillType());
		ii.setContractMainSid(bill.getContractMainInfo());
		ii.setApplyInvoiceId(bill.getBillApplyId());
		ii.setReceAmount(0D);
		ii.setInputState("0");
		ii.setRecordDate(new Date());
		service.save(ii);
		contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
		contractservice.itemIsCloseByApplyId(billId);
	}
	/**
	 * 新增联票,根据剩余金额开票,以100W为单位,大于100W分成100W一次.发票号自动 + 1 !  日期以当天为开票日期!
	 * @return
	 */
	public String addRelationBill(){
		errorInfo.delete(0, errorInfo.length());
		Long uid = new UserUtils().getUser().getId();
		ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, billId);
		String no=contractCommonService.checkInvoiceNoIsExists(ii.getInvoiceNo());
		if(no!=null){
			errorInfo.append(no+"在系统中已存在");
			return showInsertOrUpdate();
		}
		saveAmountPublic(bill);
		// 总金额
		Double totalAmount = bill.getBillAmountTax();
		logger.info("总金额"+totalAmount);
		// 已经开票的金额
		Double balance = billService.balanceInvoiceAmount(billId);
		logger.info("已经开票的金额:"+balance);
		Double remaindAmount = totalAmount - balance;
		logger.info("还可以开票:"+remaindAmount);
		logger.info("刚刚添加的发票号是:"+ii.getInvoiceNo());
		logger.info("刚刚添加的发票金额是:"+ii.getInvoiceAmount());
		ii.setId(null);
		int i = Integer.parseInt(ii.getInvoiceNo());
		while(remaindAmount > 0){
			if(remaindAmount > ii.getInvoiceAmount()){
				ii.setInvoiceAmount(ii.getInvoiceAmount());
			}else{
				ii.setInvoiceAmount(remaindAmount);
			}
			ii.setInvoiceNo(StringUtils.leftPad((++i)+"", ii.getInvoiceNo().length(),"0"));
			String no1=contractCommonService.checkInvoiceNoIsExists(ii.getInvoiceNo());
			if(no1!=null){
				errorInfo.append(no1+"在系统中已存在");
				break;
			}
			service.save(ii);
			remaindAmount = remaindAmount - ii.getInvoiceAmount();
		}
		contractCommonService.calBillInvoiceAmount(bill.getBillApplyId());
		contractservice.itemIsCloseByApplyId(billId);
		errorInfo.append("1");
		return showInsertOrUpdate();
	}
	/**
	 * 删除发票申请金额
	 * @return
	 */
	
	public String delMount()
	{
		logger.info("删除金额信息");
		InvoiceInfo updateInfo = (InvoiceInfo) service.load(InvoiceInfo.class, amountId);
		systemService.deleteAmount("InvoiceInfo",amountId);
		contractCommonService.calBillInvoiceAmount(updateInfo.getApplyInvoiceId());
		contractservice.itemIsCloseByApplyId(updateInfo.getApplyInvoiceId());
		errorInfo.append("1");
		return showAdmit();
	}
	/**
	 * 删除发票申请金额  new
	 * @return
	 */
	
	public String delNewAmount()
	{
		logger.info("删除金额信息");
		InvoiceInfo updateInfo = (InvoiceInfo) service.load(InvoiceInfo.class, amountId);
		systemService.deleteAmount("InvoiceInfo",amountId);
		contractCommonService.calBillInvoiceAmount(updateInfo.getApplyInvoiceId());
		contractservice.itemIsCloseByApplyId(updateInfo.getApplyInvoiceId());
		errorInfo.append("1");
		return showInsertOrUpdate();
	}
	
	/**
	 * 显示修改发票申请金额
	 * @return
	 */
	public String showUpdateMount()
	{   
		logger.info("进入修改金额页面");
		logger.info(iiId);
		logger.info(billId);
		billList=service.list(" select bill from ApplyBill bill where bill.billApplyId=" + billId);
		invoiceList=service.list(" from InvoiceInfo ii where ii.id=" + iiId);
		List list=service.list("select sum(i.invoiceAmount) from InvoiceInfo i where i.applyInvoiceId="+billId);
		increaceAmount=(Double) list.iterator().next()-(((InvoiceInfo)invoiceList.iterator().next()).getInvoiceAmount());
		logger.info("increaceAmount================="+increaceAmount);
		appAmount=((ApplyBill)(billList.iterator().next())).getBillAmountTax();
		logger.info("appAmount================="+appAmount);
		
		return "showUpdate";
	}
	/**
	 * 显示修改发票申请金额   new
	 * @return
	 */
	public String showNewUpdateMount()
	{   
		logger.info("进入修改金额页面");
		logger.info(amountId);
		logger.info(billId);
		billList=service.list("select bill from ApplyBill bill where bill.billApplyId=" + billId);
		invoiceList=service.list(" from InvoiceInfo ii where ii.id=" + amountId);
		List list=service.list("select sum(i.invoiceAmount) from InvoiceInfo i where i.applyInvoiceId="+billId);
		increaceAmount=(Double) list.iterator().next()-(((InvoiceInfo)invoiceList.iterator().next()).getInvoiceAmount());
		logger.info("increaceAmount================="+increaceAmount);
		appAmount=((ApplyBill)(billList.iterator().next())).getBillAmountTax();
		return "showUpdate";
	}
	/**
	 * 修改发票申请金额
	 * 
	 * @return String
	 */
	
	public String updateMount()
	{
		errorInfo.delete(0, errorInfo.length());
		logger.info("修改金额信息");
		InvoiceInfo updateInfo = (InvoiceInfo) service.load(InvoiceInfo.class, invoiceID);	
		Long uid = UserUtils.getUser().getId();
		updateInfo.setInvoiceAmount(invoiceAmount);
		updateInfo.setInvoiceNo(invoiceNo);
		updateInfo.setInvoiceDate(invoiceDate);
		updateInfo.setUpdateBy(new Date());
		updateInfo.setById(uid);
		updateInfo.setRecordDate(new Date());
		String no=contractCommonService.checkInvoiceNoIsExists(updateInfo.getInvoiceNo(),updateInfo.getId());
		if(no!=null){
			errorInfo.append(no+"在系统中已经存在");
			return showNewUpdateMount();
		}
		service.update(updateInfo);
		contractCommonService.calBillInvoiceAmount(updateInfo.getApplyInvoiceId());
		contractservice.itemIsCloseByApplyId(updateInfo.getApplyInvoiceId());
		errorInfo.append("1");
		return "udpates";
	}
	/**
	 * 确认开票   更新标志为:已确认
	 * @return
	 */
	public String updateInputState(){
		invoiceList=billService.findInvoiceInputState(UserUtils.getUser().getId());
		for (InvoiceInfo info : invoiceList) {
			info.setInputState("1");
			service.update(info);
			ApplyBill bill = (ApplyBill) service.load(ApplyBill.class, info.getApplyInvoiceId());
			//更新申请单状态为已开票
			bill.setApplyBillState(5L);
			service.update(bill);
			invoiceService.updatePlanApplyBillState(5L, bill.getBillApplyId());
			List<Long> realPlanIdList =  service.list(" select br.realContractBillandRecePlan from BillandProRelaion br where br.applyBill = "+bill.getBillApplyId());
			//未签的没有计划
			if(realPlanIdList != null){
				for (Long realPlanId : realPlanIdList) {
					contractCommonService.updatePlanStatistic(info.getInvoiceDate(), realPlanId, false);
				}
			}
		}
		return "udpates";
	}

	public  String showUpdateInvoiceNo(){
		ii = (InvoiceInfo) service.load(InvoiceInfo.class, amountId);
		return "updateInvoiceNo";
	}
	
	public  String updateInvoiceNo(){
		String no=contractCommonService.checkInvoiceNoIsExists(invoiceNo,amountId);
		if(no!=null){
			errorInfo.append(no+"在系统中已经存在");
			return showUpdateInvoiceNo();
		}
		else{
			InvoiceInfo invoice = (InvoiceInfo) service.load(InvoiceInfo.class, amountId);
			invoice.setInvoiceNo(invoiceNo);
			invoice.setInvoiceDate(invoiceDate);
			invoice.setUpdateBy(new Date());
			invoice.setById(UserUtils.getUser().getId());
			invoiceService.modifyInvoice(invoice);
//			service.update(invoice);
			errorInfo.append("1");
			return "udpates";
		}
	}
	
	private Double getDefaultIfNull(Number n){
		if(n != null){
			return n.doubleValue();
		}else{
			return 0.0;
		}
	}
	/**
	 * 显示登陆人录入过的发票信息
	 * @return
	 */
	public String showAdmit(){
		billList = service.list(billService.showNoInputState());
		invoiceList=billService.findInvoiceInputState(UserUtils.getUser().getId());
		return "confirmInput";
	}
	
	/**
	 * 删除已确认的发票
	 */
	public String delInvoice(  ){
		processResult =  billService.delHasSuredInvoice(amountId);
		return showInsertOrUpdate();
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

	public InvoiceInfo getIi() {
		return ii;
	}

	public void setIi(InvoiceInfo ii) {
		this.ii = ii;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getAmountId() {
		return amountId;
	}

	public void setAmountId(Long amountId) {
		this.amountId = amountId;
	}

	public Long getIiId() {
		return iiId;
	}

	public void setIiId(Long iiId) {
		this.iiId = iiId;
	}

	public List<ApplyBill> getBillList() {
		return billList;
	}

	public void setBillList(List<ApplyBill> billList) {
		this.billList = billList;
	}

	public List<InvoiceInfo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceInfo> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(Long invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Long getMainConId() {
		return mainConId;
	}

	public void setMainConId(Long mainConId) {
		this.mainConId = mainConId;
	}

	public Double getIncreaceAmount() {
		return increaceAmount;
	}

	public void setIncreaceAmount(Double increaceAmount) {
		this.increaceAmount = increaceAmount;
	}

	public Double getAppAmount() {
		return appAmount;
	}

	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public IApplyBillService getBillService() {
		return billService;
	}

	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
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

	public String getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}


}

