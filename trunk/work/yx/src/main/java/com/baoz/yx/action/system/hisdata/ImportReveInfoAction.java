package com.baoz.yx.action.system.hisdata;

import java.io.File;
import java.util.List;

import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.impl.CommonService;
import com.baoz.yx.entity.importfile.TempImportReveInfo;
import com.baoz.yx.excel.builder.RowResult;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.service.impl.ImportHisDataService;
import com.baoz.yx.tools.exception.DefineException;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.iap.Response;

@Results({
	@Result(name="success",value="/WEB-INF/jsp/system/hisdata/importReveInfo.jsp"),
	@Result(name="succeForward",value="/WEB-INF/jsp/system/hisdata/noContractReciveAmountError.jsp"),
	@Result(name="input",value="/WEB-INF/jsp/system/hisdata/noContractReciveAmount.jsp")
})
public class ImportReveInfoAction extends ActionSupport {

	private static Logger logger = Logger
	.getLogger(ImportReveInfoAction.class);
	
	private static final long serialVersionUID = 110110101248L;
	private File excelFile;
		
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	private List<TempImportReveInfo> confirmList;//可以操作的列表
	
	/**错误信息**/
	private StringBuffer loggerError=new StringBuffer();
	
	/**成功标志**/
	private StringBuffer loggerSuccess=new StringBuffer();
	
	
	public List<TempImportReveInfo> getConfirmList() {
		return confirmList;
	}

	public void setConfirmList(List<TempImportReveInfo> confirmList) {
		this.confirmList = confirmList;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public StringBuffer getLoggerError() {
		return loggerError;
	}

	public void setLoggerError(StringBuffer loggerError) {
		this.loggerError = loggerError;
	}

	public StringBuffer getLoggerSuccess() {
		return loggerSuccess;
	}

	public void setLoggerSuccess(StringBuffer loggerSuccess) {
		this.loggerSuccess = loggerSuccess;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		loggerSuccess=null;
		loggerError=null;
		if (excelFile != null) {
			logger.error("***********导入收款开始***********");
			Workbook workBook=Workbook.getWorkbook(excelFile);
			Long begin=System.currentTimeMillis();
			String countHql=" from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1";
			try{
				List<TempImportReveInfo> reveList=commonService.list(countHql, UserUtils.getUser().getId());
				for (TempImportReveInfo reve : reveList) {
					commonService.delete(reve);
				}
				importHisDataService.importReve(workBook.getSheet(0));
			}catch(DefineException exception){
				
				logger.error(exception.getMessage());
			}
			Long end=System.currentTimeMillis();
			logger.error("***********导入收款结束***********");
			logger.error("***********用时："+(end-begin)+"毫秒***********");
			
			return showReveInfoList();
		}
		return "success";
	}
	
	public String confirmReve()throws Exception{
		String countHql=" from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1";
		confirmList=commonService.list(countHql, UserUtils.getUser().getId());
		importHisDataService.importReveInfo(confirmList);
		return "succeForward";
	}
	
	public String showReveInfoList()throws Exception{
		String countHql=" from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1 order by tab.errorState desc,tab.errorMsg";
		confirmList=commonService.list(countHql, UserUtils.getUser().getId());
		return "input";
	}
	private String getRowMessage(RowResult result) {
		// TODO Auto-generated method stub
		return " 行:" + (result.getRow() + 1) + " ";
	}


	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}
}
