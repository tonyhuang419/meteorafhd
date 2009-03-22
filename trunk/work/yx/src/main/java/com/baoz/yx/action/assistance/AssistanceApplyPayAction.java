package com.baoz.yx.action.assistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;

public class AssistanceApplyPayAction extends DispatchAction{
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Autowired
	@Qualifier("assistanceService")
	private IAssistanceService assistanceService;

	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;
	
	
	private List<AssistancePayInfo> prePayInfoList;/////预付款付款申请列表
	
	private Long assistanceId;////外协合同id
	
	
	public String checkExistsPrepPay()throws Exception{
		
		AssistanceContract contract = (AssistanceContract)commonService.load(AssistanceContract.class, assistanceId);
		List<AssistancePayInfo> list = assistanceService.getAssistancepayInfoBySupplierId(contract);
		if(list !=null &&list.size() > 0){
			this.renderText("true");///////true表示存在
		}else{
			this.renderText("false");////////false表示不存在
		}
		return null;
	}
	/**
	 * 判断这个合同是否有发票
	 * @return
	 */
	public String checkIsNull(){
		int ticketSize = assistancePayService.getTicketByConId(assistanceId).size();
		if(ticketSize > 0){
			this.renderText("1");
		}else{
			this.renderText("0");
		}
		return null;
	}
	/**
	 * 判断合同发票是否添加完了
	 * @return
	 */
	public String checkIsNullBill(){
		AssistanceContract contract = (AssistanceContract)commonService.load(AssistanceContract.class, assistanceId);
		if(contract.getTicketMoney().doubleValue() == contract.getContractMoney() ){
			this.renderText("1");
		}else{
			this.renderText("0");
		}
		return null;
	}
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IAssistanceService getAssistanceService() {
		return assistanceService;
	}

	public void setAssistanceService(IAssistanceService assistanceService) {
		this.assistanceService = assistanceService;
	}


	public Long getAssistanceId() {
		return assistanceId;
	}


	public void setAssistanceId(Long assistanceId) {
		this.assistanceId = assistanceId;
	}


	public List<AssistancePayInfo> getPrePayInfoList() {
		return prePayInfoList;
	}


	public void setPrePayInfoList(List<AssistancePayInfo> prePayInfoList) {
		this.prePayInfoList = prePayInfoList;
	}

}
