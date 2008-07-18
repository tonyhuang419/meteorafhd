package com.baoz.yx.action.billtoReceipt;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.utils.UserUtils;
/**
 *	开票收据管理
 *
 *  
 */
@Results( {	
	@Result(name = "success", type = ServletRedirectResult.class, value = "/billtoReceipt/billReceiptQuery.action"),
	@Result(name = "billreceipt", value = "/WEB-INF/jsp/billtoReceipt/billReceiptManager.jsp"),
	@Result(name = "udpates", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp"),
	@Result(name = "showUpdate", value = "/WEB-INF/jsp/billtoReceipt/updateAmount.jsp")
	})

public class BillReceiptAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	private PageInfo 			info;
	private ServletRequest		request;
	private InvoiceInfo         ii;
	private Long 				billId;
	private Long				amountId;
	private Long 				iiId;
	private List<ApplyBill>		billList;
	private List<InvoiceInfo>	invoiceList;
	private Double				invoiceAmount;
	private String				invoiceNo;
	private Date				invoiceDate;
	private Long 				invoiceID;
	private String				billType;
	private Long				mainConId;
	private Double increaceAmount=0.;//总开票金额减去当前开票金额
	private Double appAmount=0.;//申请金额
	@Override
	public String doDefault() throws Exception {
		this.logger.info("开票收据管理功能实现");
		return "billreceipt";
	}
	
	/**
	 * 新增发票申请金额
	 * 
	 * @return String
	 */
	
	public String saveMount() throws Exception
	{
		logger.info("新增发票申请金额");
		logger.info(billId);
		Long uid = new UserUtils().getUser().getId();
		ii.setById(uid);
		ii.setIs_active("1");
		ii.setUpdateBy(new Date());
		ii.setType(billType);
		ii.setContractMainSid(mainConId);
		ii.setApplyInvoiceId(billId);
		ii.setReceAmount(0D);
		service.save(ii);
		return SUCCESS;
	}
	
	/**
	 * 删除发票申请金额
	 * 
	 * @return String
	 */
	
	public String delMount()
	{
		logger.info("删除金额信息");
		service.executeUpdate("delete from InvoiceInfo ii where ii.id="+amountId);
		return SUCCESS;
	}
	
	/**
	 * 显示修改发票申请金额
	 * 
	 * @return String
	 */
	
	public String showUpdateMount()
	{   
		logger.info("进入修改金额页面");
		logger.info(iiId);
		logger.info(billId);
		billList=service.list("select bill from ApplyBill bill where bill.billApplyId=" + billId);
		invoiceList=service.list(" from InvoiceInfo ii where ii.id=" + iiId);
		List list=service.list("select sum(i.invoiceAmount) from InvoiceInfo i where i.applyInvoiceId="+billId);
		increaceAmount=(Double) list.iterator().next()-(((InvoiceInfo)invoiceList.iterator().next()).getInvoiceAmount());
		logger.info("increaceAmount================="+increaceAmount);
		appAmount=((ApplyBill)(billList.iterator().next())).getBillAmountTax();
		logger.info("appAmount================="+appAmount);
		return "showUpdate";
	}
	
	/**
	 * 修改发票申请金额
	 * 
	 * @return String
	 */
	
	public String updateMount()
	{
		logger.info("修改金额信息");
		InvoiceInfo updateInfo = (InvoiceInfo) service.load(InvoiceInfo.class, invoiceID);	
		Long uid = UserUtils.getUser().getId();
		updateInfo.setInvoiceAmount(invoiceAmount);
		updateInfo.setInvoiceNo(invoiceNo);
		updateInfo.setInvoiceDate(invoiceDate);
		updateInfo.setUpdateBy(new Date());
		updateInfo.setById(uid);
		service.update(updateInfo);
		return "udpates";
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


}

