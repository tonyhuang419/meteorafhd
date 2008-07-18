package com.baoz.yx.action.assistance;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

@Result(name="success", value="/WEB-INF/jsp/assistance/chooseSup.jsp")
public class ChooseSupAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	private String supName; 
	@Override
	public String doDefault() throws Exception {
		this.logger.info("选择供应商");
		String hql = "from SupplierInfo s where s.is_active='1'";
		info = queryService.listQueryResult(hql, info);
		return "success";
	}
	
	public String selectByName() throws Exception {
		this.logger.info("模糊查询供应商");
		String hql = "from SupplierInfo s where s.is_active='1' and s.supplierName like '%" + supName +"%'";
		info = queryService.listQueryResult(hql, info);
		return "success";
	}
	
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}
	
}
