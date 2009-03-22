package com.baoz.yx.action.system.hisdata;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.entity.ImportDueFromCompare;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/system/hisdata/importDueFromCompare.jsp"),
	@Result(name="input",value="/WEB-INF/jsp/system/hisdata/importDueFromCompareInput.jsp")
})
public class ImportDueFromCompareAction extends ActionSupport{

	private static Logger logger = Logger
	.getLogger(ImportDueFromCompareAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 12341234233L;
	private File excelFile;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;
	
	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	@Override
	public String execute() throws Exception {
		if(excelFile != null ){
			//表示excel中已经有值了
			logger.info("导入应收信息开始");
			Workbook workBook=Workbook.getWorkbook(excelFile);
			String countHql=" from ImportDueFromCompare tab where tab.opPerson = ?";
			List<ImportDueFromCompare> compareList=service.list(countHql,UserUtils.getUser().getId());
			for (ImportDueFromCompare compare : compareList) {
				service.delete(compare);
			}
			importHisDataService.importDueFromCompare(workBook.getSheet(0));
			return downLoadExcel();
		}
		return "success";
	}
	public String downLoadExcel()throws Exception{
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"客户名称","订单号/项目号","项目名称","销售人员","发票余额","帐龄(天数)","账龄(月)","《=1-3个月"
				,"4-6个月","7-12个月","1年以上","订单号/项目号","与系统符合","错误代码","系统情况"});
		export.setTableName("应收检查结果");
		String countHql=" from ImportDueFromCompare tab where tab.opPerson = ? order by tab.id ";
		List<ImportDueFromCompare> compareList=service.list(countHql,UserUtils.getUser().getId());
		for(ImportDueFromCompare compare:compareList){
			Object[] obj=new Object[15];
			obj[0] = compare.getCustomerName();
			obj[1] = compare.getOrderOrItemNo();
			obj[2] = compare.getItemName();
			obj[3] = compare.getSaleManName();
			obj[4] = compare.getBillFee();
			obj[5] = compare.getLogicDayAccountAge();
			obj[6] = compare.getLogicMonthAccountAge();
			obj[7] = compare.getFirstThreeMonth();
			obj[8] = compare.getSecondThreeMonth();
			obj[9] = compare.getThridSixMonth();
			obj[10] = compare.getBlowOneYear();
			obj[11] = compare.getOrderOrItemNo();
			ProcessResult result = importHisDataService.checkDueFromCompare(compare.getId());
			if(!result.isSuccess()){
				obj[12] = "N";
				obj[13]=result.getErrorCode();
				String str = "";
				List<String> errorMsg = result.getErrorMessages();
				for (String string : errorMsg) {
					str +=string;
				}
				obj[14] = str;
			}else{
				obj[12] = "Y";
				obj[13]=result.getErrorCode();
			}
			export.addRow(obj);
		}
		OutputStream os = DownloadUtils.getResponseOutput("应收检查结果.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
		return null;

}
	
	
}
