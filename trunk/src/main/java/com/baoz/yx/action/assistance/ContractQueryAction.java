package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * 查询出所有合同的信息
 * 
 * @author chenqing (chenqing@baoz.com.cn)
 */
@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/showcontract.jsp"),
	@Result(name = "showClient", value = "/WEB-INF/jsp/assistance/showClient.jsp")})
public class ContractQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("purService")
	private IPurService pruService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
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

	@Override
	public String doDefault() throws Exception {
		info = queryService.listQueryResult(pruService.oql(conName, conId,
				mainItemDept, partyAProId, null).toString(), info);
		logger.info(pruService.oql(conName, conId, mainItemDept, partyAProId, null).toString());
		logger.info("查询合同成功");
		conList = pruService.contractList((List) info.getResult());
		dutyList = typemanageservice.getYXTypeManage(1018L);
		return "queryList";
	}
	
	public String showClient() throws Exception{
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


}
