package com.baoz.yx.action.purchase;

import java.util.List;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;

import com.baoz.core.web.struts2.DispatchAction;

/**
 * 初始化部分查询信息,点击次action条到条件查询页
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/purchase/purVerifyQuery.jsp")
// 跳转到输入查询条件页
public class PurQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	private List<Object> listExp; // 查询显示所有的销售员
	private List<Object> listCli; // 查询显示所有的客户

	@Override
	public String doDefault() throws Exception {
		this.logger.info("");
		// 查询条件 销售员,审购类型（2种）,客户,审购日期
		String expHql = "from Employee d where d.id not in(0) and d.is_active!=0";
		String cliHql = "from YXClientCode d where d.id not in(0) and d.is_active!=0";
		listExp = service.list(expHql);
		listCli = service.list(cliHql);
		return "queryList";
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

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

}
