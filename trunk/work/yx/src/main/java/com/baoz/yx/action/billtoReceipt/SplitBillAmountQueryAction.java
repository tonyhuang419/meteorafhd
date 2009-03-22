package com.baoz.yx.action.billtoReceipt;

import java.util.List;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/splitBillAmount.jsp"),
	@Result(name = "showSplit", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
})
public class SplitBillAmountQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 						service;
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 					billService;
	
	private Long								realPlanId;
	private List<Object>						realAmountList;
	private Double								splitAmount;
	private Double 								splitReceAmount;
	private String 								splitSelect;


	@Override
	public String doDefault() throws Exception {
		logger.info("显示拆分金额");
		logger.info(realPlanId);
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) service.load(RealContractBillandRecePlan.class, realPlanId);
		if(plan.getRelationReceAmount() == null || plan.getRelationReceAmount() == 0.00){
			splitSelect = "0";
			realAmountList=service.list(" from RealContractBillandRecePlan r where r.realConBillproSid ="+realPlanId);
		}
		else{
			if(plan.getBillType().equals("4")){
				List<Object> relationReceiptList = service.list(" select rr from RelationBillAndReceipt rr where rr.receiptRealId = ?", realPlanId);
				if(relationReceiptList.size() == 1){
					splitSelect = "0";
					realAmountList=service.list(" from RealContractBillandRecePlan r where r.realConBillproSid ="+realPlanId);
				}
				else{
					splitSelect = "1";
				}
			}
			else{
				splitSelect = "2";
			}
		}
		return SUCCESS;
	}
	
	public String splitAmount()
	{
		logger.info("拆分保存");
		billService.splitBillAmount(realPlanId, splitAmount, splitReceAmount);
		return "showSplit";
	}

	public void isRelation(){
		if(billService.isRelationReceipt(realPlanId)){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
	}
	
	public Long getRealPlanId() {
		return realPlanId;
	}

	public void setRealPlanId(Long realPlanId) {
		this.realPlanId = realPlanId;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<Object> getRealAmountList() {
		return realAmountList;
	}

	public void setRealAmountList(List<Object> realAmountList) {
		this.realAmountList = realAmountList;
	}

	public Double getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(Double splitAmount) {
		this.splitAmount = splitAmount;
	}

	public Double getSplitReceAmount() {
		return splitReceAmount;
	}

	public void setSplitReceAmount(Double splitReceAmount) {
		this.splitReceAmount = splitReceAmount;
	}
	public String getSplitSelect() {
		return splitSelect;
	}

	public void setSplitSelect(String splitSelect) {
		this.splitSelect = splitSelect;
	}
}
