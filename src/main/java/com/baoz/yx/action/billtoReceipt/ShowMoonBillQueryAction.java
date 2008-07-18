package com.baoz.yx.action.billtoReceipt;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IYXTypeManageService;



/**
 *	显示月度开票计划
 *
 *  
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/moonBillProject.jsp"),
	@Result(name = "showselect", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp")})

public class ShowMoonBillQueryAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 				queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	private PageInfo					info;          
	private ServletRequest				request;
	
	private String 						yearSel;
	private String 						monthSel;
	private Map<String,String> yearMap = new TreeMap<String, String>();
	
	@Override
	public String doDefault() throws Exception {
		this.logger.info("月度开票收据管理");
		/**
		 * m  代表MonthlyBillpro 月度开票计划表                        0
		 * r  代表RealContractBillandRecePlan  实际合同开票计划表       1
		 * yc 代表YXClientCode    客户表                              2
		 * cm 代表ContractMainInfo   合同主体信息表                    3
		 *  
		 */
		logger.info(yearSel);
		logger.info(monthSel);
		
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearSel = defaultYear+"";
		int defaultMonth = calendar.get(Calendar.MONTH)+1;
		monthSel = StringUtils.leftPad((defaultMonth)+"", 2 ,"0");
		DecimalFormat d=new DecimalFormat("00");
		String month=d.format(defaultMonth);
		logger.info("月是:"+month);
		yearMap.put(defaultYear+"",defaultYear+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		
		StringBuffer hql = new StringBuffer();
		hql.append("select m,r,yc,cm from MonthlyBillpro m,RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"m.realContractBillandRecePlan = r.realConBillproSid " +
				"and cm.conCustomer = yc.id " +
				"and r.conMainInfoSid = cm.conMainInfoSid ");
		
		hql.append(" and to_char(m.billproMonth,'yyyy-mm')= '").append(defaultYear).append("-").append(month).append("'");

		hql.append(" order by m.monthlyBillproSid DESC");	
		info=queryService.listQueryResult(hql.toString(), info);
		return SUCCESS;
	}
	
	public String selMonth()
	{
		logger.info("选择月份查询显示");
		Calendar calendar = Calendar.getInstance();
		int defaultYear = calendar.get(Calendar.YEAR);
		yearMap.put((defaultYear-2)+"",(defaultYear-2)+"");
		yearMap.put((defaultYear-1)+"",(defaultYear-1)+"");
		yearMap.put(defaultYear+"",defaultYear+"");
		StringBuffer hql = new StringBuffer();
		hql.append("select m,r,yc,cm from MonthlyBillpro m,RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
				"m.realContractBillandRecePlan = r.realConBillproSid " +
				"and r.conMainInfoSid = cm.conMainInfoSid " +
				"and cm.conCustomer = yc.id " +
				"and r.realConBillproSid = cm.conMainInfoSid ");
		
		hql.append(" and to_char(m.billproMonth,'yyyy-mm')= '").append(yearSel).append("-").append(monthSel).append("'");

		hql.append(" order by m.monthlyBillproSid");	
		info=queryService.listQueryResult(hql.toString(), info);
		return SUCCESS;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public ServletRequest getRequest() {
		return request;
	}
	public void setRequest(ServletRequest request) {
		this.request = request;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;		
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
	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}
	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public String getYearSel() {
		return yearSel;
	}

	public void setYearSel(String yearSel) {
		this.yearSel = yearSel;
	}

	public String getMonthSel() {
		return monthSel;
	}

	public void setMonthSel(String monthSel) {
		this.monthSel = monthSel;
	}


	public Map<String, String> getYearMap() {
		return yearMap;
	}


	public void setYearMap(Map<String, String> yearMap) {
		this.yearMap = yearMap;
	}

}

