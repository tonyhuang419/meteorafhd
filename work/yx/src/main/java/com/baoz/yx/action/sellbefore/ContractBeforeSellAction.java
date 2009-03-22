
package com.baoz.yx.action.sellbefore;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 售前合同新增操作
 */
@Results( {	
	@Result(name = "saveSBSSelect", type = ServletRedirectResult.class, value = "/sellbefore/contractBeforeSellQuery.action"),
	@Result(name = "success", type = ServletRedirectResult.class, value = "/sellbefore/showSellBefore.action?id=${cbs.ID}&method=selectUpdate&comeFrom=new"),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/sellbefore/addContractBeforeSell.jsp"),
	@Result(name = "find",type = ServletRedirectResult.class,value = "/sellbefore/selectSellBefore.action?message=modSucc")
})
public class ContractBeforeSellAction extends DispatchAction implements  ServletRequestAware , SessionAware {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;

	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;

	@Autowired
	@Qualifier("systemService")
	private ISystemService 		systemService;

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("beforeSellContractService")
	private IBeforeSellContractService beforeSellContractService;

	private ContractBeforeSell  cbs;
	private HttpServletRequest request;
	private Map session;
	private Long				clientId;
	//	private String 				businessTypeId;

	private Long				projectEconomyId;
	private String 				succSave; 
	private String 				selectExit;
	private String 				customerLinkMan;                   	//客户联系人
	private  boolean         cbsHasMod = false;
	private Long				importantProjectId;				//重点工程系统号

	public String doDefault() throws Exception {
		logger.info("售前合同管理新增");		
		return ENTER_SAVE;
	}

	/**
	 * 保存售前合同管理
	 */
	@SuppressWarnings("unchecked")
	public String saveCBS() throws Exception {
		logger.info("保存售前合同");
		//设置客户联系人
		if(customerLinkMan!=null && !"".equals(customerLinkMan)){
			cbs.setLinkManId(contractservice.getLinkManIdByName(customerLinkMan,cbs.getCustomerId()));
		}
		cbs.setTimeOfVary(0L);
		cbs = this.genContractBeforeSell(cbs , true);
		eventService.saveContractBeforeSell(cbs);
		beforeSellContractService.saveImpAndCBSRelation(cbs.getID() , importantProjectId );
		systemService.addRelation(cbs.getCustomerId());
		cbs.getID();
		session.put("selectExit", selectExit);
		return "success";
	}

	/**
	 * 保存并关闭
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String saveSBSSelect() throws Exception{
		logger.info("保存售前合同");
		//设置客户联系人
		if(customerLinkMan!=null && !"".equals(customerLinkMan)){
			cbs.setLinkManId(contractservice.getLinkManIdByName(customerLinkMan,cbs.getCustomerId()));
		}
		systemService.addRelation(cbs.getCustomerId());
		if(request.getParameter("comeFrom").equals("mod")){   //修改界面进入
			//			保存变更履历
			cbsHasMod = beforeSellContractService.recordCBSChange(cbs , importantProjectId );  //返回值true售前合同存在修改
			cbs = this.genContractBeforeSell(cbs , cbsHasMod);
			eventService.saveContractBeforeSell(cbs);
			beforeSellContractService.saveImpAndCBSRelation(cbs.getID() , importantProjectId );
			return "find";
		}
		else{
			cbs = this.genContractBeforeSell(cbs,true);
			ActionContext.getContext().getSession().put("succSave", "0");
			eventService.saveContractBeforeSell(cbs);
			beforeSellContractService.saveImpAndCBSRelation(cbs.getID() , importantProjectId );
			return "saveSBSSelect";
		}
	}

	private ContractBeforeSell genContractBeforeSell(ContractBeforeSell cbs , boolean modUpdateTime){
		cbs.setIs_active("1");
		cbs.setById(UserUtils.getUser().getId());
		if( modUpdateTime ){
			cbs.setUpdateBy(new Date());
			if(  request.getParameter("comeFrom").equals("mod") ){
				if( cbs.getTimeOfVary() == null){
					cbs.setTimeOfVary(1L);
				}
				else{
					cbs.setTimeOfVary(cbs.getTimeOfVary()+1);
				}
			}
		}
		else{
			ContractBeforeSell tempCBS = (ContractBeforeSell)service.load(ContractBeforeSell.class, cbs.getID()) ;
			cbs.setUpdateBy(tempCBS.getUpdateBy());
		}
		if( StringUtils.isBlank(cbs.getSellBeforeNum()) ){
			cbs.setSellBeforeNum(codeGenerateService.generateSellbeforeCode(cbs.getCustomerId()));
		}
		cbs.setConState("0");
		return cbs;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public ContractBeforeSell getCbs() {
		return cbs;
	}

	public void setCbs(ContractBeforeSell cbs) {
		this.cbs = cbs;
	}




	//	public String getBusinessTypeId() {
	//		return businessTypeId;
	//	}
	//
	//	public void setBusinessTypeId(String businessTypeId) {
	//		this.businessTypeId = businessTypeId;
	//	}

	public Long getProjectEconomyId() {
		return projectEconomyId;
	}

	public void setProjectEconomyId(Long projectEconomyId) {
		this.projectEconomyId = projectEconomyId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getSuccSave() {
		return succSave;
	}

	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public String getSelectExit() {
		return selectExit;
	}

	public void setSelectExit(String selectExit) {
		this.selectExit = selectExit;
	}

	public IEventInfoService getEventService() {
		return eventService;
	}

	public void setEventService(IEventInfoService eventService) {
		this.eventService = eventService;
	}

	public String getCustomerLinkMan() {
		return customerLinkMan;
	}

	public void setCustomerLinkMan(String customerLinkMan) {
		this.customerLinkMan = customerLinkMan;
	}

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map session) {
		this.session = session;  
	}

	public IBeforeSellContractService getBeforeSellContractService() {
		return beforeSellContractService;
	}

	public void setBeforeSellContractService(
			IBeforeSellContractService beforeSellContractService) {
		this.beforeSellContractService = beforeSellContractService;
	}

	public boolean isCbsHasMod() {
		return cbsHasMod;
	}

	public void setCbsHasMod(boolean cbsHasMod) {
		this.cbsHasMod = cbsHasMod;
	}

	public Long getImportantProjectId() {
		return importantProjectId;
	}

	public void setImportantProjectId(Long importantProjectId) {
		this.importantProjectId = importantProjectId;
	}

}
