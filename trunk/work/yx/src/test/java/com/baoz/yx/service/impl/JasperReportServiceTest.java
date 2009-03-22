package com.baoz.yx.service.impl;

import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.action.jasperReport.JasperAction;
import com.baoz.yx.service.IJasperReportService;

public class JasperReportServiceTest extends YingXiaoBaseTest {

	private IJasperReportService jasperReportService;
	
	public IJasperReportService getJasperReportService() {
		return jasperReportService;
	}
	public void setJasperReportService(IJasperReportService jasperReportService) {
		this.jasperReportService = jasperReportService;
	}
	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
	}
	public void testCreateApplyBillPDF()throws Exception {
		JasperAction jasper=new JasperAction();
		jasper.setJasperReportService(jasperReportService);
		jasper.setParamId(new Long(2103));
		jasper.applyBillJaspser();
	}

}
