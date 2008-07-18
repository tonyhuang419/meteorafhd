package com.baoz.yx.action.contract;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.contract.BillReceChangeHistory;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.utils.UserUtils;

@Results( { @Result(name = "success", type=ServletRedirectResult.class, value = "/contract/realContractBillandRecePlan.action?monthlyBillproSids=${monthlyBillproSids}&msg=${mesaage}") })
public class RealContractHistoryAction extends DispatchAction {
	private String beforBillDate;

	private String beforReceDate;

	private String afterChangeDate;

	private String afterReceDate;

	private String changeExp;

	private String mainId;

	private Long realConBillproSid;
	private Long[]monthlyBillproSids;
	private String mesaage;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Override
	public String doDefault() throws Exception {
        logger.info("mainId============================"+mainId);
        logger.info("monthlyBillproSids========="+monthlyBillproSids);
             for(Long l:monthlyBillproSids){
             logger.info("monthlyBillproSids"+l);
        }
		RealContractBillandRecePlan rpec = (RealContractBillandRecePlan) commonService
				.load(RealContractBillandRecePlan.class, realConBillproSid);
		rpec.setRealPredBillDate(SimpleDateFormat.getDateInstance()
				.parse(afterChangeDate));	
		rpec.setRealPredReceDate(SimpleDateFormat.getDateInstance().parse(
					afterReceDate));
		BillReceChangeHistory history = new BillReceChangeHistory();
		history.setRealContractBillandRecePlan(rpec);
		if (beforBillDate != null && !beforBillDate.equals("")) {
			history.setBeforBillDate(SimpleDateFormat.getDateInstance().parse(
					beforBillDate));
		} else {
			history.setBeforBillDate(new Date());
		}
		if (beforReceDate != null && !beforReceDate.equals("")) {
			history.setBeforReceDate(SimpleDateFormat.getDateInstance().parse(
					beforReceDate));
		} else {
			history.setBeforBillDate(new Date());
		}
		if (afterChangeDate != null && !afterChangeDate.equals("")) {
			history.setAfterChangeDate(SimpleDateFormat.getDateInstance()
					.parse(afterChangeDate));
		}
		if (afterReceDate != null && !afterReceDate.equals("")) {
			history.setAfterReceDate(SimpleDateFormat.getDateInstance().parse(
					afterReceDate));
		}
		history.setChangeTime(new Date());
		history.setChangePeople(UserUtils.getUser().getId());
		history.setChangeExp(changeExp);
		
		commonService.save(history);
		commonService.update(rpec);
		
		setMesaage("success");
		logger.info(getMesaage());
		return SUCCESS;
	}

	public String getAfterChangeDate() {
		return afterChangeDate;
	}

	public void setAfterChangeDate(String afterChangeDate) {
		this.afterChangeDate = afterChangeDate;
	}

	public String getAfterReceDate() {
		return afterReceDate;
	}

	public void setAfterReceDate(String afterReceDate) {
		this.afterReceDate = afterReceDate;
	}

	public String getBeforBillDate() {
		return beforBillDate;
	}

	public void setBeforBillDate(String beforBillDate) {
		this.beforBillDate = beforBillDate;
	}

	public String getBeforReceDate() {
		return beforReceDate;
	}

	public void setBeforReceDate(String beforReceDate) {
		this.beforReceDate = beforReceDate;
	}

	public String getChangeExp() {
		return changeExp;
	}

	public void setChangeExp(String changeExp) {
		this.changeExp = changeExp;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getMesaage() {
		return mesaage;
	}

	public void setMesaage(String mesaage) {
		this.mesaage = mesaage;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getRealConBillproSid() {
		return realConBillproSid;
	}

	public void setRealConBillproSid(Long realConBillproSid) {
		this.realConBillproSid = realConBillproSid;
	}

	public Long[] getMonthlyBillproSids() {
		return monthlyBillproSids;
	}

	public void setMonthlyBillproSids(Long[] monthlyBillproSids) {
		this.monthlyBillproSids = monthlyBillproSids;
	}

}
