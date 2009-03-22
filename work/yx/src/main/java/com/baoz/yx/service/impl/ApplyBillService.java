package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.bill.HongChongApplyBill;
import com.baoz.yx.entity.bill.HongChongInvoiceInfo;
import com.baoz.yx.entity.bill.HongChongRelationPlan;
import com.baoz.yx.entity.bill.RelationBillAndReceipt;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
import com.baoz.yx.vo.UserDetail;

@Service("applyBillService")
@Transactional
public class ApplyBillService implements IApplyBillService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;

	@Autowired
	@Qualifier("harvestService")
	private IHarvestService 		harvestService;
	
	private Logger logger = Logger.getLogger(ImportHisDataService.class);

	@SuppressWarnings("unchecked")
	public List<ApplyBill> findApplyBillsByContractNo(Long contractMainInfo) {
		String hql="from ApplyBill a where a.contractMainInfo="+contractMainInfo;
		List<ApplyBill> bills=(List<ApplyBill>)commonDao.list(hql);
		return bills;
	}
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}


	/**	
	 * 根据 合同主体号，获取未出库申购清单 
	 * 
	 *  select count(*) from yx_apply_message a, yx_client c where 
	 *	( a.MIAIN_ID = 2186  and a.OUT_STATE = 0 and a.TICKET_APPLY_ID is null and a.CUSTOMER_ID = c.id )  
	 *     or
	 *    ( a.sellman_id = 1  and a.OUT_STATE = 0 and a.TICKET_APPLY_ID is null and a.CUSTOMER_ID = c.id  and a.miain_id is  null)  			 
	 */
	public PageInfo loadNotOutPurchase(Long uid , Long conSid,String aid,PageInfo info){
		//不是分页查询...默认搜索    （自己的申购  and  没有出库   没开票申请    没合同号 ）
		if(aid == null || "".equals(aid)){
			info = queryService.listQueryResult(
					" select a,c from ApplyMessage a, YXClientCode c where" +
					" ( a.mainId = ? and a.sellmanId = ? and  a.customerId = c.id  and " +
					" a.outState = 0 and a.ticketApplyId is null  and " +
					" a.is_active != 0  and  a.affirmState = '2'   )   " +
					"  or " +
					" ( a.sellmanId = ? and  a.customerId = c.id  and " +
					" a.is_active != 0  and  a.affirmState = '2' and   " +
					" a.outState = 0 and a.ticketApplyId is null and a.mainId is null)" 
					, info, conSid,  uid, uid);
		}
		//是分页查询...条件搜索  （自己的申购  and  没有出库   没开票申请    没合同号 ）
		else{
			info = queryService.listQueryResult(
					" select a,c from ApplyMessage a, YXClientCode c where " +
					" ( a.mainId = ? and a.sellmanId = ? and  a.customerId = c.id  and " +
					" a.outState = 0 and a.ticketApplyId is null  and " +
					" a.is_active != 0  and a.affirmState = '2'    " +
					" and a.applyId  like '%" + aid + "%' )   " +
					"  or " +
					" ( a.sellmanId = ? and  a.customerId = c.id  and " +
					" a.outState = 0 and a.ticketApplyId is null and a.mainId is null and " +
					" a.is_active != 0  and  a.affirmState = '2'  " +
					" and a.applyId  like '%" + aid + "%')" 
					, info, conSid,  uid, uid);
		}
		return info;
	}
	public String showNoInputState() {
		Long uid = UserUtils.getUser().getId();
		StringBuffer str = new StringBuffer();
		str.append("select bill,(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = bill.contractMainInfo ) " +
				" from ApplyBill bill " +
		"where bill.applyBillState in (3,5,7) "); 
		str.append(" and bill.billApplyId in(select distinct inf.applyInvoiceId from InvoiceInfo inf where inf.inputState = 0 and inf.byId = "+uid+" )");
		str.append(" order by bill.billApplyId DESC");	
		return str.toString();
	}

	public String showBillReceiptQuery(Long confirm,String groupId,Long saleMan,String contractNo,String itemNo,Double billAmountTax,String invoiceNo,String hasSigned) {
		StringBuffer str = new StringBuffer();
		str.append("select bill,yc,emp, " +
				"(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = bill.contractMainInfo )," +
				" (select sum(ii.invoiceAmount) from InvoiceInfo ii where ii.applyInvoiceId = bill.billApplyId)," +
				"(select cm.conName from ContractMainInfo cm where cm.conMainInfoSid = bill.contractMainInfo )," +
				"(select yc.fullName from YXClientCode yc where yc.id = bill.billCustomer)" +
				" from ApplyBill bill,YXClientCode yc,Employee emp,OrganizationTree orgTree " +
				"where bill.employeeId = emp.id " +
				"and emp.position=orgTree.id " +
				"and yc.is_active != 0 " +
		"and bill.billCustomer = yc.id " ); 

		if(hasSigned!=null && hasSigned.equals("true")){
			str.append(" and bill.applyBillState = 6" );
		}
		else{
			str.append(" and bill.applyBillState in (5,7)  " );
			if(new Long(0).equals(confirm)) {
				// 有未确认的
				Long uid = UserUtils.getUser().getId();
				str.append(" and exists (select 1 from InvoiceInfo ii where ii.inputState = '0' and ii.applyInvoiceId = bill.billApplyId and ii.byId = "+uid+" ) ");
			}else if(new Long(1).equals(confirm)){
				// 没有未确认的而且开过票
				str.append(" and not exists (select 1 from InvoiceInfo ii where ii.inputState = '0' and ii.applyInvoiceId = bill.billApplyId) " +
				" and exists (select 1 from InvoiceInfo ii where ii.applyInvoiceId = bill.billApplyId) ");
			}else if(new Long(2).equals(confirm)){
				// 未录完的
				str.append(" and ( not exists (select 1 from InvoiceInfo ii where ii.applyInvoiceId = bill.billApplyId and ii.inputState = '1' ) " +
				" or bill.billAmountTax <> (select sum(invoiceAmount) from InvoiceInfo ii where ii.applyInvoiceId = bill.billApplyId  and ii.inputState = '1' ) )");
			}
		}
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleMan = user.getUser().getId();
		}else if(groupId != null && !"".equals(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}
		if (saleMan != null && !"".equals(saleMan)) {
			str.append(" and emp.id= ").append(saleMan);
		}
		if(StringUtils.isNotBlank(contractNo)){
			str.append(" and exists (select 1 from ContractMainInfo cm where lower(cm.conId) like '%"+StringUtils.lowerCase(StringUtils.trim(contractNo))+"%' and cm.conMainInfoSid = bill.contractMainInfo) ");
		}
		if(StringUtils.isNotBlank(itemNo)){
			str.append(" and exists (select 1 from ContractItemMaininfo im,RealContractBillandRecePlan r,BillandProRelaion br " +
					"where lower(im.conItemId) like '%"+StringUtils.lowerCase(StringUtils.trim(itemNo))+"%' " +
					"and r.contractItemMaininfo = im.conItemMinfoSid " +
					"and br.realContractBillandRecePlan = r.realConBillproSid " +
			"and br.applyBill = bill.billApplyId) ");
		}
		if(StringUtils.isNotBlank(invoiceNo)){
			str.append(" and exists (select 1 from InvoiceInfo ii where ii.applyInvoiceId = bill.billApplyId " +
					" and ii.invoiceNo = '"+invoiceNo.trim()+"')");
		}
		if(billAmountTax != null && !"".equals(billAmountTax)){
			str.append("and bill.billAmountTax = ").append(billAmountTax);
		}
		str.append(" order by bill.applyId desc");
		return str.toString();
	}
	public Double balanceInvoiceAmount(Long billId) {
		Double balance = (Double)commonDao.uniqueResult(" select sum(ii.invoiceAmount) from InvoiceInfo ii where ii.applyInvoiceId = "+billId);
		return balance;
	}
	public List<InvoiceInfo> findInvoiceInputState(Long uid) {
		List<InvoiceInfo> invoiceList = commonDao.list(" from InvoiceInfo invoice where invoice.inputState = 0 and  invoice.byId = "+uid+" ) order by invoice.inputState");
		return invoiceList;
	}
	public String[] showRealRelation(String conName,String conId, String itemId,Long billId) {
		Long uid = UserUtils.getUser().getId();
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class, billId);
		String select = "select r,yc,cm,(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)," +
		"(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage),"+
		"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
		" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , " +
		"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
		" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ) " ;
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and exists (select 1 from ContractItemMaininfo im where im.conItemId is not null and r.contractItemMaininfo = im.conItemMinfoSid ) "+
				"and cm.saleMan = "+ uid);
		if (StringUtils.isNotBlank(conName) && StringUtils.isNotBlank(conName)) {
			from.append(" and cm.conName like '%").append(StringUtils.trim(conName)).append("%'");
		}
		if (StringUtils.isNotBlank(conId) && StringUtils.isNotBlank(conId)) {
			from.append(" and lower(cm.conId) like '%").append(StringUtils.lowerCase(StringUtils.trim(conId))).append("%'");
		}
		if (StringUtils.isNotBlank(itemId) && StringUtils.isNotBlank(itemId)) {
			from.append(" and exists (select 1 from ContractItemMaininfo im where lower(im.conItemId) like '%"+StringUtils.lowerCase(StringUtils.trim(itemId))+"%' and r.contractItemMaininfo = im.conItemMinfoSid ) ");
		}
		if(bill.getBillType() != null){
			from.append(" and r.billType = " + bill.getBillType());
		}
		from.append(" order by cm.conId,r.realConBillproSid");	

		String hql[] = new String[2];
		hql[0]="select count(*) "+from.toString();
		hql[1]=select+from.toString();
		return hql;
	}

	public String[] showApplyBillQuery(boolean isFromMenu, String conId,
			String itemId,String customerName, Long customer, Double minRealTaxBillAmount,
			Double maxRealTaxBillAmount, Double minRealBillAmount,
			Double maxRealBillAmount, String monthSel, String yearSel,Double minConAmount,Double maxConAmount,String startDate,String endDate) {
		Long uid = UserUtils.getUser().getId();
		String select = "select r,yc,cm,(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)," +
		"(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = r.conItemStage)," +
		"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
		" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , " +
		"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
		" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ) " ;
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm,YXTypeManage ym where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and r.billType = ym.typeSmall " +
				"and ym.typeBig = 1004 " +
				"and r.realBillAmount > 0 " +
				"and cm.saleMan = "+ uid);
		//合同号
		if (StringUtils.isNotBlank(conId) && StringUtils.isNotBlank(conId)) {
			from.append(" and lower(cm.conId) like '%").append(StringUtils.lowerCase(StringUtils.trim(conId))).append("%'");
		}
		//项目号
		if (StringUtils.isNotBlank(itemId) && StringUtils.isNotBlank(itemId)) {
			from.append(" and exists (select 1 from ContractItemMaininfo im where lower(im.conItemId) like '%"+StringUtils.lowerCase(StringUtils.trim(itemId))+"%' and r.contractItemMaininfo = im.conItemMinfoSid ) ");
		}
		if(StringUtils.isNotBlank(customerName)){
			YXClientCode yc = (YXClientCode) commonDao.uniqueResult(" from YXClientCode yc where yc.fullName = '"+customerName+"'");
			if(yc == null){
				customer = null;
				if (StringUtils.isNotBlank(customerName)) {
					from.append(" and yc.fullName like '%").append(customerName).append("%'");
				}
			}
		}
		//客户ID
		if(customer!=null&&!"".equals(customer)) {
			from.append(" and yc.id = " + customer);
		}
		//大于含税
		if(minRealTaxBillAmount!=null&&!"".equals(minRealTaxBillAmount)) {
			from.append(" and r.realTaxBillAmount >= " + minRealTaxBillAmount);
		}
		//小于含税
		if(maxRealTaxBillAmount!=null&&!"".equals(maxRealTaxBillAmount)) {
			from.append(" and r.realTaxBillAmount <= " + maxRealTaxBillAmount);
		}
		//大于不含税
		if(minRealBillAmount!=null&&!"".equals(minRealBillAmount)) {
			from.append(" and r.realTaxBillAmount/ym.info >= " + minRealBillAmount);
		}
		//小于不含税
		if(maxRealBillAmount!=null&&!"".equals(maxRealBillAmount)) {
			from.append(" and r.realTaxBillAmount/ym.info <= " + maxRealBillAmount);
		}
		//大于合同金额
		if(minConAmount!=null&&!"".equals(minConAmount)) {
			from.append(" and cm.conTaxTamount >= " + minConAmount);
		}
		//小于合同金额
		if(maxConAmount!=null&&!"".equals(maxConAmount)) {
			from.append(" and cm.conTaxTamount <= " + maxConAmount);
		}
		if (StringUtils.isNotBlank(monthSel) && StringUtils.isNotBlank(yearSel)) {
			from.append(" and to_char(r.realPredBillDate,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		else if(isFromMenu){
			Calendar calendar = Calendar.getInstance();
			int defaultYear = calendar.get(Calendar.YEAR);
			yearSel = defaultYear+"";
			int defaultMonth = calendar.get(Calendar.MONTH)+1;
			monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
			DecimalFormat d=new DecimalFormat("00");
			String month=d.format(defaultMonth);
			from.append(" and to_char(r.realPredBillDate,'yyyy-mm')= '").append(defaultYear).append("-").append(month).append("'");
		}
		if(startDate!=null&&startDate.trim().length()>0){
			from.append(" and r.realPredBillDate>= to_date('"+startDate+"','yyyy-mm-dd')");
		}
		if(endDate!=null&&endDate.trim().length()>0){
			from.append(" and r.realPredBillDate<= to_date('"+endDate+"','yyyy-mm-dd')");
		}
		from.append(" order by decode(r.applyBillState,null,'0','3','4','4','3','7','5','5','6','6','7',r.applyBillState),cm.conId,r.realPredBillDate");	

		String hql1[] = new String[2];
		hql1[0]="select count(*) "+from.toString();
		hql1[1]=select+from.toString();
		return hql1;
	}

	public boolean isRelationReceipt(Long planId) {
		RelationBillAndReceipt relation = (RelationBillAndReceipt) commonDao.uniqueResult(" select rr from RelationBillAndReceipt rr where rr.receiptRealId = ", planId);
		if(relation != null){
			return true;
		}
		else{
			return false;
		}
	}
	public String showSelectApplyBillQuery(Long[] monthlyBillproSids) {
		StringBuffer hql = new StringBuffer();
		hql.append("select r,yc,cm,(select cg.itemStageName from ContractItemStage cg " +
				"where cg.conIStageSid = r.conItemStage)," +
				" (select im.itemResDept from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)," +
				"(select yl.name from YXLinkMan yl where yl.id = cm.linkManId) " +
				"from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
		"and r.conMainInfoSid = cm.conMainInfoSid ");
		hql.append(" and r.realConBillproSid IN (").append(StringUtils.join(monthlyBillproSids,",")).append(") ");
		hql.append(" order by r.realConBillproSid");
		return hql.toString();
	}

	public String showSelectRealReceipt(Long[] monthlyBillproSids) {
		StringBuffer hql = new StringBuffer();
		hql.append("select r,yc,cm,(select cg.itemStageName from ContractItemStage cg " +
				"where cg.conIStageSid = r.conItemStage)," +
				" (select im.itemResDept from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)  " +
				"from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
		"and r.realTaxBillAmount != r.realTaxReceAmount and r.billType <> '4' ");
		hql.append(" and r.realConBillproSid IN (").append(StringUtils.join(monthlyBillproSids,",")).append(") ");
		hql.append(" order by r.realConBillproSid");
		return hql.toString();
	}


	public String showSelectRealReceipt(Long realId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select r,yc,cm,(select cg.itemStageName from ContractItemStage cg " +
				"where cg.conIStageSid = r.conItemStage)," +
				" (select im.itemResDept from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)  " +
				"from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
		"and r.realTaxBillAmount != r.realTaxReceAmount and r.billType <> '4' ");
		hql.append(" and r.realConBillproSid = ").append(realId);
		return hql.toString();
	}

	public String showRecitpQuery(String[] billNature,Long[] conId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select r,yc,cm,(select cg.itemStageName from ContractItemStage cg " +
				"where cg.conIStageSid = r.conItemStage)," +
				" (select im.itemResDept from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid)  " +
				"from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
		"and r.billType = '4' ");
		String nature="";
		for(int k=0;k<billNature.length;k++){
			nature+="'"+billNature[k]+"'";
			if(k<billNature.length-1){
				nature+=",";
			}

		}
		hql.append(" and r.billNature IN (").append(nature).append(") ");
		hql.append(" and r.conMainInfoSid IN (").append(StringUtils.join(conId,",")).append(") ");
		hql.append(" order by r.realConBillproSid");
		return hql.toString();
	}
	public String showTheSameConId(String conId) {
		Long uid = UserUtils.getUser().getId();
		StringBuffer from = new StringBuffer();
		from.append("select r,yc,cm,(select im from ContractItemMaininfo im where r.contractItemMaininfo = im.conItemMinfoSid),(select cg.itemStageName from ContractItemStage cg " +
				"where cg.conIStageSid = r.conItemStage)," +
				"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
				" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType != 4 ) , " +
				"(select count(*) from ApplyBill b ,BillandProRelaion br where " +
		" b.billApplyId = br.applyBill and br.realContractBillandRecePlan = r.realConBillproSid and  b.billType = 4 ) " );
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.saleMan = "+ uid);
		from.append(" and cm.conId = '").append(conId).append("'");	
		return from.toString();
	}
	public String showBillReceiptQuery(Long confirm, Long selectState) {
		StringBuffer str = new StringBuffer();
		str.append("select bill,yc,emp, (select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = bill.contractMainInfo ) " +
				" from ApplyBill bill,YXClientCode yc,Employee emp " +
				"where bill.employeeId = emp.id " +      
				"and yc.is_active != 0 " +
				"and bill.customerId = yc.id " +
		"and bill.applyBillState = 3 "); /*+
				"and bill.employeeId = "+ uid); /*+
				"and bill.applyBillState = 3");*/
		if(confirm!=null&&!"".equals(confirm)) {
			if (confirm == 1) {
				str.append(" and bill.billApplyId in(select inf.applyInvoiceId from InvoiceInfo inf)");
			} 
			else if(confirm == 2){
				str.append(" and bill.billApplyId not in(select inf.applyInvoiceId from InvoiceInfo inf)");
			}
		}
		if(selectState!=null&&!"".equals(selectState)) {
			if (selectState == 1) {
				str.append(" and bill.sign = 0");
			}
			else if(selectState == 2){
				str.append(" and bill.sign = 1");
			}
			else{
				str.append("");
			}
		}
		str.append(" order by bill.billApplyId DESC");	
		return str.toString();
	}
	public String[] showReceiptToBillQuery(Integer relationState,Long customerId,
			String contractId, Double minBillAmount, Double maxBillAmount,
			String stratReceiptDate, String endReceiptDate) {
		Long uid = UserUtils.getUser().getId();
		String select="select bill,yc,ii,(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = ii.contractMainSid)," +
		"(select count(*) from InvoiceInfo i where i.applyInvoiceId = bill.billApplyId ) ";
		StringBuffer str = new StringBuffer();
		str.append(" from ApplyBill bill,YXClientCode yc,InvoiceInfo ii " +
				"where yc.is_active != 0 " +
				" and bill.customerId = yc.id " +
				" and ii.applyInvoiceId = bill.billApplyId " +
				" and ii.type = 4 " +
				" and ii.byId = " + uid);
		if (StringUtils.isNotBlank(contractId)) {
			str.append(" and exists (select 1 from ContractMainInfo cm where lower(cm.conId) like '%").append(StringUtils.lowerCase(StringUtils.trim(contractId))).append("%'")
			.append(" and cm.conMainInfoSid = ii.contractMainSid) ");
		}
		// 未关联发票
		if(new Integer(0).equals(relationState)){
			str.append(" and not exists (select 1 from RelationAmount ra where ra.invoiceToReceip = ii.id) ");
		}
		// 部分关联发票
		if(new Integer(1).equals(relationState)){
			str.append(" and ii.invoiceAmount > (select sum(ra.relateAmount) from RelationAmount ra where ra.invoiceToReceip = ii.id) ");
		}
		// 全额关联发票
		if(new Integer(2).equals(relationState)){
			str.append(" and ii.invoiceAmount = (select sum(ra.relateAmount) from RelationAmount ra where ra.invoiceToReceip = ii.id) ");
		}
		if (customerId!=null&&!"".equals(customerId)) {
			str.append(" and bill.customerId=").append(customerId);
		}
		if (minBillAmount!=null&&!"".equals(minBillAmount)) {
			str.append(" and ii.invoiceAmount >= ").append(minBillAmount);
		}
		if (maxBillAmount!=null&&!"".equals(maxBillAmount)) {
			str.append(" and ii.invoiceAmount <= ").append(maxBillAmount);
		}
		if (stratReceiptDate != null && !stratReceiptDate.equals("")) {
			str.append(" and ii.invoiceDate >= to_date('" + stratReceiptDate
					+ "','yyyy-mm-dd')");
		}
		if (endReceiptDate != null && !endReceiptDate.equals("")) {
			str.append(" and ii.invoiceDate <= to_date('" + endReceiptDate
					+ "','yyyy-mm-dd')");
		}
		str.append(" order by ii.id DESC");	

		String hql[] = new String[2];
		hql[0]="select count(*) "+str.toString();
		hql[1]=select+str.toString();
		return hql;
	}
	public boolean judgementBill(Long[] monthlyBillproSids) {
		List<RealContractBillandRecePlan> realList = commonDao.list(" from RealContractBillandRecePlan r where r.realConBillproSid in ("+StringUtils.join(monthlyBillproSids,",")+")");
		int flag = 0;
		for (RealContractBillandRecePlan real : realList) {
			if(real.getBillType().equals("4")){
				flag++;
				continue;
			}
			double realTaxBillAmount = real.getRealTaxBillAmount()==null ? 0.0 :real.getRealTaxBillAmount().doubleValue();
			double realTaxReceAmount = real.getRealTaxReceAmount()==null ? 0.0 :real.getRealTaxReceAmount().doubleValue();
			double relationReceAmount = real.getRelationReceAmount()==null ? 0.0 :real.getRelationReceAmount();
			double judgementAmount = realTaxReceAmount + relationReceAmount;
			if(Math.abs(realTaxBillAmount-judgementAmount)<0.00001){
				flag++;
			}
		}
		if(flag < realList.size())
		{
			return false;

		}
		else{
			return true;
		}
	}

	public boolean judgementBillSingle(Long realId) {
		RealContractBillandRecePlan real = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, realId);
		double realTaxBillAmount = real.getRealTaxBillAmount()==null ? 0.0 :real.getRealTaxBillAmount().doubleValue();
		double realTaxReceAmount = real.getRealTaxReceAmount()==null ? 0.0 :real.getRealTaxReceAmount().doubleValue();
		double relationReceAmount = real.getRelationReceAmount()==null ? 0.0 :real.getRelationReceAmount();
		double judgementAmount = realTaxReceAmount + relationReceAmount;
		if(realTaxBillAmount == judgementAmount){
			return true;
		}
		else{
			return false;
		}

	}
	public Double sumRelationBill(Long billAndReceipt) {
		Double sumAmount = (Double) commonDao.uniqueResult("select sum(rb.relationAmount) from RelationBillAndReceipt rb where rb.billRealId = "+ billAndReceipt);
		return sumAmount;
	}

	public Double sumRelationReceipt(Long billAndReceipt) {
		Double sumAmount = (Double) commonDao.uniqueResult(" select sum(rb.relationAmount) from RelationBillAndReceipt rb where rb.receiptRealId = "+ billAndReceipt);
		return sumAmount;
	}
	/**
	 * 计算计划内笔数和额度
	 */
	public List<Object> totalPlaninside(Long groupId, Long saleMan,
			String yearSel, String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer("select count(*),sum(mb.billTaxAmount) " +
				"from MonthlyBillpro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
		"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> countPlan = commonDao.list(totalPlanHql.toString());
		return countPlan;
	}
	/**
	 *  计划实际笔数和额度
	 */
	public List<Object> totalReal(Long groupId, Long saleMan, String yearSel,
			String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer("select count(*),sum(mb.planBillAmount) from MonthlyBillpro mb,RealContractBillandRecePlan t,ContractMainInfo cm where " +
				" t.conMainInfoSid = cm.conMainInfoSid and mb.planBillAmount > 0 " +
		"and mb.realContractBillandRecePlan = t.realConBillproSid ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> totalReal = commonDao.list(totalPlanHql.toString());
		return totalReal;
	}
	/**
	 * 计算实际计划内笔数和额度
	 */
	public List<Object> totalRealInside(Long groupId, Long saleMan,
			String yearSel, String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer("select count(*),sum(mb.planBillAmount) from MonthlyBillpro mb,RealContractBillandRecePlan t,ContractMainInfo cm where " +
				" t.conMainInfoSid = cm.conMainInfoSid and mb.planBillAmount > 0 " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
		"and mb.isInsidePlan = 0 ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> totalRealPlan = commonDao.list(totalPlanHql.toString());
		return totalRealPlan;
	}
	/**
	 * 计划内收款笔数和额度
	 */
	public List<Object> totalRecePlaninside(Long groupId, Long saleMan,
			String yearSel, String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer("select count(*),sum(mb.receTaxAmount) " +
				"from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm where " +
				" t.conMainInfoSid = cm.conMainInfoSid " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
		"and mb.isInsidePlan = 0  ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> countPlan = commonDao.list(totalPlanHql.toString());
		return countPlan;
	}
	/**
	 * 计算实际收款笔数和额度
	 */
	public List<Object> totalReceReal(Long groupId, Long saleMan,
			String yearSel, String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer("select count(*),sum(mb.alreadyArrivedAmount) " +
				" from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where t.conMainInfoSid = cm.conMainInfoSid " +
				"and mb.alreadyArrivedAmount > 0  " +
		"and mb.realContractBillandRecePlan = t.realConBillproSid ");
		//日期范内围
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> countPlan = commonDao.list(totalPlanHql.toString());
		return countPlan;
	}
	/**
	 * 计算实际计划内笔数和额度  5 6
	 */
	public List<Object> totalReceRealInside(Long groupId, Long saleMan,
			String yearSel, String monthSel) {
		StringBuffer totalPlanHql = new StringBuffer(" select count(*),sum(mb.alreadyArrivedAmount) from MonthlyRecepro mb,RealContractBillandRecePlan t,ContractMainInfo cm " +
				"where mb.alreadyArrivedAmount > 0 " +
				"and t.conMainInfoSid = cm.conMainInfoSid  " +
				"and mb.realContractBillandRecePlan = t.realConBillproSid " +
		"and mb.isInsidePlan = 0 ");
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			totalPlanHql.append("and to_char(mb.billproMonth,'yyyy-mm') = '").append(yearSel).append("-").append(monthSel).append("' ");
		}
		totalPlanHql.append(" and exists (select 1 from OrganizationTree tr,Employee emp where emp.id = cm.saleMan and emp.position = tr.id and tr.organizationCode like '"+groupId+"%') ");
		if (saleMan!=null&&!"".equals(saleMan)) {	
			totalPlanHql.append(" and cm.saleMan = " + saleMan);
		}
		List<Object> countPlan = commonDao.list(totalPlanHql.toString());
		return countPlan;
	}

	/**
	 * 显示开票精度统计明细
	 */
	public PageInfo billDetialQuery(PageInfo info, Long empId, String yearSel,
			String monthSel) {
		String select = "select r,yc,cm ," +
		"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
		"(select e.name from Employee e where e.id = cm.saleMan),month ";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm,MonthlyBillpro month where " +
				"cm.conCustomer = yc.id and r.realConBillproSid = month.realContractBillandRecePlan " +
		"and r.conMainInfoSid = cm.conMainInfoSid ");

		if (empId != null ) {
			from.append(" and cm.saleMan = "+empId+" ");
		}
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			from.append("and to_char(month.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		from.append(" order by month.isInsidePlan,cm.conId,month.actualBillDate");

		info = queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);		
		return  info;
	}
	/**
	 * 收款精度统计明细
	 */
	public PageInfo receDetialQuery(PageInfo info, Long empId, String yearSel,
			String monthSel) {
		String select = "select r,yc,cm ," +
		"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
		"(select e.name from Employee e where e.id = cm.saleMan)," +
		" month ";
		StringBuffer from = new StringBuffer();
		from.append("from RealContractBillandRecePlan r,YXClientCode yc,MonthlyRecepro month,ContractMainInfo cm where " +
				"cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
		"and r.realConBillproSid = month.realContractBillandRecePlan ");
		if (empId != null ) {
			from.append(" and cm.saleMan = "+empId+" ");
		}
		if(StringUtils.isNotBlank(yearSel) && StringUtils.isNotBlank(monthSel)){
			from.append("and to_char(month.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");
		}
		from.append(" order by month.isInsidePlan,cm.conId,month.actualArrivedDate");
		info=queryService.listQueryResult("select count(*) "+from.toString(),select+from.toString(), info);
		return info;
	}
	public Boolean isBill(Long applyBillId) {
		Long count = (Long) commonDao.uniqueResult(" select count(*) from InvoiceInfo ii where ii.applyInvoiceId = ?", applyBillId);
		if(count > 0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 验证是否能否合并开票
	 */
	public Boolean isSameReal(Long[] monthlyBillproSids) {
		Boolean flag = true;
		if (monthlyBillproSids != null) {
			List<Long> isSame = commonDao.list(" select distinct p.conMainInfoSid,p.contractItemMaininfo,p.conItemStage,p.billNature,p.billType from RealContractBillandRecePlan p where p.realConBillproSid in ("+StringUtils.join(monthlyBillproSids,",")+")");
			//Long count = (Long) commonDao.uniqueResult(" select count(*) from ApplyBill bill where bill.realPlanId in ( "+StringUtils.join(monthlyBillproSids)+")");
			if(isSame.size() > 1){
				flag =  false;
			}
			//else if(count > 0){
			//flag =  false;
			//}
			else{
				flag = true;
			}

		}
		return flag;
	}
	
	
	public Boolean isSameRealById(Long[] monthlyBillproSids) {
		Boolean flag = true;
		if (monthlyBillproSids != null) {
			List<Long> isSame = commonDao.list(" select distinct p.conMainInfoSid,p.billType from RealContractBillandRecePlan p where p.realConBillproSid in ("+StringUtils.join(monthlyBillproSids,",")+")");
			//Long count = (Long) commonDao.uniqueResult(" select count(*) from ApplyBill bill where bill.realPlanId in ( "+StringUtils.join(monthlyBillproSids)+")");
			if(isSame.size() > 1){
				flag =  false;
			}
			//else if(count > 0){
			//flag =  false;
			//}
			else{
				flag = true;
			}

		}
		return flag;
	}
	/**
	 * 合并
	 */
	public void mergeRealPlan(Long[] monthlyBillproSids) {
		String sql = " from RealContractBillandRecePlan plan " +
		"where plan.realConBillproSid in ("+StringUtils.join(monthlyBillproSids,",")+") order by plan.realConBillproSid ";
		List<RealContractBillandRecePlan> realList = commonDao.list(sql);
		RealContractBillandRecePlan plan = realList.get(0);
		for (int i = 1; i < realList.size(); i++) {
			RealContractBillandRecePlan plan1 = realList.get(i);
			//基准
			plan.setRealBillAmount(plan.getRealBillAmount().add(plan1.getRealBillAmount()));
			plan.setRealReceAmount(plan.getRealReceAmount().add(plan1.getRealReceAmount()));
			//含税
			plan.setRealTaxReceAmount(plan.getRealTaxReceAmount().add(plan1.getRealTaxReceAmount()));
			plan.setRealTaxBillAmount(plan.getRealTaxBillAmount().add(plan1.getRealTaxBillAmount()));
			//收款
			commonDao.delete(plan1);
		}
		commonDao.update(plan);
	}

	/**
	 * 提交红冲申请单
	 */	 
	public ProcessResult applyHongChong(Long invoiceId, List<HongChongRelationPlan> hongChongRelation) {
		ProcessResult result = new ProcessResult();
		InvoiceInfo ii = (InvoiceInfo) commonDao.load(InvoiceInfo.class,invoiceId);
		// 保存红冲申请单信息
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class,ii.getApplyInvoiceId());
		HongChongApplyBill hbill = new HongChongApplyBill();
		//添加申请单id与红冲申请单关系,就上保存下申请单ID
		hbill.setApplyBillId(bill.getBillApplyId());
		hbill.setApplyBillState(bill.getApplyBillState());
		hbill.setContractMainInfo(bill.getContractMainInfo());
		hbill.setApplyId(bill.getApplyId());
		hbill.setBillAmountTax(bill.getBillAmountTax());
		hbill.setBillApplyNum(bill.getBillApplyNum());
		//添加红冲申请单号
		hbill.setHongChongApplyBillNum(codeGenerateService.generateHCBillCode());
		hbill.setBillContent(bill.getBillContent());
		hbill.setCustomerId(bill.getCustomerId());
		hbill.setBillNature(bill.getBillNature());
		hbill.setBillType(bill.getBillType());
		hbill.setBillSpot(bill.getBillSpot());
		hbill.setSign(bill.getSign());
		hbill.setStockOrg(bill.getStockOrg());
		hbill.setEmployeeId(bill.getEmployeeId());
		hbill.setInitIsNoContract(bill.getInitIsNoContract());
		hbill.setIsNoContract(bill.getIsNoContract());
		hbill.setOneOut(bill.getOneOut());
		hbill.setRealPlanId(bill.getRealPlanId());
		hbill.setRemarks(bill.getRemarks());
		hbill.setReturnReason(bill.getReturnReason());
		hbill.setHongChongState("1");
		commonDao.save(hbill);
		//保存红冲发票信息
		HongChongInvoiceInfo hii = new HongChongInvoiceInfo();
		hii.setContractMainSid(ii.getContractMainSid());
		//红冲发票表中关联红冲申请单id
		hii.setHongChongBIllId(hbill.getId());
		hii.setInvoiceAmount(ii.getInvoiceAmount());
		hii.setInvoiceId(ii.getId());
		hii.setInvoiceDate(ii.getInvoiceDate());
		hii.setInvoiceNo(ii.getInvoiceNo());
		hii.setRecordDate(ii.getRecordDate());
		commonDao.save(hii);
		ii.setHongChongState("1");
		commonDao.update(ii);
		//查询出计划信息,然后把计划与申请单信息关联到红冲关联表中
		List<BillandProRelaion> brList = commonDao.list(" from BillandProRelaion br where br.applyBill = ? ", bill.getBillApplyId());
		for (int i = 0; i < brList.size(); i++) {
			BillandProRelaion br = brList.get(i);
			RealContractBillandRecePlan orginalPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, br.getRealContractBillandRecePlan());
			HongChongRelationPlan hrp = new HongChongRelationPlan();
			hrp.setHongChongApplyBill(hbill.getId());
			hrp.setRealContractBillandRecePlan(orginalPlan.getRealConBillproSid());
			hrp.setRelateAmount(hongChongRelation.get(i).getRelateAmount());
			commonDao.save(hrp);	
		}
		result.addSuccessMessage("成功申请红冲");
		result.setSuccess(true);
		return result;	
	}
	/**
	 * 显示发票申请单信息
	 */
	public List<Object> shouInvoiceInfo(Long invoiceId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select bill,ii," +
				"(select cm from ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid)," +
				"(select emp from Employee emp,ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid and emp.id = cm.saleMan)," +
		"(select yc from YXClientCode yc,ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid and cm.conCustomer = yc.id) ");
		sql.append(" from ApplyBill bill,InvoiceInfo ii where ");
		sql.append("ii.applyInvoiceId = bill.billApplyId " +
				"and ii.id = "+invoiceId);
		List<Object> invoiceBillList = commonDao.list(sql.toString());
		return invoiceBillList;
	}

	/**
	 * 显示计划
	 */
	public List<Object[]> showPlanHongChong(Long invoiceId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select p,yc,cm,(select im from ContractItemMaininfo im where p.contractItemMaininfo = im.conItemMinfoSid)," +
		"(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = p.conItemStage) ");
		sql.append(" from ApplyBill bill,InvoiceInfo ii,BillandProRelaion br,RealContractBillandRecePlan p,YXClientCode yc,ContractMainInfo cm where ");
		sql.append("ii.applyInvoiceId = bill.billApplyId " +
				"and cm.conCustomer = yc.id " +
				"and p.conMainInfoSid = cm.conMainInfoSid " +
				"and br.applyBill = bill.billApplyId " +
				"and br.realContractBillandRecePlan = p.realConBillproSid " +
				"and ii.id = "+invoiceId);
		List<Object[]> invoiceBillList = commonDao.list(sql.toString());
		return invoiceBillList;
	}

	/**
	 * 发票,申请单信息   确认红冲页面
	 */
	public List<Object> showConfirmInvoiceInfo(Long invoiceId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select bill,ii," +
				"(select cm from ContractMainInfo cm where bill.contractMainInfo = cm.conMainInfoSid)," +
				"(select emp from Employee emp where bill.employeeId = emp.id)," +
		"(select yc from YXClientCode yc,ContractMainInfo cm where cm.conCustomer = yc.id and bill.contractMainInfo = cm.conMainInfoSid) ");
		sql.append(" from HongChongApplyBill bill,HongChongInvoiceInfo ii where ");
		sql.append("ii.hongChongBIllId = bill.id " +
				"and ii.id = "+invoiceId);
		List<Object> invoiceBillList = commonDao.list(sql.toString());
		return invoiceBillList;
	}
	/**
	 * 计划信息    确认红冲页面
	 */
	public List<Object[]> showConfirmPlanInfo(Long invoiceId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select p,yc,cm,(select im from ContractItemMaininfo im where p.contractItemMaininfo = im.conItemMinfoSid)," +
		"(select cg.itemStageName from ContractItemStage cg where cg.conIStageSid = p.conItemStage),br ");
		sql.append(" from HongChongApplyBill bill,HongChongInvoiceInfo ii,HongChongRelationPlan br,RealContractBillandRecePlan p,YXClientCode yc,ContractMainInfo cm where ");
		sql.append("ii.hongChongBIllId = bill.id " +
				"and cm.conCustomer = yc.id " +
				"and p.conMainInfoSid = cm.conMainInfoSid " +
				"and br.hongChongApplyBill = bill.id " +
				"and br.realContractBillandRecePlan = p.realConBillproSid " +
				"and ii.id = "+invoiceId);
		List<Object[]> invoiceBillList = commonDao.list(sql.toString());
		return invoiceBillList; 
	}
	/**
	 *  取消一张发票,生成一个新的计划重新申请开票
	 *  选择一张发票,执行此操作.  删除本发票,减去申请单,原计划中数据字段
	 *  生成一新的计划,重新开票
	 */
	public ProcessResult returnInvoicePlan(Long invoiceId) {
		ProcessResult result = new ProcessResult();
		HongChongInvoiceInfo hii = (HongChongInvoiceInfo) commonDao.load(HongChongInvoiceInfo.class, invoiceId);
		HongChongApplyBill hbill = (HongChongApplyBill) commonDao.load(HongChongApplyBill.class, hii.getHongChongBIllId());
		List<HongChongRelationPlan> hrList = commonDao.list(" from HongChongRelationPlan hr where hr.hongChongApplyBill = ? ", hbill.getId());
		//发票信息
		InvoiceInfo ii = (InvoiceInfo) commonDao.load(InvoiceInfo.class,hii.getInvoiceId());
		//申请单信息
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class,hbill.getApplyBillId());
		//计划与申请单关联信息
		List<BillandProRelaion> brList = commonDao.list(" from BillandProRelaion br where br.applyBill = ? ", bill.getBillApplyId());
		if(hbill.getContractMainInfo() == null){
			if(ii.getInvoiceAmount() == bill.getBillAmountTax()){
				bill.setApplyBillState(1L);
				bill.setBillApplyNum(null);
				bill.setReturnReason(null);
				bill.setReturnDate(null);
				commonDao.update(bill);
				commonDao.delete(ii);
			}else{
				//如果发票金额和申请单金额不相同 就拆分申请单 
				ApplyBill newBill = null;
				try {
					newBill = (ApplyBill) BeanUtils.cloneBean(bill);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(),e);
				}
				//更新原来的申请单

				bill.setBillAmountTax(bill.getBillAmountTax() - ii.getInvoiceAmount());
				Double noTaxMoney = TaxChange.TaxToNoTaxDouble(bill.getBillAmountTax(),bill.getBillType());
				newBill.setBillAmountNotax(noTaxMoney);
				//新拆分出来的申请单
				newBill.setBillApplyId(null);
				newBill.setBillApplyNum(null);
				newBill.setApplyBillState(1L);  //状态置为保存
				newBill.setBillAmountTax(ii.getInvoiceAmount());
				Double noTaxMoney1 = TaxChange.TaxToNoTaxDouble(newBill.getBillAmountTax(),newBill.getBillType());
				newBill.setBillAmountNotax(noTaxMoney1);
				newBill.setReturnReason(null);
				newBill.setReturnDate(null);
				hbill.setHongChongState("2"); //修改为已红冲
				commonDao.update(bill);
				commonDao.delete(ii);
				commonDao.save(newBill);
			}
			hbill.setHongChongState("2"); //修改为已红冲
			commonDao.update(hbill);
			result.addSuccessMessage("红冲成功!");
			result.setSuccess(true);
			return result;
		}
		//跟新关联表
		for (HongChongRelationPlan hr : hrList) {
			BillandProRelaion br = getBillandProRelaionByPlanIdFromList(hr.getRealContractBillandRecePlan(),brList);
			if(br!=null){	
				//减去红冲金额
				//关联
				br.setRelateAmount(br.getRelateAmount() - BigDecimalUtils.defaultIfNull(hr.getRelateAmount()));
				//已开票
				br.setRelateInvoiceAmount(BigDecimalUtils.defaultIfNull(br.getRelateInvoiceAmount()) - BigDecimalUtils.defaultIfNull(hr.getRelateAmount()));
				//如果关联金额为0,就删除关联
				if(br.getRelateAmount() == 0){
					commonDao.delete(br);
				}else{
					commonDao.update(br);
				}
			}
			//分割计划
			splitPlanByHongChong(hr.getRealContractBillandRecePlan(),BigDecimalUtils.defaultIfNull(hr.getRelateAmount()), ii.getInvoiceDate());
		}
		//更新申请单
		/**申请单中的申请金额减去发票金额*/
		bill.setBillAmountTax(bill.getBillAmountTax() - ii.getInvoiceAmount());
		Double noTaxMoney = TaxChange.TaxToNoTaxDouble(bill.getBillAmountTax(),bill.getBillType());
		bill.setBillAmountNotax(noTaxMoney);
		//如果申请单金额为0,删除
		if(bill.getBillAmountTax() == 0){
			commonDao.delete(bill);
		}else{
			commonDao.update(bill);
		}
		//修改红冲申请单中的红冲状态
		hbill.setHongChongState("2"); //修改为已红冲
		commonDao.update(hbill);

		//删除发票信息
		commonDao.delete(ii);
		result.addSuccessMessage("红冲成功!");
		result.setSuccess(true);
		return result;
	}
	/**
	 * 拆分红冲的计划
	 * @param planId
	 * @param hongchongAmount
	 * @param invocieDate
	 */
	private void splitPlanByHongChong(Long planId, double hongchongAmount, Date invocieDate) {
		//红冲金额为0,跳过
		if(hongchongAmount == 0 ){
			return;
		}
		RealContractBillandRecePlan orginalPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, planId);
		//合同信息
		ContractMainInfo cm = (ContractMainInfo)commonDao.load(ContractMainInfo.class, orginalPlan.getConMainInfoSid());
		///////
		//如果红冲金额等于开票金额,取消开票就可以
		if(orginalPlan.getRealTaxBillAmount().doubleValue() == hongchongAmount){
			/**已开票*/
			orginalPlan.setBillInvoiceAmount(null);
			/**应收*/
			orginalPlan.setShouldReceAmount(null);
			///
			commonDao.update(orginalPlan);
		}else{
			//如果不等,需要拆分
			RealContractBillandRecePlan newPlan = null;
			try {
				newPlan = (RealContractBillandRecePlan) BeanUtils.cloneBean(orginalPlan);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
			newPlan.setRealConBillproSid(null);

			/**计划开票*/
			newPlan.setRealTaxBillAmount(BigDecimalUtils.toBigDecial(hongchongAmount));
			//基准为不含税
			if(cm.getStandard().equals("2")){
				newPlan.setRealBillAmount(TaxChange.TaxToNoTaxBigDecimal(newPlan.getRealTaxBillAmount(), newPlan.getBillType()));
			}
			//基准为含税
			if(cm.getStandard().equals("1")){
				newPlan.setRealBillAmount(BigDecimalUtils.toBigDecial(hongchongAmount)); 
			}
			/**计划收款*/
			newPlan.setRealReceAmount(BigDecimalUtils.toBigDecial(hongchongAmount));	
			newPlan.setRealTaxReceAmount(BigDecimalUtils.toBigDecial(hongchongAmount));
			/**已开票*/
			newPlan.setBillInvoiceAmount(null);
			/**应收*/
			newPlan.setShouldReceAmount(null);
			/** 关联金额*/
			newPlan.setRelationReceAmount(null);
			//已收款在下边算,需要计算后得到
			newPlan.setApplyBillState(null);

			/**********************************************************/				
			/**更新原来计划中金额信息*/
			/**计划开票*/
			orginalPlan.setRealBillAmount(orginalPlan.getRealBillAmount().subtract(newPlan.getRealBillAmount()));
			orginalPlan.setRealTaxBillAmount(orginalPlan.getRealTaxBillAmount().subtract(newPlan.getRealTaxBillAmount()));
			/**计划收款*/
			orginalPlan.setRealReceAmount(orginalPlan.getRealReceAmount().subtract(newPlan.getRealReceAmount()));			
			orginalPlan.setRealTaxReceAmount(orginalPlan.getRealTaxReceAmount().subtract(newPlan.getRealReceAmount()));		
			/**已开票*/
			orginalPlan.setBillInvoiceAmount(orginalPlan.getBillInvoiceAmount() - hongchongAmount);
			/**应收*/
			orginalPlan.setShouldReceAmount(Math.min(orginalPlan.getBillInvoiceAmount(),orginalPlan.getRealTaxReceAmount().doubleValue()));
			/**已收款**/
			//如果原计划里已到款金额比计划收款金额大,多出来的部分放入新计划里
			if(orginalPlan.getRealArriveAmount()!=null
					&& orginalPlan.getRealArriveAmount().compareTo(orginalPlan.getRealTaxReceAmount()) > 0 ){
				newPlan.setRealArriveAmount(orginalPlan.getRealArriveAmount().subtract(orginalPlan.getRealTaxReceAmount()));
				orginalPlan.setRealArriveAmount(orginalPlan.getRealTaxReceAmount());
			}
			/**更新原来的计划*/
			commonDao.update(orginalPlan);
			/**保存新的计划*/
			commonDao.save(newPlan);
			/**如果是收据的话拆分关联收据*/
			if(orginalPlan.getBillType().equals("4")){
				splitRelationBillAndRece(orginalPlan.getRealConBillproSid(),newPlan.getRealConBillproSid(),hongchongAmount);
			}
		}

		//发票收据关联表
		contractCommonService.updateRelationAndReceiptShouldAmount(orginalPlan.getRealConBillproSid());
		//月度计划表
		contractCommonService.updatePlanStatistic(invocieDate,planId, true);
	}

	private BillandProRelaion getBillandProRelaionByPlanIdFromList(Long planId,List<BillandProRelaion> brList){
		for (BillandProRelaion billandProRelaion : brList) {
			if(billandProRelaion.getRealContractBillandRecePlan().equals(planId)){
				return billandProRelaion;
			}
		}
		return null;
	}
	/**
	 * 拆分关联收据表
	 */
	public void splitRelationBillAndRece(Long orginalReceiptPlanId,Long newReceiptPlanId, double hongchongAmount){
		RelationBillAndReceipt receipt = (RelationBillAndReceipt) commonDao.uniqueResult(" " +
				"select rr from RelationBillAndReceipt rr where rr.receiptRealId = ?", orginalReceiptPlanId);
		RelationBillAndReceipt newReceiptPlan = null;
		try {
			newReceiptPlan = (RelationBillAndReceipt) BeanUtils.cloneBean(receipt);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		newReceiptPlan.setId(null);
		newReceiptPlan.setReceiptRealId(newReceiptPlanId);
		newReceiptPlan.setRelationAmount(hongchongAmount);
		receipt.setRelationAmount(receipt.getRelationAmount() - hongchongAmount);
		commonDao.update(receipt);
		commonDao.save(newReceiptPlan);
	}
	public ProcessResult returnConfirm(Long billId) {
		ProcessResult result = new ProcessResult();
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class, billId);
		if(bill.getApplyBillState() == 3){
			bill.setApplyBillState(2L);
			commonDao.update(bill);
			invoiceService.updatePlanApplyBillState(2L, bill.getBillApplyId());
			result.addSuccessMessage("申请单已取消确认");
		}
		else{
			result.addErrorMessage("该申请单不是确认通过状态");
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	public ProcessResult returnProcessed(Long billId) {
		ProcessResult result = new ProcessResult();
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class, billId);
		if(bill.getApplyBillState() == 7){
			bill.setApplyBillState(3L);
			commonDao.update(bill);
			invoiceService.updatePlanApplyBillState(3L, bill.getBillApplyId());
			result.addSuccessMessage("申请单已取消处理");
		}
		else{
			result.addErrorMessage("该申请单不是已处理状态");
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	public Double calPercentage(Double divisor, Double divdend) {
		divisor = divisor != null ? divisor: 0 ; 
		divdend = divdend != null ? divdend: 0 ; 
		if(!divdend.equals(0D)){
			return ( divisor / divdend ) * 100;
		}
		return 0.00;
	}
	public Double calLogicTotal(Long minMonth, Long maxMonth) {
		//逻辑帐龄条件
		StringBuffer select = new StringBuffer();
		select.append(" select sum(nvl(p.shouldReceAmount,0) - nvl(p.realArriveAmount,0)) from RealContractBillandRecePlan p where 1=1");
		if(minMonth != null){
			select.append(" and p.logicMonthAccountAge >= ").append(minMonth);
		}
		if(maxMonth != null){
			select.append(" and p.logicMonthAccountAge <= ").append(maxMonth);
		}
		select.append(" and ( p.shouldReceAmount - decode(p.realArriveAmount,null,0,p.realArriveAmount) ) > 0 and p.shouldReceAmount is not null ");
		return (Double) commonDao.uniqueResult(select.toString());
	}

	public Double calRealTotal(Long minMonth, Long maxMonth) {
		//时间帐龄条件
		StringBuffer select = new StringBuffer();
		select.append(" select sum(nvl(p.shouldReceAmount,0) - nvl(p.realArriveAmount,0)) from RealContractBillandRecePlan p where 1=1 ");
		if(minMonth != null){
			select.append(" and p.realMonthAccountAge >= ").append(minMonth);
		}
		if(maxMonth != null){
			select.append(" and p.realMonthAccountAge <= ").append(maxMonth);
		}
		select.append(" and ( p.shouldReceAmount - decode(p.realArriveAmount,null,0,p.realArriveAmount) ) > 0 and p.shouldReceAmount is not null ");
		return (Double) commonDao.uniqueResult(select.toString());
	}
	public void splitBillAmount(Long realPlanId, Double splitAmount,
			Double splitReceAmount) {
		RealContractBillandRecePlan oldPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, realPlanId);
		ContractMainInfo cm = (ContractMainInfo)commonDao.load(ContractMainInfo.class, oldPlan.getConMainInfoSid());
		try {
			RealContractBillandRecePlan newPlan= (RealContractBillandRecePlan) BeanUtils.cloneBean(oldPlan);
			newPlan.setRealConBillproSid(null);

			newPlan.setRealBillAmount(BigDecimalUtils.toBigDecial(splitAmount));

			//基准为不含税
			if(cm.getStandard().equals("2")){
				newPlan.setRealBillAmount(BigDecimalUtils.toBigDecial(splitAmount));
				Double TaxMoney = TaxChange.NoTaxToTaxDouble(oldPlan.getRealBillAmount().doubleValue(),oldPlan.getBillType());
				newPlan.setRealTaxBillAmount(BigDecimalUtils.toBigDecial(TaxMoney));
			}
			//基准为含税
			if(cm.getStandard().equals("1")){
				newPlan.setRealBillAmount(BigDecimalUtils.toBigDecial(splitAmount)); 
				newPlan.setRealTaxBillAmount(BigDecimalUtils.toBigDecial(splitAmount));
			}

			newPlan.setRealReceAmount(BigDecimalUtils.toBigDecial(splitReceAmount));	
			newPlan.setRealTaxReceAmount(BigDecimalUtils.toBigDecial(splitReceAmount));
			commonDao.save(newPlan);

			oldPlan.setRealBillAmount(oldPlan.getRealBillAmount().subtract(newPlan.getRealBillAmount()));
			oldPlan.setRealTaxBillAmount(oldPlan.getRealTaxBillAmount().subtract(newPlan.getRealTaxBillAmount()));

			oldPlan.setRealReceAmount(oldPlan.getRealReceAmount().subtract(newPlan.getRealReceAmount()));			
			oldPlan.setRealTaxReceAmount(oldPlan.getRealTaxReceAmount().subtract(newPlan.getRealReceAmount()));			

			commonDao.update(oldPlan);

			if(oldPlan.getBillType().equals("4")){
				List<RelationBillAndReceipt> relationReceiptList = commonDao.list(" select rr from RelationBillAndReceipt rr where rr.receiptRealId = ?", oldPlan.getRealConBillproSid());
				//现在只允许有一个关联的收据拆分
				if(relationReceiptList.size() == 1){
					RelationBillAndReceipt oldRelation = relationReceiptList.get(0);
					RelationBillAndReceipt newRelation= (RelationBillAndReceipt) BeanUtils.cloneBean(oldRelation);
					//老关联金额
					oldRelation.setRelationAmount(oldRelation.getRelationAmount() - splitReceAmount);
					oldPlan.setRelationReceAmount(oldRelation.getRelationAmount());
					//新关联
					newRelation.setId(null);
					newRelation.setReceiptRealId(newPlan.getRealConBillproSid());
					newRelation.setRelationAmount(splitReceAmount);
					newPlan.setRelationReceAmount(splitReceAmount);
					//拆分应收
					if(oldRelation.getReceRelationAmount() != null){
						//多出的应收金额
						double remainShouldRelationAmount = Math.max(0,oldRelation.getReceRelationAmount() - oldRelation.getRelationAmount());
						oldRelation.setReceRelationAmount(oldRelation.getReceRelationAmount() - remainShouldRelationAmount);
						newRelation.setReceRelationAmount(remainShouldRelationAmount);
						//更新计收据应收
						oldPlan.setShouldReceAmount(oldRelation.getReceRelationAmount());
						newPlan.setShouldReceAmount(newRelation.getReceRelationAmount());
					}
					commonDao.update(oldPlan);
					commonDao.update(newPlan);
					commonDao.update(oldRelation);
					commonDao.save(newRelation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public List<InvoiceInfo> showInvoiceInfo(Long billId) {
		List<InvoiceInfo> iiList = commonDao.list(" from InvoiceInfo ii where ii.applyInvoiceId = ?", billId);
		return iiList;
	}



	/**
	 * 删除已被确认的发票
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult delHasSuredInvoice( Long invoiceId ){
		ProcessResult result = new ProcessResult();

		//获取发票
		InvoiceInfo invoice = (InvoiceInfo)commonDao.load(InvoiceInfo.class , invoiceId);

		//获取发票申请
		ApplyBill ab = (ApplyBill)commonDao.load( ApplyBill.class , invoice.getApplyInvoiceId() );

		//获取实际计划
		List<RealContractBillandRecePlan> realPlanList = commonDao.list(" select r  from   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab    ,InvoiceInfo ii " +
					"  where r.realConBillproSid = br.realContractBillandRecePlan  " +
					"  and br.applyBill = ab.billApplyId  " +
					"  and ab.billApplyId = ii.applyInvoiceId  " +
					"  and ii.id = ? order by r.realPredBillDate desc ", invoiceId);

		//获取月度计划
		List<MonthlyBillpro> mrList  =  commonDao.list("  select m  from MonthlyBillpro m,   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab    ,InvoiceInfo ii " +
					"  where  m.realContractBillandRecePlan = r.realConBillproSid " +
					"  and  r.realConBillproSid = br.realContractBillandRecePlan  " +
					"  and br.applyBill = ab.billApplyId  " +
					"  and ab.billApplyId = ii.applyInvoiceId  " +
					"  and ii.id = ? order by r.realPredBillDate desc ", invoiceId);

		//如果是发票，检查是否存在相关联的收据
		for( RealContractBillandRecePlan plan : realPlanList){
			if (!plan.getBillType().equals("4") && getDefaultIfNull(plan.getRelationReceAmount())>0) {
				//			logger.error(planId + "已经有关联过收据,先取消收据关联");
				result.addErrorMessage("该计划已经关联过收据,需要手工处理");
				logger.error("该计划已经关联过收据,需要手工处理,不能取消开票");
				return result;
			}
		}


		// when开票申请金额  = 删除发票金额  ，      修改开票申请开票状态 ， 计划开票状态
		if( ab.getBillAmountTax()  ==  invoice.getInvoiceAmount() ){
			invoiceService.updatePlanApplyBillState(7L, ab.getBillApplyId());
			for( RealContractBillandRecePlan plan : realPlanList){
				plan.setApplyBillState(7L);
				commonDao.update(plan);
			}
		}

		//剩余金额
		Double balance ;
		Double sum = (Double)commonDao.uniqueResult(
				" select sum(r.billInvoiceAmount) " +
				"  from   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab  , InvoiceInfo ii " +
				"  where r.realConBillproSid = br.realContractBillandRecePlan  " +
				"  and br.applyBill = ab.billApplyId  " +
				"  and ab.billApplyId = ii.applyInvoiceId  and ii.id = ? ", invoiceId  );
		balance = sum - invoice.getInvoiceAmount();
		//更新实际计划
		if(balance==0){     //当剩余金额==0时，说明计划金额总和==收款金额，所以全部归零/null
			for( RealContractBillandRecePlan r:  realPlanList){
				r.setBillInvoiceAmount(null);
				r.setRealNowBillDate(null);
				commonDao.update(r);
				//更新应收
				this.updateShouldRece(r, 0.0);
			}
		}
		else{
			int i =0;
			boolean succ = false;   //是否分配完成
			for( RealContractBillandRecePlan r:realPlanList ){
				if(succ){  //分配完成，其余计划全部置0
					r.setBillInvoiceAmount(null);
					r.setRealNowBillDate(null);
					commonDao.update(r);
				}
				else{
					i++;
					balance = balance - r.getRealTaxReceAmount().doubleValue();   //剩余填充计划金额
					if( balance>0 ){
						if( realPlanList.size()!= i){ 
							r.setBillInvoiceAmount(r.getRealTaxReceAmount().doubleValue());
						}else{     //最后一个了，填充全部金额
							r.setBillInvoiceAmount(r.getRealTaxReceAmount().add(new BigDecimal(balance)).doubleValue());
						}
						Date maxBillDate = 	this.getMaxBillDate( invoice.getId() ,   r.getRealConBillproSid());
						r.setRealNowReceDate(maxBillDate);
						commonDao.update(r);
					}
					else{
						Date maxBillDate = 	this.getMaxBillDate( invoice.getId() ,   r.getRealConBillproSid());
						r.setBillInvoiceAmount(r.getRealTaxReceAmount().add(new BigDecimal(balance)).doubleValue());
						r.setRealNowReceDate(maxBillDate);
						commonDao.update(r);
						succ = true;  //分配完成
					}
				}
			}
		}

		//更新月度计划
		balance = invoice.getInvoiceAmount();
		for( MonthlyBillpro mr: mrList ){
			balance = balance - mr.getPlanBillAmount();
			if( balance>=0 ){
				if( mr.getIsInsidePlan() == 0){  //计划内的更新
					mr.setActualBillDate(null);
					mr.setPlanBillAmount(0.0);
					commonDao.update(mr);
				}
				else{
					//外的删除
					commonDao.delete(mr);
				}	
				if( balance==0 ){
					break;
				}
			}
			else{    //balance<0  说明计划中的金额>收款金额，故不需理会 计划内or计划外
				Date maxBillDate = 	this.getMaxBillDate( invoice.getId() ,  mr.getRealContractBillandRecePlan().getRealConBillproSid() );
				mr.setActualBillDate(maxBillDate);
				mr.setPlanBillAmount(balance*-1);
				commonDao.update(mr);
				break;
			}
		}


		//获取合同
		ContractMainInfo contract = (ContractMainInfo)commonDao.load(ContractMainInfo.class , invoice.getContractMainSid() );

		//更新合同统计字段
		Number billAmount = (Number) commonDao.uniqueResult(
				"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.conMainInfoSid = ? ", invoice.getContractMainSid() );
		Number receiptAmount = (Number) commonDao.uniqueResult(
				"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.conMainInfoSid = ? ", invoice.getContractMainSid());
		Number shouldAmount = (Number) commonDao.uniqueResult(
				"select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? ", invoice.getContractMainSid());
		contract.setBillInvoiceAmount(getDefaultIfNull(billAmount));
		contract.setBillReceiptAmount(getDefaultIfNull(receiptAmount));
		contract.setShouldReceAmount(getDefaultIfNull(shouldAmount));
		commonDao.update(contract);


		//更新项目统计字段
		if (StringUtils.equals(contract.getContractType(), "1")) {
			Set<ContractItemMaininfo> itemMainList = new HashSet<ContractItemMaininfo>();
			ContractItemMaininfo itemMain  = null;
			for( RealContractBillandRecePlan plan : realPlanList){
				if (plan.getContractItemMaininfo() != null) {
					itemMain  = (ContractItemMaininfo) commonDao.load( ContractItemMaininfo.class, plan.getContractItemMaininfo() );
					itemMainList.add ( itemMain );
				}
			}
			for( ContractItemMaininfo i :  itemMainList  ){
				Number ba = (Number) commonDao.uniqueResult(
						"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.contractItemMaininfo = ? ",
						i.getConItemMinfoSid());
				Number ra = (Number) commonDao.uniqueResult(
						"select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.contractItemMaininfo = ? ",
						i.getConItemMinfoSid());
				Number sa = (Number) commonDao.uniqueResult(
						"select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? ",
						i.getConItemMinfoSid());
				i.setBillInvoiceAmount(getDefaultIfNull(ba));
				i.setBillReceiptAmount(getDefaultIfNull(ra));
				i.setShouldReceAmount(getDefaultIfNull(sa));
				commonDao.update(i);
			}
		}


		//删除发票
		commonDao.delete(invoice);

		harvestService.modifyInvoiceAmount( invoice.getContractMainSid() );

		result.setSuccess(true);
		return result;
	}


	private Date getMaxBillDate( Long invoiceId, Long planId  ){
		Date maxBillDate = (Date)commonDao.uniqueResult("  select max(ii.invoiceDate) " +
				"  from   RealContractBillandRecePlan r,  BillandProRelaion br  , ApplyBill ab  , InvoiceInfo ii " +
				"  where r.realConBillproSid = br.realContractBillandRecePlan  " +
				"  and br.applyBill = ab.billApplyId  " +
				"  and ab.billApplyId = ii.applyInvoiceId  and ii.id <> ?" +
				"  and r.realConBillproSid = ? ", invoiceId ,   planId );
		return maxBillDate;
	}
	
	private RealContractBillandRecePlan updateShouldRece( RealContractBillandRecePlan r , Double   shouldRece){
		if(!r.getBillType().equals("4")){
			r.setShouldReceAmount(shouldRece);
		}else{
			//如果是收据，要重新算应收
			contractCommonService.updateReciptShouldAmount(r);
		}
		return r;
	}


	private Double getDefaultIfNull(Number n) {
		if (n != null) {
			return n.doubleValue();
		} else {
			return 0.0;
		}
	}





}
