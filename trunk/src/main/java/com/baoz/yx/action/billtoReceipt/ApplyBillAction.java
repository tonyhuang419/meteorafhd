package com.baoz.yx.action.billtoReceipt;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.tools.TaxChange;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp")
})
public class ApplyBillAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 										commonService;
	private Long[] 												realPanid;
	private Double[] 											realBillAmount;
	private String[]											billContent;
	private Long[]												base;
	
	private List<RealContractBillandRecePlan>					realConList;
	
	@Override
	public String doDefault() throws Exception {
		logger.info("保存");
		logger.info(StringUtils.join(realPanid,","));   
		logger.info(StringUtils.join(realBillAmount,","));
		logger.info(StringUtils.join(billContent,","));
		logger.info(StringUtils.join(base,","));
		ApplyBill bill = new ApplyBill();
		for(int i=0;i<=realPanid.length;i++){
			RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realPanid[i]);
			ContractMainInfo mainCon = (ContractMainInfo) commonService.load(ContractMainInfo.class, plan.getConMainInfoSid());
			logger.info("合同主体信息系统号是+++  "+plan.getConMainInfoSid());
			logger.info("开票金额是+++  "+realBillAmount[i]);
			logger.info("开票内容是+++  "+billContent[i]);
			
			bill.setContractMainInfo(plan.getConMainInfoSid());   //主体合同信息系统号
			bill.setBillContent(billContent[i]);             	  //开票内容
//			bill.setApplyId(plan.getRealPredBillDate());          //开票日期
			bill.setApplyId(new Date());   
			
			bill.setBillType(plan.getBillType());				  //开票类型
			bill.setBase(base[i]);							      //是否含税
			bill.setEmployeeId(mainCon.getSaleMan());							  
			bill.setBillSpot("宝山");                  
			bill.setIsNoContract(false);  // 已签开票申请....false
			bill.setSign(Boolean.TRUE);
			bill.setInitIsNoContract(false);   //
			bill.setOneOut(false);
			bill.setApplyBillState(1L);
			bill.setCustomerId(mainCon.getConCustomer());
			bill.setBillCustomer(mainCon.getBillCustomer());
			bill.setContactName(mainCon.getConName());
			bill.setBillNature(plan.getBillNature());			  //开票性质
			bill.setRealPlanId(plan.getRealConBillproSid());
			Double taxMoney = 0d;
			Double noTaxMoney = 0d;
			
			//基准不含税
			if(base[i] == 0){
				noTaxMoney  = 	realBillAmount[i];  //开票金额
				taxMoney = TaxChange.NoTaxToTaxDouble(noTaxMoney,plan.getBillNature());
			}
			//基准含税
			else if(base[i] == 1){
				taxMoney  = 	realBillAmount[i];  //开票金额
				noTaxMoney = TaxChange.NoTaxToTaxDouble(taxMoney,plan.getBillNature());
			}
			
			bill.setBillAmountTax(taxMoney);
			bill.setBillAmountNotax(noTaxMoney);
		             
			
			
			
			commonService.save(bill);
			
//			SeeBeanFields.travBean(bill);
			
			logger.info("bill系统号是:"+bill.getBillApplyId());
			commonService.executeUpdate("update ApplyBill bill set bill.billApplyNum = '" + "KPSQ"+bill.getBillApplyId().toString() + "' where bill.billApplyId = " + bill.getBillApplyId());
			
			BillandProRelaion relation = new BillandProRelaion();
			relation.setApplyBill(bill.getBillApplyId());
			relation.setRealContractBillandRecePlan(plan.getRealConBillproSid());
			relation.setRelateAmount(realBillAmount[i]);
			commonService.save(relation);
			//设置统一开票客户
			plan.setUniteBill(Boolean.TRUE);
			commonService.update(plan);
			return SUCCESS;
		}
		return SUCCESS;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public Long[] getRealPanid() {
		return realPanid;
	}
	public void setRealPanid(Long[] realPanid) {
		this.realPanid = realPanid;
	}
	public Double[] getRealBillAmount() {
		return realBillAmount;
	}
	public void setRealBillAmount(Double[] realBillAmount) {
		this.realBillAmount = realBillAmount;
	}
	public String[] getBillContent() {
		return billContent;
	}
	public void setBillContent(String[] billContent) {
		this.billContent = billContent;
	}
	public Long[] getBase() {
		return base;
	}
	public void setBase(Long[] base) {
		this.base = base;
	}
	public List<RealContractBillandRecePlan> getRealConList() {
		return realConList;
	}
	public void setRealConList(List<RealContractBillandRecePlan> realConList) {
		this.realConList = realConList;
	}
}
