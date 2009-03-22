package com.baoz.yx.action.billtoReceipt;

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
import com.baoz.yx.entity.bill.RelationBillAndReceipt;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IApplyBillService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.tools.TaxChange;
import com.opensymphony.xwork2.ActionContext;

@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp"),
	@Result(name = "relationBill", value = "/commons/jsp/closeAndCallBackOpener.jsp")
})
public class ApplyBillAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 										commonService;
	@Autowired
	@Qualifier("applyBillService")
	private IApplyBillService 			billService;
	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService 			contractCommonService;
	private Long[] 												realPanid;
	private Double[] 											realBillAmount;
	private String[]											billContent;
	private Long[]												base;
	private String[]											stockOrgList;
	private List<RealContractBillandRecePlan>					realConList;
	
	private String												succSave;  //表示保存成功
	
	private String 												billContentList;
	
	private String[]												receipt;
	
	private boolean[] 											oneOut;  //是否一次出库
	
	
	private String[] remark;                // 备注
	
	private String[] billSpot;    //取票地点
	
	private String[] firstReceiveMan;  //甲方接收人
	
	private ApplyBill bill;
	
	private List<RelationBillAndReceipt> billAndReceiptList;
	
	private List<ApplyBill> applyBillList;
	
	public String getSuccSave() {
		return succSave;
	}
	public void setSuccSave(String succSave) {
		this.succSave = succSave;
	}
	@Override
	public String doDefault() throws Exception {
		logger.info("保存");
		logger.info(StringUtils.join(realPanid,","));   
		logger.info(StringUtils.join(realBillAmount,","));
		logger.info(StringUtils.join(billContent,","));
		logger.info(StringUtils.join(stockOrgList,","));
		for(int i=0;i<realPanid.length;i++){
		    ApplyBill bill = applyBillList.get(i);
			commonService.save(bill);
			RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realPanid[i]);
			ContractMainInfo mainCon = (ContractMainInfo) commonService.load(ContractMainInfo.class, plan.getConMainInfoSid());
			logger.info("合同主体信息系统号是+++  "+plan.getConMainInfoSid());
			logger.info("开票金额是+++  "+realBillAmount[i]);
			logger.info("开票内容是+++  "+billContent[i]);
			
			bill.setContractMainInfo(plan.getConMainInfoSid());   //主体合同信息系统号
			bill.setBillContent(billContent[i]);             	  //开票内容
//			bill.setApplyId(new Date());   
			
			bill.setBillType(plan.getBillType());				  //开票类型
			bill.setBase(1L);							      //是否含税（ 0-不含税；1-含税；）........... PS：合同新建时，#@java.util.TreeMap@{1:'含税',2:'不含税'}
			bill.setEmployeeId(mainCon.getSaleMan());							  
			bill.setIsNoContract(false);  // 已签开票申请....false
			bill.setSign(Boolean.FALSE);
			bill.setInitIsNoContract(0L);   //
			bill.setApplyBillState(1L);
			bill.setCustomerId(mainCon.getConCustomer());
			bill.setBillCustomer(mainCon.getBillCustomer());
			bill.setContactName(mainCon.getConName());
			bill.setBillNature(plan.getBillNature());			  //开票性质
			bill.setRealPlanId(plan.getRealConBillproSid());
			
			bill.setApplyWay(1L);    //申请入口：  合同已签开票申请
			
			bill.setBillAmountTax(plan.getRealTaxBillAmount().doubleValue());
			Double noTaxMoney = TaxChange.TaxToNoTaxDouble(plan.getRealTaxBillAmount().doubleValue(),plan.getBillType());
			bill.setBillAmountNotax(noTaxMoney);
			
			bill.setStockOrg(stockOrgList[i]);
			commonService.update(bill);
			logger.info("bill系统号是:"+bill.getBillApplyId());
			// 保存到关联表 BillandProRelaion 
			BillandProRelaion relation = new BillandProRelaion();
			relation.setApplyBill(bill.getBillApplyId());
			relation.setRealContractBillandRecePlan(plan.getRealConBillproSid());
			
			relation.setRelateAmount(Double.valueOf(plan.getRealTaxBillAmount().toString()));
			//relation.setRelateAmount(realBillAmount[i]);
			commonService.save(relation);
			//设置统一开票客户
			plan.setUniteBill(Boolean.TRUE);
			plan.setApplyBillState(1L);
			commonService.update(plan);
			// 设置保存成功标记
			succSave="0";
			ActionContext.getContext().getSession().put("succSave", succSave);
		}
		return SUCCESS;
	}
	/**
	 * 合并统一开票
	 * @return
	 */
	public String together()
	{
		logger.info("合并开票");
		double sumAmount = 0;
		ApplyBill bill = new ApplyBill();
		for(int i=0;i<realPanid.length;i++){
			logger.info("开票金额是+++  "+realBillAmount[i]);
			sumAmount += realBillAmount[i];
			logger.info("总金额是"+sumAmount);
		}
		RealContractBillandRecePlan plan = (RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realPanid[0]);
		ContractMainInfo mainCon = (ContractMainInfo) commonService.load(ContractMainInfo.class, plan.getConMainInfoSid());
		logger.info("合同主体信息系统号是+++  "+plan.getConMainInfoSid());
		bill.setContractMainInfo(plan.getConMainInfoSid());   //主体合同信息系统号
		bill.setBillContent(billContentList);             	  //开票内容
//		bill.setApplyId(new Date());   
		
		bill.setBillType(plan.getBillType());				  //开票类型
		bill.setBase(1L);							      //是否含税（ 0-不含税；1-含税；）........... PS：合同新建时，#@java.util.TreeMap@{1:'含税',2:'不含税'}
		bill.setEmployeeId(mainCon.getSaleMan());	
		if(billSpot != null){
			bill.setBillSpot(billSpot[0]);  //取票地点                
		}
		bill.setIsNoContract(false);  // 已签开票申请....false
		bill.setSign(Boolean.FALSE);
		bill.setInitIsNoContract(0L);   //
		bill.setOneOut(oneOut[0]);   //是否一次出库
		if(remark != null){
			bill.setRemarks(remark[0]);  //备注
		}
		bill.setApplyBillState(1L);
		bill.setCustomerId(mainCon.getConCustomer());
		bill.setBillCustomer(mainCon.getBillCustomer());
		bill.setContactName(mainCon.getConName());
		bill.setBillNature(plan.getBillNature());			  //开票性质
		bill.setRealPlanId(plan.getRealConBillproSid());
		
		bill.setApplyWay(1L);      //申请入口：  合同已签开票申请
		
		Double noTaxMoney = TaxChange.TaxToNoTaxDouble(sumAmount,plan.getBillType());
		
		bill.setBillAmountTax(sumAmount);
		bill.setBillAmountNotax(noTaxMoney);
		
		if(firstReceiveMan!=null){
			bill.setFirstReceiveMan(firstReceiveMan[0]);
		}
		bill.setStockOrg(stockOrgList[0]);
		commonService.save(bill);
		// 修改bill的编号
		logger.info("bill系统号是:"+bill.getBillApplyId());
		// 保存到关联表 BillandProRelaion 
		BillandProRelaion relation = null;
		for(int i=0;i<realPanid.length;i++){
			relation = new BillandProRelaion();
			RealContractBillandRecePlan plan1 = (RealContractBillandRecePlan) commonService.load(RealContractBillandRecePlan.class, realPanid[i]);
			relation.setApplyBill(bill.getBillApplyId());
			relation.setRealContractBillandRecePlan(plan1.getRealConBillproSid());
			relation.setRelateAmount(Double.valueOf(plan1.getRealTaxBillAmount().toString()));
			//relation.setRelateAmount(sumAmount);
			commonService.save(relation);
			//设置统一开票客户
			plan1.setUniteBill(Boolean.TRUE);
			plan1.setApplyBillState(1L);
			commonService.update(plan1);
		}
		succSave="2";
		ActionContext.getContext().getSession().put("succSave", succSave);
		return SUCCESS;
	}
	
	public String relationBillRecipt(){
		for (RelationBillAndReceipt billAndReceipt : billAndReceiptList) {
			if(billAndReceipt != null && billAndReceipt.getReceiptRealId() != null && billAndReceipt.getRelationAmount() != 0.00){
				// 选中的关联
				commonService.save(billAndReceipt);
				RealContractBillandRecePlan plan = (RealContractBillandRecePlan) this.commonService.load(RealContractBillandRecePlan.class, billAndReceipt.getBillRealId()); 
				plan.setRelationReceAmount(billService.sumRelationBill(billAndReceipt.getBillRealId()));
				commonService.update(plan);
				//如果关联时已经开过票，需要更新关联和收据的应收，一般会在开票之前就关联掉，但是有部分历史数据开票之后才关联
				if(plan.getBillInvoiceAmount()!= null && plan.getBillInvoiceAmount() > 0){
					contractCommonService.updateRelationAndReceiptShouldAmount(plan.getRealConBillproSid());
				}
				RealContractBillandRecePlan plan1 = (RealContractBillandRecePlan) this.commonService.load(RealContractBillandRecePlan.class, billAndReceipt.getReceiptRealId()); 
				plan1.setRelationReceAmount(billService.sumRelationReceipt(billAndReceipt.getReceiptRealId()));
				commonService.update(plan1);
			}
		}
		return "relationBill";
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
	public String getBillContentList() {
		return billContentList;
	}
	public void setBillContentList(String billContentList) {
		this.billContentList = billContentList;
	}
	public String[] getStockOrgList() {
		return stockOrgList;
	}
	public void setStockOrgList(String[] stockOrgList) {
		this.stockOrgList = stockOrgList;
	}
	public List<RelationBillAndReceipt> getBillAndReceiptList() {
		return billAndReceiptList;
	}
	public void setBillAndReceiptList(List<RelationBillAndReceipt> billAndReceipt) {
		this.billAndReceiptList = billAndReceipt;
	}
	public IApplyBillService getBillService() {
		return billService;
	}
	public void setBillService(IApplyBillService billService) {
		this.billService = billService;
	}
	public String[] getReceipt() {
		return receipt;
	}
	public void setReceipt(String[] receipt) {
		this.receipt = receipt;
	}
	public boolean[] getOneOut() {
		return oneOut;
	}
	public void setOneOut(boolean[] oneOut) {
		this.oneOut = oneOut;
	}
	public String[] getRemark() {
		return remark;
	}
	public void setRemark(String[] remark) {
		this.remark = remark;
	}
	public String[] getBillSpot() {
		return billSpot;
	}
	public void setBillSpot(String[] billSpot) {
		this.billSpot = billSpot;
	}
	public ApplyBill getBill() {
		return bill;
	}
	public void setBill(ApplyBill bill) {
		this.bill = bill;
	}
	public List<ApplyBill> getApplyBillList() {
		return applyBillList;
	}
	public void setApplyBillList(List<ApplyBill> applyBillList) {
		this.applyBillList = applyBillList;
	}
	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}
	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}
	public String[] getFirstReceiveMan() {
		return firstReceiveMan;
	}
	public void setFirstReceiveMan(String[] firstReceiveMan) {
		this.firstReceiveMan = firstReceiveMan;
	}


}
