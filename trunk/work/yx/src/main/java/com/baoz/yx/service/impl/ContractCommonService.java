package com.baoz.yx.service.impl;

import java.math.BigDecimal;
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
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXChargemanDepartment;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.bill.RelationBillAndReceipt;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.vo.ProcessResult;

@Service("contractCommonService")
@Transactional
public class ContractCommonService implements IContractCommonService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao yxCommonDao;

	protected Log logger = LogFactory.getLog(this.getClass());
	
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

	public void addChargeManAndDepartment(String departmentCode,
			String chargeManName) {
		if(StringUtils.isBlank(departmentCode)||StringUtils.isBlank(chargeManName)){
			return;
		}
		/**
		 * 通过输入负责人姓名查询是否存在
		 */
		List chargeManList = commonDao.list(
				"from YXChargeMan man where man.name=? and man.is_active='1'",
				chargeManName);
		if (chargeManList != null && chargeManList.size() > 0) {
			/**
			 * No1. 如果存在获取负责人的id，然后把这个id和部门code作为查询条件，
			 * 看看是否有关联，如果没有关联重新添加一个关联，如果有关联什么也不做
			 */
			YXChargeMan chargeMan = (YXChargeMan) chargeManList.get(0);
			List chargeDepartMentList = commonDao
					.list(
							"from YXChargemanDepartment depart where depart.chargemanid=? and depart.departmentid=?",
							chargeMan.getId(), departmentCode);
			if(chargeDepartMentList==null||chargeDepartMentList.size()==0)
			{
				YXChargemanDepartment departMent=new YXChargemanDepartment();
				departMent.setChargemanid(chargeMan.getId());
				departMent.setDepartmentid(departmentCode);
				commonDao.save(departMent);
			}

		} else {
			/**
			 * No2. 添加一个姓名，然后添加一个关联
			 */
			YXChargeMan chargeMan=new YXChargeMan();
			chargeMan.setName(chargeManName);
			chargeMan.setIs_active("1");
			commonDao.save(chargeMan);
			YXChargemanDepartment departMent=new YXChargemanDepartment();
			departMent.setChargemanid(chargeMan.getId());
			departMent.setDepartmentid(departmentCode);
			commonDao.save(departMent);
		}

	}

	public int checkItemInfoAmountEqualsMainInfoAmount(Long mainInfoId) {
		// TODO Auto-generated method stub
		//通过合同id查询合同费用
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainInfoId);
		//通过合同id查询项目，通过项目id查询项目费用的总和
		String hql="select sum(itemInfo.conItemAmountWithTax) from ContractItemInfo itemInfo,ContractItemMaininfo minfo" +
				"where itemInfo.contractItemMaininfo=minfo.conItemMinfoSid and minfo.contractMainInfo=?";
		BigDecimal big=(BigDecimal)commonDao.uniqueResult(hql, mainInfoId);
		if(big.doubleValue()==mainInfo.getConTaxTamount()){
			return 1;
		}else{
		    return 0;
		}
	}

	public int checkStagePlanAmountEqualsMainInfoAmount(Long mainInfoId) {
		// TODO Auto-generated method stub
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainInfoId);
		String hql="select sum(stagePlan.stageAmout) from ContractItemStagePlan stagePlan where stagePlan.contractMainSid=?";
		Double big=(Double)commonDao.uniqueResult(hql, mainInfoId);
		if(mainInfo.getConTaxTamount().doubleValue()==big.doubleValue())
		{
			return 1;
		}else{
			return 0;
		}
	}

	public int checkBillProAmountEqualsMainInfoAmount(Long mainInfoId) {
		// TODO Auto-generated method stub
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainInfoId);
		String hql="select sum() from InitContractBillpro billPro where billPro.conMainInfoSid=?";
		BigDecimal big=(BigDecimal)commonDao.uniqueResult(hql, mainInfoId);
		if(big.doubleValue()==mainInfo.getConTaxTamount()){
			return 1;
		}else{
		    return 0;
		}
	}

	public int checkPartInfoAmountEqualsMainInfoAmount(Long mainInfoId) {
		// TODO Auto-generated method stub
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainInfoId);
		String hql="select sum(infoPart.money) from ContractMaininfoPart infoPart where infoPart.contractmainid=?";
		Double big=(Double)commonDao.uniqueResult(hql, mainInfoId);
		if(mainInfo.getConTaxTamount().doubleValue()==big.doubleValue())
		{
			return 1;
		}else{
			return 0;
		}
	}

	public void setChangeStatusByInvoiceId(Long invoiceId) {
		// TODO Auto-generated method stub
		InvoiceInfo invoiceInfo=(InvoiceInfo)commonDao.load(InvoiceInfo.class, invoiceId);
		String hql="select sum(relation.relateAmount) from RelationAmount relation where relation.invoiceToReceip=?";
		Double relateAmount=(Double)commonDao.uniqueResult(hql, invoiceId);
		if(invoiceInfo.getInvoiceAmount().doubleValue()==relateAmount){
			invoiceInfo.setChangeState("1");
		}else{
			invoiceInfo.setChangeState("0");
		}
		commonDao.saveOrUpdate(invoiceInfo);
		
	}

	public void updateItemNoOnMessageAndAssistanceContract(String oldItemNo,
			String newItemNo) {
		// TODO Auto-generated method stub
		String applyHql="from ApplyMessage message where message.eventId=?";
		String assistanceHql="from AssistanceContract assistance where assistance.mainProjectId=?";
		List<ApplyMessage> appList=commonDao.list(applyHql, oldItemNo);
		List<AssistanceContract> assistanceList=commonDao.list(assistanceHql, oldItemNo);
		for (AssistanceContract assistanceContract : assistanceList) {
			assistanceContract.setMainProjectId(newItemNo);
			commonDao.update(assistanceContract);
		}
		for (ApplyMessage applyMessage : appList) {
			applyMessage.setEventId(newItemNo);
			commonDao.update(applyMessage);
		}
	}

	public YXClientCode getOneYXclientCode(Long id) {
		// TODO Auto-generated method stub
		YXClientCode clientCode=(YXClientCode)commonDao.load(YXClientCode.class, id);
		return clientCode;
	}

	public void updateBillAndReceplan(RealContractBillandRecePlan oldPlan,
			BillReceChangeHistory history) {
		RealContractBillandRecePlan plan=(RealContractBillandRecePlan)commonDao.load(RealContractBillandRecePlan.class, oldPlan.getRealConBillproSid());
		plan.setRealPredReceDate(oldPlan.getRealPredReceDate());
		history.setRealContractBillandRecePlan(plan);
		//增加计数
		Integer receChangeCount = plan.getReceChangeCount();
		if(receChangeCount == null){
			receChangeCount = 0;
		}
		receChangeCount++;
		plan.setReceChangeCount(receChangeCount);
		commonDao.update(plan);
		commonDao.save(history);
		
	}
	public void calBillInvoiceAmount(Long applyBillId){

		yxCommonDao.flushSession();
		/**
		 * No1.查询出同一申请单下面的InvoiceInfo开票金额的和
		 * **/
		String invoiceHql="select sum(info.invoiceAmount) from InvoiceInfo info where info.applyInvoiceId=?";
		Double sum=(Double)commonDao.uniqueResult(invoiceHql, applyBillId);
		if(sum==null){
			sum=0.00;
		}
		/**
		 * No2.查询开票申请和实际开票计划关联表中的实际开票计划。然后通过时间升序排列
		 * */
		String planHql="select plan from RealContractBillandRecePlan plan,BillandProRelaion relation,ApplyBill bill" +
				" where relation.applyBill=bill.billApplyId" +
				" and relation.realContractBillandRecePlan=plan.realConBillproSid" +
				" and bill.billApplyId=? order by plan.realPredBillDate";
		List<RealContractBillandRecePlan> planList=commonDao.list(planHql, applyBillId);
		
		/**三步操作**/
		/**
		 * step1.先把list里面的已开票总金额置零
		 * step2.然后再去赋值
		 * step3.更新应收款总额的值。规则:发票：min(已开票金额，计划收款金额),收据：max(已开票金额，应收关联总金额)
		 * **/
		//
		List<RealContractBillandRecePlan> receiptList = new ArrayList<RealContractBillandRecePlan>();
		for (RealContractBillandRecePlan plan : planList) {
			//关联表,已开票金额
			String applyPlanRelationHql="select relation from BillandProRelaion relation" +
			" where relation.realContractBillandRecePlan = ? " +
			" and relation.applyBill=? ";
			BillandProRelaion applyPlanRelation = (BillandProRelaion) commonDao.uniqueResult(applyPlanRelationHql, plan.getRealConBillproSid(), applyBillId );
			plan.setBillInvoiceAmount(0D);
			applyPlanRelation.setRelateInvoiceAmount(0.0);
			////////////////////////////////////
			//抵消关联计划已开票金额
			if(sum>0.0){
				if(sum>applyPlanRelation.getRelateAmount().doubleValue()){
					applyPlanRelation.setRelateInvoiceAmount(applyPlanRelation.getRelateAmount().doubleValue());
					sum-=applyPlanRelation.getRelateAmount().doubleValue();
				}else{
					applyPlanRelation.setRelateInvoiceAmount(sum);
					sum=0.0;
				}
			}
			commonDao.update(applyPlanRelation);
			////////////////////////计划已收金额
			yxCommonDao.flushSession();
			String planInvoiceHql ="select sum(relation.relateInvoiceAmount) from BillandProRelaion relation where relation.realContractBillandRecePlan = ? ";
			Double planInvoice = (Double) commonDao.uniqueResult(planInvoiceHql, plan.getRealConBillproSid());
			plan.setBillInvoiceAmount(planInvoice);
			//如果开票类型是4表示是收据。其他的都为发票
			if(StringUtils.equals(plan.getBillType(), "4")){
				String receiptHql="select sum(receipt.receRelationAmount)from RelationBillAndReceipt receipt where receipt.receiptRealId=?";
				Double receRelationAmount=(Double)commonDao.uniqueResult(receiptHql, plan.getRealConBillproSid());
					receRelationAmount=receRelationAmount== null ? 0.0 : receRelationAmount;
				Double first=plan.getBillInvoiceAmount()==null ? 0.0 :plan.getBillInvoiceAmount();
				plan.setShouldReceAmount(Math.max(first,receRelationAmount));
			}else{
				Double first=plan.getBillInvoiceAmount()==null ? 0.0:plan.getBillInvoiceAmount();
				Double second=plan.getRealTaxReceAmount()==null ?0.0:plan.getRealTaxReceAmount().doubleValue();
				plan.setShouldReceAmount(Math.min(first,second));
				/**开票金额-计划收款金额**/
				double fee=plan.getBillInvoiceAmount()-plan.getRealTaxReceAmount().doubleValue();
				String relationHql="select receipt from RealContractBillandRecePlan plan,RelationBillAndReceipt receipt where plan.realConBillproSid = receipt.receiptRealId and  receipt.billRealId=? order by plan.realPredBillDate ";
				List<RelationBillAndReceipt> receiptRelationList=commonDao.list(relationHql, plan.getRealConBillproSid());
				for (RelationBillAndReceipt receipt : receiptRelationList) {
					if(fee>0){
						if(fee-receipt.getRelationAmount()>0.0){
							receipt.setReceRelationAmount(receipt.getRelationAmount());
							fee-=receipt.getRelationAmount();
						}else{
							receipt.setReceRelationAmount(fee);
							fee=0.0;
						}
					}else{
						receipt.setReceRelationAmount(0.0);
					}
					commonDao.update(receipt);
					receiptList.add((RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, receipt.getReceiptRealId()));
				}
			}
			commonDao.update(plan);
		}
		yxCommonDao.flushSession();
		/////
		//更新改动过关联应收金额的收据计划应收金额
		for (RealContractBillandRecePlan receiptPlan : receiptList) {
			updateReciptShouldAmount(receiptPlan);
		}
	}

	public void updateReciptShouldAmount(
			RealContractBillandRecePlan receiptPlan) {
		yxCommonDao.flushSession();
		String receiptHql="select sum(receipt.receRelationAmount)from RelationBillAndReceipt receipt where receipt.receiptRealId=?";
		Double receRelationAmount=(Double)commonDao.uniqueResult(receiptHql, receiptPlan.getRealConBillproSid());
		receRelationAmount=receRelationAmount== null ? 0.0 : receRelationAmount;
		Double first=receiptPlan.getBillInvoiceAmount()==null ? 0.0 :receiptPlan.getBillInvoiceAmount();
		receiptPlan.setShouldReceAmount(Math.max(first,receRelationAmount));
		commonDao.update(receiptPlan);
	}

	/**
	 * 更新开票计划，关联的应收金额和关联的对应的收据的应收金额
	 * @param billPlanId
	 */
	public void updateRelationAndReceiptShouldAmount(Long billPlanId){
		yxCommonDao.flushSession();
		RealContractBillandRecePlan billPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, billPlanId);
		//影响到的收据
		List<RealContractBillandRecePlan> receiptList = new ArrayList<RealContractBillandRecePlan>();
		/**开票金额-计划收款金额**/
		double fee=BigDecimalUtils.defaultIfNull(billPlan.getBillInvoiceAmount())-billPlan.getRealTaxReceAmount().doubleValue();
		String relationHql="select receipt from RealContractBillandRecePlan plan,RelationBillAndReceipt receipt where plan.realConBillproSid = receipt.receiptRealId and  receipt.billRealId=? order by plan.realPredBillDate ";
		List<RelationBillAndReceipt> receiptRelationList=commonDao.list(relationHql, billPlan.getRealConBillproSid());
		//设置关联表的应收金额
		for (RelationBillAndReceipt receiptRelation : receiptRelationList) {
			if(fee>0){
				if(fee-receiptRelation.getRelationAmount()>0.0){
					receiptRelation.setReceRelationAmount(receiptRelation.getRelationAmount());
					fee-=receiptRelation.getRelationAmount();
				}else{
					receiptRelation.setReceRelationAmount(fee);
					fee=0.0;
				}
			}else{
				receiptRelation.setReceRelationAmount(0.0);
			}
			commonDao.update(receiptRelation);
			receiptList.add((RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, receiptRelation.getReceiptRealId()));
		}
		//更新改动过关联应收金额的收据计划应收金额
		for (RealContractBillandRecePlan receiptPlan : receiptList) {
			updateReciptShouldAmount(receiptPlan);
		}
	}
	
	/**
	 * 取消发票收据关联
	 */
	@SuppressWarnings("unchecked")
	public void releaseRelationAndReceiptShouldAmount(Long billPlanId){
		//通过开票收款计划号，找到所有计划，将所有关联值清0，关联表数据清除
		
		
		//获得开票收款计划,已关联金额清0
		RealContractBillandRecePlan billPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, billPlanId);
//		logger.info(billPlan.getRealConBillproSid());
		billPlan.setRelationReceAmount(0d);
		commonDao.update(billPlan);
				
		//通过计划号，找到收据-发票关联
		String relationHql = "select receipt from " +
				" RealContractBillandRecePlan plan,RelationBillAndReceipt receipt " +
				" where plan.realConBillproSid = receipt.receiptRealId and  receipt.billRealId=? ";
		List<RelationBillAndReceipt> receiptRelationList = commonDao.list(relationHql, billPlan.getRealConBillproSid());
//		logger.info("关联收据数 "+  receiptRelationList.size() );
		
		for (RelationBillAndReceipt receiptRelation : receiptRelationList) {
			RealContractBillandRecePlan p =  (RealContractBillandRecePlan)commonDao.load(RealContractBillandRecePlan.class, receiptRelation.getReceiptRealId());
//			logger.info(p.getRealConBillproSid());
			p.setRelationReceAmount(p.getRelationReceAmount()-receiptRelation.getRelationAmount());
			commonDao.update(p);
			commonDao.delete(receiptRelation);		
		}
		
		for (RelationBillAndReceipt receiptRelation : receiptRelationList) {
			RealContractBillandRecePlan p =  (RealContractBillandRecePlan)commonDao.load(RealContractBillandRecePlan.class, receiptRelation.getReceiptRealId());
			this.updateReciptShouldAmount(p);
		}
	}

	
	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao yxCommonDao) {
		this.yxCommonDao = yxCommonDao;
	}

	public String checkInvoiceNoIsExists(String invoiceNo) {
		yxCommonDao.flushSession();
		String hql="select count(*) from InvoiceInfo invoice where invoice.invoiceNo=?";
		Long count=(Long)commonDao.uniqueResult(hql, invoiceNo);
		if(count>0){
			return invoiceNo;
		}
		return null;
	}
	public String checkInvoiceNoIsExists(String invoiceNo,Long invoiceId) {
		yxCommonDao.flushSession();
		String hql="select count(*) from InvoiceInfo invoice where invoice.invoiceNo=? and invoice.id<>?";
		Long count=(Long)commonDao.uniqueResult(hql, invoiceNo,invoiceId);
		if(count>0){
			return invoiceNo;
		}
		return null;
	}
	/**
	 * 修改计划里面的统计字段
	 */
	public void updatePlanStatistic(Date invoiceDate, Long realPlanId, boolean isHongChong) {
		yxCommonDao.flushSession();
		//跟新计划开票日期
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, realPlanId);
		Date lastInvoiceDate = (Date) commonDao.uniqueResult("select max(ii.invoiceDate) from InvoiceInfo ii , BillandProRelaion br where ii.applyInvoiceId = br.applyBill and br.realContractBillandRecePlan = ? ", realPlanId);
		plan.setRealNowBillDate(lastInvoiceDate);
		commonDao.update(plan);
		//更新月度表统计字段
		MonthlyBillpro currentMonth = (MonthlyBillpro) commonDao.uniqueResult(" from  MonthlyBillpro m where m.billproMonth = ? and m.realContractBillandRecePlan = " + plan.getRealConBillproSid() , DateUtils.truncate(new Date(), Calendar.MONTH));
		MonthlyBillpro invoiceMonth = (MonthlyBillpro) commonDao.uniqueResult(" from  MonthlyBillpro m where m.billproMonth = ? and m.realContractBillandRecePlan = " + plan.getRealConBillproSid() , DateUtils.truncate(invoiceDate, Calendar.MONTH));
		//如果开票就在本月
		if(DateUtils.truncate(new Date(), Calendar.MONTH).equals(DateUtils.truncate(invoiceDate, Calendar.MONTH))){
			updateInvoiceDateMonth(plan, invoiceDate, currentMonth ,isHongChong);
		}else{
			if(currentMonth!= null){
				//如果是红冲
				if(isHongChong){
					//忽略,现在系统没有部分开票,不会出现在两个月都有开票
				}else{
					//如果不是红冲,即是开票,日期不在本月,直接删除
					commonDao.delete(currentMonth);
				}
			}
			updateInvoiceDateMonth(plan, invoiceDate, invoiceMonth, isHongChong);
		}
		//更新项目和合同统计字段
		ContractItemMaininfo itemMain = null;
		if(plan.getContractItemMaininfo() != null){
			itemMain = (ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, plan.getContractItemMaininfo());
			if(itemMain != null){
				Number billAmount = (Number) commonDao.uniqueResult("select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.contractItemMaininfo = ? ",plan.getContractItemMaininfo());
				Number receiptAmount = (Number) commonDao.uniqueResult("select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.contractItemMaininfo = ? ",plan.getContractItemMaininfo());
				Number shouldAmount = (Number) commonDao.uniqueResult("select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.contractItemMaininfo = ? ",plan.getContractItemMaininfo());
				itemMain.setBillInvoiceAmount(getDefaultIfNull(billAmount));
				itemMain.setBillReceiptAmount(getDefaultIfNull(receiptAmount));
				itemMain.setShouldReceAmount(getDefaultIfNull(shouldAmount));
				commonDao.update(itemMain);
			}
		}
		
		//
		ContractMainInfo contract = (ContractMainInfo) commonDao.load(ContractMainInfo.class, plan.getConMainInfoSid());
		Number billAmount = (Number) commonDao.uniqueResult("select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where p.billType <> '4' and p.conMainInfoSid = ? ",plan.getConMainInfoSid());
		Number receiptAmount = (Number) commonDao.uniqueResult("select sum(p.billInvoiceAmount) from RealContractBillandRecePlan p where billType = '4' and p.conMainInfoSid = ? ",plan.getConMainInfoSid());
		Number shouldAmount = (Number) commonDao.uniqueResult("select sum(p.shouldReceAmount) from RealContractBillandRecePlan p where p.conMainInfoSid = ? ",plan.getConMainInfoSid());
		contract.setBillInvoiceAmount(getDefaultIfNull(billAmount));
		contract.setBillReceiptAmount(getDefaultIfNull(receiptAmount));
		contract.setShouldReceAmount(getDefaultIfNull(shouldAmount));
		commonDao.update(contract);
	}

	/**
	 * 更新invoiceDate所在月的月度计划
	 * @param plan
	 * @param invoiceDate
	 * @param invoiceMonth  和invoiceDate在同一个月
	 */
	private void updateInvoiceDateMonth(RealContractBillandRecePlan plan,
			Date invoiceDate, MonthlyBillpro invoiceMonth ,  boolean isHongChong) {
		// 更新票所在月份的月度计划
		if(invoiceMonth != null){
			// 更新票所在月份的月度计划
			invoiceMonth.setActualBillDate(invoiceDate);
			invoiceMonth.setPlanBillAmount(plan.getBillInvoiceAmount());
			if(isHongChong){
				//如果是红冲,可能已被分割,所以要更新计划收款金额
				invoiceMonth.setBillAmount(plan.getRealBillAmount().doubleValue());
				invoiceMonth.setBillTaxAmount(plan.getRealTaxBillAmount().doubleValue());
			}
			commonDao.update(invoiceMonth);
			// 如果开票金额为0，计划外的要删除
			if(invoiceMonth.getIsInsidePlan()==1&&getDefaultIfNull(invoiceMonth.getPlanBillAmount())==0.00){
				commonDao.delete(invoiceMonth);
			}
		 }else if(!isHongChong){
			 // 放入开票所在月计划外
			 invoiceMonth = new MonthlyBillpro();
			 invoiceMonth.setIsInsidePlan(1); // 设置为计划外
			 invoiceMonth.setProCreateDate(new Date());
			 invoiceMonth.setBillproMonth(DateUtils.truncate(invoiceDate, Calendar.MONTH));
			 invoiceMonth.setRealContractBillandRecePlan(plan);
			 invoiceMonth.setBillAmount(plan.getRealBillAmount().doubleValue());
			 invoiceMonth.setBillTaxAmount(plan.getRealTaxBillAmount().doubleValue());
			 invoiceMonth.setPlanBillAmount(plan.getBillInvoiceAmount());//已经开票金额
			 invoiceMonth.setBillPlanDate(plan.getRealPredBillDate());
			 invoiceMonth.setActualBillDate(invoiceDate);
			 commonDao.save(invoiceMonth);
		 }
	}
	private Double getDefaultIfNull(Number n){
		if(n != null){
			return n.doubleValue();
		}else{
			return 0.0;
		}
	}

	public void cancelInvoice(Long invoiceInfoId) {
		/**
		 * step1.查询出invoiceInfo的对象
		 */
		InvoiceInfo invoiceInfo=(InvoiceInfo)commonDao.load(InvoiceInfo.class, invoiceInfoId);
		/**
		 * step2.删除发票并清空session
		 */
		commonDao.delete(invoiceInfo);
		yxCommonDao.flushSession();
		/**
		 * step3.查询出开票申请单
		 */
		ApplyBill bill=(ApplyBill)commonDao.load(ApplyBill.class, invoiceInfo.getApplyInvoiceId());
		bill.setApplyBillState(7L);
		commonDao.update(bill);
		/**
		 * step4.计算开票金额
		 */
		calBillInvoiceAmount(bill.getBillApplyId());
		/**
		 * step5.查询出同一张申请单下的发票的最大日期
		 */
		String dateHql="select max(invoice.invoiceDate) from InvoiceInfo invoice where invoice.applyInvoiceId=?";
		Date invoiceDate=(Date)commonDao.uniqueResult(dateHql, bill.getBillApplyId());
		/**
		 * step6.查询出同一张开票收款申请单下的计划的列表
		 */
		String planHql="select plan from RealContractBillandRecePlan plan,BillandProRelaion br" +
				" where br.realContractBillandRecePlan=plan.realConBillproSid and br.applyBill=?";
		List<RealContractBillandRecePlan> planList=commonDao.list(planHql, bill.getBillApplyId());
		/**
		 * step7.修改统计字段
		 */
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {
			updatePlanStatistic(invoiceDate, realContractBillandRecePlan.getRealConBillproSid(), false);
		}
		
	}

	public ProcessResult updateNoApplyBillPlan(
			RealContractBillandRecePlan newNoBillApplyPlan) {
		ProcessResult result = new ProcessResult();
		////////
		RealContractBillandRecePlan updatedPlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, newNoBillApplyPlan.getRealConBillproSid());
		boolean isAmountChanged = false;
		if(updatedPlan.getRealBillAmount().doubleValue() != newNoBillApplyPlan.getRealBillAmount().doubleValue()
				|| updatedPlan.getRealReceAmount().doubleValue() != newNoBillApplyPlan.getRealReceAmount().doubleValue()){
			isAmountChanged = true;
		}
		//日期
		updatedPlan.setRealPredBillDate(newNoBillApplyPlan.getRealPredBillDate());
		updatedPlan.setRealPredReceDate(newNoBillApplyPlan.getRealPredReceDate());
		if(isAmountChanged){
			//基准开票金额
			updatedPlan.setRealBillAmount(newNoBillApplyPlan.getRealBillAmount());
			//收款金额
			updatedPlan.setRealReceAmount(newNoBillApplyPlan.getRealReceAmount());
			updatedPlan.setRealTaxReceAmount(updatedPlan.getRealReceAmount());
			//计划含税金额
			ContractMainInfo contract = (ContractMainInfo) commonDao.load(ContractMainInfo.class, updatedPlan.getConMainInfoSid());
			if("1".equals(contract.getStandard())){
				updatedPlan.setRealTaxBillAmount(updatedPlan.getRealBillAmount());
			}else{
				updatedPlan.setRealTaxBillAmount(TaxChange.NoTaxToTaxBigDecimal(updatedPlan.getRealBillAmount(), updatedPlan.getBillType()));
			}
		}
		commonDao.update(updatedPlan);
		///////
		if(isAmountChanged){
			updateContractTaxAndNoTaxByPlan(updatedPlan.getConMainInfoSid());
		}
		///////
		result.setSuccess(true);
		return result;
	}

	public ProcessResult updateContractTaxAndNoTaxByPlan(Long contractId) {
		ProcessResult result = new ProcessResult();
		////////
		yxCommonDao.flushSession();
		//项目
		commonDao.executeUpdate("update ContractItemInfo cii set " +
				" cii.conItemAmountWithTax = " +
				" (select sum(p.realBillAmount) from RealContractBillandRecePlan p , ContractMaininfoPart part " +
				" where cii.contractItemMaininfo = p.contractItemMaininfo and cii.mainInfoPartId = part.id and part.moneytype = p.billNature" +
				" and part.contractmainid = p.conMainInfoSid and p.billType <> '4' ) " +
				" where " +
				" exists (select 1 from ContractItemMaininfo cm where cm.conItemMinfoSid = cii.contractItemMaininfo and cm.contractMainInfo = ? ) "
				, contractId);
		//阶段
		commonDao.executeUpdate("update ContractItemStagePlan cisp set " +
				//开票金额
				" cisp.stageAmout = " +
				" (select sum(p.realBillAmount) from RealContractBillandRecePlan p " +
				" where cisp.contractItemStage.conIStageSid = p.conItemStage and cisp.contractMaininfoPart.moneytype = p.billNature " +
				" and cisp.contractMainSid = p.conMainInfoSid and p.billType <> '4' ) , " +
				//收款金额
				" cisp.reveAmount = " +
				" (select sum(p.realReceAmount) from RealContractBillandRecePlan p " +
				" where cisp.contractItemStage.conIStageSid = p.conItemStage and cisp.contractMaininfoPart.moneytype = p.billNature " +
				" and cisp.contractMainSid = p.conMainInfoSid ) " +
				" where " +
				" cisp.contractMainSid = ? "
				, contractId);
		//费用
		commonDao.executeUpdate("update ContractMaininfoPart part set " +
				" part.money = " +
				" (select sum(p.realBillAmount) from RealContractBillandRecePlan p " +
				" where part.moneytype = p.billNature and part.contractmainid = p.conMainInfoSid" +
				" and p.billType <> '4' ) " +
				" where " +
				" part.contractmainid = ?  "
				, contractId);		
		//合同
		commonDao.executeUpdate("update ContractMainInfo c set " +
				//含税金额
				" c.conTaxTamount = " +
				" (select sum(p.realTaxBillAmount) from RealContractBillandRecePlan p " +
				" where c.conMainInfoSid = p.conMainInfoSid and p.billType <> '4' ) , " +
				//不含税金额
				" c.conNoTaxTamount = " +
				" (select sum(p.realTaxBillAmount/t.info) from RealContractBillandRecePlan p , YXTypeManage t " +
				" where t.typeSmall = p.billType and t.typeBig = 1004 " +
				" and c.conMainInfoSid = p.conMainInfoSid and p.billType <> '4' ) " +
				" where " +
				" c.conMainInfoSid = ? "
				, contractId);
		///////
		result.setSuccess(true);
		return result;
	}

	public List<RealContractBillandRecePlan> showReceInfo(Long billId) {
		//查出申请单对应的开票计划id    
		List<Long> billProList = commonDao.list(" select br.realContractBillandRecePlan from BillandProRelaion br where br.applyBill = ? ", billId);
		if(billProList.size() > 0){
			List<Long> receList = commonDao.list(" select rr.receiptRealId " +
					"from RelationBillAndReceipt rr,BillandProRelaion br where rr.receiptRealId = br.realContractBillandRecePlan and rr.billRealId in ("+StringUtils.join(billProList,",") + ") " );
			if(receList.size() > 0){
				//判断如果这个计划关联过收据计9询出收据信息
				String sql = " select recePlan from RelationBillAndReceipt rr,RealContractBillandRecePlan recePlan " +
				"where recePlan.realConBillproSid = rr.receiptRealId " +
				"and recePlan.billInvoiceAmount > 0 " +
				"and rr.billRealId in ( "+StringUtils.join(billProList,",")+" )";
				List<RealContractBillandRecePlan> receiptList = commonDao.list(sql);
				return receiptList;
			}
		}
		return null;
	}

}
