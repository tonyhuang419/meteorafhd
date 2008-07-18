package com.baoz.yx.action.billtoReceipt;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/splitBillAmount.jsp"),
	@Result(name = "showSplit", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
})
public class SplitBillAmountQueryAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 						service;
	
	private Long								realPlanId;
	private List<Object>						realAmountList;
	private Double								splitAmount;
	


	@Override
	public String doDefault() throws Exception {
		logger.info("显示拆分金额");
		logger.info(realPlanId);
		realAmountList=service.list(" select r.realConBillproSid,r.realBillAmount from RealContractBillandRecePlan r where r.realConBillproSid ="+realPlanId);
		return SUCCESS;
	}
	
	public String splitAmount()
	{
		logger.info("拆分保存");
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) service.load(RealContractBillandRecePlan.class, realPlanId);
		try {
			RealContractBillandRecePlan realPlan= (RealContractBillandRecePlan) BeanUtils.cloneBean(plan);
			realPlan.setRealConBillproSid(null);
			realPlan.setRealBillAmount(new BigDecimal(splitAmount));
			service.save(realPlan);
			plan.setRealBillAmount(plan.getRealBillAmount().subtract(realPlan.getRealBillAmount()));
			
			service.update(plan);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "showSplit";
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
	
}
