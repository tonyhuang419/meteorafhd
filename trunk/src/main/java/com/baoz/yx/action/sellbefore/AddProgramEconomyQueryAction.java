package com.baoz.yx.action.sellbefore;

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
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.programEconomy.YXOProgramEconomy;
import com.baoz.yx.entity.programEconomy.YXOSectionInfo;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;


/**
 *	新增工程经济
 * 
 */
@Results( {	
	@Result(name = "addProgramEconomy", value = "/WEB-INF/jsp/sellbefore/addProgramEconomy.jsp"),
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
	
	private List<YXTypeManage>  		designSpeedList;
	
	private YXOProgramEconomy   yp;  			//工程经济实体
	private YXOSectionInfo		ys;				//阶段信息实体
	private List<YXOProgramEconomy> 		proEcoList;
	

	@Override
	public String doDefault()  throws Exception  {
		this.logger.info("根据选择售前合同ID查询添加工程经济");
		String ids = request.getParameter("ids");		
		logger.info(ids);
		Long id=Long.valueOf(ids);
		proEcoList=commonService.list(" select pe.shellContractId from YXOProgramEconomy pe");	
		for (int i=0;i<=proEcoList.size();i++) {
			
		}
		info=queryService.listQueryResult(" from ContractBeforeSell c where c.ID='" + id + "' and is_active=1",info);
		designSpeedList=typeManageService.getYXTypeManage(1011L);
		
		for(int i=0;i<this.designSpeedList.size();i++)
		{
			if(!designSpeedList.get(i).getTypeSmall().equals("可研报告") && !designSpeedList.get(i).getTypeSmall().equals("可报告(二阶段设计)"))
			{
				designSpeedList.remove(i);
			}
		}
		logger.info("查询显示完毕");
		return "addProgramEconomy";
	}	
	
	//新增工程经济
	public String saveProgramEconomy()throws Exception {
		logger.info("===================我被执行了");
		logger.info(ID);
		    Long uid = UserUtils.getUser().getId();
			yp.setIs_active("1");		
			yp.setState("6");
			yp.setById(uid);
			yp.setShellContractId(ID);
			yp.setExployeeId(uid);
			yp.setProjectName(projectName);
			yp.setEnterFlag("0");
			logger.info(projectName);
			commonService.save(yp);		
			ys.setEconomy(yp);
			ys.setBpmsFlag("0");
			ys.setIs_active("1");
			commonService.save(ys);
			logger.info("新增工程经济>>成功");
		return "program";
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


}

