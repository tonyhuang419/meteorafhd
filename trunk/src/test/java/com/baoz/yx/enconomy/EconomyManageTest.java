/**
 * 
 */
package com.baoz.yx.enconomy;

import com.baoz.core.dao.impl.CommonDao;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.dao.impl.YXCommonDao;

/**
 * @author Administrator
 *
 */
public class EconomyManageTest extends YingXiaoBaseTest {
	IYXCommonDao yxCommonDao;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
	}

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao commonDao) {
		this.yxCommonDao = commonDao;
	}
	
	public void testHql(){
		yxCommonDao.uniqueResult("from ContractItemStagePlan");
	}
}
