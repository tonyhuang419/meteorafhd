package com.baoz.yx.action.billtoReceipt;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;

import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;

import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/**
 * 开票签收相关操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/billtoReceipt/signManageQuery.action?returnSign=1"),
		@Result(name = "success",  value = "/WEB-INF/jsp/billtoReceipt/billApplyVerifyList.jsp"),
		@Result(name = "pinfo", value = "/WEB-INF/jsp/programEconomy/projectInfo.jsp"),
		@Result(name = "showpurs", value = "/WEB-INF/jsp/purchase/showpurs.jsp"),
		@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
		@Result(name = "economyInfo", value = "/WEB-INF/jsp/programEconomy/projectEconomyInfo.jsp"),
		@Result(name = "economyView", value = "/WEB-INF/jsp/programEconomy/economyView.jsp"),
		@Result(name = "showClientsList", value = "/WEB-INF/jsp/programEconomy/showClients.jsp"),
		@Result(name = "edit", value = "/WEB-INF/jsp/programEconomy/economyEdit.jsp") })
public class SignManageAction extends DispatchAction implements ServletRequestAware{

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
	private String bId;
	private String action;
    private Long   expId; 
    private HttpServletRequest request = null;
    private List<Object> listExp; // 查询显示所有的销售员
    
    private String[]	billApplyId;   // 申请单ID
    
    private boolean Cancel;  //判断false是取消签收，true是签收
    


	public String[] getBillApplyId() {
		return billApplyId;
	}

	public void setBillApplyId(String[] billApplyId) {
		this.billApplyId = billApplyId;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public String doDefault() throws Exception {
		logger.info("=================executeHere!");

		return "";
	}

	/**
	 * 确认签收
	 * 
	 * @return
	 */
	public String verifyState() {
		logger.info(StringUtils.join(billApplyId,","));
		for(int i=0;i<billApplyId.length;i++){
			String a[] = billApplyId[i].split("/");
			Long billId = Long.valueOf(a[0]);
			ApplyBill bill = (ApplyBill) service.load(ApplyBill.class,billId);
			//判断false是取消签收，true是已签收
			if(Cancel){
				bill.setSign(Boolean.TRUE);
				bill.setApplyBillState(6L);
				invoiceService.updatePlanApplyBillState(6L, bill.getBillApplyId());
			}
			else{
				bill.setSign(Boolean.FALSE);
				InvoiceInfo ii = (InvoiceInfo) service.uniqueResult(" from InvoiceInfo ii where ii.applyInvoiceId = ?", bill.getBillApplyId());
				if(ii == null){
					bill.setApplyBillState(3L);
					invoiceService.updatePlanApplyBillState(3L, bill.getBillApplyId());
				}
				else{
					bill.setApplyBillState(5L);
					invoiceService.updatePlanApplyBillState(5L, bill.getBillApplyId());
				}
			}
			service.update(bill);
		}
		return "succ";
	}
	
	public String checkPrint()throws Exception{
		
		String hql="select count(invoice) from InvoiceInfo invoice where invoice.applyInvoiceId=?";
		Long count=(Long)service.uniqueResult(hql, expId);
		if(count.longValue()>0){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
		return null;
	}
	public ICommonService getService() {
		return service;
	}


	public void setService(ICommonService service) {
		this.service = service;
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


	
	public String getBId() {
		return bId;
	}

	public void setBId(String id) {
		bId = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		
	}

	public boolean isCancel() {
		return Cancel;
	}

	public void setCancel(boolean cancel) {
		Cancel = cancel;
	}


}
