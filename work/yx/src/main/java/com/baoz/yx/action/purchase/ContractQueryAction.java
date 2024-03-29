package com.baoz.yx.action.purchase;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.SqlUtils;

/**
 * 查询出所有合同的信息
 * 
 * @author chenqing (chenqing@baoz.com.cn)
 */
@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/purchase/showcontract.jsp"),
	@Result(name = "showClient", value = "/WEB-INF/jsp/purchase/showClient.jsp")})
	public class ContractQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("purService")
	private IPurService pruService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
	private ServletRequest request;
	private List conList;//合同列表
	private PageInfo info;
	private List<YXTypeManage> dutyList;//工程部门列表
	private String conName;//合同名称——模糊查询
	private String conId;//合同编号——精确查询
	private String mainItemDept;//工程部门代码
	private String partyAProId;//项目号——精确查询
	private String clientName;//客户名称——模糊查询
	private String amids;
	private String action;
	private String applyType;//合同类型
	private ApplyMessage am;
	private List<Object> yxClient;

	@Override
	public String doDefault() throws Exception {
		this.searchContract();
		return "queryList";
	}

	private void searchContract() {
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
//		String  chql = pruService.oqlfromWhere(conName, conId,mainItemDept, partyAProId,applyType);
		String hql = pruService.oql(conName, conId,mainItemDept, partyAProId,applyType)+pruService.oqlfromWhere(conName, conId,mainItemDept, partyAProId,applyType);
		info = queryService.listQueryResult( SqlUtils.getCountSql(hql)  , hql,  info);
		//		List<Object[]> oList = (List<Object[]>)info.getResult();
		logger.info("查询合同成功");
		dutyList = typemanageservice.getYXTypeManage(1018L);
	}

	public String link() throws Exception {		
		logger.info(amids);
		ApplyMessage o = (ApplyMessage) service.uniqueResult("from ApplyMessage obj where obj.id='" + amids + "'");
		applyType= o.getApplyType();
		this.searchContract();
		return "queryList";
	}
	
	public String showClient() throws Exception{
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		info =queryService.listQueryResult(pruService.oql1(clientName).toString(), info);
		return "showClient";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IPurService getPruService() {
		return pruService;
	}

	public void setPruService(IPurService pruService) {
		this.pruService = pruService;
	}

	public IYXTypeManageService getTypemanageservice() {
		return typemanageservice;
	}

	public void setTypemanageservice(IYXTypeManageService typemanageservice) {
		this.typemanageservice = typemanageservice;
	}

	public List getConList() {
		return conList;
	}

	public void setConList(List conList) {
		this.conList = conList;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<YXTypeManage> getDutyList() {
		return dutyList;
	}

	public void setdutyList(List<YXTypeManage> dutyList) {
		this.dutyList = dutyList;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getMainItemDept() {
		return mainItemDept;
	}

	public void setMainItemDept(String mainItemDept) {
		this.mainItemDept = mainItemDept;
	}

	public String getPartyAProId() {
		return partyAProId;
	}

	public void setPartyAProId(String partyAProId) {
		this.partyAProId = partyAProId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setDutyList(List<YXTypeManage> dutyList) {
		this.dutyList = dutyList;
	}

	public String getAmids() {
		return amids;
	}

	public void setAmids(String amids) {
		this.amids = amids;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public ApplyMessage getAm() {
		return am;
	}

	public void setAm(ApplyMessage am) {
		this.am = am;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public List<Object> getYxClient() {
		return yxClient;
	}
	public void setYxClient(List<Object> yxClient) {
		this.yxClient = yxClient;
	}

}
