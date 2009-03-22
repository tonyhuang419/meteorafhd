package com.baoz.yx.service;

import java.util.List;

import com.baoz.yx.entity.contract.ContractMainInfo;

public interface ITestService {

	/**
	 *  传入需要修改转正日期的合同列表
	 */
	public void updateContractIsActiveDate(List<ContractMainInfo> list);
	/**
	 * 修改某一个合同的转正式日期
	 * @param contract
	 */
	public void updateOneContractIsActiveDate(ContractMainInfo contract);
}
