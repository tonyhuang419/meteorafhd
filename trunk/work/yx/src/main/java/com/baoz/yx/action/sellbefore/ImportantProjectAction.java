package com.baoz.yx.action.sellbefore;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ImportantProject;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;

/**
 *  重点工程项目管理
 */
@Results( {
	@Result(name = "goFrame", value = "/WEB-INF/jsp/sellbefore/importProject/imFrame.jsp"),
	@Result(name = "goSearch", value = "/WEB-INF/jsp/sellbefore/importProject/imSearch.jsp"),
	@Result(name = "goQueryInfo", value = "/WEB-INF/jsp/sellbefore/importProject/imQueryInfo.jsp"),
	@Result(name = "addIMP", value = "/WEB-INF/jsp/sellbefore/importProject/imProjectForm.jsp"),
	@Result(name = "goQueryInfoForCBS", value = "/WEB-INF/jsp/sellbefore/importProject/imQueryInfoForCBS.jsp")
})
public class ImportantProjectAction  extends DispatchAction {

	@Autowired
	@Qualifier("commonService")	
	private ICommonService 			commonService;

	@Autowired
	@Qualifier("beforeSellContractService")
	private IBeforeSellContractService beforeSellContractService;

	private PageInfo 							info;
	private ImportantProject				 imp;
	private List<YXOEmployeeClient>        	yxClientCodeList;			
	private String						 alertInfo;
	private Long 						 impId;
	private List<Object[]>        impHisList;		//修改历史
	private Long 						clientSelectId;  //搜索下拉列表客户保存（定位客户）

	//search condition
	private Long 						customerId;
	private String 						projectNum;
	private String						 projectName;

	private String						comeFrom;

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"queryIMPParameters",this,new String[]{"customerId", "projectNum","projectName"});    

	public String goFrame()  {
		return "goFrame";
	}

	public String goSearch()  {
		yxClientCodeList = beforeSellContractService.getEmployeeClientList( );
		return "goSearch";
	}

	public String goQueryInfo()   {
		ParameterUtils.prepareParameters(holdTool);
		info = beforeSellContractService.searchIMP(  customerId  ,   projectNum  , projectName   , info) ;
		return "goQueryInfo";
	}

	public String goQueryInfoForCBS()   {
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		yxClientCodeList = beforeSellContractService.getEmployeeClientList( );
		//处理搜索客户list
		if( clientSelectId!=null){
			yxClientCodeList = beforeSellContractService.processEmployeeClientList(clientSelectId );
		}
		info = beforeSellContractService.searchIMP(  customerId  ,   projectNum  , projectName   , info) ;
		return "goQueryInfoForCBS";
	}

	public String addIMP(){
		yxClientCodeList = beforeSellContractService.getEmployeeClientList(  );
		return "addIMP";
	}

	public String openModIMP(){
		imp = (ImportantProject)commonService.load(ImportantProject.class, impId);
		yxClientCodeList = beforeSellContractService.processEmployeeClientList( imp.getCustomerId()  );
		comeFrom = "mod";
		impHisList = beforeSellContractService.doGetImpModHistory(imp.getId());
		return "addIMP";
	}

	public String saveIMP( ){
		beforeSellContractService.saveIMP(imp);
		alertInfo = "建立成功";
		return goQueryInfo();
	}

	public String modIMP(){
		ImportantProject tempImp = (ImportantProject)commonService.load(ImportantProject.class, imp.getId());
		imp.setCreateEmployeeID(tempImp.getCreateEmployeeID());
		imp.setCreateTime(tempImp.getCreateTime());
		beforeSellContractService.recordIMPChange(imp);
		beforeSellContractService.modIMP(imp);
		alertInfo = "修改成功";
		return goQueryInfo();
	}

	public ImportantProject getImp() {
		return imp;
	}

	public void setImp(ImportantProject imp) {
		this.imp = imp;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public List<YXOEmployeeClient> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<YXOEmployeeClient> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public IBeforeSellContractService getBeforeSellContractService() {
		return beforeSellContractService;
	}

	public void setBeforeSellContractService(
			IBeforeSellContractService beforeSellContractService) {
		this.beforeSellContractService = beforeSellContractService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getImpId() {
		return impId;
	}

	public void setImpId(Long impId) {
		this.impId = impId;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public List<Object[]> getImpHisList() {
		return impHisList;
	}

	public void setImpHisList(List<Object[]> impHisList) {
		this.impHisList = impHisList;
	}

	public Long getClientSelectId() {
		return clientSelectId;
	}

	public void setClientSelectId(Long clientSelectId) {
		this.clientSelectId = clientSelectId;
	}

}
