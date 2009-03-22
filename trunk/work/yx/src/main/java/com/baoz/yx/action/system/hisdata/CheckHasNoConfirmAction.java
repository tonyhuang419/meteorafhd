package com.baoz.yx.action.system.hisdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.utils.UserUtils;

public class CheckHasNoConfirmAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public String checkHasNoConfirm()throws Exception{
		
		/**
		 * 导入之前先判断有没有还没有确认的记录
		 */
		String countHql="select count(*) from TempImportReveInfo tab where tab.authorId=? and tab.isActive=1";
		Long count=(Long)commonService.uniqueResult(countHql, UserUtils.getUser().getId());
		if(count>0){
			this.renderText("true");//表示有记录，有的话都给提示
		}else{
			this.renderText("false");//没有的话就不提示
		}
		return null;
	}
	
	public String checkHasSelfDueFrom ( ) throws Exception{
		
		String countHql = "select count(*)  from ImportDueFromCompare tab where tab.opPerson = ?";
		Number count=(Number)commonService.uniqueResult(countHql, UserUtils.getUser().getId());
		if(count!=null &&count.longValue() > 0){
			this.renderText("true");//表示有记录，有的话都给提示
		}else{
			this.renderText("false");//没有的话就不提示
		}
		return null;
	}
}
