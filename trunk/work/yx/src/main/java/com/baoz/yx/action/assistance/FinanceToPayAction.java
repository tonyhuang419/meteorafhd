package com.baoz.yx.action.assistance;

import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.tools.export.TableExport;
import com.baoz.yx.tools.export.TableExportFactory;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;
import com.baoz.yx.vo.ProcessResult;
import com.baoz.yx.vo.UserDetail;


@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/financeToPayQuery.jsp"),
	@Result(name = "showList", value = "/WEB-INF/jsp/assistance/financeToPayList.jsp")
	})
public class FinanceToPayAction extends DispatchAction{
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;
	
	private PageInfo info;
	private List<Object> 			listExp; 	// 查询显示所有的销售员
	private List<Department>		groupList;
	private String 					saleMan;   //销售员
	private String 					groupId;   //组别
	private String 					payInfoState;//付款申请状态
	private Long 					supplierid;//外协供应商id
	private String 					startDate;  //開始時間
	private String					endDate;   //結束時間
	private String 					assistanceContractNo;//外协合同号
	private String 					applyId;  //支付申请单ID
	
	private ProcessResult 			processResult; //返回结果
	private String				exportX;   //1导出
	
	private ObjectPropertySessionHoldTool  holdTool = new ObjectPropertySessionHoldTool("financeToPayParameters",this,
			new String[]{"saleMan","groupId","payInfoState","supplierid","startDate","endDate","assistanceContractNo"});
	
	@Override
	public String doDefault() throws Exception {
		listExp = commonService.list("from Employee d where d.id not in(0) and d.is_active!=0");
		groupList = UserUtils.getUserDetail().getDepartments();
		return SUCCESS;
	}
	/**
	 * 查询条件
	 * @return
	 */
	public String showQueryList(){
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer sb = new StringBuffer();
		sb.append("select pi, " +
				"(select ac.assistanceId from AssistanceContract ac where ac.id = pi.assistanceId )," +
				"(select ac.assistanceName from AssistanceContract ac where ac.id = pi.assistanceId )," +
				" si.supplierName,emp " +
				"from AssistancePayInfo pi, SupplierInfo si , Employee emp " +
				" where pi.is_active='1' " +
				"and pi.supplyId = si.supplierid " +
				"and emp.id = pi.employeeId " +
				"and pi.payState in (2,4) ");
		UserDetail user = UserUtils.getUserDetail();
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			saleMan = user.getUser().getId() + "";
		} else if (StringUtils.isBlank(groupId)) {
			// 是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if(saleMan!=null&&!"".equals(saleMan)){
			sb.append(" and emp.id = " + saleMan);
		}
		if(groupId!=null&&!"".equals(groupId)){
			sb.append(" and emp.position in (select tr.id from OrganizationTree tr where tr.organizationCode like '"
					+ groupId + "%') ");
		}
		if(StringUtils.isNotBlank(payInfoState)){
			sb.append(" and pi.payState = '"+payInfoState+"'");
		}
		if(supplierid!=null){
			sb.append(" and si.supplierid = "+supplierid+" ");
		}
		if(startDate!=null && !"".equals(startDate)){
			sb.append(" and to_date('"+startDate+"', 'yyyy-MM-dd') <= pi.applyDate  ");
		}
		if(endDate!=null && !"".equals(endDate)){
			sb.append(" and trunc(pi.applyDate) <= to_date('"+endDate+"', 'yyyy-MM-dd') ");
		}
		if(StringUtils.isNotBlank(assistanceContractNo)){
			sb.append(" and exists (select 1 from AssistanceContract ac where ac.id = pi.assistanceId and ac.assistanceId like '%"+assistanceContractNo+"%') ");
		}
		sb.append(" order by pi.payState,pi.id");
		
		if(exportX!=null && exportX.equals("1")){
			List<Object[]> objList  = commonService.list( sb.toString() );
			this.processExport(objList);
			return null;
		}
		else{
			info = ParameterUtils.preparePageInfo(info, "passApplyPageInfo");
			info = queryService.listQueryResult(SqlUtils.getCountSql(sb.toString()),sb.toString(), info);
			return "showList";
		}
	}
	
	private  void processExport(List<Object[]> oArray) {	
		TableExport export = TableExportFactory.createExcelTableExport();
		export.addTitle(new String[]{"外协合同号","申请序号","外协合同名称","外协供应商","申请日期","申请金额","销售员",
				"是否预付","状态" });
		export.setTableName("支付财务交接");
		AssistancePayInfo pi ;
		Employee e;
		String prePay = "正常付款";
		String payState="";
		for(Object[] obj:oArray){
			pi = (AssistancePayInfo)obj[0];
			e = (Employee)obj[4];
			if(pi.getApplyPay() ){
				prePay = "预付款";
			}
			if(pi.getPayState().equals("2") ){
				payState = "确认通过";
			}else if(pi.getPayState().equals("4")){
				payState = "确认处理";
			}
			export.addRow(new Object[]{ obj[1]  ,	pi.getApplyInfoCode(), obj[2]  ,	obj[3]  ,	pi.getApplyDate(),pi.getPayNum(),e.getName(),prePay,payState
			});
		}
		OutputStream os = DownloadUtils.getResponseOutput("售前合同统计.xls");
		export.export(os);
		DownloadUtils.closeResponseOutput();
		
	}
	
	/**
	 * 确认处理的操作
	 * @return
	 * @throws Exception
	 */
	public String confirmOperator()throws Exception{
		processResult = new ProcessResult();
		String[] opId = StringUtils.split(applyId,",");
		assistanceService.confirmPayInfoOperator(opId);
		processResult.addSuccessMessage("已处理成功.");
		processResult.setErrorCode(1);
		processResult.setSuccess(true);
		return showQueryList();
	}
	
	/**
	 * 取消处理的操作
	 * @return
	 * @throws Exception
	 */
	public String cancelOperator()throws Exception{
		processResult = new ProcessResult();
		String[] opId = StringUtils.split(applyId,",");
		assistanceService.cancelPayInfoOperator(opId);
		processResult.addSuccessMessage("取消处理成功.");
		processResult.setErrorCode(2);
		processResult.setSuccess(true);
		return showQueryList();
	}
	
	
	
	public PageInfo getInfo() {
		return info;
	}
	public void setInfo(PageInfo info) {
		this.info = info;
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
	public String getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}
	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}
	public String getPayInfoState() {
		return payInfoState;
	}
	public void setPayInfoState(String payInfoState) {
		this.payInfoState = payInfoState;
	}
	public Long getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(Long supplierid) {
		this.supplierid = supplierid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAssistanceContractNo() {
		return assistanceContractNo;
	}
	public void setAssistanceContractNo(String assistanceContractNo) {
		this.assistanceContractNo = assistanceContractNo;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public ProcessResult getProcessResult() {
		return processResult;
	}
	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}
	public String getExportX() {
		return exportX;
	}
	public void setExportX(String exportX) {
		this.exportX = exportX;
	}
	
	
	
}
