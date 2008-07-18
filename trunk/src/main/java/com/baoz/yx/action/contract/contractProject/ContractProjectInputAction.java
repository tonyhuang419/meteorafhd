package com.baoz.yx.action.contract.contractProject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;

import com.baoz.core.utils.DateUtil;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.Gonggao;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ItemCostingSure;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;

/**
 * 合同项目成本录入
 * 
 * @author 
 */
@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/contract/contractProject/contractProjectInputQuery.action" ),
	@Result(name = "queryList", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInput.jsp"),
	@Result(name = "queryModify", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputModify.jsp"),
	@Result(name = "queryInput", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputReinput.jsp"),
	@Result(name = "queryInputClick", value = "/WEB-INF/jsp/contract/contractProject/contractProjectInputClick.jsp"),
	@Result(name = "queryInputRightClick", value = "/WEB-INF/jsp/contract/contractProject/contractProjectDepartment.jsp"),
	@Result(name = "addOrUpdate", value = "/WEB-INF/jsp/contract/contractProject/addOrUpdateResult.jsp")
})

public class ContractProjectInputAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired 
	@Qualifier("commonService")
	private ICommonService service;
	
	private PageInfo info;
	private ServletRequest request;
	private Object[] projectInfo1;
	private String projectInfo2;
	private List<ContractItemMaininfo> contractProList;
	
    private ItemCostingSure s;
	private String confirmOrAll;
	private Double remainAssistance;
	private Double remainBill;
	private Long id;
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"ContractProjectInputParameter",this,new String[]{"confirmOrAll"});

	public String getConfirmOrAll() {
		return confirmOrAll;
	}

	public void setConfirmOrAll(String confirmOrAll) {
		this.confirmOrAll = confirmOrAll;
	}

	@Override
	public String doDefault() throws Exception {
		this.logger.info("合同项目成本确认");
		Employee user=UserUtils.getUser(); 
		StringBuffer str = new StringBuffer();
		ParameterUtils.prepareParameters(holdTool);
		str.append(" select i,m,e,t.typeName from ContractItemMaininfo i , ContractMainInfo m ,Employee e,YXTypeManage t where " +
				" i.contractMainInfo=m.conMainInfoSid and i.itemResDept=t.typeSmall and t.typeBig=1018 and m.saleMan = "+user.getId()+" and m.saleMan = e.id  " );
		//0/全部；1/已录入；2/待确认；3/确认通过；4/确认退回
		if (StringUtils.equals(confirmOrAll, "0")){
			str.append(" and i.conItemCostSure in (1,2,3,4)");
		}
		if (StringUtils.equals(confirmOrAll, "1")){
			str.append(" and i.conItemCostSure in (1)");
		}
		if (StringUtils.equals(confirmOrAll, "2")){
			str.append(" and i.conItemCostSure in (2)");
		}
		if (StringUtils.equals(confirmOrAll, "3")){
			str.append(" and i.conItemCostSure in (3)");
		}
		if (StringUtils.equals(confirmOrAll, "4")){
			str.append(" and i.conItemCostSure in (4)");
		}
		str.append(" order by m.conId ,i.conItemCostSure");	
        info = queryService.listQueryResult(str.toString(), info);
		return "queryList";
//		+user.getId()
	}
	
	public String modify() throws Exception{
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		projectInfo1 = (Object[])service.uniqueResult("select i,m.conId from ContractItemMaininfo i , ContractMainInfo m " +
				" where i.contractMainInfo=m.conMainInfoSid and i.conItemMinfoSid = '" + id + "'");
		return "queryModify";
	}
	
	public String modifySubmit() throws Exception{	
        ContractItemMaininfo i = (ContractItemMaininfo) service
		.uniqueResult("from ContractItemMaininfo obj where obj.conItemMinfoSid='" + id + "'");
        i.setRemainAssistance(remainAssistance);
        i.setRemainBill(remainBill);
        service.update(i);
		return "addOrUpdate";
	}
	
	public String reinput() throws Exception{
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		projectInfo1 = (Object[])service.uniqueResult("select i,m.conId from ContractItemMaininfo i , ContractMainInfo m " +
				" where i.contractMainInfo=m.conMainInfoSid and i.conItemMinfoSid = '" + id + "'");
		projectInfo2 = (String)service.uniqueResult("select ss.feedbackInfo from ItemCostingSure ss where ss.id in (select max(s.id) from ItemCostingSure s where s.contractItemMaininfo = '" + id + "'" + ")" );
		return "queryInput";
	}
	
	public String reinputSubmit() throws Exception{	
        ContractItemMaininfo i = (ContractItemMaininfo) service
		.uniqueResult("from ContractItemMaininfo obj where obj.conItemMinfoSid='" + id + "'");
        i.setRemainAssistance(remainAssistance);
        i.setRemainBill(remainBill);
        i.setConItemCostSure(1L);
        service.update(i);
		return "addOrUpdate";
	}
	
	public String confirmSub() throws Exception{
		logger.info("提交确认");
		if (contractProList == null)
			contractProList = new ArrayList<ContractItemMaininfo>();
		Employee user=UserUtils.getUser(); 
		contractProList = service.list("select i from ContractItemMaininfo i,ContractMainInfo m where " +
				"i.contractMainInfo=m.conMainInfoSid and m.saleMan = "+user.getId()+" and i.conItemCostSure in (1)");
		 if (contractProList != null && contractProList.size() > 0){
			for (ContractItemMaininfo Self:contractProList) 
			{
				if (Self.getConItemMinfoSid() != null)
				{
					ContractItemMaininfo toDel = (ContractItemMaininfo) service.load(ContractItemMaininfo.class, Self.getConItemMinfoSid());
				    toDel.setConItemCostSure(2L);
				    service.saveOrUpdate(toDel);
				}
			}
		 logger.info("提交确认->成功");
		 }
		 return SUCCESS;
	}
	
	public String inputClick() throws Exception{
		this.logger.info("合同导入");
		return "queryInputClick";
	}
	
	public String inputRightClick() throws Exception{
		this.logger.info("合同项目号导入");
		return "queryInputRightClick";
	}
	
	public ItemCostingSure getS() {
		return s;
	}

	public void setS(ItemCostingSure s) {
		this.s = s;
	}

	public String getProjectInfo2() {
		return projectInfo2;
	}

	public void setProjectInfo2(String projectInfo2) {
		this.projectInfo2 = projectInfo2;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public Double getRemainAssistance() {
		return remainAssistance;
	}

	public void setRemainAssistance(Double remainAssistance) {
		this.remainAssistance = remainAssistance;
	}

	public Double getRemainBill() {
		return remainBill;
	}

	public void setRemainBill(Double remainBill) {
		this.remainBill = remainBill;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object[] getProjectInfo1() {
		return projectInfo1;
	}

	public void setProjectInfo1(Object[] projectInfo1) {
		this.projectInfo1 = projectInfo1;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;		
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public List<ContractItemMaininfo> getContractProList() {
		return contractProList;
	}

	public void setContractProList(List<ContractItemMaininfo> contractProList) {
		this.contractProList = contractProList;
	}

	
}

