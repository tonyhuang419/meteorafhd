package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;

@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/passApplyLeft.jsp"),
	@Result(name = "query", type = ServletRedirectResult.class, value = "/assistance/passApply.action?method=query"),
	@Result(name = "queryList", value = "/WEB-INF/jsp/assistance/passApplyList.jsp")})
public class PassApplyAction extends DispatchAction {
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private AssistancePayInfo pi ;
	private PageInfo info ;
	private String supply;
	private String type;
	private String sDate;
	private String eDate;
	private String ids;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("进入付款申请管理");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	public String query() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select pi, ac.assistanceId, ac.assistanceName, si.supplierName from AssistancePayInfo pi, AssistanceContract ac, SupplierInfo si where pi.is_active='1' and pi.assistanceId=ac.id and ac.supplyId=si.supplierid and pi.payState='1'");
		if(supply!=null && !"".equals(supply)){
			sb.append(" and si.supplierName like '%"+supply+"%'");
		}
		if(type!=null && !"".equals(type)){
			sb.append(" and pi.payState = '"+type+"'");
		}
		if(sDate!=null && !"".equals(sDate)){
			sb.append(" and to_date('"+sDate+"', 'yyyy-MM-dd') <= pi.applyDate  ");
		}
		if(eDate!=null && !"".equals(eDate)){
			sb.append(" and pi.applyDate <= to_date('"+eDate+"', 'yyyy-MM-dd') ");
		}
		info = queryService.listQueryResult(sb.toString(), info);
		return "queryList";
	}
	public String pass() throws Exception {
		this.logger.info("确认通过");
		String hql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		pi.setPayState("2");
		commonService.update(pi);
		return "query";
	}
	public String back() throws Exception {
		this.logger.info("确认退回");
		String hql = "from AssistancePayInfo pi where pi.id = "+ids;
		pi = (AssistancePayInfo)commonService.uniqueResult(hql);
		pi.setPayState("0");
		commonService.update(pi);
		return "query";
	}
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}
	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}
	public AssistancePayInfo getPi() {
		return pi;
	}
	public void setPi(AssistancePayInfo pi) {
		this.pi = pi;
	}
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
	}
	public String getSupply() {
		return supply;
	}
	public void setSupply(String supply) {
		this.supply = supply;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSDate() {
		return sDate;
	}
	public void setSDate(String date) {
		sDate = date;
	}
	public String getEDate() {
		return eDate;
	}
	public void setEDate(String date) {
		eDate = date;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<Object> getListExp() {
		return listExp;
	}
	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}
	public List<Department> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}
}
