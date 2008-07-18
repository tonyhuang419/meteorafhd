package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/assistanceQueryLeft.jsp")
public class AssistanceLeftQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	private List<Object> listSup; // 查询显示所有的供应商
	private List<Object> listExp; // 查询显示所有的销售员
	@Override
	public String doDefault() throws Exception {
		String shql = "from SupplierInfo d where d.id not in(0) and d.is_active='1'";
		String exHql = "from Employee d where d.id not in(0) and d.is_active!=0";
		listSup = service.list(shql);
		listExp = service.list(exHql);
		return "queryList";
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public List<Object> getListSup() {
		return listSup;
	}
	public void setListSup(List<Object> listSup) {
		this.listSup = listSup;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}	
}
