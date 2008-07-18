package com.baoz.yx.tools;

import junit.framework.TestCase;


import com.baoz.yx.entity.contract.ContractMainInfo;

public class TestSeeBeanFields extends TestCase {
	
	public void testSeeBeanFields() throws Exception {
		ContractMainInfo c = new ContractMainInfo();
		SeeBeanFields.travBean(c);
	}
}
