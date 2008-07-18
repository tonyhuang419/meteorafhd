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
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
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
	
	public void generateCurrentMonthPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		//获得下下个月
		Date nextNextMonth = DateUtils.truncate(DateUtils.addMonths(today, 2), Calendar.MONTH);
		//删除上个月生成的本月计划
		commonDao.executeUpdate("delete from MonthlyRecepro where proCreateDate < ? and billproMonth = ? ", nowMonth ,nowMonth);
		//重新生成本月计划
		List<RealContractBillandRecePlan> listBillRecPlan = commonDao.list("from RealContractBillandRecePlan where realPredReceDate >= ? and realPredReceDate < ?", nowMonth , nextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : listBillRecPlan) {
			MonthlyRecepro monthlyPlan = new MonthlyRecepro();
			monthlyPlan.setProCreateDate(new Date());
			monthlyPlan.setProCreatePeo(UserUtils.getUser().getId());
			monthlyPlan.setBillproMonth(nowMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			commonDao.save(monthlyPlan);
		}
		//生成下月计划
		List<RealContractBillandRecePlan> nextMonthListBillRecPlan = commonDao.list("from RealContractBillandRecePlan where realPredReceDate >= ? and realPredReceDate < ?", nextMonth , nextNextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : nextMonthListBillRecPlan) {
			MonthlyRecepro monthlyPlan = new MonthlyRecepro();
			monthlyPlan.setProCreateDate(new Date());
			monthlyPlan.setProCreatePeo(UserUtils.getUser().getId());
			monthlyPlan.setBillproMonth(nextMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			commonDao.save(monthlyPlan);
		}
	}

	public boolean isGeneratedCurrentMonthPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		Number currentMonthGeneratedPlanCount = (Number)commonDao.uniqueResult("select count(*) from MonthlyRecepro where proCreateDate >= ? and billproMonth < ? and rownum = 1", nowMonth, nextMonth);
		return currentMonthGeneratedPlanCount.intValue() > 0;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
