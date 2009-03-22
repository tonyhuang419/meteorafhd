package com.baoz.yx.service.impl;

import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IJasperReportService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.Convertnumber;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.utils.Moneyconvert;

@Service("jasperReportService")
@Transactional
public class JasperReportService implements IJasperReportService {
	private static Logger logger = Logger.getLogger(JasperReportService.class);

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao yxCommonDao;
	
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService  contractCommonService;
	
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}

	public void setYxCommonDao(IYXCommonDao yxCommonDao) {
		this.yxCommonDao = yxCommonDao;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@SuppressWarnings({ "unused", "unchecked" })
	public void createBillAndInvoicePDF(Long paramId) {
		ApplyBill bill=(ApplyBill)commonDao.load(ApplyBill.class, paramId);
		Long contractId=bill.getContractMainInfo();
		String str_conId="";
		String str_itemId="";
		if(contractId!=null){
			ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, contractId);
			str_conId+=mainInfo.getConId();
			if(StringUtils.equals(mainInfo.getContractType(),"1")){//如果是项目类就有项目号
				String itemHql="select distinct(item) from BillandProRelaion relation," +
						"RealContractBillandRecePlan plan," +
						"ContractItemMaininfo item " +
						"where relation.realContractBillandRecePlan = plan.realConBillproSid " + 
						"and item.conItemMinfoSid = plan.contractItemMaininfo " +
						"and relation.applyBill=?";
			
				List<ContractItemMaininfo> itemList=yxCommonDao.list(itemHql, bill.getBillApplyId());
				for (ContractItemMaininfo contractItemMaininfo : itemList) {
					str_itemId+=contractItemMaininfo.getConItemId()+",";
				}
				if(str_itemId.length()>1){
					str_itemId=StringUtils.substring(str_itemId, 0, str_itemId.length()-1);
				}
				
			}else{//如果是集成类则没有项目号
			}
		}
		
		str_conId+="-"+bill.getBillApplyNum();
		YXClientCode customer = (YXClientCode) commonDao.load(
		YXClientCode.class, bill.getCustomerId());
		Connection con=this.yxCommonDao.getConnection();
		Map parameters=new HashMap();
		parameters.put("billApplyNum", str_conId);
		parameters.put("billId", paramId);
		parameters.put("projectName", bill.getContactName());
		parameters.put("customerName", customer.getFullName());
		parameters.put("projectNo",str_itemId);
		parameters.put("firstReceiveMan", bill.getFirstReceiveMan());
		String path = ServletActionContext.getRequest().getContextPath();
		String basePath = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName() + ":"
				+ ServletActionContext.getRequest().getServerPort() + path
				+ "/";
		parameters.put("imgUrl",basePath + "jasper/report_clip_image002.jpg");
		try {
			String jrxmlSource;
			jrxmlSource = ServletActionContext.getServletContext()
					.getRealPath("/jasper/BillAndInvoice.jrxml");
			File parent = new File(jrxmlSource).getParentFile();
			JasperCompileManager.compileReportToFile(jrxmlSource, new File(
					parent, "BillAndInvoice.jasper").getAbsolutePath());
			String jasperSource= ServletActionContext.getServletContext()
			.getRealPath("/jasper/BillAndInvoice.jasper");
			JasperPrint print = JasperFillManager.fillReport(jasperSource, parameters, con);
			byte[] outPut = JasperExportManager.exportReportToPdf(print);
			/////////////
			HttpServletResponse oResponse = ServletActionContext.getResponse();
			OutputStream resOutput = oResponse.getOutputStream();
			 // Set the content type
			oResponse.setContentType("application/pdf");
			resOutput.write(outPut);
			resOutput.flush();
			resOutput.close();
			// ////////////
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}finally
		{
			try {
				if(con!=null&&!con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(),e);
			}
		}

	}
	@SuppressWarnings("unchecked")
	public void createApplyBillPDF(Long pid,boolean isDownLoad){
		ApplyBill bill=(ApplyBill)commonDao.load(ApplyBill.class, pid);
		Map parameters=new HashMap();
		parameters.put("applyBillId", pid);
		parameters.put("billApplyNum", bill.getBillApplyNum());
		parameters.put("applyId", bill.getApplyId());
		Long employeeId = bill.getEmployeeId();
		Employee emp=(Employee)commonDao.load(Employee.class, employeeId);
		String applyDept = userService.getDepartment(emp).getOrganizationName();
		parameters.put("employeeName", emp.getName());
		parameters.put("employeeDepartment", applyDept);
		Long customerId = bill.getBillCustomer();
		YXClientCode customer = (YXClientCode) commonDao.load(
				YXClientCode.class, customerId);
		parameters.put("clientName", customer.getBillName());
		parameters.put("clientBillBank", customer.getBillBank());
		parameters.put("clientBillAdd", customer.getBillAdd());
		parameters.put("clientAccount", customer.getAccount());
		parameters.put("clientTaxNumber", customer.getTaxNumber());
		parameters.put("clientBillPhone", customer.getBillPhone());
		parameters.put("firstReceiveMan", bill.getFirstReceiveMan());
		Long contractMainInfoId = bill.getContractMainInfo();
		ContractMainInfo contract=null;
		if(bill.getInitIsNoContract().equals(0L)){//已签操作
			contract=(ContractMainInfo)commonDao.load(ContractMainInfo.class, contractMainInfoId);
			parameters.put("contactName", contract.getConName());
		}
		parameters.put("contactName",bill.getContactName());
		parameters.put("billContent", bill.getBillContent());
		parameters.put("billAmountNotax", bill.getBillAmountNotax());
		parameters.put("billAmountTax", bill.getBillAmountTax());
		Convertnumber cnum = new Convertnumber();
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		parameters.put("lowerBillAmountNotax", cnum.convertNumber(decimalFormat.format(bill.getBillAmountNotax())));
		parameters.put("lowerBillAmountTax", cnum.convertNumber(decimalFormat.format(bill.getBillAmountTax())));
		YXTypeManage yx = typeManageService.getYXTypeManage(1004L, bill
				.getBillType());
		parameters.put("billType", yx.getTypeName());
		if (bill.getOneOut()) {
			parameters.put("isOneOut", "是");
		} else {
			parameters.put("isOneOut", "否");
		}
		parameters.put("billSpot", bill.getBillSpot());
		YXTypeManage org = typeManageService.getYXTypeManage(1021L, bill.getStockOrg());
		if(org!=null){
			parameters.put("stockOrg",org.getTypeName());
		}
		List<RealContractBillandRecePlan> realList = contractCommonService.showReceInfo(bill.getBillApplyId());
		String remarks ="";
		if(bill.getRemarks()!=null){
			remarks += bill.getRemarks()+ "     ";
		}
		if(realList!=null){
			for (RealContractBillandRecePlan plan : realList) {
				if(plan.getBillInvoiceAmount()!= null){
					remarks+="收据金额:"+plan.getBillInvoiceAmount().doubleValue()+"|";
				}
				if(plan.getRealNowBillDate()!= null){
					remarks+="开据日期:"+NumberToTime.getDateFormat(plan.getRealNowBillDate(), "yyyy-MM-dd")+"|";
				}
				if(plan.getRealArriveAmount()!= null){
					remarks+="收款金额:"+plan.getRealArriveAmount().doubleValue()+"|";
				}
				if(plan.getRealNowReceDate() != null){
					remarks+="收款日期:"+NumberToTime.getDateFormat(plan.getRealNowReceDate(), "yyyy-MM-dd")+"|";
				}
			}
		}
		
		parameters.put("remarks",remarks );
		Connection con=yxCommonDao.getConnection();
		String fileName="";
		if(bill.getApplyId()!=null){
			fileName+=NumberToTime.getDateFormat(bill.getApplyId(), "yyyyMMdd");
		}
		fileName+=emp.getName();
		if(contract!=null){
			fileName+=contract.getConId();
		}
		if(bill.getBillApplyNum()!=null){
			fileName+=bill.getBillApplyNum();
		}
		try {
	
			String jrxmlSource;
			jrxmlSource = ServletActionContext.getServletContext()
					.getRealPath("/jasper/applyBill_jasper_template.jrxml");
			File parent = new File(jrxmlSource).getParentFile();
			File jasperFile=new File(
					parent, "compiled_jasper_template.jasper");
			if(jasperFile.exists()){
				jasperFile.delete();
			}
			JasperCompileManager.compileReportToFile(jrxmlSource, jasperFile.getAbsolutePath());
			String jasperSource= ServletActionContext.getServletContext()
			.getRealPath("/jasper/compiled_jasper_template.jasper");
			JasperPrint print = JasperFillManager.fillReport(jasperSource, parameters, con);
//			JasperExportManager.exportReportToPdfFile(print, "test.pdf");
			//byte[] outPut = JasperExportManager.exportReportToPdf(print);
			byte[] outPut=JasperRunManager.runReportToPdf(jasperSource, parameters, con);
			/////////////
			HttpServletResponse oResponse = ServletActionContext.getResponse();
			OutputStream resOutput = oResponse.getOutputStream();
			 // Set the content type
			oResponse.setContentType("application/pdf");
			if(isDownLoad){
				oResponse.addHeader("Content-disposition", "attachment;filename="+new String((fileName+".pdf").getBytes("GBK"),"iso-8859-1"));
			}else{
				oResponse.addHeader("Content-disposition", "online;filename="+new String((fileName+".pdf").getBytes("GBK"),"iso-8859-1"));
			}
			resOutput.write(outPut);
			resOutput.flush();
			resOutput.close();
			 ////////////
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}finally
		{
			try {
				if(con!=null&&!con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(),e);
			}
		}
	}
	
	
	public void createAssistancePayInfoPDF(Long paramId){
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, paramId);
		AssistanceContract contract = assistanceService.getContactByPayInfoId(payInfo.getId());
		ContractMainInfo contractMain = (ContractMainInfo)commonDao.load(ContractMainInfo.class, contract.getContractId());
		ContractItemMaininfo itemMain = (ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, contract.getConItemMainInfoSid());
		Map<String, Object> parameters = new HashMap<String, Object>();
		Date date = payInfo.getApplyDate();
		String d = NumberToTime.getDateFormat(date, "yyyyMMdd");
		parameters.put("year", d.substring(0, 4));
		parameters.put("month", d.substring(4, 6));
		parameters.put("day", d.substring(6, 8));
		SupplierInfo supply = assistanceService.getSupplierByAssistanceContractId(contract.getId());
		parameters.put("supplierName", supply.getSupplierName());
		parameters.put("supplierCode", supply.getSupplierCode());
		parameters.put("assistanceInfoId", contract.getAssistanceId());
		parameters.put("billBank", supply.getBillBank());
		parameters.put("billAccount", supply.getBillAccount());
		parameters.put("mainProjectId", itemMain.getConItemId());
		parameters.put("mainProjectName", contractMain.getConName());
		YXTypeManage manage = typeManageService.getYXTypeManage(1018L,
				itemMain.getItemResDept());
		parameters.put("mainItemDept", manage.getTypeName());
		parameters.put("assignmentId", payInfo.getAssignmentId());
		parameters.put("receivNum", payInfo.getReceivNum());
		parameters.put("info", payInfo.getInfo());
		Employee emp = (Employee)commonDao.load(Employee.class, payInfo.getEmployeeId());
		parameters.put("applyDept", userService.getDepartment(emp).getOrganizationName());
		parameters.put("payNum", payInfo.getPayNum());
		parameters.put("UpPayNum", Moneyconvert.NumToRMBStr(payInfo
				.getPayNum()));
		parameters.put("payInfoId", payInfo.getId());
		if(payInfo.getApplyPay()!=null &&payInfo.getApplyPay()){
			parameters.put("isApplyPay", "预付款");
		}else{
			parameters.put("isApplyPay", "外协付款");
		}
		Connection con = yxCommonDao.getConnection();
		try {
			String jrxmlSource;
			jrxmlSource = ServletActionContext.getServletContext()
					.getRealPath("/jasper/AssistanceContract.jrxml");
			File parent = new File(jrxmlSource).getParentFile();
			JasperCompileManager.compileReportToFile(jrxmlSource, new File(
					parent, "AssistanceContract.jasper").getAbsolutePath());
			String jasperSource= ServletActionContext.getServletContext()
			.getRealPath("/jasper/AssistanceContract.jasper");
			JasperPrint print = JasperFillManager.fillReport(jasperSource, parameters, con);
			byte[] outPut = JasperExportManager.exportReportToPdf(print);
			/////////////
			HttpServletResponse oResponse = ServletActionContext.getResponse();
			OutputStream resOutput = oResponse.getOutputStream();
			 // Set the content type
			oResponse.setContentType("application/pdf");
			resOutput.write(outPut);
			resOutput.flush();
			resOutput.close();
			// ////////////
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}finally
		{
			try {
				if(con!=null&&!con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				logger.info(e.getMessage(),e);
			}
		}
	}
	
	
}
