package com.baoz.yx.action.system;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Role;
import com.baoz.yx.entity.RoleEmployee;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.ITestService;
import com.baoz.yx.service.MonthHarvestPlanService;
import com.baoz.yx.service.ShowMoonBillQueryService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;

/**
 * 类ConsoleAction.java的实现描述：用于开发阶段方便调试，上线后去掉
 * @author xurong Aug 7, 2008 10:58:27 AM
 */
@Results( {
	@Result(name = "console", value = "/WEB-INF/jsp/system/console.jsp"),
	@Result(name = "consoleResult", value = "/WEB-INF/jsp/system/consoleOperationResult.jsp")
})
public class ConsoleAction extends DispatchAction {
	@Autowired
	@Qualifier("ShowMoonBillQueryService")
	private ShowMoonBillQueryService showMoonBillQueryService;
	
	@Autowired
	@Qualifier("monthHarvestPlanService")
	private MonthHarvestPlanService monthHarvestPlanService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("ForamlContractService")	
	private IForamlContractService foramlContractService;
	
	@Autowired
	@Qualifier("testService")
	private ITestService testService;
	private String resultMessage;
	
	@Override
	public String doDefault() throws Exception {
		if(!isAdmin()){
			resultMessage="您无权访问该页";
			return "consoleResult";
		}
		return "console";
	}

	public String reGenerateCurrentMonthBillPlan(){
		if(!isAdmin()){
			resultMessage="您无权访问该页";
			return "consoleResult";
		}
		if(!showMoonBillQueryService.isGeneratedCurrentMonthBillPlan()){
			showMoonBillQueryService.generateCurrentMonthBillPlan();
			resultMessage="本月月度开票计划生成成功";
		}else{
			resultMessage="本月月度开票计划已经生成，不能重复生成";
		}
		
		return "consoleResult";
	}
	
	public String reGenerateCurrentMonthHarvestPlan(){
		if(!isAdmin()){
			resultMessage="您无权访问该页";
			return "consoleResult";
		}
		if(!monthHarvestPlanService.isGeneratedCurrentMonthPlan()){
			monthHarvestPlanService.generateCurrentMonthPlan();
			resultMessage="本月月度收款计划生成成功";	
		}else{
			resultMessage="本月月度收款计划已经生成，不能重复生成";
		}

		return "consoleResult";
	}
	
	public String updateAccountAge(){
		if(!isAdmin()){
			resultMessage="您无权访问该页";
			return "consoleResult";
		}
		List<RealContractBillandRecePlan> planList = foramlContractService.getToBeUpdatedAccountAgePlan();
		logger.info("开始更新帐龄，符合条件计划条数："+planList.size());
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {
			foramlContractService.updateAccountAge(realContractBillandRecePlan);
		}
		resultMessage="帐龄更新成功";
		return "consoleResult";
	}
	
	public String updateContractActiveDate(){
		if(!isAdmin()){
			resultMessage="您无权访问该页";
			return "consoleResult";
		}
		String hql = "from ContractMainInfo minfo where minfo.conState >=4 and minfo.ContractType = '1'";
		List<ContractMainInfo> list  =  commonService.list(hql);
		testService.updateContractIsActiveDate(list);
		resultMessage="修正合同转正日期成功";
		return "consoleResult";
	}
	
	private boolean isAdmin(){
		// 判断角色，管理员就返回true
		List<RoleEmployee> l = commonService.list("from RoleEmployee re where re.userId=?", UserUtils.getUser().getId());
		for(int i=0; i<l.size(); i++) {
			
			Role role = (Role) commonService.load(Role.class, l.get(i).getRoleId());
			if(YxConstants.adminRoleCode.equals(role.getCode())) {
				//管理员
				return true;
			}
		}
		return false;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}