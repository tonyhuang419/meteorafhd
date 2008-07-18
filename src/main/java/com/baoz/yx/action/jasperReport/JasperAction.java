package com.baoz.yx.action.jasperReport;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.OrganizationTree;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.jasperReport.ApplyBillReport;
import com.baoz.yx.entity.jasperReport.AssistanceContractReport;
import com.baoz.yx.entity.jasperReport.BillAndInvoiceInfo;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.NumberToTime;
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
		ApplyBillReport billReport = new ApplyBillReport();
		ApplyBill bill = invoiceService.findOneInvocie(String.valueOf(paramId));
		BeanUtils.copyProperties(billReport, bill);
		// 获取申请人信息
		Long employeeId = bill.getEmployeeId();
		Employee employee = (Employee) commonService.load(Employee.class,
				employeeId);
		billReport.setEmployeeName(employee.getName());
		long position = employee.getPosition();
		OrganizationTree department = (OrganizationTree) commonService.load(
				OrganizationTree.class, position);
		billReport.setEmployeeDepartment(department.getOrganizationName());
		Long customerId = bill.getBillCustomer();
		YXClientCode customer = (YXClientCode) commonService.load(
				YXClientCode.class, customerId);
		billReport.setClientName(customer.getBillName());
		billReport.setClientBillBank(customer.getBillBank());
		billReport.setClientBillAdd(customer.getBillAdd());
		billReport.setClientAccount(customer.getAccount());
		billReport.setClientTaxNumber(customer.getTaxNumber());
		billReport.setClientBillPhone(customer.getBillPhone());
		// bill.getIsNoContract==true表示未签，==false表示已签，未签申请没项目号，有合同名称,没有合同号
		if (!bill.getIsNoContract()) {// 已签操作
			// 通过计划找项目
			// 合同信息
			Long contractMainInfoId = bill.getContractMainInfo();
			ContractMainInfo contractMainInfo = (ContractMainInfo) commonService
					.load(ContractMainInfo.class, contractMainInfoId);
			billReport.setContactName(contractMainInfo.getConName());
			billReport.setConId(contractMainInfo.getConId());

			List list = commonService
					.list(
							"select  rc.contractItemMaininfo ,  "
									+ " rr.relateAmount from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab"
									+ " where rc.realConBillproSid = rr.realContractBillandRecePlan"
									+ " and  ab.billApplyId  =  rr.applyBill and ab.billApplyId  = ?",
							paramId);
			String projectNo = "";
			if (list != null && list.size() > 0) {
				for (int k = 0; k < list.size(); k++) {
					Object[] opTemp = (Object[]) list.get(k);
					Long itemId = (Long) opTemp[0];
					ContractItemMaininfo mainInfo = (ContractItemMaininfo) commonService
							.load(ContractItemMaininfo.class, itemId);
					mainInfo.getConItemId();
					projectNo += mainInfo.getConItemId();
					if (!(k == list.size() - 1)) {
						projectNo += "/";
					}
				}
			}
			if (projectNo != null && !"".equals(projectNo)) {
				billReport.setProjectNo(projectNo);
			} else {
				billReport.setProjectNo("  ");
			}
		} else {// 未签操作
			billReport.setConId(" ");
			billReport.setProjectNo("  ");

		}
		if (bill.getBillContent() == null || "".equals(bill.getBillContent())) {
			billReport.setBillContent(" ");
		}
		YXTypeManage yx = typeManageService.getYXTypeManage(1004L, bill
				.getBillType());
		billReport.setBillType(yx.getTypeName());
		YXTypeManage yxTypeManage = typeManageService.getYXTypeManage(1012L,
				bill.getBillNature());
		billReport.setBillNature(yxTypeManage.getTypeName());
		billReport.setLowerBillAmountNotax(Moneyconvert.NumToRMBStr(bill
				.getBillAmountNotax()));
		billReport.setLowerBillAmountTax(Moneyconvert.NumToRMBStr(bill
				.getBillAmountTax()));
		if (bill.getOneOut()) {
			billReport.setIsOneOut("是");
		} else {
			billReport.setIsOneOut("否");
		}
		if (bill.getRemarks() == null || "".equals(bill.getRemarks())) {
			billReport.setRemarks("    ");
		}
		myList = new ArrayList<ApplyBillReport>();
		myList.add(billReport);
		/*
		 * Here we compile our xml jasper template to a jasper file. Note: this
		 * isn't exactly considered 'good practice'. You should either use
		 * precompiled jasper files (.jasper) or provide some kind of check to
		 * make sure you're not compiling the file on every request. If you
		 * don't have to compile the report, you just setup your data source
		 * (eg. a List)
		 */
		try {
			String reportSource;
			reportSource = ServletActionContext.getServletContext()
					.getRealPath("/jasper/applyBill_jasper_template.jrxml");
			File parent = new File(reportSource).getParentFile();
			JasperCompileManager.compileReportToFile(reportSource, new File(
					parent, "compiled_jasper_template.jasper")
					.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String assistancePayFor() throws Exception {
		// create some imaginary persons
		logger.info("获取的paramId的值是：" + paramId);
		AssistanceContractReport assistanceReport = new AssistanceContractReport();
		AssistancePayInfo payInfo = (AssistancePayInfo) commonService.load(
				AssistancePayInfo.class, Long.valueOf(paramId));
		BeanUtils.copyProperties(assistanceReport, payInfo);

		Date date = payInfo.getApplyDate();
		String d = NumberToTime.getDateFormat(date, "yyyyMMdd");
		assistanceReport.setYear(d.substring(0, 4));
		assistanceReport.setMonth(d.substring(4, 6));
		assistanceReport.setDay(d.substring(6, 8));
		// 外协合同信息表
		AssistanceContract assistanceContract = (AssistanceContract) commonService
				.load(AssistanceContract.class, payInfo.getAssistanceId());

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
		String mainProejectId = assistanceContract.getMainProjectId();

		String hql = "from ContractMainInfo mainInfo where mainInfo.conId=?";
		List list = commonService.list(hql, mainProejectId);
		if (list != null && list.size() > 0) {
			ContractMainInfo mainInfo = (ContractMainInfo) list.get(0);
			assistanceReport.setMainProjectId(mainInfo.getConId());
			assistanceReport.setMainProjectName(mainInfo.getConName());
			assistanceReport.setMainItemDept(mainInfo.getMainItemDept());
		}

		// 通过外协合同查询发票类型，发票号
		List ticketList = commonService.list(
				"from AssistanceTicket ticket where ticket.contractId=?",
				assistanceContract.getId());

		String ticketNo = "";
		if (ticketList != null && ticketList.size() > 0) {
			for (int k = 0; k < ticketList.size(); k++) {
				AssistanceTicket ticket = (AssistanceTicket) ticketList.get(k);
				if (ticket.getNum() != null && !"".equals(ticket.getNum())) {
					ticketNo += ticket.getNum();
				}

				YXTypeManage manage = typeManageService.getYXTypeManage(1004L,
						ticket.getType());
				if (manage != null) {
					ticketNo += "(" + manage.getTypeName() + ")";
				}
				if (k < ticketList.size() - 1) {
					ticketNo += "、";
				}
			}

		}
		assistanceReport.setTicketNo(ticketNo);
		payForList = new ArrayList<AssistanceContractReport>();
		payForList.add(assistanceReport);
		/*
		 * Here we compile our xml jasper template to a jasper file. Note: this
		 * isn't exactly considered 'good practice'. You should either use
		 * precompiled jasper files (.jasper) or provide some kind of check to
		 * make sure you're not compiling the file on every request. If you
		 * don't have to compile the report, you just setup your data source
		 * (eg. a List)
		 */
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
		// 传过来的参数是Applybill的id
		BillAndInvoiceInfo info = new BillAndInvoiceInfo();
		ApplyBill bill = invoiceService.findOneInvocie(String.valueOf(paramId));
		// 给申请编号赋值
		info.setBillApplyNum(bill.getBillApplyNum());
		YXClientCode customer = (YXClientCode) commonService.load(
				YXClientCode.class, bill.getCustomerId());
		// 给客户名称赋值
		info.setCustomerName(customer.getName());

		// 给合同名称赋值
		info.setMainInfoName(bill.getContactName());
		// 给项目号赋值
		List list = commonService
				.list(
						"select  rc.contractItemMaininfo ,  "
								+ " rr.relateAmount from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab"
								+ " where rc.realConBillproSid = rr.realContractBillandRecePlan"
								+ " and  ab.billApplyId  =  rr.applyBill and ab.billApplyId  = ?",
						paramId);
		String projectNo = "";
		if (list != null && list.size() > 0) {
			for (int k = 0; k < list.size(); k++) {
				Object[] opTemp = (Object[]) list.get(k);
				Long itemId = (Long) opTemp[0];
				ContractItemMaininfo mainInfo = (ContractItemMaininfo) commonService
						.load(ContractItemMaininfo.class, itemId);
				mainInfo.getConItemId();
				projectNo += mainInfo.getConItemId();
				if (!(k == list.size() - 1)) {
					projectNo += "\n";
				}
			}
		}
		if (projectNo != null && !"".equals(projectNo)) {
			info.setProjectNos(projectNo);
		} else {
			info.setProjectNos("  ");
		}

		// 通过开票申请系统号查询发票信息
		List invoiceInfoList = commonService.list(
				"from InvoiceInfo info where info.applyInvoiceId=?", paramId);
		String ticketNo = "";
		String ticketMoney = "";
		String ticketType = "";
		String createTicketDate = "";
		Double tempSum = 0.0;
		if (invoiceInfoList != null && invoiceInfoList.size() > 0) {
			for (int k = 0; k < invoiceInfoList.size(); k++) {
				InvoiceInfo invoice = (InvoiceInfo) invoiceInfoList.get(k);
				ticketMoney += invoice.getInvoiceAmount();
				ticketNo += invoice.getInvoiceNo();
				tempSum += invoice.getInvoiceAmount();
				createTicketDate += NumberToTime.getDateFormat(invoice
						.getInvoiceDate(), "yyyy年MM月dd日");
				if (k != invoiceInfoList.size() - 1) {
					ticketMoney += "/";
					ticketNo += "/";
					createTicketDate += "/";
				}
			}
			ticketMoney += ",共(" + tempSum + ")元\n";
			ticketMoney += Moneyconvert.NumToRMBStr(tempSum);
			info.setTicketNo(ticketNo);
			info.setTicketMoney(ticketMoney);
			info.setTicketType(ticketType);
		} else {
			info.setTicketNo("  ");
			info.setTicketMoney("   ");
			info.setTicketType("    ");
		}
		// String imgUrl=ServletActionContext.getServletContext()
		// .getRealPath("/yx/jasper/report_clip_image002.jpg");
		// logger.info(imgUrl);
		// info.setImgUrl(StringUtils.replace(imgUrl, "\\", "\\"));
		String path = ServletActionContext.getRequest().getContextPath();
		String basePath = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+path+"/";
		
		info
				.setImgUrl(basePath+"jasper/report_clip_image002.jpg");
		logger.info(info.getImgUrl());
		billAndInvoiceList = new ArrayList<BillAndInvoiceInfo>();
		billAndInvoiceList.add(info);
		/*
		 * Here we compile our xml jasper template to a jasper file. Note: this
		 * isn't exactly considered 'good practice'. You should either use
		 * precompiled jasper files (.jasper) or provide some kind of check to
		 * make sure you're not compiling the file on every request. If you
		 * don't have to compile the report, you just setup your data source
		 * (eg. a List)
		 */
		try {
			String reportSource;
			reportSource = ServletActionContext.getServletContext()
					.getRealPath("/jasper/BillAndInvoice.jrxml");
			File parent = new File(reportSource).getParentFile();

			JasperCompileManager.compileReportToFile(reportSource, new File(
					parent, "BillAndInvoice.jasper").getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
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
}
