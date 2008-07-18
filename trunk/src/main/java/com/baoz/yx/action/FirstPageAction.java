package com.baoz.yx.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.Gonggao;
import com.baoz.yx.entity.Notice;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.utils.UserUtils;

/**
 *	首页显示
 *  
 */
@Results({
	@Result(name = "showReportList", value = "/WEB-INF/jsp/message.jsp"),
	@Result(name = "showNoticeList", value = "/WEB-INF/jsp/notice.jsp")
	}
)
public class FirstPageAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	@Autowired
	@Qualifier("noticeService")
	private INoticeService 		noticeService;
	private PageInfo 			info;
	private List<Gonggao>  reportList;
	private List<Notice>  noticeList;
	
	
	private	Date lastSellBeforeUpdateDate;      //售前合同显示
	private Integer subSellBeforeDate;          //售前合同显示
	
	private Long countContractItem;             //无项目号合同的笔数
	
	private Long countApplyMessage;             //未签合同申购笔数
	
	private Long countContractMain;             //合同建议关闭
	
	private Long countApplyBill;                //未签合同开票
	
	private Long countBillandRecePlan1;         //本月开票显示:计划开票
	private Long countBillandRecePlan2;         //本月开票显示:已经开票
	private Long countBillandRecePlan3;         //本月开票显示:部分开票
	private Long countBillandRecePlan4;         //本月开票显示:未开票
	
	private Long countRecePlan1;         //本月收款显示:计划收款
	private Long countRecePlan2;         //本月收款显示:已经收款
	private Long countRecePlan3;         //本月收款显示:部分收款
	private Long countRecePlan4;         //本月收款显示:未收款
	
	private Long countBillPlan;         //开票显示
	private Long countRecePlan;         //收款显示
	private Long countBillandRecePlan;         //已开票未收款显示
	
	private Long countReceipt;          //收据显示

	public Long getCountApplyBill() {
		return countApplyBill;
	}

	public void setCountApplyBill(Long countApplyBill) {
		this.countApplyBill = countApplyBill;
	}

	public Long getCountContractMain() {
		return countContractMain;
	}

	public void setCountContractMain(Long countContractMain) {
		this.countContractMain = countContractMain;
	}

	public Long getCountApplyMessage() {
		return countApplyMessage;
	}

	public void setCountApplyMessage(Long countApplyMessage) {
		this.countApplyMessage = countApplyMessage;
	}

	public Long getCountContractItem() {
		return countContractItem;
	}

	public void setCountContractItem(Long countContractItem) {
		this.countContractItem = countContractItem;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getNotice() throws Exception {
		this.logger.info("公告显示");
		reportList=commonService.list("select g.content,to_char(g.updateBy,'YYYY-MM-DD') from Gonggao g where g.is_active=1 and (g.updateBy + g.gdays) > to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd') order by g.updateBy desc");
		showSellbefore();
		showRealContractBillPlan();
		showRealContractRecePlan();
		showRealContractBillandRecePlan();
		showContractItemMaininfo();
		showReceipt();
		showApplyMessage();
		showContractMainInfo();
		showApplyBill();
	    return "showReportList";
	}
	
	public String getMessage() throws Exception {
		this.logger.info("通知显示");
		Employee user=UserUtils.getUser(); 
	    noticeList=noticeService.getValidNoticeList(user.getId());
	    return "showNoticeList";
	}
	
	private void showSellbefore() throws Exception {
		this.logger.info("售前合同显示");
		Employee user=UserUtils.getUser();
		lastSellBeforeUpdateDate =(Date)commonService.uniqueResult("select max(c.updateBy) from ContractBeforeSell c where c.byId = ? ", user.getId());
		if(lastSellBeforeUpdateDate!=null){
			subSellBeforeDate = DateUtil.getDisDays(new Date(), lastSellBeforeUpdateDate);
		}		
	}
	
	private void showRealContractBillPlan() throws Exception{
		this.logger.info("本月开票显示");
		Employee user=UserUtils.getUser();
		countBillandRecePlan1 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) " +
				"from RealContractBillandRecePlan p,ContractMainInfo m " +
				"where p.conMainInfoSid=m.conMainInfoSid and p.realPredBillDate>=trunc(sysdate,'month') " +
				"and p.realPredBillDate<(LAST_DAY(SYSDATE)+1) and m.saleMan = ? ", user.getId());
		countBillandRecePlan3 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m where p.conMainInfoSid=m.conMainInfoSid and " +
				"p.realPredBillDate>=trunc(sysdate,'month') and p.realPredBillDate<(LAST_DAY(SYSDATE)+1) " +
				"and m.saleMan = ? and p.realBillAmount>(select sum(i.invoiceAmount) from " +
				"BillandProRelaion r,ApplyBill a,InvoiceInfo i where r.applyBill=a.billApplyId and " +
				"i.applyInvoiceId=a.billApplyId and r.realContractBillandRecePlan=p.realConBillproSid)", user.getId());
		countBillandRecePlan2 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m where p.conMainInfoSid=m.conMainInfoSid and " +
				"p.realPredBillDate>=trunc(sysdate,'month') and p.realPredBillDate<(LAST_DAY(SYSDATE)+1) " +
				"and m.saleMan = ? and p.realBillAmount=(select sum(i.invoiceAmount) from " +
				"BillandProRelaion r,ApplyBill a,InvoiceInfo i where r.applyBill=a.billApplyId and " +
				"i.applyInvoiceId=a.billApplyId and r.realContractBillandRecePlan=p.realConBillproSid)", user.getId());
		countBillandRecePlan4=countBillandRecePlan1-countBillandRecePlan3-countBillandRecePlan2;
	}
	
	private void showRealContractRecePlan() throws Exception{
		this.logger.info("本月收款显示");
		Employee user=UserUtils.getUser();
		countRecePlan1 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) " +
				"from RealContractBillandRecePlan p,ContractMainInfo m " +
				"where p.conMainInfoSid=m.conMainInfoSid and p.realPredReceDate>=trunc(sysdate,'month') " +
				"and p.realPredReceDate<(LAST_DAY(SYSDATE)+1) and m.saleMan = ? ", user.getId());
		countRecePlan3 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m where p.conMainInfoSid=m.conMainInfoSid and " +
				"p.realPredReceDate>=trunc(sysdate,'month') and p.realPredReceDate<(LAST_DAY(SYSDATE)+1) " +
				"and m.saleMan = ? and p.realBillAmount>(select sum(o.amount) from " +
				"BillandProRelaion r,ApplyBill a,InvoiceInfo i,ReveInfo o where r.applyBill=a.billApplyId and " +
				"i.applyInvoiceId=a.billApplyId and r.realContractBillandRecePlan=p.realConBillproSid and o.billSid=i.id)", user.getId());
		countRecePlan2 = (Long)commonService.uniqueResult("select count(distinct p.realConBillproSid) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m where p.conMainInfoSid=m.conMainInfoSid and " +
				"p.realPredReceDate>=trunc(sysdate,'month') and p.realPredReceDate<(LAST_DAY(SYSDATE)+1) " +
				"and m.saleMan = ? and p.realBillAmount=(select sum(o.amount) from " +
				"BillandProRelaion r,ApplyBill a,InvoiceInfo i,ReveInfo o where r.applyBill=a.billApplyId and " +
				"i.applyInvoiceId=a.billApplyId and r.realContractBillandRecePlan=p.realConBillproSid and o.billSid=i.id)", user.getId());
		countRecePlan4=countRecePlan1-countRecePlan2-countRecePlan3;
	}
	
	private void showRealContractBillandRecePlan() throws Exception{
		this.logger.info("已开票未到款显示");
		Employee user=UserUtils.getUser();
		countBillPlan = (Long)commonService.uniqueResult("select count(distinct i.id) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m,BillandProRelaion r,ApplyBill a,InvoiceInfo i " +
				"where p.conMainInfoSid=m.conMainInfoSid and r.applyBill=a.billApplyId and i.applyInvoiceId=a.billApplyId " +
				"and r.realContractBillandRecePlan=p.realConBillproSid and i.type!=4 and m.saleMan = ? ", user.getId());
		countRecePlan= (Long)commonService.uniqueResult("select count(distinct o.id) from ReveInfo o," +
				"RealContractBillandRecePlan p,ContractMainInfo m,BillandProRelaion r,ApplyBill a,InvoiceInfo i " +
				"where p.conMainInfoSid=m.conMainInfoSid and r.applyBill=a.billApplyId and i.applyInvoiceId=a.billApplyId " +
				"and r.realContractBillandRecePlan=p.realConBillproSid and i.type!=4 and o.billSid=i.id and m.saleMan = ? ", user.getId());
		countBillandRecePlan=countBillPlan-countRecePlan;	
	}
	
	private void showContractItemMaininfo() throws Exception{
		this.logger.info("无项目号合同的笔数");
		Employee user=UserUtils.getUser();
		countContractItem =(Long)commonService.uniqueResult("select count(distinct i.contractMainInfo)" +
				" from ContractMainInfo m , ContractItemMaininfo i " +
				" where i.conItemId is null and m.conState = 4 " +
				" and i.contractMainInfo = m.conMainInfoSid and " +
				" m.opPeople = ? ", user.getId());
	}
	
	private void showReceipt() throws Exception{
		this.logger.info("您有收据数");
		Employee user=UserUtils.getUser();
		countReceipt = (Long)commonService.uniqueResult("select count(distinct i.id) from " +
				"RealContractBillandRecePlan p,ContractMainInfo m,BillandProRelaion r,ApplyBill a,InvoiceInfo i " +
				"where p.conMainInfoSid=m.conMainInfoSid and r.applyBill=a.billApplyId and i.applyInvoiceId=a.billApplyId " +
				"and r.realContractBillandRecePlan=p.realConBillproSid and i.type=4 and m.saleMan = ? ", user.getId());
	}
	
	private void showApplyMessage() throws Exception{
		this.logger.info("未签合同申购笔数");
		Employee user=UserUtils.getUser();
		countApplyMessage=(Long)commonService.uniqueResult("select count(*) from ApplyMessage a where a.mainId is null and a.sellmanId = ? ", user.getId());
	}
	
	private void showContractMainInfo() throws Exception{
		this.logger.info("合同建议关闭");
		Employee user=UserUtils.getUser();
		countContractMain = (Long)commonService.uniqueResult("select count(*) from ContractMainInfo m where m.conState = 8 and m.opPeople = ? ", user.getId());
	}
	
	private void showApplyBill() throws Exception{
		this.logger.info("未签合同开票");   //这样搜索只是搜索到了“未签申请”，但没有搜索到票
		Employee user=UserUtils.getUser();
		countApplyBill = (Long)commonService.uniqueResult("select count(*) from ApplyBill a where a.isNoContract = 1 and a.employeeId = ? ", user.getId());
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

	public List<Gonggao> getReportList() {
		return reportList;
	}

	public void setReportList(List<Gonggao> reportList) {
		this.reportList = reportList;
	}

	public List<Notice> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<Notice> noticeList) {
		this.noticeList = noticeList;
	}

	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public Date getLastSellBeforeUpdateDate() {
		return lastSellBeforeUpdateDate;
	}

	public void setLastSellBeforeUpdateDate(Date lastSellBeforeUpdateDate) {
		this.lastSellBeforeUpdateDate = lastSellBeforeUpdateDate;
	}

	public Integer getSubSellBeforeDate() {
		return subSellBeforeDate;
	}

	public void setSubSellBeforeDate(Integer subSellBeforeDate) {
		this.subSellBeforeDate = subSellBeforeDate;
	}

	public Long getCountBillandRecePlan1() {
		return countBillandRecePlan1;
	}

	public void setCountBillandRecePlan1(Long countBillandRecePlan1) {
		this.countBillandRecePlan1 = countBillandRecePlan1;
	}

	public Long getCountBillandRecePlan2() {
		return countBillandRecePlan2;
	}

	public void setCountBillandRecePlan2(Long countBillandRecePlan2) {
		this.countBillandRecePlan2 = countBillandRecePlan2;
	}

	public Long getCountBillandRecePlan3() {
		return countBillandRecePlan3;
	}

	public void setCountBillandRecePlan3(Long countBillandRecePlan3) {
		this.countBillandRecePlan3 = countBillandRecePlan3;
	}

	public Long getCountBillandRecePlan4() {
		return countBillandRecePlan4;
	}

	public void setCountBillandRecePlan4(Long countBillandRecePlan4) {
		this.countBillandRecePlan4 = countBillandRecePlan4;
	}

	public Long getCountRecePlan1() {
		return countRecePlan1;
	}

	public void setCountRecePlan1(Long countRecePlan1) {
		this.countRecePlan1 = countRecePlan1;
	}

	public Long getCountRecePlan2() {
		return countRecePlan2;
	}

	public void setCountRecePlan2(Long countRecePlan2) {
		this.countRecePlan2 = countRecePlan2;
	}

	public Long getCountRecePlan3() {
		return countRecePlan3;
	}

	public void setCountRecePlan3(Long countRecePlan3) {
		this.countRecePlan3 = countRecePlan3;
	}

	public Long getCountRecePlan4() {
		return countRecePlan4;
	}

	public void setCountRecePlan4(Long countRecePlan4) {
		this.countRecePlan4 = countRecePlan4;
	}

	public Long getCountBillPlan() {
		return countBillPlan;
	}

	public void setCountBillPlan(Long countBillPlan) {
		this.countBillPlan = countBillPlan;
	}

	public Long getCountRecePlan() {
		return countRecePlan;
	}

	public void setCountRecePlan(Long countRecePlan) {
		this.countRecePlan = countRecePlan;
	}

	public Long getCountBillandRecePlan() {
		return countBillandRecePlan;
	}

	public void setCountBillandRecePlan(Long countBillandRecePlan) {
		this.countBillandRecePlan = countBillandRecePlan;
	}

	public Long getCountReceipt() {
		return countReceipt;
	}

	public void setCountReceipt(Long countReceipt) {
		this.countReceipt = countReceipt;
	}


}

