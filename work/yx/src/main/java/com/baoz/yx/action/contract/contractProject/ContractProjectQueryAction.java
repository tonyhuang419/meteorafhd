package com.baoz.yx.action.contract.contractProject;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ItemCostingSure;
import com.baoz.yx.utils.UserUtils;

/**
 * 合同项目成本确认查询操作
 * 
 * @author 
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/contract/contractProject/contractProjectConfirm.jsp")
public class ContractProjectQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;
	@Override
	public String doDefault() throws Exception {
		this.logger.info("合同项目成本确认查询");
		Employee user=UserUtils.getUser(); 
		info = queryService.listQueryResult(
				"select i,m,e,t.typeName from ContractItemMaininfo i , ContractMainInfo m ,Employee e,YXTypeManage t where " +
				"i.contractMainInfo=m.conMainInfoSid and i.itemResDept=t.typeSmall and t.typeBig=1018 and i.conItemCostSure in (2,3,4)" +
				" and m.saleMan = e.id and m.saleMan = "+user.getId()+" order by m.conId ,i.conItemCostSure", info);
		return "queryList";
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
	
}

