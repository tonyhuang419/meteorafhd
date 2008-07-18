package com.baoz.yx.service;

import jxl.Sheet;

/**
 * 类IExcelImportService.java的实现描述：excel导入 
 * @author xurong Jul 1, 2008 1:28:08 PM
 */
public interface IExcelImportService {
	/**
	 * @param engineeringSheet 工程类合同
	 */
	public void importHistoryEngineeringContract(Sheet engineeringSheet);
	public void importHistoryIntegrationContract(Sheet engineeringSheet);
	public void importHistoryAssistanceContract(Sheet assistanceSheet);

}
