package com.baoz.yx.action.assistance;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
@Results({
	@Result(name="queryList", value="/WEB-INF/jsp/assistance/assistanceList.jsp"),
	@Result(name="success", value="/WEB-INF/jsp/assistance/affirmAssistanceContractLeft.jsp")})
public class AffirmAssistanceContractAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	private String supplyName;
	private AssistanceContract ac ;
	private PageInfo info;
	private String min;
	private String max;
	private String sDate;
	private String eDate;
	private List<AssistanceContract> list;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>			groupList;
	
	@Override
	public String doDefault() throws Exception {
		logger.info("外协合同确认左查询");
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return "success";
	}
	
	public String query() throws Exception {
		this.logger.info("");
		StringAppender sb = new StringAppender();
		sb.append("from AssistanceContract ac where ac.is_active='1' and ac.assistanceContractType='1'");
		sb.appendIfNotEmpty(" and exists (select s.supplierid from SupplierInfo s where s.supplierName like '%"+supplyName+"%' and s.supplierid = ac.supplyId)", supplyName);	
		sb.appendIfNotEmpty(" and "+min+"<ac.contractMoney ", min);	
		sb.appendIfNotEmpty(" and ac.contractMoney<"+max+" ", max);	
		sb.appendIfNotEmpty(" and to_date('"+sDate+"', 'yyyy-MM-dd') <= ac.contractDate  ", sDate);	
		sb.appendIfNotEmpty(" and ac.contractDate <= to_date('"+eDate+"', 'yyyy-MM-dd') ", eDate);
		info = queryService.listQueryResult(sb.getString(), info);
		List queryList = (List)info.getResult();
		list = assistanceService.queryAssistanceContract(queryList);
		return "queryList";
	}	
	
	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
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
	public List getList() {
		return list;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public void setList(List<AssistanceContract> list) {
		this.list = list;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
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
