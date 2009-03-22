package com.baoz.yx.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baoz.yx.service.IForamlContractService;

/**
 * 类SuggestContractCloseJob.java的实现描述：更新建议关闭的状态 
 * @author xurong Aug 11, 2008 10:31:09 AM
 */
public class SuggestContractCloseJob extends QuartzJobBean {
	private Logger logger = Logger.getLogger(MonthBillPlanJob.class);
	
	private IForamlContractService foramlContractService;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("开始更新建议关闭合同状态");
		foramlContractService.doSuggustClose();
		logger.info("更新建议关闭合同状态结束");
		
	}
	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}
	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

}
