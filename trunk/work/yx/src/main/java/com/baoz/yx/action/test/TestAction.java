package com.baoz.yx.action.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IImportHisDataService;

public class TestAction extends DispatchAction{
	
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		RealContractBillandRecePlan plan=importHisDataService.getPlan(1284L);
		importHisDataService.createFullBillAndRecePlan(plan, true, true);
		return null;
	}
}
