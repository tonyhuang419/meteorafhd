package com.baoz.yx.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;

/**
 * 类AccountAgeJob.java的实现描述：计算开票计划的帐龄
 * @author xurong Aug 22, 2008 2:07:03 PM
 */
public class AccountAgeJob extends QuartzJobBean {
	private Logger logger = Logger.getLogger(AccountAgeJob.class);
	private IForamlContractService foramlContractService;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		List<RealContractBillandRecePlan> planList = foramlContractService.getToBeUpdatedAccountAgePlan();
		logger.info("开始更新帐龄，符合条件计划条数："+planList.size());
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {
			foramlContractService.updateAccountAge(realContractBillandRecePlan);
		}
		logger.info("帐龄更新结束");
	}
	
	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}
	
	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}
}
