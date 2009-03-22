package com.baoz.yx.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.mchange.v2.codegen.bean.BeangenUtils;

public class YXTypeManageServiceTest extends YingXiaoBaseTest {

	private IYXCommonDao yxCommonDao;

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao yxCommonDao) {
		this.yxCommonDao = yxCommonDao;
	}

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(false);
		super.prepareTestInstance();
	}

	public void testCreate() throws Exception {
		List<YXTypeManage> manageList  = yxCommonDao.list("FROM YXTypeManage tm WHERE tm.typeBig=? order by orderNum", 1024L);
		for (YXTypeManage typeManage : manageList) {
			YXTypeManage manage=new YXTypeManage();
			BeanUtils.copyProperties(manage, typeManage);
			manage.setId(null);
			manage.setTypeBig(1025L);
			yxCommonDao.save(manage);
		}
		yxCommonDao.flushSession();
		
	}

}
