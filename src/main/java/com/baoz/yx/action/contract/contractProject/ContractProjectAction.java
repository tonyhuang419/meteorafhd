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
 * 合同项目成本确认操作
 * 
 * @author 
 */
@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/contract/contractProject/contractProjectQuery.action" ),
	@Result(name = "queryList", value = "/WEB-INF/jsp/contract/contractProject/contractProjectConfirm.jsp"),
	@Result(name = "queryView", value = "/WEB-INF/jsp/contract/contractProject/contractProjectView.jsp"),
	@Result(name = "queryRollback", value = "/WEB-INF/jsp/contract/contractProject/contractProjectRollback.jsp"),
	@Result(name = "addOrUpdate", value = "/WEB-INF/jsp/contract/contractProject/addOrUpdateResult.jsp")
})

public class ContractProjectAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired 
	@Qualifier("commonService")
	private ICommonService service;
	
	private PageInfo info;
	private ServletRequest request;
	private Object[] projectInfo1;
	private List<ContractItemMaininfo> contractProList;
	
    private ItemCostingSure s;
	private String confirmOrAll;
	private String rollbackRemark;
	private Long id;
	
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"ContractProjectInputParameter",this,new String[]{"confirmOrAll"});

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

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
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer str = new StringBuffer();
		str.append(" select i,m,e,t.typeName from ContractItemMaininfo i , ContractMainInfo m ,Employee e,YXTypeManage t where " +
				" i.contractMainInfo=m.conMainInfoSid and i.itemResDept=t.typeSmall and t.typeBig=1018 and m.saleMan = "+user.getId()+" and m.saleMan = e.id  " );
		//0/全部；1/未确认
		if (StringUtils.equals(confirmOrAll, "0")){
			str.append(" and i.conItemCostSure in (2,3,4)");
		}
		if (StringUtils.equals(confirmOrAll, "1")){
			str.append(" and i.conItemCostSure in (2)");
		}
		str.append(" order by m.conId ,i.conItemCostSure");	
        info = queryService.listQueryResult(str.toString(), info);
		return "queryList";
//		+user.getId()
	}

	public String enterView() throws Exception{
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		projectInfo1 = (Object[])service.uniqueResult("select i,m.conId from ContractItemMaininfo i , ContractMainInfo m " +
				" where i.contractMainInfo=m.conMainInfoSid and i.conItemMinfoSid = '" + id + "'");	
		info = new PageInfo();
		info.setResult(service.list("select s from ContractItemMaininfo i ,ItemCostingSure s " +
				"where i.conItemMinfoSid=s.contractItemMaininfo and i.conItemMinfoSid = '" + id + "'"));
		return "queryView";
	}
	
	public String rollback() throws Exception{
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		projectInfo1 = (Object[])service.uniqueResult("select i,m.conId from ContractItemMaininfo i , ContractMainInfo m " +
				" where i.contractMainInfo=m.conMainInfoSid and i.conItemMinfoSid = '" + id + "'");
		return "queryRollback";
	}
	
	public String rollbackSubmit() throws Exception{
        ItemCostingSure ss=new ItemCostingSure();
		ss.setContractItemMaininfo(id);
        ss.setFeedbackInfo(rollbackRemark);
        service.save(ss);	
        ContractItemMaininfo i = (ContractItemMaininfo) service
		.uniqueResult("from ContractItemMaininfo obj where obj.conItemMinfoSid='" + id + "'");
        i.setConItemCostSure(4L);
        service.update(i);
		return "addOrUpdate";
	}
	
	public String confirm() throws Exception{
		logger.info("确认通过");	
		 if (contractProList != null && contractProList.size() > 0){
			for (ContractItemMaininfo Self:contractProList) 
			{
				if (Self.getConItemMinfoSid() != null)
				{
					ContractItemMaininfo toDel = (ContractItemMaininfo) service.load(ContractItemMaininfo.class, Self.getConItemMinfoSid());
				    toDel.setConItemCostSure(3L);
				    service.saveOrUpdate(toDel);
				}
			}
		 logger.info("确认通过->成功");
		 }
		 return SUCCESS;
	}
	
	public String recall() throws Exception{
		logger.info("取消确认");	
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
		 logger.info("取消确认->成功");
		 }
		 return SUCCESS;
	}
	
	public void setIds(Long[] ids) {
		if (contractProList == null)
			contractProList = new ArrayList<ContractItemMaininfo>();
		for (Long id : ids) {
			ContractItemMaininfo temp = new ContractItemMaininfo();
			temp.setConItemMinfoSid(id);
			contractProList.add(temp);
		}
	}
	
	public ItemCostingSure getS() {
		return s;
	}

	public void setS(ItemCostingSure s) {
		this.s = s;
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

	public String getRollbackRemark() {
		return rollbackRemark;
	}

	public void setRollbackRemark(String rollbackRemark) {
		this.rollbackRemark = rollbackRemark;
	}

	public List<ContractItemMaininfo> getContractProList() {
		return contractProList;
	}

	public void setContractProList(List<ContractItemMaininfo> contractProList) {
		this.contractProList = contractProList;
	}

	
}

