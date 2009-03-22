package com.baoz.yx.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.utils.UserUtils;

/**
 * 类MonthHarvestPlanService.java的实现描述：收款月度计划 
 * @author xurong Jul 9, 2008 1:05:07 PM
 */

@Service("monthHarvestPlanService")
@Transactional
public class MonthHarvestPlanServiceImpl implements MonthHarvestPlanService{
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;

	@SuppressWarnings("unchecked")
	public void generateCurrentMonthPlan() {

		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		//删除上以前生成的本月计划,更新计划内外标志
		List<MonthlyRecepro> preMonthlyList = commonDao.list("from MonthlyRecepro where billproMonth = ? and isInsidePlan = 0 ",nowMonth);
		for (MonthlyRecepro monthlyRecepro : preMonthlyList) {
			monthlyRecepro.getRealContractBillandRecePlan().setRecePlanInMonthPlan(Boolean.FALSE);
			//更新计划内计划外标志，置为计划外
			commonDao.update(monthlyRecepro.getRealContractBillandRecePlan());
			//删除上月生成的本月计划
			commonDao.delete(monthlyRecepro);
		}
		//重新生成本月计划
		//下月之前，所有未收款计划
		List<RealContractBillandRecePlan> listBillRecPlan = commonDao.list("from RealContractBillandRecePlan where realTaxReceAmount >0 and ( realArriveAmount is null or realArriveAmount < realTaxReceAmount or ( realArriveAmount = realTaxReceAmount and realNowReceDate >= ? ) ) and realPredReceDate < ?", nowMonth , nextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : listBillRecPlan) {
			MonthlyRecepro monthlyPlan = new MonthlyRecepro();
			monthlyPlan.setProCreateDate(new Date());
			monthlyPlan.setProCreatePeo(UserUtils.getUser().getId());
			monthlyPlan.setBillproMonth(nowMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			monthlyPlan.setReceTaxAmount(realContractBillandRecePlan.getRealTaxReceAmount().doubleValue() - getArrivedAmountBeforeMonth(realContractBillandRecePlan.getRealConBillproSid(),nowMonth));
			monthlyPlan.setRecePlanDate(realContractBillandRecePlan.getRealPredReceDate());
			monthlyPlan.setAlreadyArrivedAmount(getArrivedAmountCurrentMonth(realContractBillandRecePlan.getRealConBillproSid(),nowMonth,nextMonth));
			if(monthlyPlan.getAlreadyArrivedAmount()>0){
				monthlyPlan.setActualArrivedDate(realContractBillandRecePlan.getRealNowReceDate());
			}
			monthlyPlan.setIsInsidePlan(0);
			commonDao.save(monthlyPlan);
			//更新计划内计划外标志，置为计划内
			realContractBillandRecePlan.setRecePlanInMonthPlan(Boolean.TRUE);
			commonDao.update(realContractBillandRecePlan);
		}
	}

	private Double getArrivedAmountCurrentMonth(Long planId,Date nowMonth , Date nextMonth){
		Number n = (Number)commonDao.uniqueResult("select sum(pr.amount) from PlanReceInfo pr where pr.amountTime >= ? and pr.amountTime < ? and pr.planId = ? ", nowMonth, nextMonth , planId);
		if(n==null){
			return 0.0;
		}else{
			return n.doubleValue();
		}
	}
	
	private Double getArrivedAmountBeforeMonth(Long planId,Date nowMonth){
		Number n = (Number)commonDao.uniqueResult("select sum(pr.amount) from PlanReceInfo pr where pr.amountTime < ? and pr.planId = ? ", nowMonth , planId);
		if(n==null){
			return 0.0;
		}else{
			return n.doubleValue();
		}
	}
	
	public boolean isGeneratedCurrentMonthPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		Number currentMonthGeneratedPlanCount = (Number)commonDao.uniqueResult("select count(*) from MonthlyRecepro where proCreateDate >= ? and billproMonth < ? and isInsidePlan = 0 and rownum = 1", nowMonth, nextMonth);
		return currentMonthGeneratedPlanCount.intValue() > 0;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public String getDepName(Long realConBillproSid) {
		RealContractBillandRecePlan rcbrp=(RealContractBillandRecePlan)commonDao.load(RealContractBillandRecePlan.class, realConBillproSid);
		String depname="";
		ContractMainInfo cmi=(ContractMainInfo) commonDao.load(ContractMainInfo.class, rcbrp.getConMainInfoSid());
		if(cmi.getContractType().equals('1')){
			ContractItemMaininfo cimi =(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, rcbrp.getContractItemMaininfo());
			if(cimi.getItemResDept()!=null){
				YXTypeManage yxtm=typeManageService.getYXTypeManage(1018L,cimi.getItemResDept());
				depname=yxtm.getTypeName();
			}
		}else{
			if(cmi.getMainItemDept()!=null){
				YXTypeManage yxtm=typeManageService.getYXTypeManage(1018L,cmi.getMainItemDept());
				depname=yxtm.getTypeName();
			}	
		}
		return depname;
	}

	public Boolean checkPlanInThisMonth(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		String hql = "select count(*) from MonthlyRecepro where trunc(billproMonth,'mm') = to_date('"+year+"-"+month+"','yyyy-MM')";
		Number count = (Number)commonDao.uniqueResult(hql);
		////大于0表示已经生成了本月的月度收款计划。生成了返回true；
		
		////小于等于0表示没有生成本月的月度计划。没有生成返回false；
		return count.intValue()>0;	
	}
	
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

}
