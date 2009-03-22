package com.baoz.yx.action.sellbefore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.programEconomy.YXOProgramEconomy;
import com.baoz.yx.entity.programEconomy.YXOSectionInfo;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;


/**
 *	新增工程经济
 * 
 */
@Results( {	
	@Result(name = "addProgramEconomy", value = "/WEB-INF/jsp/sellbefore/addProgramEconomy.jsp"),
	@Result(name = "programSubmit",type = ServletRedirectResult.class, value = "/sellbefore/addProgramEconomyQuery.action?ids=${yp.id}&method=showUpdate&ID=${ID}"),
	@Result(name = "program",type = ServletRedirectResult.class, value = "/sellbefore/selectSellBefore.action")})
public class AddProgramEconomyQueryAction extends DispatchAction  implements ServletRequestAware {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	private PageInfo 			info;
	private ServletRequest		request;
	private List<Object>       	list;
	
	private Long   				ID;
	private String 				projectName;
	
	private Long 				ids;
	
	
	private List<YXTypeManage>  		designSpeedList;
	
	private YXOProgramEconomy   yp;  			//工程经济实体
	private YXOSectionInfo		ys;				//阶段信息实体
	private List<YXOProgramEconomy> 		proEcoList;
	private String 					proSave;

	@SuppressWarnings("unchecked")
	@Override
	public String doDefault()  throws Exception  {
		this.logger.info("根据选择售前合同ID查询添加工程经济");
		logger.info(ids);
		proEcoList=commonService.list(" select pe from YXOProgramEconomy pe where pe.shellContractId =" + ids);	
		if(proEcoList.size() == 0){
			logger.info("查询显示完毕");
		}
		else{
			yp = proEcoList.get(0);
			ys = (YXOSectionInfo) commonService.uniqueResult(" from YXOSectionInfo yx where yx.economy.id = ? ", yp.getId());
			if(!"5".equals(yp.getState())){
				proSave="1";
				ActionContext.getContext().getSession().put("proSave", proSave);
				return "program";
			}
		}
		info=queryService.listQueryResult(" from ContractBeforeSell c where c.ID='" + ids + "' and is_active=1",info);
		List<YXTypeManage> aaa=typeManageService.getYXTypeManage(1011L);
		designSpeedList=new ArrayList<YXTypeManage>();
		for (YXTypeManage typeManage : aaa) {
			if(!typeManage.getTypeSmall().equals("3") && !typeManage.getTypeSmall().equals("4")){
				designSpeedList.add(typeManage);
			}
		}
		return "addProgramEconomy";
	}	
	
	/**
	 * 新增工程经济
	 * @return
	 * @throws Exception
	 */
	public String saveProgramEconomy() throws Exception {
		saveOrUpdateProgramEconomy("6");
		proSave="3";
		ActionContext.getContext().getSession().put("proSave", proSave);
		return "program";
	}
	/**
	 * 保存停留到页面上,显示修改页面
	 * @return
	 * @throws Exception
	 */
	public String saveProgramEconomySubmit() throws Exception {
			saveOrUpdateProgramEconomy("5");
			return "programSubmit";
	}

	private void saveOrUpdateProgramEconomy(String ypState) {
		logger.info(ID);
		Long uid = UserUtils.getUser().getId();
		ContractBeforeSell cbs = (ContractBeforeSell) commonService.load(ContractBeforeSell.class, ID);
		yp.setIs_active("1");		
		yp.setApplyTime(new Date());    //申请时间
		yp.setById(uid);                //保存人
		yp.setUpdateBy(new Date());       //保存时间
		yp.setShellContractId(ID);        //售前ID
		yp.setExployeeId(uid);            
		yp.setProjectName(projectName);
		yp.setEnterFlag("0");
		yp.setState(ypState);
		yp.setClientId(cbs.getCustomerId());
		logger.info(projectName);
		commonService.saveOrUpdate(yp);		
		ys.setEconomy(yp);
		ys.setBpmsFlag("0");
		ys.setIs_active("1");
		commonService.saveOrUpdate(ys);
	}	
	/**
	 * 保存后显示信息到页面
	 * @return
	 */
	public String showUpdate(){
		List<YXTypeManage> aaa=typeManageService.getYXTypeManage(1011L);
		designSpeedList=new ArrayList<YXTypeManage>();
		for (YXTypeManage typeManage : aaa) {
			if(!typeManage.getTypeSmall().equals("3") && !typeManage.getTypeSmall().equals("4")){
				designSpeedList.add(typeManage);
			}
		}
		logger.info(ids);
		info=queryService.listQueryResult(" from ContractBeforeSell c where c.ID='" + ID + "' and is_active=1",info);
		logger.info("售前合同的编号是"+info.getPageSize());
		yp = (YXOProgramEconomy) commonService.uniqueResult(" from YXOProgramEconomy yp where yp.id ="+ids);
		
		ys = (YXOSectionInfo) commonService.uniqueResult(" from YXOSectionInfo yx where yx.economy.id = ? ", yp.getId());
		
		return "addProgramEconomy";
	}
	/**
	 * 验证工程经济编号是否重复
	 * @return
	 */
	public String isExist(){
		logger.info("isExist");
		YXOProgramEconomy Temp=(YXOProgramEconomy) commonService.uniqueResult("from YXOProgramEconomy sp where sp.projectNumber = ?", yp.getProjectNumber());
		logger.info(Temp);
		if(Temp!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}
	
	public YXOProgramEconomy getYp() {
		return yp;
	}

	public void setYp(YXOProgramEconomy yp) {
		this.yp = yp;
	}


	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}


	public ICommonService getCommonService() {
		return commonService;
	}


	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}


	public List<Object> getList() {
		return list;
	}


	public void setList(List<Object> list) {
		this.list = list;
	}


	public IQueryService getQueryService() {
		return queryService;
	}


	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public YXOSectionInfo getYs() {
		return ys;
	}

	public void setYs(YXOSectionInfo ys) {
		this.ys = ys;
	}


	public List<YXTypeManage> getDesignSpeedList() {
		return designSpeedList;
	}

	public void setDesignSpeedList(List<YXTypeManage> designSpeedList) {
		this.designSpeedList = designSpeedList;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXOProgramEconomy> getProEcoList() {
		return proEcoList;
	}

	public void setProEcoList(List<YXOProgramEconomy> proEcoList) {
		this.proEcoList = proEcoList;
	}

	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public String getProSave() {
		return proSave;
	}

	public void setProSave(String proSave) {
		this.proSave = proSave;
	}


}

