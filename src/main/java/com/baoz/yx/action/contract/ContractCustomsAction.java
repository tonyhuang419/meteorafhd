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
import com.baoz.yx.service.IYXClientCodeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
@Results( {@Result(name="customersContract",value="/WEB-INF/jsp/contract/customersContract.jsp") })
public class ContractCustomsAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("yXClietCodeService")
	private IYXClientCodeService clientCodeService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private List<YXTypeManage>contractTypeList;
	private PageInfo info;
	private YXClientCode clientCode;
	private Employee employee;
	private String clietId;
	@Override
	public String doDefault() throws Exception {
		
    	StringBuffer sp = new StringBuffer();
        Employee emp=UserUtils.getUser();
		sp
				.append("select c from ContractMainInfo c where c.saleMan="+emp.getId());
		sp.append(" and c.conCustomer="+new Long(clietId));
		sp.append(" and (c.conState=0 or c.conState=2)");
		//sp.append(" order by c.conState asc");
		sp.append(" order by c.conState asc");
		info=queryService.listQueryResult(sp.toString(), info);
		clientCode=clientCodeService.getYXClientCode(new Long(clietId));
		employee=UserUtils.getUser();
		contractTypeList=typeManageService.getYXTypeManage(1019L);
    	return "customersContract";
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public YXClientCode getClientCode() {
		return clientCode;
	}
	public void setClientCode(YXClientCode clientCode) {
		this.clientCode = clientCode;
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public IYXClientCodeService getClientCodeService() {
		return clientCodeService;
	}
	public void setClientCodeService(IYXClientCodeService clientCodeService) {
		this.clientCodeService = clientCodeService;
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
	public String getClietId() {
		return clietId;
	}
	public void setClietId(String clietId) {
		this.clietId = clietId;
	}

}
