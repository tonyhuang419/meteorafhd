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
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.ShowMoonBillQueryService;

/**
 * 类ShowMoonBillQueryService.java的实现描述：开票月度计划 
 * @author xurong Jul 9, 2008 1:05:07 PM
 */

@Service("ShowMoonBillQueryService")
@Transactional
public class ShowMoonBillQueryServiceImpl implements ShowMoonBillQueryService{
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	
	public void generateCurrentMonthBillPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		//获得下下个月
		Date nextNextMonth = DateUtils.truncate(DateUtils.addMonths(today, 2), Calendar.MONTH);
		//删除上个月生成的本月计划
		commonDao.executeUpdate("delete from MonthlyBillpro where proCreateDate < ? and billproMonth = ? ", nowMonth ,nowMonth);
		//重新生成本月计划
		List<RealContractBillandRecePlan> listBillPlan = commonDao.list("from RealContractBillandRecePlan where realPredBillDate >= ? and realPredBillDate < ?", nowMonth , nextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : listBillPlan) {
			MonthlyBillpro monthlyPlan = new MonthlyBillpro();
			monthlyPlan.setProCreateDate(new Date());
//			monthlyPlan.setProCreatePeo(UserUtils.getUser().getId());
			monthlyPlan.setBillproMonth(nowMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			commonDao.save(monthlyPlan);
		}
		//生成下月计划
		List<RealContractBillandRecePlan> nextMonthListBillPlan = commonDao.list("from RealContractBillandRecePlan where realPredBillDate >= ? and realPredBillDate < ?", nextMonth , nextNextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : nextMonthListBillPlan) {
			MonthlyBillpro monthlyPlan = new MonthlyBillpro();
			monthlyPlan.setProCreateDate(new Date());
//			monthlyPlan.setProCreatePeo(UserUtils.getUser().getId());
			monthlyPlan.setBillproMonth(nextMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			commonDao.save(monthlyPlan);
		}
	}

	public boolean isGeneratedCurrentMonthBillPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		Number currentMonthGeneratedPlanCount = (Number)commonDao.uniqueResult("select count(*) from MonthlyBillpro where proCreateDate >= ? and billproMonth < ? and rownum = 1", nowMonth, nextMonth);
		return currentMonthGeneratedPlanCount.intValue() > 0;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}


}
