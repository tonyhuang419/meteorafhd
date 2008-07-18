package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXClientCodeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/*
 * 草搞合同相关操作
 * @author xiao ping (xiaoping@baoz.com.cn)
 */
@Results( { @Result(name = "searchList", value = "/WEB-INF/jsp/contract/searchContractList.jsp") })
public class SearchContractListAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	private List<YXTypeManage>projectDeptTypeList;//工程部门列表
	private List<YXTypeManage>contractTypeList;
	private List<YXClientCode>yXClientCodeList;
	
	private PageInfo info;

	private String name;

	private String conType;

	private String minConSignDate;

	private String maxConSignDate;

	private Long conState;
	//private String clietId;
	
	@Override
	public String doDefault() throws Exception {
		
		System.out.println("excute!!!!!!!!!!!!!!!!!!!!!!" + getName()
				+ maxConSignDate);

		StringBuffer sp = new StringBuffer();
        Employee emp=UserUtils.getUser();
		sp
				.append("select c,e from ContractMainInfo c,Employee e where c.saleMan=e.id and c.saleMan="+emp.getId());
		
		/*if(clietId!=null&&!clietId.equals("")){
			sp.append(" and c.id="+new Long(getClietId()));
		}*/
		if (name != null && !name.equals("")) {
			sp.append(" and c.conCustomer='" + name + "'");
		}
		if (conType != null && !conType.equals("")) {
			sp.append(" and c.conType like '%" + conType + "%'");
		}
		if (minConSignDate != null && !minConSignDate.equals("")
				&& maxConSignDate != null && !maxConSignDate.equals("")) {

			sp.append(" and c.conSignDate>="
					+ "to_date('"+minConSignDate+"','yyyy-mm-dd')"
					+ " and c.conSignDate<="
					+ "to_date('"+maxConSignDate+"','yyyy-mm-dd')");
		}
		if (conState != null && !conState.equals("")) {
			sp.append(" and c.conState=" + conState);
		}else{
			sp.append(" and (c.conState=0 or c.conState=2 or c.conState=1)");
		}
       if(minConSignDate!=null&&!minConSignDate.equals("")&&(maxConSignDate==null||maxConSignDate.equals(""))){
        	sp.append(" and c.conSignDate>="+"to_date('"+minConSignDate+"','yyyy-mm-dd')");
        }
        if(maxConSignDate!=null&&!maxConSignDate.equals("")&&(minConSignDate==null||minConSignDate.equals(""))){
        	sp.append(" and c.conSignDate<="+"to_date('"+maxConSignDate+"','yyyy-mm-dd')");
        }
        sp.append(" order by c.conState asc");
		info = queryService.listQueryResult(sp.toString(), info);
		contractTypeList=typeManageService.getYXTypeManage(1019L);
		yXClientCodeList=systemService.queryAllClients();
		projectDeptTypeList=typeManageService.getYXTypeManage(1018L);
		return "searchList";
	}
    
	public Long getConState() {
		return conState;
	}

	public void setConState(Long conState) {
		this.conState = conState;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getMaxConSignDate() {
		return maxConSignDate;
	}

	public void setMaxConSignDate(String maxConSignDate) {
		this.maxConSignDate = maxConSignDate;
	}

	public String getMinConSignDate() {
		return minConSignDate;
	}

	public void setMinConSignDate(String minConSignDate) {
		this.minConSignDate = minConSignDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}

	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}

	public List<YXClientCode> getYXClientCodeList() {
		return yXClientCodeList;
	}

	public void setYXClientCodeList(List<YXClientCode> clientCodeList) {
		yXClientCodeList = clientCodeList;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public List<YXTypeManage> getProjectDeptTypeList() {
		return projectDeptTypeList;
	}

	public void setProjectDeptTypeList(List<YXTypeManage> projectDeptTypeList) {
		this.projectDeptTypeList = projectDeptTypeList;
	}

	
	
	
	

}
