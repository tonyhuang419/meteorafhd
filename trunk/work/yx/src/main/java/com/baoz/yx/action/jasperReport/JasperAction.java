package com.baoz.yx.action.jasperReport;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.jasperReport.ApplyBillReport;
import com.baoz.yx.entity.jasperReport.AssistanceContractReport;
import com.baoz.yx.entity.jasperReport.BillAndInvoiceInfo;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IJasperReportService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.Moneyconvert;

public class JasperAction extends DispatchAction {
	private static final long serialVersionUID = 1L;

	private Long paramId;

	private List<ApplyBillReport> myList;

	private List<AssistanceContractReport> payForList;

	private List<BillAndInvoiceInfo> billAndInvoiceList;

	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	@Autowired
	@Qualifier("systemService")
	private ISystemService systemservice;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService applyBillService;

	@Autowired
	@Qualifier("jasperReportService")
	private IJasperReportService jasperReportService;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IContractService getContractservice() {
		return contractservice;
	}

	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}

	public ISystemService getSystemservice() {
		return systemservice;
	}

	public void setSystemservice(ISystemService systemservice) {
		this.systemservice = systemservice;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IApplyBillService getApplyBillService() {
		return applyBillService;
	}

	public void setApplyBillService(IApplyBillService applyBillService) {
		this.applyBillService = applyBillService;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public void setMyList(List<ApplyBillReport> myList) {
		this.myList = myList;
	}

	public List<ApplyBillReport> getMyList() {
		return myList;
	}

	public List<AssistanceContractReport> getPayForList() {
		return payForList;
	}

	public void setPayForList(List<AssistanceContractReport> payForList) {
		this.payForList = payForList;
	}

	public String applyBillJaspser() throws Exception {
		// create some imaginary persons
		logger.info("获取的paramId的值是：" + paramId);
		jasperReportService.createApplyBillPDF(paramId,false);
		return null;
	}
	public String downLoadApplyBill()throws Exception{
			// create some imaginary persons
			logger.info("获取的paramId的值是：" + paramId);
			jasperReportService.createApplyBillPDF(paramId,true);
			return null;
	}

	private String getItemNum(String projectNo) {
		List list = commonService
		.list(
				"select  rc.contractItemMaininfo ,  "
				+ " rr.relateAmount from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab"
				+ " where rc.realConBillproSid = rr.realContractBillandRecePlan"
				+ " and  ab.billApplyId  =  rr.applyBill and ab.billApplyId  = ?",
				paramId);

		if (list != null && list.size() > 0) {
			for (int k = 0; k < list.size(); k++) {
				Object[] opTemp = (Object[]) list.get(k);
				Long itemId = (Long) opTemp[0];
				ContractItemMaininfo mainInfo = (ContractItemMaininfo) commonService
				.load(ContractItemMaininfo.class, itemId);
				if (mainInfo.getConItemId() != null) {
					projectNo += mainInfo.getConItemId();
				} else {
					projectNo += " ";
				}

				if (!(k == list.size() - 1)) {
					projectNo += "/";
				}
			}
		}
		return projectNo;
	}

	public String assistancePayFor() throws Exception {
		
		//jasperReportService.createAssistancePayInfoPDF(paramId);
		//return null;
		logger.info("获取的paramId的值是：" + paramId);
		AssistanceContractReport assistanceReport = new AssistanceContractReport();
		AssistancePayInfo payInfo = (AssistancePayInfo) commonService.load(
				AssistancePayInfo.class, Long.valueOf(paramId));
		BeanUtils.copyProperties(assistanceReport, payInfo);
		Employee emp = (Employee)commonService.load(Employee.class, payInfo.getEmployeeId());
		String applyDept = userService.getDepartment(emp).getOrganizationName();
		assistanceReport.setApplyDept(applyDept);
		if(payInfo.getApplyPay()!=null &&payInfo.getApplyPay()){
			assistanceReport.setIsApplyPay("预付款");
		}else{
			assistanceReport.setIsApplyPay("外协付款");
		}
		Date date = payInfo.getApplyDate();
		String d = NumberToTime.getDateFormat(date, "yyyyMMdd");
		assistanceReport.setYear(d.substring(0, 4));
		assistanceReport.setMonth(d.substring(4, 6));
		assistanceReport.setDay(d.substring(6, 8));
		// 外协合同信息表
		AssistanceContract assistanceContract = (AssistanceContract) commonService
		.load(AssistanceContract.class, payInfo.getAssistanceId());
		assistanceReport.setMainProjectName(assistanceContract
				.getAssistanceName());
		// 通过外协合同信息查询供应商信息
		SupplierInfo supply = (SupplierInfo) commonService.load(
				SupplierInfo.class, assistanceContract.getSupplyId());
		assistanceReport.setSupplierName(supply.getSupplierName());
		assistanceReport.setSupplierCode(supply.getSupplierCode());
		assistanceReport.setAssistanceInfoId(assistanceContract
				.getAssistanceId());
		assistanceReport.setBillBank(supply.getBillBank());
		assistanceReport.setBillAccount(supply.getBillAccount());
		assistanceReport.setUpPayNum(Moneyconvert.NumToRMBStr(payInfo
				.getPayNum()));

		// 通过外协合同项目名称，项目编号，成本中心
		String mainProjectId = assistanceContract.getMainProjectId();
		assistanceReport.setMainProjectId(mainProjectId);
		assistanceReport.setMainProjectName(assistanceContract
				.getAssistanceName());
		
		ContractItemMaininfo mainInfo = (ContractItemMaininfo)commonService.load(ContractItemMaininfo.class, assistanceContract.getConItemMainInfoSid());
		YXTypeManage manage = typeManageService.getYXTypeManage(1018L,
					mainInfo.getItemResDept());
		assistanceReport.setMainItemDept(manage.getTypeName());
		

		// 通过外协合同查询发票类型，发票号
		String atHql = "select a from Assistance a, AssistancePayInfo pi, AssistanceTicket at where a.assistancePayId=pi.id and a.ticket=at.id and pi.id = "
			+ paramId;
		List<Assistance> ticketList = commonService.list(atHql);
		String ticketNo = "";
		if (ticketList != null && ticketList.size() > 0) {
			for (Assistance assistance : ticketList) {
				AssistanceTicket ticket = assistance.getTicket();
				if (ticket.getNum() != null && !"".equals(ticket.getNum())) {
					ticketNo += StringUtils.rightPad(ticket.getNum(), 10, "_");
				}
				YXTypeManage tm = typeManageService.getYXTypeManage(1004L,
						ticket.getType());
				if (tm != null) {
					ticketNo +=tm.getTypeName()+ StringUtils.rightPad("", 20-tm.getTypeName().length()*2,"_");
				}
				ticketNo += BigDecimalUtils.doubleToString(ticket.getMoney())+"元";
				ticketNo += "\n";
				//ticketNo += "\n";
			}
		}
		System.out.println(ticketNo);
	
		assistanceReport.setTicketNo(ticketNo);
		payForList = new ArrayList<AssistanceContractReport>();
		payForList.add(assistanceReport);
		try {
			String reportSource;
			reportSource = ServletActionContext.getServletContext()
			.getRealPath("/jasper/AssistanceContract.jrxml");
			File parent = new File(reportSource).getParentFile();
			JasperCompileManager.compileReportToFile(reportSource, new File(
					parent, "AssistanceContract.jasper").getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
		
	}

	public String BillAndInvoice() throws Exception {
		// create some imaginary persons
		logger.info("获取的paramId的值是：" + paramId);
		jasperReportService.createBillAndInvoicePDF(paramId);
		return null;
	}

	public List<BillAndInvoiceInfo> getBillAndInvoiceList() {
		return billAndInvoiceList;
	}

	public void setBillAndInvoiceList(
			List<BillAndInvoiceInfo> billAndInvoiceList) {
		this.billAndInvoiceList = billAndInvoiceList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public IJasperReportService getJasperReportService() {
		return jasperReportService;
	}

	public void setJasperReportService(IJasperReportService jasperReportService) {
		this.jasperReportService = jasperReportService;
	}

}
