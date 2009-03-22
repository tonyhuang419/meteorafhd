package com.baoz.yx.service.impl;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.service.ISequenceService;
import com.baoz.yx.utils.SequenceKey;

public class SequenceServiceImplTest extends YingXiaoBaseTest {
	ISequenceService sequenceService;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
	}

	public ISequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}
	
	public void testSequence(){
		System.out.println(sequenceService.getNextValue(SequenceKey.sellbeforeNumber));
	}
}
