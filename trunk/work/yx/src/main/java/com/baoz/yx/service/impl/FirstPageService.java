package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.Role;
import com.baoz.yx.entity.RoleEmployee;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.tools.GetMonthTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.UserDetail;

@Service("firstPageService")
@Transactional
public class FirstPageService implements IFirstPageService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	protected Log logger = LogFactory.getLog(this.getClass());

	/*
	 * 项目建议关闭数
	 */
	public Long showContractItemInfo() {
		this.logger.info("项目建议关闭");
		StringBuffer sb = new StringBuffer(
				" select count(*) from ContractItemMaininfo i , "
				+ " ContractMainInfo cmi , OrganizationTree orgTree ,Employee emp "
				+ " where cmi.saleMan = emp.id "
				+ " and cmi.conMainInfoSid = i.contractMainInfo and cmi.conState != 10"
				+ " and   i.conItemState = 1 ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		// logger.info(s);
		return (Long) commonDao.uniqueResult(s);
	}

	/*
	 * 项目建议关闭
	 */
	public PageInfo queryItemSuggestClose(PageInfo info, String groupId,
			Long saleId) {
		StringBuffer sb = new StringBuffer(
				"select cmi,i,y,emp from ContractItemMaininfo i , "
				+ " ContractMainInfo cmi , YXClientCode y ,Employee emp  , OrganizationTree orgTree "
				+ " where cmi.conMainInfoSid = i.contractMainInfo "
				+ " and y.id = cmi.conCustomer  and cmi.conState != 10 "
				+ " and cmi.saleMan = emp.id  "
				+ " and i.conItemState = 1 ");
		String s = this.addAuthority(sb, " cmi.saleMan ", groupId, saleId);
		s = s + " order by cmi.conMainInfoSid desc ";
		info = queryService.listQueryResult(s, info);
		return info;
	}

	/*
	 * 合同建议关闭数
	 */
	public Long showContractSuggestClose() {
		this.logger.info("合同建议关闭");
		StringBuffer sb = new StringBuffer(
				"select count(*) from ContractMainInfo cmi ,"
				+ " OrganizationTree orgTree ,Employee emp "
				+ " where cmi.saleMan = emp.id "
				+ " and cmi.conState = 8  ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		return (Long) commonDao.uniqueResult(s);
	}

	/*
	 * 合同建议关闭
	 */
	public PageInfo queryConSuggestCloseInfo(PageInfo info, String groupId,
			Long saleId) {
		StringBuffer sb = new StringBuffer(
				"select cmi,emp.name,y.name from ContractMainInfo cmi ,"
				+ " OrganizationTree orgTree ,Employee emp ,YXClientCode y "
				+ " where cmi.saleMan = emp.id and y.id = cmi.conCustomer "
				+ " and cmi.conState = 8  ");
		String s = this.addAuthority(sb, " cmi.saleMan ", groupId, saleId);
		s = s + "  order by  cmi.conMainInfoSid desc ";
		info = queryService.listQueryResult(s, info);
		return info;
	}
	
	/**
	 * 前日收款
	 */
	public Double beforeYesterdayReve(){
		StringBuffer sb = new StringBuffer(
				"select sum(r.amount) from ReveInfo r , ContractMainInfo cmi , "
				+ " Employee emp  , OrganizationTree orgTree"
				+ " where cmi.conMainInfoSid = r.conMainInfoSid  "
				+ " and cmi.saleMan = emp.id  "
				+ " and  trunc(r.amountTime,'dd') = ? " 
				+	" and r.hasSure = 1 ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		Double x = (Double) commonDao.uniqueResult(s, DateUtils.addDays( DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),-2) );
		if (x == null) {
			x = 0d;
		}
		return x;
	}

	public Double yesterdayReve() {
		StringBuffer sb = new StringBuffer(
				"select sum(r.amount) from ReveInfo r , ContractMainInfo cmi , "
				+ " Employee emp  , OrganizationTree orgTree"
				+ " where cmi.conMainInfoSid = r.conMainInfoSid  "
				+ " and cmi.saleMan = emp.id  "
				+ " and  trunc(r.amountTime,'dd') = ? " 
				+	" and r.hasSure = 1 ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		//		logger.info(s);
		//		logger.info( DateUtils.addDays( DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),-1) );
		Double x = (Double) commonDao.uniqueResult(s, DateUtils.addDays( DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH),-1) );
		if (x == null) {
			x = 0d;
		}
		return x;
	}

	public Double todayReve() {
		StringBuffer sb = new StringBuffer(
				"select sum(r.amount) from ReveInfo r , ContractMainInfo cmi , "
				+ " Employee emp  , OrganizationTree orgTree"
				+ " where cmi.conMainInfoSid = r.conMainInfoSid  "
				+ " and cmi.saleMan = emp.id  "
				+ " and   trunc(r.amountTime,'dd') =  ? " 
				+	" and r.hasSure = 1  ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		Double x = (Double) commonDao.uniqueResult(s,DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		if (x == null) {
			x = 0d;
		}
		return x;
	}


	/*
	 * 本月到款
	 */
	public Double thisMonthReve(){
		StringBuffer sb = new StringBuffer(
				"select sum(r.amount) from ReveInfo r , ContractMainInfo cmi , "
				+ " Employee emp  , OrganizationTree orgTree"
				+ " where cmi.conMainInfoSid = r.conMainInfoSid  "
				+ " and cmi.saleMan = emp.id  "
				+ " and  r.amountTime >= ? and r.amountTime < ? " 
				+	" and r.hasSure = 1  ");
		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		logger.info(   DateUtils.truncate(new Date(), Calendar.MONTH)  );
		logger.info(  DateUtils.addMonths(DateUtils.truncate(new Date(), Calendar.MONTH),1)  );
		Double x = (Double) commonDao.uniqueResult(s,DateUtils.truncate(new Date(), Calendar.MONTH),
				DateUtils.addMonths(DateUtils.truncate(new Date(), Calendar.MONTH),1));

		if (x == null) {
			x = 0d;
		}
		return x;
	}

	/*
	 * 查询到款信息
	 */
	public Object[] queryHisReveInfo(PageInfo info, Date reveStartDate,
			Date reveEndDate, String groupId, Long saleId) {
		StringBuffer sb = new StringBuffer(
				" select cmi, "
				+ " ( select i.conItemId from ContractItemMaininfo i where i.conItemMinfoSid = r.billSid ) , "
				+ " r , emp.name ,y.name from ReveInfo r , "
				+ " ContractMainInfo cmi , Employee emp  , OrganizationTree orgTree , YXClientCode y "
				+ " where  cmi.conMainInfoSid = r.conMainInfoSid  and y.id = cmi.conCustomer "
				+ " and cmi.saleMan = emp.id " 
				+	 "  and r.hasSure = 1  ");

		// logger.info(reveStartDate);
		// logger.info(reveEndDate);

		String s = this.addAuthority(sb, " cmi.saleMan ", groupId, saleId);
		// 日期放上面处理会不正常，I DON'T KNOW WHY
		sb = new StringBuffer(s);
		if (reveStartDate != null) {
			sb.append(" and  r.amountTime >= to_date('"
					+ DateUtil.format(reveStartDate, "yyyy-MM-dd")
					+ "','yyyy-mm-dd')");
		}
		if (reveEndDate != null) {
			sb.append(" and r.amountTime < to_date('"
					+ DateUtil.format(DateUtils.addDays(reveEndDate, 1),
					"yyyy-MM-dd") + "','yyyy-mm-dd')");
		}
		sb.append(" order by r.amountTime desc ");
		info = queryService.listQueryResult(
				SqlUtils.getCountSql(sb.toString()), sb.toString(), info);

		Object[] o = new Object[2];
		o[0] = info;

		Double sum = (Double) commonDao.uniqueResult("select sum(r.amount)" +  SqlUtils.getFromSql(sb.toString()));
		o[1] = sum;
		return o;
	}

	/*
	 * 新签合同 0：all 1：工程类 2：集成类
	 */
	public Long countNewCountContract(int i) {
		StringBuffer sbBase = new StringBuffer(
				"  select count(*) from "
				+ "ContractMainInfo cmi , OrganizationTree orgTree ,Employee emp  "
				+ " where cmi.saleMan = emp.id "
				+ " and cmi.activeDate >= ?  and cmi.activeDate < ? "
				+ " and cmi.conState > 3  ");
		StringBuffer sb = null;
		if (i == 0) {
			sb = sbBase;
		} else if (i == 2) {
			sb = sbBase.append(" and cmi.ContractType = 2 ");
		} else {
			sb = sbBase.append(" and cmi.ContractType = 1 ");
		}

		String s = this.addAuthority(sb, " cmi.saleMan ", null, null);
		Long x = (Long) commonDao.uniqueResult(s , DateUtils.truncate(new Date(), Calendar.MONTH),
				DateUtils.addMonths(DateUtils.truncate(new Date(), Calendar.MONTH),1));
		logger.info(s);
		return x;
	}

	/*
	 * 新签合同信息 0：all 1：工程类 2：集成类
	 */
	public PageInfo queryNewSignContractInfo(PageInfo info, Date signStartDate,
			Date signEndDate, int sign, String groupId, Long saleId) {
		StringBuffer sbBase = new StringBuffer(
		"  select cmi,emp.name,y.name ");

		StringBuffer sb = processSumNewSignContract(signStartDate, signEndDate,
				sign, groupId, saleId, sbBase);

		sb.append(" order by cmi.conMainInfoSid desc ");
		info = queryService.listQueryResult( SqlUtils.getCountSql(sb.toString()), sb.toString(), info);
		return info;
	}


	public Double sumNewSignContractInfo(Date signStartDate,
			Date signEndDate, int sign, String groupId, Long saleId) {
		StringBuffer sbBase = new StringBuffer(
		"  select sum(cmi.conTaxTamount) ");
		StringBuffer sb = processSumNewSignContract(signStartDate, signEndDate,
				sign, groupId, saleId, sbBase);	
		return (Double) commonDao.uniqueResult(sb.toString());
	}


	private StringBuffer processSumNewSignContract(Date signStartDate,
			Date signEndDate, int sign, String groupId, Long saleId,
			StringBuffer sbBase) {
		sbBase.append(" from "
				+ "ContractMainInfo cmi , OrganizationTree orgTree ,Employee emp , YXClientCode y  "
				+ " where cmi.saleMan = emp.id and y.id = cmi.conCustomer"
				+ " and cmi.conState > 3   ");
		StringBuffer sb = null;
		if (sign == 0) {
			sb = sbBase;
		} else if (sign == 2) {
			sb = sbBase.append(" and cmi.ContractType = 2 ");
		} else {
			sb = sbBase.append(" and cmi.ContractType = 1 ");
		}

		String s = this.addAuthority(sb, " cmi.saleMan ", groupId, saleId);
		// 日期放上面处理会不正常，I DON'T KNOW WHY
		sb = new StringBuffer(s);
		// logger.info(signStartDate);
		// logger.info(signEndDate);
		if (signStartDate != null) {
			sb.append(" and  cmi.activeDate >= to_date('"
					+ DateUtil.format(signStartDate, "yyyy-MM-dd")
					+ "','yyyy-mm-dd')");
		}
		if (signEndDate != null) {
			sb.append(" and cmi.activeDate < to_date('"
					+ DateUtil.format(DateUtils.addDays(signEndDate, 1),
					"yyyy-MM-dd") + "','yyyy-mm-dd')");
		}
		return sb;
	}


	/*
	 * 未签合同开票
	 */
	public Long countNoSignInvoice() {
		StringBuffer sb = new StringBuffer(
				" select count(*) from ApplyBill a , "
				+ " InvoiceInfo i , OrganizationTree orgTree ,Employee emp "
				+ " where a.employeeId = emp.id and a.billApplyId = i.applyInvoiceId and a.isNoContract = 1 ");
		String s = this.addAuthority(sb, " a.employeeId ", null, null);
		logger.info(s);
		return (Long) commonDao.uniqueResult(s);
	}

	/*
	 * 未签合同开票
	 */
	public PageInfo countNoSignInvoiceInfo(PageInfo info, String groupId,
			Long saleId) {
		StringBuffer sb = new StringBuffer(
				" select a , i ,emp.name , y.name from ApplyBill a , YXClientCode y , "
				+ " InvoiceInfo i , OrganizationTree orgTree ,Employee emp "
				+ " where a.employeeId = emp.id "
				+ " and   y.id = a.customerId and a.billApplyId = i.applyInvoiceId and a.isNoContract = 1 ");
		String s = this.addAuthority(sb, " a.employeeId ", groupId, saleId);
		logger.info(s);
		s = s + "order by a.billApplyNum desc ";
		info = queryService.listQueryResult(s, info);
		return info;
	}

	// 处理hql 增加权限 注：Employee emp , OrganizationTree orgTree
	synchronized private String addAuthority(StringBuffer sb,
			String saleManString, String groupId, Long saleId) {
		UserDetail user = UserUtils.getUserDetail();

		sb.append(" and emp.position = orgTree.id    ");

		if (!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())) {
			saleId = user.getUser().getId();
		} else if(StringUtils.isBlank(groupId)) {
			groupId = user.getPosition().getOrganizationCode();
		}

		if (groupId != null && !"".equals(groupId)) {
			String temp = " and orgTree.organizationCode like '" + groupId + "%'";
			sb.append(temp);
		}

		if (saleId != null && !saleId.equals("")) {
			String temp = " and " + saleManString + " = " + saleId;
			sb.append(temp);
		}
		return sb.toString();
	}

	/*
	 * 实际合同开票计划 1 全部 2 已开 3 未开
	 */
	public Long countRealContractBillPlan(int i) {
		this.logger.info("本月开票显示");
		//
		Date today = new Date();
		// 获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		// 获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1),
				Calendar.MONTH);
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		// //////////////////////
		StringBuilder allPlanSb = new StringBuilder();
		allPlanSb.append("select count(*) "
				+ " from RealContractBillandRecePlan p,ContractMainInfo m ,MonthlyBillpro month "
				+ " where p.realConBillproSid = month.realContractBillandRecePlan "
				+ " and p.conMainInfoSid=m.conMainInfoSid and month.billproMonth >= ? "
				+ " and month.billproMonth < ? and month.isInsidePlan = 0  ");
		if (expId != null) {
			allPlanSb.append(" and m.saleMan = " + expId + " ");
		}
		if (groupId != null && !"".equals(groupId)) {
			allPlanSb.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = m.saleMan ) ");
		}
		// /////////////////////
		switch (i) {
		case 1:
			// 本月所有开票计划
			break;
		case 2:
			// 本月已开票计划
			allPlanSb.append(" and p.billInvoiceAmount > 0 ");
			break;
			//			case 3:
			//			// 本月未开票计划
			//			allPlanSb.append(" and ( p.billInvoiceAmount = 0 or p.billInvoiceAmount is null ) ");
			//			break;
		case 5:
			// 本月未开票计划，已申请
			allPlanSb.append(" and exists ( select 1 from  BillandProRelaion a where a.realContractBillandRecePlan = p.realConBillproSid )");
			break;
		case 6:
			// 本月未开票计划，未申请
			allPlanSb.append(" and not exists ( select 1 from  BillandProRelaion a where a.realContractBillandRecePlan = p.realConBillproSid )");
			break;
		case 7:
			// 本月未开票计划，计划日期修改过
			allPlanSb.append(" and not exists ( select 1 from  BillandProRelaion a where a.realContractBillandRecePlan = p.realConBillproSid ) and trunc(p.realPredBillDate,'mm') >  trunc(month.billproMonth,'mm') ");
			break;	
		}

		return (Long) commonDao.uniqueResult(allPlanSb.toString(), nowMonth,
				nextMonth);
	}

	/*
	 * 开票计划查询 inPlan：1 计划内 0 计划外 billState： 1 已开 0 未开
	 */
	public Object[] queryShowTotalBill(PageInfo info, Integer inPlan,
			String billState, Date billStartDate, Date billEndDate,
			String groupId, Long expId , Integer hasApply , Integer hasModify ) {

		UserDetail user = UserUtils.getUserDetail(); 
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		String select = "select r,yc,cm ,"
			+ "(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo),"
			+ "(select e.name from Employee e where e.id = cm.saleMan) ," 
			+ "(select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo) ";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm,MonthlyBillpro month where "
				+ "cm.conCustomer = yc.id and r.realConBillproSid = month.realContractBillandRecePlan "
				+ "and r.conMainInfoSid = cm.conMainInfoSid ");

		// 计划内
		if (new Integer(1).equals(inPlan)) {
			from.append(" and month.isInsidePlan = 0 ");
			if (billStartDate != null) {
				from.append(" and month.billproMonth >= to_date('"
						+ DateUtil.format(billStartDate, "yyyy-MM-dd")
						+ "','yyyy-mm-dd') ");
			}
			if (billEndDate != null) {
				// 小于第二天0点0分0秒
				from.append(" and month.billproMonth < to_date('"
						+ DateUtil.format(DateUtils.addDays(billEndDate, 1),
						"yyyy-MM-dd") + "','yyyy-mm-dd')  ");
			}
		}
		// 计划外
		else if (new Integer(0).equals(inPlan)) {
			from.append(" and  month.isInsidePlan = 1 ");
			if (billStartDate != null) {
				from.append(" and month.actualBillDate >= to_date('"
						+ DateUtil.format(billStartDate, "yyyy-MM-dd")
						+ "','yyyy-mm-dd') ");
			}
			if (billEndDate != null) {
				// 小于第二天0点0分0秒
				from.append(" and month.actualBillDate < to_date('"
						+ DateUtil.format(DateUtils.addDays(billEndDate, 1),
						"yyyy-MM-dd") + "','yyyy-mm-dd')  ");
			}
		} else {
			if (billStartDate != null) {
				from.append(" and ( (  month.isInsidePlan = 0 and month.billPlanDate >= to_date('"
						+ DateUtil.format(billStartDate, "yyyy-MM-dd")
						+ "','yyyy-mm-dd') ) "
						+ "or ( month.isInsidePlan = 1 and month.actualBillDate >= to_date('"
						+ DateUtil.format(billStartDate, "yyyy-MM-dd")
						+ "','yyyy-mm-dd') ) )");
			}
			if (billEndDate != null) {
				// 小于第二天0点0分0秒
				from.append(" and ( (  month.isInsidePlan = 0 and month.billPlanDate < to_date('"
						+ DateUtil.format(DateUtils.addDays(billEndDate, 1),
						"yyyy-MM-dd")
						+ "','yyyy-mm-dd') ) "
						+ "or ( month.isInsidePlan = 1 and month.actualBillDate < to_date('"
						+ DateUtil.format(DateUtils.addDays(billEndDate, 1),
						"yyyy-MM-dd") + "','yyyy-mm-dd') ) )");
			}
		}

		// 已开票
		if ("1".equals(billState)) {
			from.append(" and r.billInvoiceAmount > 0 ");
		}
		// 未开票
		else if ("0".equals(billState)) {
			from.append(" and ( r.billInvoiceAmount is null or r.billInvoiceAmount = 0 ) ");
		}

		// 已申请
		if (new Integer(1).equals(hasApply)) {
			from.append(" and exists ( select 1 from  BillandProRelaion a where a.realContractBillandRecePlan = r.realConBillproSid ) ");
		}
		// 未申请
		else if (new Integer(0).equals(hasApply)) {
			from.append(" and not exists ( select 1 from  BillandProRelaion a where a.realContractBillandRecePlan = r.realConBillproSid ) ");
		}

		// 已修改
		if (new Integer(1).equals(hasModify)) {
			from.append(" and trunc(r.realPredBillDate,'mm') >  trunc(month.billproMonth,'mm')  ");
		}
		// 未修改
		else if (new Integer(0).equals(hasModify)) {
			from.append(" and trunc(r.realPredBillDate,'mm') <=  trunc(month.billproMonth,'mm') ");
		}


		if (expId != null) {
			from.append(" and cm.saleMan = " + expId + " ");
		}
		if (groupId != null && !"".equals(groupId)) {
			from.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = cm.saleMan ) ");
		}
		from.append(" order by r.realPredBillDate");

		info = queryService.listQueryResult("select count(*) "
				+ from.toString(), select + from.toString(), info);

		//////累计
		Object[] sumArray = (Object[]) commonDao.uniqueResult("select sum(r.realTaxBillAmount),sum(r.billInvoiceAmount) "+from.toString());
		return new Object[]{info,sumArray};
	}

	/**
	 * 获取本月计划外开票数
	 */
	public Long countOutplanBill() {
		Date start = GetMonthTool.getFirstDayOfMonth();
		Date end = DateUtils.addDays(GetMonthTool.getLastDayOfMonth(), 1);

		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		StringBuffer bf = new StringBuffer(
				" select count(*) from RealContractBillandRecePlan r,MonthlyBillpro month, ContractMainInfo cm where "
				+ " r.realConBillproSid = month.realContractBillandRecePlan and r.conMainInfoSid = cm.conMainInfoSid and r.billInvoiceAmount > 0 "
				+ " and month.actualBillDate >= to_date('"
				+ DateUtil.format(start, "yyyy-MM-dd")
				+ "','yyyy-mm-dd') "
				+ " and month.actualBillDate < to_date('"
				+ DateUtil.format(end, "yyyy-MM-dd")
				+ "','yyyy-mm-dd') " + " and month.isInsidePlan = 1 ");
		if (expId != null) {
			bf.append(" and cm.saleMan = " + expId + " ");
		}
		if (groupId != null && !"".equals(groupId)) {
			bf
			.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = cm.saleMan ) ");
		}
		return (Long) commonDao.uniqueResult(bf.toString());
	}

	public Long countRealContractRecePlan(int i) {
		// this.logger.info("本月收款显示");
		//
		Date today = new Date();
		// 获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		// 获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1),
				Calendar.MONTH);
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		// //////////////////////
		StringBuilder allPlanSb = new StringBuilder();
		allPlanSb.append("select count(*) "
				+ " from RealContractBillandRecePlan p,ContractMainInfo m ,MonthlyRecepro month "
				+ " where p.realConBillproSid = month.realContractBillandRecePlan "
				+ " and p.conMainInfoSid=m.conMainInfoSid and month.billproMonth >= ? "
				+ " and month.billproMonth < ? and month.isInsidePlan = 0 ");
		if (expId != null) {
			allPlanSb.append(" and m.saleMan = " + expId + " ");
		}
		if (groupId != null && !"".equals(groupId)) {
			allPlanSb
			.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = m.saleMan ) ");
		}
		// /////////////////////
		switch (i) {
		case 1:
			// 本月所有收款计划
			break;
		case 2:
			// 本月已收款计划
			allPlanSb.append(" and month.alreadyArrivedAmount > 0 ");
			break;
		case 3:
			// 本月未收款计划
			allPlanSb.append(" and ( month.alreadyArrivedAmount = 0 or month.alreadyArrivedAmount is null ) ");
			break;
		case 4:
			// 本月未收款计划，已修改
			allPlanSb.append(" and ( month.alreadyArrivedAmount = 0 or month.alreadyArrivedAmount is null ) and " +
			" trunc(p.realPredReceDate,'mm') >  trunc(month.billproMonth,'mm') ");
			break;
		}
		return (Long) commonDao.uniqueResult(allPlanSb.toString(), nowMonth,
				nextMonth);
	}

	public Long countOutplanRece() {
		Date start = GetMonthTool.getFirstDayOfMonth();
		Date end = DateUtils.addDays(GetMonthTool.getLastDayOfMonth(), 1);

		UserDetail user = UserUtils.getUserDetail();
		Long expId = null;
		String groupId = null;
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			expId = user.getUser().getId();
		} else {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		StringBuffer bf = new StringBuffer(
				" select count(*) from RealContractBillandRecePlan r,MonthlyRecepro month, ContractMainInfo cm where "
				+ " r.realConBillproSid = month.realContractBillandRecePlan and r.conMainInfoSid = cm.conMainInfoSid and r.realArriveAmount > 0 "
				+ " and month.actualArrivedDate >= to_date('"
				+ DateUtil.format(start, "yyyy-MM-dd")
				+ "','yyyy-mm-dd') "
				+ " and month.actualArrivedDate < to_date('"
				+ DateUtil.format(end, "yyyy-MM-dd")
				+ "','yyyy-mm-dd') " + " and month.isInsidePlan = 1 ");
		if (expId != null) {
			bf.append(" and cm.saleMan = " + expId + " ");
		}
		if (groupId != null && !"".equals(groupId)) {
			bf.append(
			" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '")
			.append(groupId + "%' and e.id = cm.saleMan ) ");
		}
		return (Long) commonDao.uniqueResult(bf.toString());
	}

	/**
	 * 申请待确认
	 */
	public Long countApplyWaitSure() {
		if(isBusiness() || isAdmin()){
			String str = "select count(*) from  ApplyBill a where a.applyBillState = 2";
			return (Long) commonDao.uniqueResult(str);
		}
		else{
			return 0L;
		}
	}

	/**
	 * 申请通过未开票
	 */
	public Long countApplyPassNoInvoice() {
		if(isBusiness() || isAdmin()){
			String str = " select count(*) "+
			" from ApplyBill a "+
			" where   ( a.billAmountTax <> " +
			" (select sum(i.invoiceAmount) from InvoiceInfo i where i.applyInvoiceId = a.billApplyId and i.inputState = 1 ) " +
			"  or not exists (select 1  from InvoiceInfo i where i.applyInvoiceId = a.billApplyId  and i.inputState = 1 ) )  and a.applyBillState in (5,7) ";
			return (Long) commonDao.uniqueResult(str);
		}
		else{
			return 0L;
		}
	}


	/**
	 * 未核销到款count
	 */
	public Long noContractReve(){
		StringBuffer sb = new StringBuffer(
				" select count(*) from NoContractRecevieAmount t , Employee emp " +
		" where t.state ='0' and t.saleMan = emp.id ");		
		//		String s = this.addAuthority(sb, " t.saleMan ", null, null);
		return (Long) commonDao.uniqueResult(sb.toString());
	}

	/**
	 * 未核销到款sum
	 */
	public Double noContractReveSum(){
		StringBuffer sb = new StringBuffer(
				" select sum(t.recevieAmount) from NoContractRecevieAmount t, Employee emp " +
		" where t.state = '0' and t.saleMan = emp.id ");		
		//		String s = this.addAuthority(sb, " t.saleMan ", null, null);
		return (Double) commonDao.uniqueResult(sb.toString());
	}

	/**
	 *未签收款PageInfo
	 */
	public Object[] queryNoContractReve( PageInfo info, String groupId ,  Long saleId , String reveState ){
		StringBuffer sb = new StringBuffer(
				" select t,emp.name,y.fullName from NoContractRecevieAmount t,OrganizationTree orgTree ,Employee emp , YXClientCode y " +
		" where t.saleMan = emp.id  and y.id =t.customerid  and emp.position = orgTree.id    " );
		if(StringUtils.isNotBlank(reveState)){
			sb.append(" and t.state = '").append(reveState).append("'");
		}
		if(StringUtils.isBlank(groupId)) {
			groupId = "10";
		}
		if (groupId != null && !"".equals(groupId)) {
			String temp = " and orgTree.organizationCode like '" + groupId + "%'";
			sb.append(temp);
		}
		if (saleId != null && !saleId.equals("")) {
			String temp = " and  t.saleMan = " + saleId;
			sb.append(temp);
		}
		sb.append(  " order by t.state ,  t.recevieDate desc " );
		String s = sb.toString();
		info = queryService.listQueryResult(s, info);
		Object[] o = new Object[2];
		o[0] = info;

		Double sum = (Double) commonDao.uniqueResult("select sum(t.recevieAmount)" +  SqlUtils.getFromSql(s));
		o[1] = sum;

		return o;
	}


	/**
	 * 发票 已开，未签收（2：增票 3：商票）
	 */
	public Long countVATInvoiceNoSign( String typeSmall){
		StringBuffer sb = new StringBuffer(
				" select count(*) from ApplyBill a , "
				+ " InvoiceInfo i , OrganizationTree orgTree ,Employee emp "
				+ " where a.employeeId = emp.id and a.billApplyId = i.applyInvoiceId " +
				" and a.billType = '"+ typeSmall +"' and a.sign=0 ");
		String s = this.addAuthority(sb, " a.employeeId ", null, null);
		logger.info(s);
		return (Long) commonDao.uniqueResult(s);
	}

	/**
	 * 增值税发票 已开，未签收
	 */
	public PageInfo queryVATInvoiceNoSign( PageInfo info, String groupId ,  Long saleId , String billType){
		StringBuffer sb = new StringBuffer(
				" select a , i ,emp.name , y.name ," +
				" ( select cmi.conId from ContractMainInfo cmi where cmi.conMainInfoSid = a.contractMainInfo ) " +
				" from ApplyBill a , YXClientCode y , "
				+ " InvoiceInfo i , OrganizationTree orgTree ,Employee emp "
				+ " where a.employeeId = emp.id "
				+ " and   y.id = a.customerId and a.billApplyId = i.applyInvoiceId " +
				" and a.billType = '"+ billType +"' and a.sign=0 ");
		String s = this.addAuthority(sb, " a.employeeId ", groupId, saleId);
		logger.info(s);
		s = s + "order by i.invoiceDate ";
		info = queryService.listQueryResult(SqlUtils.getCountSql(s),s, info);
		return info;
	}


	public Long countConPreToFinal() {
		if(isBusiness()||isAdmin()){
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from ChangingContractMainInfo c where c.changeType='2' and c.changeContractState=1 ");
			return (Long) commonDao.uniqueResult(sb.toString());
		}
		return 0L;
	}

	/**
	 * 外协合同待确认count
	 */
	public Long  countOutSourceContractWaitSure( ){
		if(isBusiness() || isAdmin()){
			String hql = " select count(*) from AssistanceContract ac where ac.is_active='1' and ac.assistanceContractType='1'";
			return (Long) commonDao.uniqueResult(hql);
		}
		else{
			return 0L;
		}
	}
	
	/**
	 * 外协合同待确认
	 */
	public PageInfo  queryOutSourceContractWaitSure( PageInfo info, String groupId ,  Long saleId ){
		StringBuffer sb = new StringBuffer(" select ac,supply.supplierName,contract.conId" +
				" from AssistanceContract ac, SupplierInfo supply,ContractMainInfo contract , OrganizationTree orgTree , Employee emp  " +
				" where ac.contractId = contract.conMainInfoSid" +
				" and ac.supplyId = supply.supplierid " +
				" and contract.saleMan = emp.id " +
				" and  ac.is_active='1'  and ac.assistanceContractType = '1' ");
		String s = this.addAuthority(sb, " contract.saleMan ", groupId, saleId);
		logger.info(s);
		s = s + "order by  ac.id desc ";
		info = queryService.listQueryResult(SqlUtils.getCountSql(s),s, info);
		return info;
	}
	
	/**
	 * 外协合同付款待确认count
	 */
	public Long  countOutSourcePayWaitSure( ){
		if(isBusiness() || isAdmin()){
			String hql = " select count(*) from AssistancePayInfo  pi where pi.is_active='1' and  pi.payState ='1' ";
			return (Long) commonDao.uniqueResult(hql);
		}
		else{
			return 0L;
		}
	}
	
	/**
	 * 外协合同付款待确认
	 */
	public PageInfo queryOutSourcePayWaitSure( PageInfo info, String groupId ,  Long saleId ){
		StringBuffer sb = new StringBuffer(" select pi, " +
				"(select ac.assistanceId from AssistanceContract ac where ac.id = pi.assistanceId )," +
				"(select ac.assistanceName from AssistanceContract ac where ac.id = pi.assistanceId )," +
				" si.supplierName,emp.name " +
				"from AssistancePayInfo pi, SupplierInfo si ,  OrganizationTree orgTree  , Employee emp " +
				" where  pi.supplyId = si.supplierid " +
				" and emp.id = pi.employeeId  " +
				" and pi.payState='1' " +
				" and pi.is_active='1' " );
		String s = this.addAuthority(sb, " pi.employeeId ", groupId, saleId);
		logger.info(s);
		s = s + "order by pi.payState DESC,pi.id" ;
		info = queryService.listQueryResult(SqlUtils.getCountSql(s),s, info);
		return info;
	}
	
	
	/**
	 * 是否商务
	 */
	@SuppressWarnings("unchecked")
	public boolean isBusiness(){
		// 判断角色，管理员就返回true
		List<RoleEmployee> l = commonDao.list("from RoleEmployee re where re.userId=?", UserUtils.getUser().getId());
		for(int i=0; i<l.size(); i++) {
			Role role = (Role) commonDao.load(Role.class, l.get(i).getRoleId());
			if(YxConstants.bussinessRoleCode.equals(role.getCode())) {
				//管理员
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否领导
	 */
	@SuppressWarnings("unchecked")
	public boolean isLeader(){
		// 判断角色，管理员就返回true
		List<RoleEmployee> l = commonDao.list("from RoleEmployee re where re.userId=?", UserUtils.getUser().getId());
		for(int i=0; i<l.size(); i++) {
			Role role = (Role) commonDao.load(Role.class, l.get(i).getRoleId());
			if(YxConstants.leader.equals(role.getCode())) {
				//管理员
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否Admin
	 */
	@SuppressWarnings("unchecked")
	public boolean isAdmin(){
		// 判断角色，管理员就返回true
		List<RoleEmployee> l = commonDao.list("from RoleEmployee re where re.userId=?", UserUtils.getUser().getId());
		for(int i=0; i<l.size(); i++) {
			Role role = (Role) commonDao.load(Role.class, l.get(i).getRoleId());
			if(YxConstants.adminRoleCode.equals(role.getCode())) {
				//管理员
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取所有部门
	 */
	@SuppressWarnings("unchecked")
	public List<Department>  doGetAllTeams( ){
		List<Department> myDepartment = new ArrayList<Department>();
		myDepartment.add(new Department(-1L,"10", "整体"));

		List<OrganizationTree>  subOrganizationTreeList=   commonDao.list("from OrganizationTree as ot where ot.organizationCode like '____'  and ot.is_active = '1'" );
		for (int i = 0; i < subOrganizationTreeList.size(); i++) {
			String myDepartmentCode = subOrganizationTreeList.get(i).getOrganizationCode();
			String myDepartmentName = subOrganizationTreeList.get(i).getOrganizationName();
			myDepartment.add(new Department(subOrganizationTreeList.get(i).getId(),myDepartmentCode, myDepartmentName));
		}
		return myDepartment;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}


	public void setLogger(Log logger) {
		this.logger = logger;
	}


}
