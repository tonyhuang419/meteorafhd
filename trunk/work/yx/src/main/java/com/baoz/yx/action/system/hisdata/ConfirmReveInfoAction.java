package com.baoz.yx.action.system.hisdata;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.importfile.TempImportReveInfo;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

@Results({
		@Result(name="succeForward",value="/WEB-INF/jsp/system/hisdata/noContractReciveAmountError.jsp"),
		@Result(name="input",value="/WEB-INF/jsp/system/hisdata/noContractReciveAmount.jsp")
})
public class ConfirmReveInfoAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("importHisDataService")
	private IImportHisDataService importHisDataService;
	
	@Autowired
	@Qualifier("harvestService")
	private IHarvestService 		harvestService;
	
	private List<TempImportReveInfo> confirmList;//可以操作的列表
	
	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}

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

	public String confirmReve()throws Exception{
		String countHql=" from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1";
		confirmList=commonService.list(countHql, UserUtils.getUser().getId());
		String dateHql = "select distinct(temp.reveDate) from TempImportReveInfo temp " +
				"where temp.isActive=1 and  temp.authorId = ?";
		List<Date> dateList = commonService.list(dateHql, UserUtils.getUser().getId());
		if(dateList!=null && dateList.size()>0){
			for(int i = 0 ; i < dateList.size() ; i ++ ){
				Date opDate = dateList.get(i);
				Boolean flag = harvestService.checkDateInMonthlyRece(opDate);
				if(!flag){
					ActionContext.getContext().put("hasErrorMsg",flag );
					return showReveInfoList();
				}
			}
		}
		
		importHisDataService.importReveInfo(confirmList);
		return "succeForward";
	}
	
	public String showReveInfoList()throws Exception{
		String countHql=" from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1 order by tab.errorState desc,tab.errorMsg";
		confirmList=commonService.list(countHql, UserUtils.getUser().getId());
		return "input";
	}

	public IHarvestService getHarvestService() {
		return harvestService;
	}

	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}
}
