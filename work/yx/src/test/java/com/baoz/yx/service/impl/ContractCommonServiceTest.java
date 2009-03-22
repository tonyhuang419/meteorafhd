package com.baoz.yx.service.impl;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.service.IContractCommonService;

public class ContractCommonServiceTest extends YingXiaoBaseTest {

	private IContractCommonService contractCommonService;
	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(false);
		super.prepareTestInstance();
	}
	
	public void testCancelInvoice() {
		contractCommonService.cancelInvoice(new Long(2109L));
	}

}
