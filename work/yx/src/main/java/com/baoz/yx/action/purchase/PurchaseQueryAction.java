package com.baoz.yx.action.purchase;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;


@Result(name = "queryList", value = "/WEB-INF/jsp/purchase/purQuery.jsp")
public class PurchaseQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	private List<Object> listExp; // 查询显示所有的销售员
	private List<Object> listCli; // 查询显示所有的客户
	private List<Object> listAff; // 查询显示所有的申购单状态
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		// 查询条件 销售员,审购类型（2种）,申购单状态,客户,审购日期
		String expHql = "from Employee d where d.id not in(0) and d.is_active!=0";
		String cliHql = "from YXClientCode d where d.id not in(0) and d.is_active!=0";
		String affHql = "from ApplyMessage a where a.id not in(0) and a.is_active!=0";
		listExp = service.list(expHql);
		listCli = service.list(cliHql);
		listAff = service.list(affHql);
		return "queryList";
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Object> getListCli() {
		return listCli;
	}

	public void setListCli(List<Object> listCli) {
		this.listCli = listCli;
	}

	public List<Object> getListAff() {
		return listAff;
	}

	public void setListAff(List<Object> listAff) {
		this.listAff = listAff;
	}
	
}
