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
		//删除以前生成的本月计划,更新计划内外标志
		List<MonthlyBillpro> preMonthlyList = commonDao.list("from MonthlyBillpro where billproMonth = ? and isInsidePlan = 0 ", nowMonth);
		for (MonthlyBillpro monthlyBillpro : preMonthlyList) {
			monthlyBillpro.getRealContractBillandRecePlan().setBillPlanInMonthPlan(Boolean.FALSE);
			//更新计划内计划外标志，置为计划外
			commonDao.update(monthlyBillpro.getRealContractBillandRecePlan());
			//删除上月生成的本月计划
			commonDao.delete(monthlyBillpro);
		}
		//重新生成本月计划
		//下月之前，所有未开票计划
		List<RealContractBillandRecePlan> listBillPlan = commonDao.list("from RealContractBillandRecePlan where realTaxBillAmount > 0 and ( billInvoiceAmount is null or billInvoiceAmount < realTaxBillAmount or ( billInvoiceAmount = realTaxBillAmount and realNowBillDate >= ? ) ) and realPredBillDate < ?" ,nowMonth, nextMonth);
		for (RealContractBillandRecePlan realContractBillandRecePlan : listBillPlan) {
			MonthlyBillpro monthlyPlan = new MonthlyBillpro();
			monthlyPlan.setProCreateDate(new Date());
			monthlyPlan.setBillproMonth(nowMonth);
			monthlyPlan.setRealContractBillandRecePlan(realContractBillandRecePlan);
			monthlyPlan.setBillAmount(realContractBillandRecePlan.getRealBillAmount().doubleValue());
			monthlyPlan.setBillTaxAmount(realContractBillandRecePlan.getRealTaxBillAmount().doubleValue());
			monthlyPlan.setBillPlanDate(realContractBillandRecePlan.getRealPredBillDate());
			monthlyPlan.setActualBillDate(realContractBillandRecePlan.getRealNowBillDate());
			monthlyPlan.setPlanBillAmount(realContractBillandRecePlan.getBillInvoiceAmount());
			monthlyPlan.setIsInsidePlan(0);      // 设置为计划内
			commonDao.save(monthlyPlan);
			//更新计划内计划外标志，置为计划内
			realContractBillandRecePlan.setBillPlanInMonthPlan(Boolean.TRUE);
			commonDao.update(realContractBillandRecePlan);
		}
	}

	public boolean isGeneratedCurrentMonthBillPlan() {
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		Number currentMonthGeneratedPlanCount = (Number)commonDao.uniqueResult("select count(*) from MonthlyBillpro where proCreateDate >= ? and billproMonth < ? and isInsidePlan = 0 and rownum = 1", nowMonth, nextMonth);
		return currentMonthGeneratedPlanCount.intValue() > 0;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}


}
