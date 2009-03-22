package com.baoz.yx.action.contract;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

@Results( { @Result(name = "contractForm", value = "/WEB-INF/jsp/contract/reservationForm.jsp") })
public class ReservationReturnFormAction extends DispatchAction {
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private List<YXTypeManage>contractTypeList;
	private List<YXClientCode> yXClientCodes;
	public List<YXTypeManage> getContractTypeList() {
		return contractTypeList;
	}
	public void setContractTypeList(List<YXTypeManage> contractTypeList) {
		this.contractTypeList = contractTypeList;
	}
	public ISystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}
	public List<YXClientCode> getYXClientCodes() {
		return yXClientCodes;
	}
	public void setYXClientCodes(List<YXClientCode> clientCodes) {
		yXClientCodes = clientCodes;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String doDefault() throws Exception {
		Employee user=UserUtils.getUser();
        contractTypeList=typeManageService.getYXTypeManage(1019L);
		yXClientCodes=(List<YXClientCode>)systemService.queryClients(user.getId());
		return "contractForm";
	}

}
