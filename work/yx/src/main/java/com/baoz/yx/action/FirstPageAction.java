package com.baoz.yx.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
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
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

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

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;


	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;



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
	private Long countBillandRecePlan2;         //本月开票显示:已申请 and 已开票 
//	private Long countBillandRecePlan3;         //本月开票显示:部分开票
//	private Long countBillandRecePlan4;         //本月开票显示:未开票
	private Long countBillandRecePlan5;         //本月开票显示:未开票已申请
	private Long countBillandRecePlan6;         //本月开票显示:未申请
	private Long countBillandRecePlan7;         //本月开票显示:月份修改 and 未申请

	private Long countRecePlan1;         //本月收款显示:计划收款
	private Long countRecePlan2;         //本月收款显示:已经收款
	private Long countRecePlan3;         //本月收款显示:部分收款
	private Long countRecePlan4;         //本月收款显示:未收款
	private Long countRecePlan5;         //本月收款显示:未收款，已修改

	private Long nextMouthBill;     //下月预计开票
	private Long nextMouthReve;		//下月预计收款

	private Double beforeYesterdatReve;  //前日到款
	private Double yesterdayReve;  //昨日到款
//	private Double todayReve;     //今日到款
	private Double thisMonthReve;   //本月到款

	private Long countBillPlan;         //开票显示
	private Long countRecePlan;         //收款显示

	private Long countOutplanBill; // 本月计划外开票
	private Long countOutplanRece; // 本月计划外收款

	private Long countReceiptNotNull;

	private Long itemSuggestClose; //项目建议关闭

	private Long newContractCount;  //新签合同
	private Long newContractCountIntegrated;  //新签合同   集成类
	private Long newContractCountItem;  //新签合同 项目类
	
	private Long applyWaitSureCount;        //申请待确认数
	private Long applyPassNoInvoiceCount;  //申请已处理未开票
	
	private Long conPreToFinalCount; //预决算转结算待确认数
	
	private Long noContractReve;     //未核销到款
	private Double noContractReveSum;     //未核销到款
	private Long vATInovoiceNoSign;   //增值税，已开、未签收
	private Long businessInovoiceNoSign;   //商业普票，已开、未签收
	
	private Long outSourceContractWaitSure;    //外协合同待确认
	private Long outSourcePayWaitSure;    //外协付款申请待确认

	private String yearSel;
	private String monthSel;
	//本月计划查询
	private String curMonthFirstDay;
	private String curMonthLastDay;
	//下月计划查询
	private String nextMonthFirstDay;
	private String nextMonthLastDay;

	@SuppressWarnings("unchecked")
	public String getNotice() throws Exception {
		this.logger.info("公告显示");
		reportList=commonService.list("select g.content,to_char(g.updateBy,'YYYY-MM-DD') from Gonggao g " +
				" where g.is_active=1 and (g.updateBy + g.gdays) > to_date('"+DateUtil.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd")+"','yyyy-MM-dd') " +
						" order by nvl(g.top,0) desc , g.updateBy desc");
		
		showSellbefore();
		showRealContractBillPlan();
		showRealContractRecePlan();
		showContractItemMaininfo();
		showApplyMessage();
		showApplyBill();
		showNextMouthBill();
		showNextMouthReve();
		countOutplanBillPlan();
		countOutplanRecePlan();
		showNewSignContract();
			
//		todayReve = firstPageService.todayReve();
		beforeYesterdatReve = firstPageService.beforeYesterdayReve();
		yesterdayReve = firstPageService.yesterdayReve();
		thisMonthReve = firstPageService.thisMonthReve();
		countContractMain = firstPageService.showContractSuggestClose();
		itemSuggestClose = firstPageService.showContractItemInfo();
		applyWaitSureCount = firstPageService.countApplyWaitSure();
		applyPassNoInvoiceCount = firstPageService.countApplyPassNoInvoice();
		noContractReve = firstPageService.noContractReve();
		noContractReveSum = firstPageService.noContractReveSum();
		vATInovoiceNoSign = firstPageService.countVATInvoiceNoSign("2");
		businessInovoiceNoSign = firstPageService.countVATInvoiceNoSign("3");
		conPreToFinalCount = firstPageService.countConPreToFinal();
		outSourceContractWaitSure =  firstPageService.countOutSourceContractWaitSure();
		outSourcePayWaitSure = firstPageService.countOutSourcePayWaitSure();
	
		//
		Date today = new Date();
		//获得本月第一天
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		curMonthFirstDay = DateUtil.format(nowMonth, "yyyy-M-d");
		//获得本月最后一天
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		curMonthLastDay = DateUtil.format(DateUtils.addDays(nextMonth, -1), "yyyy-M-d");
		//
		//获得下月第一天
		nextMonthFirstDay = DateUtil.format(nextMonth, "yyyy-M-d");
		//获得下月最后一天
		Date nextNextMonth = DateUtils.truncate(DateUtils.addMonths(nextMonth, 1), Calendar.MONTH);
		nextMonthLastDay = DateUtil.format(DateUtils.addDays(nextNextMonth, -1), "yyyy-M-d");
		//
		return "showReportList";
	}

	public String getMessage() throws Exception {
		this.logger.info("通知显示");
		Employee user=UserUtils.getUser(); 
		noticeList=noticeService.getValidNoticeList(user.getId());
		return "showNoticeList";
	}

	private void showSellbefore() throws Exception {
		Employee user=UserUtils.getUser();
		lastSellBeforeUpdateDate =(Date)commonService.uniqueResult("select max(c.updateBy) from ContractBeforeSell c  where c.is_active!='0' and c.byId = ? ", user.getId());
		if(lastSellBeforeUpdateDate!=null){
			subSellBeforeDate = DateUtil.getDisDays(new Date(), lastSellBeforeUpdateDate);
		}		
	}

	private void showRealContractBillPlan() throws Exception{
		countBillandRecePlan1 = firstPageService.countRealContractBillPlan(1);
		countBillandRecePlan2 = firstPageService.countRealContractBillPlan(2);
//		countBillandRecePlan4 = firstPageService.countRealContractBillPlan(3);
		countBillandRecePlan5 = firstPageService.countRealContractBillPlan(5);
		countBillandRecePlan6 = firstPageService.countRealContractBillPlan(6);
		countBillandRecePlan7 = firstPageService.countRealContractBillPlan(7);
	}


	private void showNextMouthBill(){    //下月开票计划
		Date today = new Date();
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		//获得下下月
		Date nextNextMonth = DateUtils.truncate(DateUtils.addMonths(nextMonth, 1), Calendar.MONTH);
		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else{
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		//本月所有开票计划
		StringBuilder allPlanSb = new StringBuilder();
		allPlanSb.append("select count(*) " +
				"from RealContractBillandRecePlan p,ContractMainInfo m " +
				"where p.conMainInfoSid=m.conMainInfoSid and p.realPredBillDate >= ? " +
		"and p.realPredBillDate < ? and ( p.billInvoiceAmount is null or p.billInvoiceAmount < p.realTaxBillAmount )  and p.realBillAmount > 0 ");
		if (expId != null ) {
			allPlanSb.append(" and m.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			allPlanSb.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = m.saleMan ) ");
		}
		nextMouthBill = (Long)commonService.uniqueResult(allPlanSb.toString(), nextMonth, nextNextMonth);
	}

	private void showNextMouthReve() throws Exception {  //下月收款计划
		Date today = new Date();
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		//获得下下月
		Date nextNextMonth = DateUtils.truncate(DateUtils.addMonths(nextMonth, 1), Calendar.MONTH);
		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else{
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		//本月所有收款计划
		StringBuilder allPlanSb = new StringBuilder();
		allPlanSb.append("select count(*) " +
				"from RealContractBillandRecePlan p,ContractMainInfo m " +
				"where p.conMainInfoSid=m.conMainInfoSid and p.realPredReceDate >= ? " +
		"and p.realPredReceDate < ? and ( p.realArriveAmount is null or p.realArriveAmount < p.realTaxReceAmount ) ");
		if (expId != null ) {
			allPlanSb.append(" and m.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			allPlanSb.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = m.saleMan ) ");
		}

		nextMouthReve = (Long)commonService.uniqueResult(allPlanSb.toString(), nextMonth, nextNextMonth);
	}


	private void showRealContractRecePlan() throws Exception{
		this.logger.info("本月收款显示");
		countRecePlan1 = firstPageService.countRealContractRecePlan(1);
		countRecePlan2 = firstPageService.countRealContractRecePlan(2);
		countRecePlan4 = firstPageService.countRealContractRecePlan(3);
		countRecePlan5 = firstPageService.countRealContractRecePlan(4);
	}

	private void countOutplanBillPlan(){
		countOutplanBill = firstPageService.countOutplanBill();
	}
	private void countOutplanRecePlan(){
		countOutplanRece = (Long)firstPageService.countOutplanRece();
	}	

	private void showContractItemMaininfo() throws Exception{
		this.logger.info("无项目号合同的笔数");
		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else{
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		StringBuilder hql= new StringBuilder();
		hql.append("select count(distinct i.contractMainInfo)" +
				" from ContractMainInfo m , ContractItemMaininfo i " +
		" where i.contractMainInfo = m.conMainInfoSid ");
		if (expId != null ) {
			hql.append(" and m.saleMan = "+expId+" ");
		}
		if (groupId != null && !"".equals(groupId)) {
			hql.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = m.saleMan ) ");
		}
		hql.append(" and m.conState>=4 and m.conState<=9 and i.conItemId is null ");
		countContractItem =(Long)commonService.uniqueResult(hql.toString());
	}

	private void showApplyMessage() throws Exception{
		this.logger.info("未签合同申购笔数");
		Employee user=UserUtils.getUser();
		countApplyMessage=(Long)commonService.uniqueResult("select count(*) from ApplyMessage a " +
				" where a.mainId is null and a.sellmanId = ?  and a.is_active = '1'", user.getId());
	}


	private void showNewSignContract() throws Exception{
		newContractCount = firstPageService.countNewCountContract(0);
		newContractCountIntegrated = firstPageService.countNewCountContract(2);
		newContractCountItem = firstPageService.countNewCountContract(1);
	}


	private void showApplyBill() throws Exception{
//		this.logger.info("未签合同开票");   //这样搜索只是搜索到了“未签申请”，但没有搜索到票
//		Employee user=UserUtils.getUser();
//		countApplyBill = (Long)commonService.uniqueResult("select count(*) from ApplyBill a where a.isNoContract = 1 and a.employeeId = ? ", user.getId());
		countApplyBill = firstPageService.countNoSignInvoice();
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

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Long getCountReceiptNotNull() {
		return countReceiptNotNull;
	}

	public void setCountReceiptNotNull(Long countReceiptNotNull) {
		this.countReceiptNotNull = countReceiptNotNull;
	}

	public IYXQueryService getYxQueryService() {
		return yxQueryService;
	}

	public void setYxQueryService(IYXQueryService yxQueryService) {
		this.yxQueryService = yxQueryService;
	}

	public Long getItemSuggestClose() {
		return itemSuggestClose;
	}

	public void setItemSuggestClose(Long itemSuggestClose) {
		this.itemSuggestClose = itemSuggestClose;
	}

	public Long getNextMouthBill() {
		return nextMouthBill;
	}

	public void setNextMouthBill(Long nextMouthBill) {
		this.nextMouthBill = nextMouthBill;
	}

	public Long getNextMouthReve() {
		return nextMouthReve;
	}

	public void setNextMouthReve(Long nextMouthReve) {
		this.nextMouthReve = nextMouthReve;
	}



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

	public String getCurMonthFirstDay() {
		return curMonthFirstDay;
	}

	public IFirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(IFirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}

	public Double getYesterdayReve() {
		return yesterdayReve;
	}

	public void setYesterdayReve(Double yesterdayReve) {
		this.yesterdayReve = yesterdayReve;
	}

	public void setCurMonthFirstDay(String curMonthFirstDay) {
		this.curMonthFirstDay = curMonthFirstDay;
	}

	public String getCurMonthLastDay() {
		return curMonthLastDay;
	}

	public void setCurMonthLastDay(String curMonthLastDay) {
		this.curMonthLastDay = curMonthLastDay;
	}

	public String getNextMonthFirstDay() {
		return nextMonthFirstDay;
	}

	public void setNextMonthFirstDay(String nextMonthFirstDay) {
		this.nextMonthFirstDay = nextMonthFirstDay;
	}

	public String getNextMonthLastDay() {
		return nextMonthLastDay;
	}

	public void setNextMonthLastDay(String nextMonthLastDay) {
		this.nextMonthLastDay = nextMonthLastDay;
	}

	public Long getCountOutplanBill() {
		return countOutplanBill;
	}

	public void setCountOutplanBill(Long countOutplanBill) {
		this.countOutplanBill = countOutplanBill;
	}

	public Long getCountOutplanRece() {
		return countOutplanRece;
	}

	public void setCountOutplanRece(Long countOutplanRece) {
		this.countOutplanRece = countOutplanRece;
	}

	public Long getNewContractCount() {
		return newContractCount;
	}

	public void setNewContractCount(Long newContractCount) {
		this.newContractCount = newContractCount;
	}

	public Long getNewContractCountIntegrated() {
		return newContractCountIntegrated;
	}

	public void setNewContractCountIntegrated(Long newContractCountIntegrated) {
		this.newContractCountIntegrated = newContractCountIntegrated;
	}

	public Long getNewContractCountItem() {
		return newContractCountItem;
	}

	public void setNewContractCountItem(Long newContractCountItem) {
		this.newContractCountItem = newContractCountItem;
	}

	public Long getApplyWaitSureCount() {
		return applyWaitSureCount;
	}

	public void setApplyWaitSureCount(Long applyWaitSureCount) {
		this.applyWaitSureCount = applyWaitSureCount;
	}

	public Long getApplyPassNoInvoiceCount() {
		return applyPassNoInvoiceCount;
	}

	public void setApplyPassNoInvoiceCount(Long applyPassNoInvoiceCount) {
		this.applyPassNoInvoiceCount = applyPassNoInvoiceCount;
	}

	public Double getThisMonthReve() {
		return thisMonthReve;
	}

	public void setThisMonthReve(Double thisMonthReve) {
		this.thisMonthReve = thisMonthReve;
	}

	public Long getNoContractReve() {
		return noContractReve;
	}

	public void setNoContractReve(Long noContractReve) {
		this.noContractReve = noContractReve;
	}

	public Long getVATInovoiceNoSign() {
		return vATInovoiceNoSign;
	}

	public void setVATInovoiceNoSign(Long inovoiceNoSign) {
		vATInovoiceNoSign = inovoiceNoSign;
	}

	public Double getNoContractReveSum() {
		return noContractReveSum;
	}

	public void setNoContractReveSum(Double noContractReveSum) {
		this.noContractReveSum = noContractReveSum;
	}

	public Long getCountBillandRecePlan5() {
		return countBillandRecePlan5;
	}

	public void setCountBillandRecePlan5(Long countBillandRecePlan5) {
		this.countBillandRecePlan5 = countBillandRecePlan5;
	}

	public Long getCountBillandRecePlan6() {
		return countBillandRecePlan6;
	}

	public void setCountBillandRecePlan6(Long countBillandRecePlan6) {
		this.countBillandRecePlan6 = countBillandRecePlan6;
	}

	public Long getCountBillandRecePlan7() {
		return countBillandRecePlan7;
	}

	public void setCountBillandRecePlan7(Long countBillandRecePlan7) {
		this.countBillandRecePlan7 = countBillandRecePlan7;
	}

	public Long getCountBillandRecePlan2() {
		return countBillandRecePlan2;
	}

	public void setCountBillandRecePlan2(Long countBillandRecePlan2) {
		this.countBillandRecePlan2 = countBillandRecePlan2;
	}

	public Long getConPreToFinalCount() {
		return conPreToFinalCount;
	}

	public void setConPreToFinalCount(Long conPreToFinalCount) {
		this.conPreToFinalCount = conPreToFinalCount;
	}

	public Long getCountRecePlan5() {
		return countRecePlan5;
	}

	public void setCountRecePlan5(Long countRecePlan5) {
		this.countRecePlan5 = countRecePlan5;
	}

	public Long getBusinessInovoiceNoSign() {
		return businessInovoiceNoSign;
	}

	public void setBusinessInovoiceNoSign(Long businessInovoiceNoSign) {
		this.businessInovoiceNoSign = businessInovoiceNoSign;
	}

	public Long getOutSourceContractWaitSure() {
		return outSourceContractWaitSure;
	}

	public void setOutSourceContractWaitSure(Long outSourceContractWaitSure) {
		this.outSourceContractWaitSure = outSourceContractWaitSure;
	}

	public Long getOutSourcePayWaitSure() {
		return outSourcePayWaitSure;
	}

	public void setOutSourcePayWaitSure(Long outSourcePayWaitSure) {
		this.outSourcePayWaitSure = outSourcePayWaitSure;
	}

	public Double getBeforeYesterdatReve() {
		return beforeYesterdatReve;
	}

	public void setBeforeYesterdatReve(Double beforeYesterdatReve) {
		this.beforeYesterdatReve = beforeYesterdatReve;
	}

}

