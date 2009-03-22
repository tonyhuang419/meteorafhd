package com.baoz.yx.action.billtoReceipt;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 开票申请确认查询操作
 * 
 */
@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/billtoReceipt/billApplyVerifyList.jsp"),
	@Result(name = "returnReason", value = "/WEB-INF/jsp/billtoReceipt/returnReason.jsp")		
 })
public class BillApplyVerifyQueryAction extends DispatchAction {
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
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	private PageInfo info;
    private HttpServletRequest request = null;
    private List<Object> listExp; 
    
    private Long expId;
	private Map<Long,List<String>> itemNoList;   //开票申请系统号，项目号
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"BillApplyVerifyQueryParameters",this,new String[]{"expId"});

	private String succSave;

	public String getSuccSave() {
		return succSave;
	}

	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String doDefault() throws Exception {
		if(ActionContext.getContext().getSession().get("succSave")!=null){
			succSave = (String)ActionContext.getContext().getSession().get("succSave");
			ActionContext.getContext().getSession().remove("succSave");
		}
		//查询默认为2待确认的：3确认通过，4确认退回
		StringBuilder hqlms = new StringBuilder();
		/////
		ParameterUtils.prepareParameters(holdTool);
		/////
		hqlms.append("select ab,e.name,(select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = ab.contractMainInfo) " +
				"from ApplyBill ab,Employee e where e.id = ab.employeeId ");
		
		if(expId != null){
			hqlms.append(" and e.id = "+expId);
		}
		hqlms.append(" and ab.applyBillState=2 order by ab.applyId DESC");
		
		String expHql = "from Employee d where  exists ( select a.employeeId from ApplyBill a where a.applyBillState = 2 " +
				" and d.id = a.employeeId )";
		listExp = service.list(expHql);
		info = ParameterUtils.preparePageInfo(info, "BillApplyVerifyQuery");
		info = queryService.listQueryResult(SqlUtils.getCountSql(hqlms.toString()),hqlms.toString(), info);
		List<Object[]> applyBillList =(List<Object[]>)info.getResult();
		if(applyBillList!=null && applyBillList.size()>0){
			itemNoList = invoiceService.getItemNumFromApplyBilll(applyBillList);
		}
		return "queryList";
	}

	/**
	 * 显示确认退回填写理由
	 * @return
	 */
	public String returnReason(){
		
		return "returnReason";
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
	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public Map<Long, List<String>> getItemNoList() {
		return itemNoList;
	}

	public void setItemNoList(Map<Long, List<String>> itemNoList) {
		this.itemNoList = itemNoList;
	}
	
}

