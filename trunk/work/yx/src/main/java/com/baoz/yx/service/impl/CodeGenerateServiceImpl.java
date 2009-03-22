/**
 * 
 */
package com.baoz.yx.service.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.ICodeGenerateService;

/**
 * @author T-08-D-121
 *
 */
@Service("codeGenerateService")
@Transactional
public class CodeGenerateServiceImpl implements ICodeGenerateService {
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao  commonDao;
	
	/** 外协
	 */
	public String generateAssistanceCode(String projectDepartmentCode,
			Date submitDate, Long clientId) {
		// W固定+ZC工程部门编码第一第三位+08年份+G0A0（营销本部）+0001四位流水号
		String hql = "from ContractItemMaininfo c where c.conItemId = '" + projectDepartmentCode+"'";
		ContractItemMaininfo c = (ContractItemMaininfo)commonDao.uniqueResult(hql);
		StringBuffer conid = new StringBuffer();
		//
		conid.append("W");
		//工程部门1、3位代码   
		conid.append(c.getItemResDept().substring(0, 1));
		conid.append(c.getItemResDept().substring(2, 3));
		//年的后两位
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(2);
		conid.append(year);
		//营销本部编码G0A0
		conid.append("G0A0");
		//四位流水号
		//获取系统同最大的外协合同号
		String sql = "select max(substring(c.assistanceId,4,10)) from AssistanceContract c where substring(c.assistanceId,4,2) = '"+year+"'";
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-4));
		}
		//获取四位流水号
		conid.append(StringUtils.leftPad(String.valueOf(a+1), 4 , "0"));		
		return conid.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.baoz.yx.service.ICodeGenerateService#generateContractCode(java.lang.Long)
	 */
	public String generateContractCode(Long clientId) {
		YXClientCode client=(YXClientCode)commonDao.load(YXClientCode.class, clientId);
		StringBuffer conid = new StringBuffer();
		conid.append("PG0");
		//获取行业市场
		conid.append(client.getBusinessAreaID());
		//添加0,备用
		conid.append("0");
		//获取但前年份
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(year.length()-2);
		conid.append(year);
		//获取系统同最大的合同号
		String sql = "select max(substring(c.conId,length(c.conId)-5,6)) from ContractMainInfo c where substring(c.conId,length(c.conId)-5,2) = '"+year+"'";
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-4));
		}
		//获取四位流水号
		conid.append(StringUtils.leftPad(String.valueOf(a+1), 4 , "0"));
		return conid.toString();	
	}

	/* (non-Javadoc)
	 * @see com.baoz.yx.service.ICodeGenerateService#generateSellbeforeCode(java.lang.Long)
	 */
	public String generateSellbeforeCode(Long clientId) {
		YXClientCode client=(YXClientCode)commonDao.load(YXClientCode.class, clientId);
		StringBuffer conid = new StringBuffer();
		// SQ + 合同号
		conid.append("SQPG0");
		//获取行业市场
		conid.append(client.getBusinessAreaID());
		//添加0,备用
		conid.append("0");
		//获取但前年份
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(year.length()-2);
		conid.append(year);
		//获取系统同最大的售前合同号
		String sql = "select max(substring(c.sellBeforeNum,length(c.sellBeforeNum)-5,6)) from ContractBeforeSell c where substring(c.sellBeforeNum,length(c.sellBeforeNum)-5,2) = '"+year+"'";
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-4));
		}
		//获取四位流水号
		conid.append(StringUtils.leftPad(String.valueOf(a+1), 4 , "0"));
		return conid.toString();
	}
	/**
	 * 未签发票申请编号
	 */
	public String generateWBillCode() {
		return generateBillWithNumber("W");
	}
	/**
	 * 已签开票申请编号
	 * 
	 */
	public String generateSBillCode() {
		return generateBillWithNumber("Q");
	}
	
	/**
	 * 发票申请编号生成规则
	 * @param number
	 * @return
	 */
	public String generateBillWithNumber(String capital) {
		StringBuffer billNum = new StringBuffer();
		billNum.append(capital);
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(year.length()-2);
		billNum.append(year);
		//获取五位流水号
		//获取系统同最大的票号
		String sql = "select max(c.billApplyNum) from ApplyBill c where c.billApplyNum like '"+billNum.toString()+"%'";
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-5));
		}
		//获取四位流水号
		billNum.append(StringUtils.leftPad(String.valueOf(a+1), 5 , "0"));
		return billNum.toString();
	}
	
	/**
	 * 红冲申请单编号
	 */
	public String generateHCBillCode() {
		return generateHongChongBillWithNumber("HC");
	}
	/**
	 * 红冲发票申请编号生成规则
	 * @param number
	 * @return
	 */
	public String generateHongChongBillWithNumber(String capital) {
		StringBuffer billNum = new StringBuffer();
		billNum.append(capital);
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(year.length()-2);
		billNum.append(year);
		//获取五位流水号
		//获取系统同最大的票号
		String sql = "select max(c.hongChongApplyBillNum) from HongChongApplyBill c where c.hongChongApplyBillNum like '"+billNum.toString()+"%'";
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-5));
		}
		//获取四位流水号
		billNum.append(StringUtils.leftPad(String.valueOf(a+1), 5 , "0"));
		return billNum.toString();
	}

	public String generateApplyCode(String applytype) {
		StringBuffer applyCode = new StringBuffer();
		applyCode.append("Y");
		if("1".equals(applytype)){
			applyCode.append("X");
		}else{
			applyCode.append("J");
		}
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH)+1);
		year=year.substring(year.length()-2);
		month=StringUtils.leftPad(String.valueOf(month), 2 , "0");
		applyCode.append(year);
		applyCode.append(month);
		//获取四位流水号
		//获取系统同最大的申购单号
		String sql = "select max(c.applyId) from ApplyMessage c where c.applyId like '"+applyCode.toString()+"%'";		
		String number = (String) commonDao.uniqueResult(sql);
		Integer a = 0;
		//生成并添加系统号
		if(number!=null){
			a = Integer.valueOf(number.substring(number.length()-4));
		}
		//获取四位流水号
		applyCode.append(StringUtils.leftPad(String.valueOf(a+1), 4 , "0"));
		return applyCode.toString();
	}

	public String assApplyInfoCode(Long assid) {
		StringBuffer applyCode = new StringBuffer();
		String sql = "select max(applyInfoCode) from AssistancePayInfo where assistanceId = "+assid+" ";
	    String number =  (String)commonDao.uniqueResult(sql);
	    Integer a = 0;
	    if(number!=null){
	    	 a = Integer.valueOf(number);
	    }
	    applyCode.append(StringUtils.leftPad(String.valueOf(a+1), 2 , "0"));
		return applyCode.toString();
	}

	public String generateInvoveNo() {
		commonDao.flushSession();
		String hql = "select max(info.invoiceNo) from InvoiceInfo info";
		String number = (String)commonDao.uniqueResult(hql);
		int a=0;
		if(number!=null){
			a=Integer.valueOf(number.substring(1));
		}
		return ("S"+StringUtils.leftPad(String.valueOf(a+1), 8 , "0"));
	}

	public Long generateBatchByImportReve() {
		commonDao.flushSession();
		String hql="select max(reve.batch) from TempImportReveInfo reve";
		Long number=(Long)commonDao.uniqueResult(hql);
		if(number!=null){
			return number+1;
		}else{
			return 1L;
		}
	}
	
	public String generateAssistanceTicketNo(){
		commonDao.flushSession();
		String hql =" select max(ticket.num) from AssistanceTicket ticket ";
		String number = (String)commonDao.uniqueResult(hql);
		int a = 0;
		if(number != null ){
			a = Integer.valueOf(number.substring(1));
		}
		return ("S"+StringUtils.leftPad(String.valueOf(a+1),8, "0"));
	}
	public String generateAssistancePayInfoAssignmentId(){
		commonDao.flushSession();
		String hql =" select max(payInfo.assignmentId) from AssistancePayInfo payInfo  where payInfo.isFromSys = 1";
		String number = (String)commonDao.uniqueResult(hql);
		int a = 0;
		if(number != null ){
			a = Integer.valueOf(number.substring(1));
		}
		return ("R"+StringUtils.leftPad(String.valueOf(a+1),6, "0"));
	}
	public String generateAssistancePayInfoReceivNum(){
		commonDao.flushSession();
		String hql =" select max(payInfo.receivNum) from AssistancePayInfo payInfo where payInfo.isFromSys = 1 ";
		String number = (String)commonDao.uniqueResult(hql);
		int a = 0;
		if(number != null ){
			a = Integer.valueOf(number.substring(1));
		}
		return ("J"+StringUtils.leftPad(String.valueOf(a+1),6, "0"));
	}
	
	
	
	
}
