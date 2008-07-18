package com.baoz.yx.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baoz.yx.service.ShowMoonBillQueryService;

/**
 * 类MonthHarvestPlanJob.java的实现描述：生成月度开票计划
 * @author xurong Jul 14, 2008 2:47:37 PM
 */
public class MonthBillPlanJob extends QuartzJobBean {
	private Logger logger = Logger.getLogger(MonthBillPlanJob.class);
	
	private ShowMoonBillQueryService showMoonBillQueryService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("月度开票计划开始生成");
		if(!showMoonBillQueryService.isGeneratedCurrentMonthBillPlan()){
			showMoonBillQueryService.generateCurrentMonthBillPlan();
		}else{
			logger.info("本月月度开票计划已经生成");
		}
		logger.info("月度开票计划生成结束");
	}

	public ShowMoonBillQueryService getShowMoonBillQueryService() {
		return showMoonBillQueryService;
	}

	public void setShowMoonBillQueryService(
			ShowMoonBillQueryService showMoonBillQueryService) {
		this.showMoonBillQueryService = showMoonBillQueryService;
	}
}
