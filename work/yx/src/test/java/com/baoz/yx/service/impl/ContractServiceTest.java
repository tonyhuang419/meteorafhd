package com.baoz.yx.service.impl;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.tools.TaxChange;

public class ContractServiceTest extends YingXiaoBaseTest {
	
	private IInvoiceService invoiceService;
	private IContractService contractService;
	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(false);
		super.prepareTestInstance();
	}
	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	
	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	public void testService(){
		TaxChange.setInvoiceService(invoiceService);
		contractService.updateItemNoTaxMoney();
		System.out.print("执行成功");
	}
	
}
