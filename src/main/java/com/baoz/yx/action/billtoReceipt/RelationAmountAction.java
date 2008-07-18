package com.baoz.yx.action.billtoReceipt;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.bill.RelationAmount;
import com.baoz.yx.utils.UserUtils;


/**
 *	开票收据管理
 *
 * @author T-08-D-121
 *
 */

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/personnelManager/clientSelected.jsp"),
	@Result(name = "deleteRelation", type = ServletRedirectResult.class, value = "/billtoReceipt/receiptToBillQuery.action")})

public class RelationAmountAction extends DispatchAction implements ServletRequestAware{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 				service;
	
	private List<RelationAmount>		relations;
	private ServletRequest				request;

	private Long 						receiptId;
	
	private List<Double>				relateAmount;
	
	private Long						relationAId;
	
	private Long						cliendId;
	 
	private Long 						ids;
	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	@Override
	public String doDefault() throws Exception {
		System.out.println("qqqqqq"+request.getParameter("method"));
		return SUCCESS;
	}	
	
	public String relationSuccess()
	{
		this.logger.info("关联金额操作");	
		Long uid = UserUtils.getUser().getId();
		logger.info("sssss"+uid);
		
		logger.info("关联中invoice的值"+ids);
		logger.info("关联中ireceipt的值"+receiptId);		
		System.out.println("ccccc"+uid);
		RelationAmount rx=new RelationAmount();  
		rx.setOpId(uid);
		rx.setOpDate(new Date());
		rx.setInvoiceToInvoice(ids);
		rx.setInvoiceToReceip(receiptId);
		System.out.println("vvvv"+relateAmount); 
		for(int i=0;i<relateAmount.size();i++)
		{
			System.out.println(relateAmount.get(i));
			rx.setRelateAmount(relateAmount.get(i));
		}
		service.save(rx);
		return SUCCESS;
	}
	

	public String delRelation()
	{
		logger.info("删除关联金额信息");
		
		service.executeUpdate("delete from RelationAmount ra where ra.id="+relationAId);
		
		return "deleteRelation";
	}
	
	
	public ICommonService getService() {
		return service;
	}


	public void setService(ICommonService service) {
		this.service = service;
	}


	public List<RelationAmount> getRelations() {
		return relations;
	}


	public void setRelations(List<RelationAmount> relations) {
		this.relations = relations;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}


	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}


	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public List<Double> getRelateAmount() {
		return relateAmount;
	}

	public void setRelateAmount(List<Double> relateAmount) {
		this.relateAmount = relateAmount;
	}

	public Long getRelationAId() {
		return relationAId;
	}

	public void setRelationAId(Long relationAId) {
		this.relationAId = relationAId;
	}

	public Long getCliendId() {
		return cliendId;
	}

	public void setCliendId(Long cliendId) {
		this.cliendId = cliendId;
	}



}

