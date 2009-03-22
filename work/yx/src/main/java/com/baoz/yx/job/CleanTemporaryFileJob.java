package com.baoz.yx.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baoz.yx.utils.TemporaryFileUtils;

/**
 * 类CleanTemporaryFileJob.java的实现描述：定时清除临时文件
 * @author xurong Oct 20, 2008 10:40:59 AM
 */
public class CleanTemporaryFileJob  extends QuartzJobBean {
	private Logger logger = Logger.getLogger(CleanTemporaryFileJob.class);
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("清除临时文件开始");
		TemporaryFileUtils.cleanTemporaryDir();
		logger.info("清除临时文件结束");
	}
}
