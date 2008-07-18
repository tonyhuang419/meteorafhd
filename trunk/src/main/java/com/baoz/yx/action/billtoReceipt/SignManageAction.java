package com.baoz.yx.action.billtoReceipt;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;

import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.entity.YXTypeManage;

import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/**
 * 开票签收相关操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/billtoReceipt/signManageQuery.action"),
		@Result(name = "success",  value = "/WEB-INF/jsp/billtoReceipt/billApplyVerifyList.jsp"),
		@Result(name = "pinfo", value = "/WEB-INF/jsp/programEconomy/projectInfo.jsp"),
		@Result(name = "showpurs", value = "/WEB-INF/jsp/purchase/showpurs.jsp"),
		@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
		@Result(name = "economyInfo", value = "/WEB-INF/jsp/programEconomy/projectEconomyInfo.jsp"),
		@Result(name = "economyView", value = "/WEB-INF/jsp/programEconomy/economyView.jsp"),
		@Result(name = "showClientsList", value = "/WEB-INF/jsp/programEconomy/showClients.jsp"),
		@Result(name = "edit", value = "/WEB-INF/jsp/programEconomy/economyEdit.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/purchase/modifyPurchase.jsp") })
public class SignManageAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private PageInfo info;
	private String bId;
	private String action;
    private Long   expId; 
    private HttpServletRequest request = null;
    private List<Object> listExp; // 查询显示所有的销售员
	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public String doDefault() throws Exception {
		logger.info("=================executeHere!");

		return "";
	}

	/**
	 * 工程经济状态 0-结束；1-售前合同；2-正式合同3-确认通过4-确认退回5-草稿-6待确认
	 * 
	 * @return
	 */
	public String verifyState() {
		// 无空指针异常
		//确认签收
		String passHql="update ApplyBill obj set obj.sign=1 where obj.id in(";
		//取消签收
		String backHql="update ApplyBill obj set obj.sign=0 where obj.id in(";
		String aaa[] = bId.split(",");
		if (aaa[0] != null && !"".equals(aaa[0])) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < aaa.length; i++) {
				sb.append(aaa[i]).append(",");
			}
			sb.append('0');
			
			int a = systemService.updateProEconomyState(action, sb, passHql, backHql);
			if (a > 0)
				return "succ";
			else {
				logger.info("更新状态操作不成功！");
				return "succ";
			}
		} else {
			return "succ";
		}

	}
	
	public ICommonService getService() {
		return service;
	}


	public void setService(ICommonService service) {
		this.service = service;
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


	
	public String getBId() {
		return bId;
	}

	public void setBId(String id) {
		bId = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
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
}
