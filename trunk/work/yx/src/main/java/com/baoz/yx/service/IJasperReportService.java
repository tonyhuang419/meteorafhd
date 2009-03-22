package com.baoz.yx.service;

public interface IJasperReportService {

	/**
	 * 通过传入的id查询发票,并生成PDF
	 * @param paramId
	 * @return
	 */
	public void createBillAndInvoicePDF(Long paramId);
	
	public void createApplyBillPDF(Long paramId,boolean isDownLoad);
	
	public void createAssistancePayInfoPDF(Long paramId);
}
