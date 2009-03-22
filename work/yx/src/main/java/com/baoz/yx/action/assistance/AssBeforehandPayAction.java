package com.baoz.yx.action.assistance;

import java.util.Date;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
@Results({
	@Result(name = "success", value = "/WEB-INF/jsp/assistance/addBeforehandPay.jsp"),
	@Result(name = "updateSuccess", value = "/WEB-INF/jsp/billtoReceipt/updateSuccess.jsp"),
	@Result(name = "enter_save",type = ServletRedirectResult.class, value = "/assistance/assBeforehandPay.action?processResult=${processResult}")
	})
public class AssBeforehandPayAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	private AssistancePayInfo sup;  //付款申请表
	private String userName;    //销售员
	private String supplierName; //供应商名称
	private String selectVal;
	private String updateSel;
	private ProcessResult processResult;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"assBeforehandPayParameters",this,new String[]{"sup","userName","supplierName" }); 
					
	@Override
	public String doDefault() throws Exception {
		userName = UserUtils.getUser().getName();
		return SUCCESS;
	}
	/**
	 * 保存
	 * @return
	 * @throws Exception 
	 */
	
	public String savePayInfo() throws Exception {
		processResult = new ProcessResult();
		ParameterUtils.prepareParameters(holdTool);
		userName = UserUtils.getUser().getName();
		if(selectVal.equals("save")){
			saveMethod();
			processResult.addSuccessMessage("保存成功!");
			processResult.setSuccess(true);
			if(updateSel!=null && !updateSel.equals("")){
				return "updateSuccess";
			}
			else{
				return SUCCESS;
			}
		}
		else if(selectVal.equals("confirm")){
			saveMethod();
			sup.setPayState("1");
			service.update(sup);
			processResult.setSuccess(true);
			processResult.addSuccessMessage("提交成功!");
			if(updateSel!=null && !updateSel.equals("")){
				return "updateSuccess";
			}
			else{
				return "enter_save";
			}
		}
		else{
			saveMethod();
			processResult.setSuccess(true);
			processResult.addSuccessMessage("保存成功!");
			return "enter_save";
		}
	}
	
	public void saveMethod(){
		sup.setIs_active("1");
		sup.setUpdateBy(new Date());
		sup.setById(UserUtils.getUser().getId());
		sup.setApplyPay(Boolean.TRUE);
		sup.setPayState("0");
		sup.setApplyDate(new Date());
		sup.setEmployeeId(UserUtils.getUser().getId());
		service.saveOrUpdate(sup);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public AssistancePayInfo getSup() {
		return sup;
	}

	public void setSup(AssistancePayInfo sup) {
		this.sup = sup;
	}
	public String getSelectVal() {
		return selectVal;
	}
	public void setSelectVal(String selectVal) {
		this.selectVal = selectVal;
	}
	public ProcessResult getProcessResult() {
		return processResult;
	}
	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}
	public String getUpdateSel() {
		return updateSel;
	}
	public void setUpdateSel(String updateSel) {
		this.updateSel = updateSel;
	}
	
}
