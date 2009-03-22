package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/showcontract.jsp"),
	@Result(name = "showClient", value = "/WEB-INF/jsp/purchase/showClient.jsp")})
public class ChooseContractAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
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

	@Override
	public String doDefault() throws Exception {
		if(info==null){
			info=new PageInfo();
		}
		dutyList = typemanageservice.getYXTypeManage(1018L);
		info.setPageSize(10);
		StringBuffer sb = new StringBuffer();
		String select = "select p , c ,(select sum(ii.conItemAmountWithTax) from ContractItemInfo ii where ii.contractItemMaininfo = p.conItemMinfoSid ) ";
		sb.append(" from ContractItemMaininfo p, ContractMainInfo c where p.contractMainInfo=c.conMainInfoSid and p.conItemId is not null and c.conState>='4' and c.conState<='9' and c.saleMan="+UserUtils.getUser().getId());
		if (StringUtils.isNotEmpty(conName))
			sb.append(" and c.conName like '%").append(conName).append("%'");
		if (StringUtils.isNotEmpty(conId))
			sb.append(" and c.conId like '%").append(conId).append("%'");
		if (StringUtils.isNotEmpty(mainItemDept) && !"-1".equals(mainItemDept))
			sb.append(" and p.itemResDept = '").append(mainItemDept)
			.append("'");
		if (StringUtils.isNotEmpty(partyAProId))
			sb.append(" and p.conItemId = '").append(partyAProId).append("'");
		
		info = queryService.listQueryResult("select count(*) "+sb,select+sb, info);
		
		List<Object[]> oList = (List<Object[]>)info.getResult();
		logger.info("查询合同成功");
		return "queryList";
	}
	
	public String showClient() throws Exception{
		info =queryService.listQueryResult(assistanceService.oql1(clientName).toString(), info);
		return "showClient";
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
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

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
}
