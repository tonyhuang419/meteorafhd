package com.baoz.yx.action.harvestMeansManager;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.NoContractRecevieAmount;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IStatisticsService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.opensymphony.xwork2.ActionContext;

@Results( {
	@Result(name = "enterListPage", value = "/WEB-INF/jsp/harvestMeansManager/noContractRecevieAmountList.jsp"),
	@Result(name = "showMain", value = "/WEB-INF/jsp/harvestMeansManager/noContractRecevieAmountMain.jsp"),
	@Result(name = "showQuery", value = "/WEB-INF/jsp/harvestMeansManager/noContractRecevieAmountQuery.jsp"),
	@Result(name = "newHavestPage", value = "/WEB-INF/jsp/harvestMeansManager/newAndModifyHavest.jsp"),
	@Result(name = "addRemark", value = "/WEB-INF/jsp/harvestMeansManager/addRemark.jsp")
	})	
public class NoContractHarvestManageAction extends DispatchAction {
	@Autowired
	@Qualifier("harvestService")
	private IHarvestService harvestService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("statisticsService")
	private IStatisticsService statisticsService;
	
	private List<Department> groupList;
	private List<Employee> 	 empList; 
	
	private String groupId;   //组别
	
	private Long saleMan;  //销售员
	
	private String startRecevieDate;  //开始收款日期

	private String endRecevieDate;    //结束收款日期
	
	private String state;  //核销状态
	
	private PageInfo info;
	
	private NoContractRecevieAmount receAmount;
	
	private String customername;
	
	private Long recevieAmountId;
	
	private Long deleteid;
	
	private Long selectedid;
	
	private Double receiveAmount;
	
	private Long hasRemark;
	
	private String remark;
	
	private int fromWhichMethod;
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"noContractHarvestManageParameters",this,new String[]{"groupId","saleMan","startRecevieDate","endRecevieDate","state"}); 

	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "noContractharvestPage");
	    info =  harvestService.getNoContractReceiveAmount(groupId, saleMan, startRecevieDate, endRecevieDate, state,UserUtils.getUser().getId(), info);
	    receiveAmount = harvestService.getSumReceiveAmount(groupId, saleMan, startRecevieDate, endRecevieDate, state, UserUtils.getUser().getId());
	    logger.info("总的金额是:"+receiveAmount);
	    if(info == null){
			info = new PageInfo();
		}
		
		return "enterListPage";
	}
	
	public String toNewAndModifyHarvestPage() throws Exception{
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		if(recevieAmountId!=null){
			receAmount = harvestService.loadNoContractRecevieAmount(recevieAmountId);
			customername = harvestService.getCustomerNameById(receAmount.getCustomerid());
			
		}
		return "newHavestPage";
	}
	
	public String saveHarvest() throws Exception{
		receAmount.setOpPerson(UserUtils.getUser().getId());
		receAmount.setOpTime(new Date());
		harvestService.saveNoContractReceiveAmount(receAmount);
		ActionContext.getContext().put("isSaveSuccess", "true");
		empList = statisticsService.getEmployee();
		groupList = statisticsService.getDepartment();
		return "newHavestPage";
	}
	
	public String deleteInfo() throws Exception{
		if(deleteid!=null){
			harvestService.delReceAmount(deleteid);
		}
		return doDefault();
	}
	
	public String sureCheck() throws Exception{
		if(hasRemark!=null){
			harvestService.changeState(selectedid,"1",remark);
			ActionContext.getContext().put("isSuccess", "true");
			return "addRemark";
		}else{		
			NoContractRecevieAmount nocontractRecevie = (NoContractRecevieAmount)commonService.load(NoContractRecevieAmount.class,selectedid);
			remark = nocontractRecevie.getRemark();
			fromWhichMethod = 1;
			return "addRemark";
		}
	}
	
	public String sureHisCheck() throws Exception{
		if(hasRemark!=null){
			harvestService.changeState(selectedid,"2",remark);
			ActionContext.getContext().put("isSuccess", "true");
			return "addRemark";
		}else{
			NoContractRecevieAmount nocontractRecevie = (NoContractRecevieAmount)commonService.load(NoContractRecevieAmount.class,selectedid);
			remark = nocontractRecevie.getRemark();
			fromWhichMethod = 2;
			return "addRemark";
		}
	}
	/**
	 * 显示主页面
	 * @return
	 */
	public String showMain(){
		
		return "showMain";
	}
	/**
	 * 显示查询条件页面
	 * @return
	 */
	public String showQuery(){
		groupList = UserUtils.getUserDetail().getDepartments();
		empList = null;
		return "showQuery";
	}
	
	public IHarvestService getHarvestService() {
		return harvestService;
	}


	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}


	public PageInfo getInfo() {
		return info;
	}


	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public NoContractRecevieAmount getReceAmount() {
		return receAmount;
	}

	public void setReceAmount(NoContractRecevieAmount receAmount) {
		this.receAmount = receAmount;
	}

	public Long getRecevieAmountId() {
		return recevieAmountId;
	}

	public void setRecevieAmountId(Long recevieAmountId) {
		this.recevieAmountId = recevieAmountId;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public Long getDeleteid() {
		return deleteid;
	}

	public void setDeleteid(Long deleteid) {
		this.deleteid = deleteid;
	}

	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}

	public String getStartRecevieDate() {
		return startRecevieDate;
	}

	public void setStartRecevieDate(String startRecevieDate) {
		this.startRecevieDate = startRecevieDate;
	}

	public String getEndRecevieDate() {
		return endRecevieDate;
	}

	public void setEndRecevieDate(String endRecevieDate) {
		this.endRecevieDate = endRecevieDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Long getSelectedid() {
		return selectedid;
	}

	public void setSelectedid(Long selectedid) {
		this.selectedid = selectedid;
	}

	public Long getHasRemark() {
		return hasRemark;
	}

	public void setHasRemark(Long hasRemark) {
		this.hasRemark = hasRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public int getFromWhichMethod() {
		return fromWhichMethod;
	}

	public void setFromWhichMethod(int fromWhichMethod) {
		this.fromWhichMethod = fromWhichMethod;
	}


	
	
}
